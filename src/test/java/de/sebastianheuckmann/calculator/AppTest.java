package de.sebastianheuckmann.calculator;

import de.sebastianheuckmann.model.CalculatorModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private final CalculatorModel calc = new CalculatorModel();

    @Test
    void testApp() {
        assertTrue(true, "This test always passes.");
    }

    @Test
    void testAddition() {
        assertEquals(5, calc.add(2,3));
    }

    @Test
    void testSubtraction() {
        assertEquals(5, calc.subtract(8,3));
    }

    @Test
    void testMultiplication() {
        assertEquals(15, calc.multiply(5,3));
    }

    @Test
    void testDivision() {
        assertEquals(5, calc.divide(15,3));
    }

    @Test
    void testDivisionByZero() {
        assertEquals(Double.NaN, calc.divide(5,0));
    }
}
