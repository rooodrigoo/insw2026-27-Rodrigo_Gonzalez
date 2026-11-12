
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uem.model.Article;

public class ArticleTest {

    private static final double DELTA = 0.0001;
    private Article article;

    @BeforeEach
    public void setUp() {
        article = new Article("Teclado", 2, 50.0, 10.0);
    }

    @Test
    public void testGetGrossAmount() {
        assertEquals(100.0, article.getGrossAmount(), DELTA);
    }


    @Test
    public void testGetDiscountedAmount() {
        // 100 - 10% = 90
        assertEquals(90.0, article.getDiscountedAmount(), DELTA);
    }

    
}
