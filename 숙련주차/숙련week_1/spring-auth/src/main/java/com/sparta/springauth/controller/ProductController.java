package com.sparta.springauth.controller;

import com.sparta.springauth.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ProductController {

    @GetMapping("/product")
    public String getProducts(HttpServletRequest request) {
        System.out.println("ProductController.getProducts : 인증 완료");
        User user = (User) request.getAttribute("user");
        System.out.println("user.getUsername = " + user.getUsername());

        return "redirect:/";
    }
}
