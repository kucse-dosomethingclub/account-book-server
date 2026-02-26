package com.example.demo.category;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public List<CategoryDto.categoryListResponse> getCategoryList(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return categoryRepository.getCategory(email);

    }

    public void addCategory(CategoryDto.categoryRequest categoryDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Category category = Category.builder()
                .userid(user.getId())
                .name(categoryDto.name())
                .type(categoryDto.type())
                .build();

        categoryRepository.addCategory(category);
    }

}
