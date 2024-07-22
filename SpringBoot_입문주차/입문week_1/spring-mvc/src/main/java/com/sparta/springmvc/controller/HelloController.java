package com.sparta.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class HelloController {

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/get")
    @ResponseBody
    public String get() {
        return "Get Method Request";
    }


    @PostMapping("/post")
    @ResponseBody
    public String post() {
        return "Post Method Request";
    }

    @PutMapping("/put")
    @ResponseBody
    public String put() {
        return "Put Method Request";
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public String delete() {
        return "Delete Method Request";
    }

}
