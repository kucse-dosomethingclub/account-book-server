package com.example.demo.domain;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(String email,String username,String rawPassword){
        /*UserRepository.findByEmail(email).ifPresent(u->{
            throw new IllegalS
        })*/
        String encryptedPassword=passwordEncoder.encode(rawPassword);

        User newUser=User.builder()
                .email(email)
                .username(username)
                .password(encryptedPassword)
                .build();

        userRepository.saveUser(newUser);
    }
}
