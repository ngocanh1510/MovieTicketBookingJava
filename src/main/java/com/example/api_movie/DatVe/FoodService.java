package com.example.api_movie.DatVe;

import com.example.api_movie.dto.FoodDto;
import com.example.api_movie.model.Food;
import com.example.api_movie.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService {
    @Autowired
    private FoodRepository foodRepository;

    //Lấy toàn bộ thông tin thức ăn
    public List<FoodDto> findAll() {
        return foodRepository.findAllByOrderByCategoryAsc().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Lấy thức ăn theo loại
    public List<FoodDto> findByCategory(Food.Category category) {
        return foodRepository.findByCategory(category).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Thêm thức ăn
    public FoodDto addFood(FoodDto foodDto) {
        Food food= new Food();
        food.setCategory(foodDto.getCategory());
        food.setName(foodDto.getName());
        food.setPrice(foodDto.getPrice());
        food.setDetail(foodDto.getDetail());
        food.setImg(foodDto.getImg());
        foodRepository.save(food);
        return convertToDto(food);
    }

    // Cập nhật thức ăn
    public FoodDto updateFood(int id,FoodDto foodDto) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món có id" + id));
        food.setCategory(foodDto.getCategory());
        food.setName(foodDto.getName());
        food.setPrice(foodDto.getPrice());
        food.setDetail(foodDto.getDetail());
        food.setImg(foodDto.getImg());
        foodRepository.save(food);
        return convertToDto(food);
    }

    // Xóa thức ăn
    public void deleteFood(int id) {
        if(!foodRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy món có id " + id);
        }
        foodRepository.deleteById(id);
    }
    private FoodDto convertToDto(Food food) {
        return new FoodDto(
                food.getCategory(),
                food.getName(),
                food.getPrice(),
                food.getDetail(),
                food.getImg()
        );
    }
}
