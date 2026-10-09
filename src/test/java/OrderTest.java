import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uem.model.Order;
import com.uem.model.Article;

public class OrderTest {

    private static final double DELTA = 0.0001;
    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order("PEDIDO-001");
        order.addArticle(new Article("Ratón", 2, 25.0, 5.0));     // bruto 50, final 47.5
        order.addArticle(new Article("Monitor", 1, 150.0, 15.0)); // bruto 150, final 127.5
    }

    @Test
    public void testGetGrossTotal() {
        assertEquals(200.0, order.getGrossTotal(), DELTA);
    }

    @Test
    public void testGetDiscountedTotal() {
        assertEquals(175.0, order.getDiscountedTotal(), DELTA);
    }
}
