import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uem.model.Calculator;


public class CalculatorTestCase {

    private static final double DELTA = 0.0001;
    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    // multiply
    @Test
    public void testMultiplyNormal() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    public void testMultiplyConCero() {
        assertEquals(0, calculator.multiply(0, 5));
    }

    @Test
    public void testMultiplyNegativos() {
        assertEquals(6, calculator.multiply(-2, -3));
        assertEquals(-6, calculator.multiply(-2, 3));
    }

    // concat
    @Test
    public void testConcatNormal() {
        assertEquals("HolaMundo", calculator.concat("Hola", "Mundo"));
    }

    @Test
    public void testConcatConNull() {
        assertEquals(Calculator.EMPTY, calculator.concat(null, "Mundo"));
        assertEquals(Calculator.EMPTY, calculator.concat("Hola", null));
        assertEquals("empty", calculator.concat(null, null));
    }

    // sum
    @Test
    public void testSumNormal() {
        assertEquals(5.0, calculator.sum(2, 3), DELTA);
    }

    @Test
    public void testSumNegativos() {
        assertEquals(-5.0, calculator.sum(-2, -3), DELTA);
    }

    // discount
    @Test
    public void testDiscountValido() {
        assertEquals(80.0, Calculator.discount(100, 20), DELTA);
    }

    @Test
    public void testDiscountLimites() {
        assertEquals(100.0, Calculator.discount(100, 0), DELTA);
        assertEquals(0.0, Calculator.discount(100, 100), DELTA);
    }

    @Test
    public void testDiscountMenorQueCero() {
        assertThrows(IllegalArgumentException.class, () -> Calculator.discount(100, -1));
    }

    @Test
    public void testDiscountMayorQueCien() {
        assertThrows(IllegalArgumentException.class, () -> Calculator.discount(100, 101));
    }

    // calculateTotal
    @Test
    public void testCalculateTotal() {
        assertEquals(60.0, calculator.calculateTotal(List.of(10.0, 20.0, 30.0)), DELTA);
    }

    @Test
    public void testCalculateTotalListaVacia() {
        assertEquals(0.0, calculator.calculateTotal(List.of()), DELTA);
    }
}
