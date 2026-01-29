package com.example.demo.user;

import com.example.demo.jwt.JwtDto;
import com.example.demo.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public User signUp(User user){
        User signUser=userRepository.email_check(user)
                .orElseThrow(() -> new IllegalArgumentException("이메일이 이미 존재합니다."));
        String encryptedPassword=passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.saveUser(signUser);
        return signUser;//저장 완료
    }

    public JwtDto.TokenResponse login(UserDto.LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        if(!passwordEncoder.matches(request.password(),  user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
        user.setPassword(null);
        //유저한테 넘어갈때, 암호화 된 비밀번호가 넘어가지 않도록 null로 해준다.
        String token=jwtTokenProvider.createToken(user.getEmail());
        return new JwtDto.TokenResponse(token);
        //토큰 Dto를 넘김
    }

    public User psaaword_change(UserDto.password_ch request) {
        User user=userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));
        //.orElseThrow()는 Repository에서 optional으로 넘겨줘서 안에 값이 없으면 처리한다.
        //IllegalArgmentException : 자바 표준 예외
        String encryptedPassword=passwordEncoder.encode(request.newpassword());
        user.setPassword(encryptedPassword);
        User change_user=userRepository.password_ch(user,encryptedPassword)
                .orElseThrow(()->new IllegalArgumentException("예외 발생"));
        return change_user;

    }
}
