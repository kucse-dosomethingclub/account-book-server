package com.example.demo.assetsource;

import com.example.demo.category.CategoryType;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class assetsourceService {

    private final assetsourceRepository assetsourceRepository;
    private final UserRepository userRepository;

    public void addAssetsource(assetsourceDto.assetsourceRequest assetsourceDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        assetsource asset = assetsource.builder()
                .userid(user.getId())
                .name(assetsourceDto.name())
                .build();

        assetsourceRepository.addAssetsource(asset);
    }

    public List<assetsourceDto.assetsourceResponse> getAssetsource(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return assetsourceRepository.getAssetsource(user.getId());
    }
}
