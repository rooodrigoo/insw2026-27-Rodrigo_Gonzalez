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

    @Test
    public void TestConcatenarNuloa() {
        assertEquals("empty", calculator.concat("A",""));
    }

    @Test
    public void TestSuma(){
        assertEquals(5, calculator.sum(2, 3));
    }

    @Test
    public void TestSumaNegativos() {
        assertEquals(-5, calculator.sum(-2, -3));
    }

    @Test
    public void TestDescuento() {
        assertEquals(80, calculator.discount(100, 20));
    }

    @Test
    public void TestDescuentoCero() {
        assertEquals(100, calculator.discount(100, 0));
    }
    
    @Test 
    public void TestDescuentoCien(){
        assertEquals(0, calculator.discount(100,100));
    }

    @Test
    public void TestDescuentoInvalidoMenorQue0() {
        assertThrows(IllegalArgumentException.class, () -> calculator.discount(100, -20));
    }

    @Test
    public void TestDescuentoInvalidoMayorQue100() {
        assertThrows(IllegalArgumentException.class, () -> calculator.discount(100, 120));
    }

    @Test
    public void TestCalcularTotal() {
        List<Double> amounts = List.of(10.0, 20.0, 30.0);
        assertEquals(60.0, calculator.calculateTotal(amounts));
    }

    @Test
    public void TestCalcularTotalVacio() {
        List<Double> amounts = List.of();
        assertEquals(0.0, calculator.calculateTotal(amounts));
    }
}