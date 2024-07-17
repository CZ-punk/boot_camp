package com.sparta.springauth.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    @NotBlank(message = "공백이 불가능합니다.")
    private String name;
    @Email(message = "이메일 형식만 가능합니다.")
    private String email;
    @Positive(message = "양수만 가능합니다.")
    private int price;
    @Negative(message = "음수만 가능합니다.")
    private int discount;
    @Size(min=2, max=10, message = "2~10개의 문자만 입력 가능합니다.")
    private String link;
    @Max(value = 10, message = "최대값은 10 입니다.")
    private int max;
    @Min(value = 2, message = "최소값은 2 입니다.")
    private int min;
}