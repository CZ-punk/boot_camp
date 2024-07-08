package com.sparta.camp.calculator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class CalculatorTest {

    @Test
    @DisplayName("+ Test")
    public void calculator() {

        Calculator calculator = new Calculator();
        Double result = calculator.operate(1, "+", 3);
        assertThat(result).isEqualTo(4);
    }

    @Test
    @DisplayName("- Test")
    public void calculator2() {

        Calculator calculator = new Calculator();
        Double result = calculator.operate(1, "-", 3);
        assertThat(result).isEqualTo(-2);
    }

    @Test
    @DisplayName("* Test")
    public void calculator3() {

        Calculator calculator = new Calculator();
        Double result = calculator.operate(1, "*", 3);
        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("/ Test")
    public void calculator4() {

        Calculator calculator = new Calculator();
        Double result = calculator.operate(2, "/", 4);
        assertThat(result).isEqualTo(0.5);
    }

    @Test
    @DisplayName("error Test")
    public void calculator5() {

        Calculator calculator = new Calculator();
        Double result = calculator.operate(2, "/", 0);
        assertThat(result).isEqualTo(null);
    }

    @Test
    @DisplayName("IllegalArgumentException Test")
    public void calculator6() {

        Calculator calculator = new Calculator();
        assertThatThrownBy(() -> calculator
                .operate(2, " / ", 4)
        ).isInstanceOf(IllegalArgumentException.class);
    }


}