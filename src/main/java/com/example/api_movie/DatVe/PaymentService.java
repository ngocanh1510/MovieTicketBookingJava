package com.example.api_movie.DatVe;

import com.example.api_movie.config.JwtService;
import com.example.api_movie.model.*;
import com.example.api_movie.repository.BookingRepository;
import com.example.api_movie.repository.SeatRepository;
import com.example.api_movie.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private CouponService couponService;
    @Autowired
    private BookingService bookingService;
    @Value("${webhook}")
    private String endpointSecret;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    public PaymentService(BookingRepository bookingRepository,
                          SeatRepository seatRepository,
                          CouponService couponService,
                          @Value("${stripe.secretKey}") String secretKey) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.couponService = couponService;
        Stripe.apiKey = secretKey;
    }

    public Session createStripeSession(Integer bookingId, String couponCode, HttpServletRequest request) throws StripeException {
        // Lấy token từ header
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Thiếu token hoặc token không hợp lệ");
        }
        String token = authHeader.substring(7);
        //Lấy userId từ token
        String tokenUsername = jwtService.extractUserId(token);
        User user = userRepository.findByUsername(tokenUsername)
                .orElseThrow(()->new RuntimeException("Không tìm thấy user"));

        //Tìm booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy booking"));

        if (booking.getUserId()!=user.getId()) {
            throw new RuntimeException("Không có quyền thực hiện thanh toán cho booking này");
        }

        if (booking.getPaymentStatus().equals("paid")) {
            throw new RuntimeException("Booking đã được thanh toán");
        }
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        // Gộp vé theo phim
        Map<String, Integer> ticketCountMap = new HashMap<>();
        Map<String, Long> ticketPriceMap = new HashMap<>();

        for (Ticket ticket : booking.getTickets()) {
            String movieTitle = ticket.getShowtime().getMovie().getTitle();
            ticketCountMap.put(movieTitle, ticketCountMap.getOrDefault(movieTitle, 0) + 1);
            ticketPriceMap.put(movieTitle, ticket.getPrice().longValue());
        }

        for (Map.Entry<String, Integer> entry : ticketCountMap.entrySet()) {
            String movieTitle = entry.getKey();
            Integer quantity = entry.getValue();
            Long price = ticketPriceMap.get(movieTitle);

            lineItems.add(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(quantity.longValue())
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("vnd")
                                            .setUnitAmount(price)
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName("Vé " + movieTitle)
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );
        }

        // Combo đồ ăn
        for (FoodBooking foodBooking : booking.getFoodBookings()) {
            lineItems.add(
                    SessionCreateParams.LineItem.builder()
                            .setQuantity((long) foodBooking.getQuantity())
                            .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("vnd")
                                            .setUnitAmount(foodBooking.getFood().getPrice().longValue())
                                            .setProductData(
                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                            .setName(foodBooking.getFood().getName())
                                                            .build()
                                            )
                                            .build()
                            )
                            .build()
            );
        }

        // Mã giảm giá (nếu có)
        List<SessionCreateParams.Discount> discounts = new ArrayList<>();
        if (couponCode != null && !couponCode.isEmpty()) {
            String promotionCodeId = couponService.getValidPromotionCodeId(couponCode);
            discounts.add(SessionCreateParams.Discount.builder()
                    .setPromotionCode(promotionCodeId)
                    .build());
        }

        // Tạo session Stripe
        SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://localhost:8080/api/payment/success")
                .setCancelUrl("https://localhost:8080/api/payment/cancel")
                .setLocale(SessionCreateParams.Locale.VI)
                .addAllLineItem(lineItems)
                .putMetadata("bookingId", bookingId.toString());

        if (!discounts.isEmpty()) {
            paramsBuilder.addAllDiscount(discounts);
        }

        return Session.create(paramsBuilder.build());
    }

    public void processWebhook(HttpServletRequest request) throws IOException, SignatureVerificationException {
        String payload;
        try (Scanner scanner = new Scanner(request.getInputStream(), "UTF-8")) {
            payload = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        }

        String sigHeader = request.getHeader("Stripe-Signature");
        Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

        if ("checkout.session.completed".equals(event.getType())) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(payload);
            JsonNode sessionNode = rootNode.path("data").path("object");

            String sessionId = sessionNode.path("id").asText(); // Lấy ID

            JsonNode metadataNode = sessionNode.path("metadata");
            if (metadataNode != null && metadataNode.has("bookingId")) {
                String bookingIdStr = metadataNode.get("bookingId").asText();
                try {
                    int bookingId = Integer.parseInt(bookingIdStr);
                    bookingService.updateStatus(bookingId, "paid");
                } catch (NumberFormatException e) {
                    System.err.println("Invalid bookingId: " + bookingIdStr);
                }
            } else {
                System.out.println("No metadata.bookingId found.");
            }
        }
    }


}
