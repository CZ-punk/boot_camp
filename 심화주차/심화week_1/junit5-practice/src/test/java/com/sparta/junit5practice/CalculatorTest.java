package com.sparta.junit5practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("계산기 연산 성공 테스트")
    void test1() {
        // given
        int num1 = 5;
        String op = "/";
        int num2 = 2;

        // when
        Double result = calculator.operate(num1, op, num2);

        // then
        assertNotNull(result);
        assertEquals(2.5, result);
    }

    @Test
    @DisplayName("계산기 연산 실패 테스트 : 분모가 0일 경우")
    void test1_1() {
        // given
        int num1 = 5;
        String op = "/";
        int num2 = 0;

        // when
        Double result = calculator.operate(num1, op, num2);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("계산기 연산 실패 테스트 : 연산자가 잘못됐을 경우")
    void test1_2() {
        // given
        int num1 = 5;
        String op = "?";
        int num2 = 2;

        // when - then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> calculator.operate(5, "?", 2));
        assertEquals("잘못된 연산자입니다.", exception.getMessage());

        // 이렇게 해당 Method 를 수행했다면 예상되는 Exception.class 를 정해 검증할 수도 있다.
        assertThrows(IllegalArgumentException.class, () ->
                calculator.operate(num1, op, num2));
    }
}