package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class WordleDictionaryLoaderTest {

    @Test
    void testDictionaryLoadException() {
        GameException.DictionaryLoadException exception = new GameException.DictionaryLoadException("Ошибка");
        assertEquals("Ошибка", exception.getMessage());
    }

    @Test
    void testDictionaryLoadExceptionWithCause() {
        IOException cause = new IOException("Файл не найден");
        GameException.DictionaryLoadException exception = new GameException.DictionaryLoadException("Ошибка загрузки", cause);
        assertEquals("Ошибка загрузки", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testLoaderCreation() {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        assertNotNull(loader);
    }
}