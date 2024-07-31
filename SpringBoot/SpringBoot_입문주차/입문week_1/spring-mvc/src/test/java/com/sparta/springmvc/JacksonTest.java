package com.sparta.springmvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springmvc.response.Star;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JacksonTest {

    @Test
    @DisplayName("Obejct To Json: required Getter!")
    public void test_1() throws JsonProcessingException {

        Star star = new Star("choi", 26);
        ObjectMapper mapper = new ObjectMapper();
        // Jackson 라이브러리의 ObjectMapper 객체 사용
        String json = mapper.writeValueAsString(star);
        System.out.println("json = " + json);
    }

    @Test
    @DisplayName("Json To Object: required Default Constructor & (Getter OR Setter)")
    public void test_2() throws JsonProcessingException {

        String json = "{\"name\":\"choi\",\"age\":26}";

        ObjectMapper mapper = new ObjectMapper();
        Star object = mapper.readValue(json, Star.class);
        System.out.println("object = " + object.getName() + ", " + object.getAge());
    }
}
