package test.java;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.Calculator;
import com.example.Searcher;


public class SearcherTestCase {
    private Searcher searcher;

    @BeforeEach
    public void setup() {
        searcher = new Searcher();
    }

    @Test
    public void testSearchWord_Exists() {
        assertTrue(searcher.searchWord("java"));
    }

    @Test
    public void testSearchWord_NotExists() {
        assertFalse(searcher.searchWord("python"));
    }

    @Test
    public void testGetWordByIndex_ValidIndex() {
        assertEquals("java", searcher.getWordByIndex(0));
    }

    @Test
    public void testGetWordByIndex_NegativeIndex() {
        assertNull(searcher.getWordByIndex(-1));
    }

    @Test
    public void testGetWordByIndex_IndexOutOfBounds() {
        assertNull(searcher.getWordByIndex(9999));
    }

    // 3. Pruebas para searchByPrefix
    @Test
    public void testSearchByPrefix_MatchesFound() {
        List<String> result = searcher.searchByPrefix("jav");
        assertTrue(result.contains("java"));
        assertFalse(result.contains("c++"));
    }

    @Test
    public void testSearchByPrefix_NoMatches() {
        List<String> result = searcher.searchByPrefix("xyz");
        assertTrue(result.isEmpty());
    }

    // 4. Pruebas para filterByKeyword
    @Test
    public void testFilterByKeyword_MatchesFound() {
        List<String> result = searcher.filterByKeyword("script");
        assertTrue(result.contains("javascript"));
    }

    @Test
    public void testFilterByKeyword_NoMatches() {
        List<String> result = searcher.filterByKeyword("nonexistent");
        assertTrue(result.isEmpty());
    }

    // 5. (Avanzado) Prueba para searchExactPhrase donde falla si no es el primer elemento
    @Test
    public void testSearchExactPhrase_NotFirstElement() {
        // Esta prueba revelará el fallo si el método solo busca/compara la frase
        // contra el primer elemento de la lista o detiene el bucle de forma prematura.
        assertTrue(searcher.searchExactPhrase("frase de prueba en segunda posicion"));
    }
}
