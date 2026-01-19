package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Boolean signUp(User user){

        //email_check에서 중복이 있으면 true반환해서 앞에 !
        if(!userRepository.email_check(user)){
            String encryptedPassword=passwordEncoder.encode(user.getPassword());

            User newUser = User.builder()
                    .email(user.getEmail())
                    .username(user.getUsername())
                    .password(encryptedPassword)
                    .build();
            userRepository.saveUser(newUser);
            return true;//저장 완료
        }
        else{
            return false;//저장 실패
        }
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        user.setPassword(null);
        return user;
    }

}
