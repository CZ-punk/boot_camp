package com.sparta.springauth;

import com.sparta.springauth.food.Food;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BeanTest {

    @Autowired
    @Qualifier("pizza")
    Food pizza;

    @Autowired
    Food chicken;



    @Test
    @DisplayName("동일 Type 의 Bean 2개")
    public void test() {
        System.out.println("test1");
        pizza.eat();
    }

    @Test
    @DisplayName("동일 Type 의 Bean 2개")
    public void test2() {
        System.out.println("test2");
        chicken.eat();
    }

}
