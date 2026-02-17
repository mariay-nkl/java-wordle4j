package ru.yandex.practicum;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class WordleDictionaryTest {

    private WordleDictionary dictionary;

    @BeforeEach
    void setUp() {
        List<String> words = Arrays.asList("арбуз", "банан", "лимон", "якорь", "егерь");
        dictionary = new WordleDictionary(words);
    }

    @Test
    void testDictionaryCreation() {
        assertNotNull(dictionary);
        assertEquals(5, dictionary.size());
        assertFalse(dictionary.isEmpty());
    }

    @Test
    void testGetRandomWord() {
        String word = dictionary.getRandomWord();
        assertNotNull(word);
        assertEquals(5, word.length());
    }

    @Test
    void testContains() {
        assertTrue(dictionary.contains("арбуз"));
        assertTrue(dictionary.contains("Арбуз"));
        assertFalse(dictionary.contains("абвгд"));
    }

    @Test
    void testNormalizeWord() {
        assertEquals("арбуз", WordleDictionary.normalizeWord("Арбуз"));
        assertEquals("ежик", WordleDictionary.normalizeWord("ёжик"));
        assertNull(WordleDictionary.normalizeWord(null));
    }

    @Test
    void testIsValidWord() {
        assertTrue(WordleDictionary.isValidWord("арбуз"));
        assertTrue(WordleDictionary.isValidWord("банан"));
        assertFalse(WordleDictionary.isValidWord("ар"));
        assertFalse(WordleDictionary.isValidWord("арбузик"));
        assertFalse(WordleDictionary.isValidWord("12345"));
    }

    @Test
    void testCompareWordsExactMatch() {
        assertEquals("+++++", WordleDictionary.compareWords("арбуз", "арбуз"));
    }

    @Test
    void testCompareWordsExampleFromTask() {
        assertEquals("+^-^-", WordleDictionary.compareWords("гонец", "герой"));
    }

    @Test
    void testGetWordsByPattern() {
        Set<Character> mustContain = new HashSet<>(Arrays.asList('а', 'р'));
        Set<Character> mustNotContain = new HashSet<>();
        List<String> result = dictionary.getWordsByPattern("ар???", mustContain, mustNotContain);
        assertEquals(1, result.size());
        assertEquals("арбуз", result.get(0));
    }

    @Test
    void testEmptyDictionaryException() {
        WordleDictionary emptyDict = new WordleDictionary(Collections.emptyList());
        try {
            emptyDict.getRandomWord();
            fail();
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("пуст") || e.getMessage().contains("empty"));
        }
    }

    @Test
    void testCompareWordsDifferentLength() {
        try {
            WordleDictionary.compareWords("короткое", "длинное");
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("длины") || e.getMessage().contains("length"));
        }
    }

    @Test
    void testCompareWordsNullArguments() {
        try {
            WordleDictionary.compareWords(null, "арбуз");
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            WordleDictionary.compareWords("арбуз", null);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @AfterEach
    void tearDown() {
        dictionary = null;
    }
}