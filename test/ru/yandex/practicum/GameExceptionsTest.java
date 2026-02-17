package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class GameExceptionsTest {

    @Test
    void testAllExceptions() {
        GameException gameEx = new GameException("Игра");
        GameException.WordNotFoundInDictionaryException notFoundEx =
                new GameException.WordNotFoundInDictionaryException("Не найдено");
        GameException.InvalidWordException invalidEx =
                new GameException.InvalidWordException("Некорректно");
        GameException.DictionaryLoadException loadEx =
                new GameException.DictionaryLoadException("Загрузка");

        assertEquals("Игра", gameEx.getMessage());
        assertEquals("Не найдено", notFoundEx.getMessage());
        assertEquals("Некорректно", invalidEx.getMessage());
        assertEquals("Загрузка", loadEx.getMessage());

        assertTrue(notFoundEx.getMessage().contains("Не найдено"));
        assertTrue(invalidEx.getMessage().contains("Некорректно"));
    }

    @Test
    void testExceptionHierarchy() {
        GameException.WordNotFoundInDictionaryException notFoundEx =
                new GameException.WordNotFoundInDictionaryException("Не найдено");
        GameException.InvalidWordException invalidEx =
                new GameException.InvalidWordException("Некорректно");

        assertEquals(GameException.WordNotFoundInDictionaryException.class, notFoundEx.getClass());
        assertEquals(GameException.InvalidWordException.class, invalidEx.getClass());

        assertTrue(notFoundEx instanceof Exception);
        assertTrue(invalidEx instanceof Exception);
    }

    @Test
    void testDictionaryLoadExceptionWithCause() {
        Exception cause = new IOException("Файл не найден");
        GameException.DictionaryLoadException loadEx =
                new GameException.DictionaryLoadException("Ошибка загрузки", cause);

        assertEquals("Ошибка загрузки", loadEx.getMessage());
        assertSame(cause, loadEx.getCause());
        assertNotNull(loadEx.getCause());
        assertEquals("Файл не найден", loadEx.getCause().getMessage());
    }

    @Test
    void testDictionaryLoadExceptionWithoutCause() {
        GameException.DictionaryLoadException loadEx =
                new GameException.DictionaryLoadException("Простая ошибка");

        assertEquals("Простая ошибка", loadEx.getMessage());
        assertNull(loadEx.getCause());
    }
}