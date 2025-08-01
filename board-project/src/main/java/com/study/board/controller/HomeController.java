package com.study.board.controller;

import com.study.board.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser, Model model) {
        if (loginUser == null) {
            return "home";
        }
        model.addAttribute("user", loginUser);
        return "loginHome";
    }
}
