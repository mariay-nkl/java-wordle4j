package ru.yandex.practicum;

public class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class WordNotFoundInDictionaryException extends GameException {
        public WordNotFoundInDictionaryException(String message) {
            super(message);
        }
    }

    public static class InvalidWordException extends GameException {
        public InvalidWordException(String message) {
            super(message);
        }
    }

    public static class DictionaryLoadException extends GameException {
        public DictionaryLoadException(String message) {
            super(message);
        }

        public DictionaryLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}