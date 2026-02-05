package ru.yandex.practicum;

class GameException extends Exception {
    public GameException(String message) {
        super(message);
    }
}

class WordNotFoundInDictionaryException extends GameException {
    public WordNotFoundInDictionaryException(String message) {
        super(message);
    }
}

class InvalidWordException extends GameException {
    public InvalidWordException(String message) {
        super(message);
    }
}

class DictionaryLoadException extends Exception {
    public DictionaryLoadException(String message) {
        super(message);
    }

    public DictionaryLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}