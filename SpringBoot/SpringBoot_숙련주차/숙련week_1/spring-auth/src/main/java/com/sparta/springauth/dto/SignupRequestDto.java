package com.sparta.springauth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupRequestDto {
    @NotBlank(message = "필수값 입니다.")
    private String username;
    @NotBlank(message = "필수값 입니다.")
    private String password;

//    @Email(message = "이메일 형식만 허용합니다.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+[.][a-zA-Z]{2,3}$")
    @NotBlank(message = "필수값 입니다.")
    private String email;

    private boolean admin = false;
    private String adminToken = "";

}
