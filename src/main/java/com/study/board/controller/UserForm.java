package com.study.board.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserForm {
    @NotEmpty(message = "필수입니다.")
    private String loginId;
    @NotEmpty(message = "필수입니다.")
    private String name;
    @NotEmpty(message = "필수입니다.")
    private String password;
    @NotEmpty(message = "필수입니다.")
    private String confirmPassword;
}
