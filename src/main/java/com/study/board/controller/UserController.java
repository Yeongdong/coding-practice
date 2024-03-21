package com.study.board.controller;

import com.study.board.entity.User;
import com.study.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/new")
    public String createForm(@ModelAttribute("userForm") UserForm userForm) {
        return "/users/createUserForm";
    }

    @PostMapping("/users/new")
    public String create(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult result) {
        //비밀번호와 비밀번호 확인이 같은지 검사
        if(!userForm.getPassword().equals(userForm.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.userForm", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        //오류가 있을시 다시 회원가입 폼으로 이동
        if(result.hasErrors()) {
            return "/users/createUserForm";
        }

        User joinUser = User.create(userForm.getLoginId(), userForm.getName(), userForm.getPassword());
        userService.join(joinUser);

        return "redirect:/";
    }
}
