package com.sparta.junit5practice;

public class Calculator {
    public Double operate(double num1, String op, double num2) {
        switch (op) {
            case "*":
                return num1 * num2;
            case "/":
                if (validateNum(num2)) {
                    return num1 / num2;
                } else {
                    return null;
                }
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            default:
                throw new IllegalArgumentException("잘못된 연산자입니다.");
        }
    }

    public boolean validateNum(double num) {
        if (num == 0) {
            return false;
        } else {
            return true;
        }
    }
}