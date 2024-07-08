package com.sparta.springmvc.response;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/response")
public class ResponseController {

    /*
    Content-Type: text/html
    ResponseBody
    { "name":"Choi", "age":"26" }
     */
    @GetMapping("/json/string")
    @ResponseBody
    public String helloStringJson() {
        return "{\"name\":\"Choi\", \"age:\":26}";
    }

    /*
    Content-Type: application/json
    Response Body
    {"name":"choi", "age":26}
     */
//    @ResponseBody
//    @GetMapping("/json/class")
//    public Star helloClassJson() {
//        return new Star("choi", 26);
//    }
}
