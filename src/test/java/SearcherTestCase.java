import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uem.model.Searcher;

public class SearcherTestCase {

    private Searcher searcher;
    private List<String> words;

    @BeforeEach
    public void setup() {
        searcher = new Searcher();
        words = List.of("java", "javascript", "c++", "frase de prueba");
    }

    // searchWord
    @Test
    public void testSearchWord_Exists() {
        assertTrue(searcher.searchWord("java", words));
    }

    @Test
    public void testSearchWord_NotExists() {
        assertFalse(searcher.searchWord("python", words));
    }

    // getWordByIndex
    @Test
    public void testGetWordByIndex_ValidIndex() {
        assertEquals("java", searcher.getWordByIndex(words, 0));
        assertEquals("frase de prueba", searcher.getWordByIndex(words, 3));
    }

    @Test
    public void testGetWordByIndex_NegativeIndex() {
        assertNull(searcher.getWordByIndex(words, -1));
    }

    @Test
    public void testGetWordByIndex_IndexOutOfBounds() {
        assertNull(searcher.getWordByIndex(words, 9999));
        assertNull(searcher.getWordByIndex(words, words.size()));
    }

    // searchByPrefix
    @Test
    public void testSearchByPrefix_MatchesFound() {
        List<String> result = searcher.searchByPrefix("jav", words);
        assertEquals(List.of("java", "javascript"), result);
        assertFalse(result.contains("c++"));
    }

    @Test
    public void testSearchByPrefix_NoMatches() {
        assertTrue(searcher.searchByPrefix("xyz", words).isEmpty());
    }

    // filterByKeyword
    @Test
    public void testFilterByKeyword_MatchesFound() {
        assertEquals(List.of("javascript"), searcher.filterByKeyword("script", words));
        assertEquals(List.of("java", "javascript"), searcher.filterByKeyword("java", words));
    }

    @Test
    public void testFilterByKeyword_NoMatches() {
        assertTrue(searcher.filterByKeyword("nonexistent", words).isEmpty());
    }

    // searchExactPhrase (avanzado): fallaba si la frase no era el primer elemento
    @Test
    public void testSearchExactPhrase_FirstElement() {
        assertTrue(searcher.searchExactPhrase("java", words));
    }

    @Test
    public void testSearchExactPhrase_NotFirstElement() {
        assertTrue(searcher.searchExactPhrase("frase de prueba", words));
    }

    @Test
    public void testSearchExactPhrase_NotExists() {
        assertFalse(searcher.searchExactPhrase("no existe", words));
    }
}
