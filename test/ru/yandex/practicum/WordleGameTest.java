package ru.yandex.practicum;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {

    private WordleGame game;
    private PrintWriter testLogWriter;

    @BeforeEach
    void setUp() {
        List<String> words = Arrays.asList("арбуз", "банан", "винт", "груша", "дымок", "егерь");
        WordleDictionary dictionary = new WordleDictionary(words) {
            @Override
            public String getRandomWord() {
                return "арбуз";
            }
        };
        testLogWriter = new PrintWriter(new StringWriter());
        game = new WordleGame(dictionary, testLogWriter);
    }

    @Test
    void testGameInitialization() {
        assertEquals("арбуз", game.getAnswer());
        assertEquals(6, game.getRemainingAttempts());
    }

    @Test
    void testCorrectGuess() {
        try {
            String result = game.makeGuess("арбуз");
            assertEquals("+++++", result);
            assertEquals(5, game.getRemainingAttempts());
        } catch (Exception e) {
            fail("Не должно было выброситься исключение: " + e.getMessage());
        }
    }

    @Test
    void testValidGuess() {
        try {
            String result = game.makeGuess("банан");
            assertNotNull(result);
        } catch (Exception e) {
            fail("Не должно было выброситься исключение: " + e.getMessage());
        }
    }

    @Test
    void testInvalidWordException() {
        try {
            game.makeGuess("ар");
            fail("Должно было выброситься InvalidWordException");
        } catch (GameException.InvalidWordException e) {
            assertNotNull(e.getMessage());
        } catch (Exception e) {
            fail("Выброшено неправильное исключение: " + e.getClass().getName());
        }

        try {
            game.makeGuess("арбузик");
            fail("Должно было выброситься InvalidWordException");
        } catch (GameException.InvalidWordException e) {
            assertNotNull(e.getMessage());
        } catch (Exception e) {
            fail("Выброшено неправильное исключение: " + e.getClass().getName());
        }
    }

    @Test
    void testWordNotFoundInDictionaryException() {
        try {
            game.makeGuess("абвгд");
            fail("Должно было выброситься WordNotFoundInDictionaryException");
        } catch (GameException.WordNotFoundInDictionaryException e) {
            assertNotNull(e.getMessage());
        } catch (Exception e) {
            fail("Выброшено неправильное исключение: " + e.getClass().getName());
        }
    }

    @Test
    void testGetHint() {
        try {
            String hint = game.getHint();
            assertNotNull(hint);
            assertEquals(5, hint.length());
        } catch (Exception e) {
            fail("Не должно было выброситься исключение: " + e.getMessage());
        }
    }

    @Test
    void testDuplicateGuessException() {
        try {
            game.makeGuess("банан");
        } catch (Exception e) {
            fail("Не должно было выброситься исключение: " + e.getMessage());
        }

        try {
            game.makeGuess("банан");
            fail("Должно было выброситься InvalidWordException");
        } catch (GameException.InvalidWordException e) {
            assertTrue(e.getMessage().contains("уже вводили"));
        } catch (Exception e) {
            fail("Выброшено неправильное исключение: " + e.getClass().getName());
        }
    }

    @Test
    void testValidGuessDecreasesAttempts() {
        try {
            game.makeGuess("банан");
            assertEquals(5, game.getRemainingAttempts());
        } catch (Exception e) {
            fail("Не должно было выброситься исключение: " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        testLogWriter.close();
    }
}