import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.beans.Transient;

import org.junit.jupiter.api.Test;

import com.example.Calculator;



public class CalculatorTestCase {
    
    private Calculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new Calculator();
    }

    @Test
    public void TestMultiplicarDosEnteros() {
        assertEquals(6, calculator.multiply(2, 3));
    }
     @Test
    public void TestMultiplicarCeros() {
        assertEquals(0, calculator.multiply(0, 0));
    }
    @Test
    public void TestMultiplicarNegativos() {
        assertEquals(6, calculator.multiply(-2, -3));
    }
    @Test
    public void TestConcatenarCadenas() {
       assertEquals("HolaMundo", calculator.concat("Hola", "Mundo"));
    }

}