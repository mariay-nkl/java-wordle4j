package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class GameExceptionsTest {

    @Test
    void testAllExceptions() {
        GameException gameEx = new GameException("Игра");
        WordNotFoundInDictionaryException notFoundEx = new WordNotFoundInDictionaryException("Не найдено");
        InvalidWordException invalidEx = new InvalidWordException("Некорректно");
        DictionaryLoadException loadEx = new DictionaryLoadException("Загрузка");

        assertEquals("Игра", gameEx.getMessage());
        assertEquals("Не найдено", notFoundEx.getMessage());
        assertEquals("Некорректно", invalidEx.getMessage());
        assertEquals("Загрузка", loadEx.getMessage());
    }

    @Test
    void testExceptionHierarchy() {
        WordNotFoundInDictionaryException notFoundEx = new WordNotFoundInDictionaryException("Не найдено");
        InvalidWordException invalidEx = new InvalidWordException("Некорректно");
        assertTrue(GameException.class.isAssignableFrom(notFoundEx.getClass()));
        assertTrue(GameException.class.isAssignableFrom(invalidEx.getClass()));
        assertTrue(notFoundEx instanceof GameException);
        assertTrue(invalidEx instanceof GameException);
    }

    @Test
    void testDictionaryLoadExceptionWithCause() {
        Exception cause = new IOException("Файл не найден");
        DictionaryLoadException loadEx = new DictionaryLoadException("Ошибка загрузки", cause);
        assertEquals("Ошибка загрузки", loadEx.getMessage());
        assertSame(cause, loadEx.getCause());
    }
}