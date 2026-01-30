package com.example.demo.user;

public class UserDto {
    public record LoginRequest(String email,String password){}
    //public record password_ch(String email, String newpassword){}
    //record를 사용하면
    //1. 필드를 생성해줌 ex) private final String email처럼 만들어야 하지만 이걸 자동으로 만들어줌
    //2. 생성자 만듬
    //3. Getter 매서드 만들어줌

}
