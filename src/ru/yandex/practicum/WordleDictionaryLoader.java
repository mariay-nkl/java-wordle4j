package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class WordleDictionaryLoader {

    public WordleDictionary loadDictionary(String filename) throws GameException.DictionaryLoadException {
        List<String> validWords = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String normalizedWord = WordleDictionary.normalizeWord(line);

                if (WordleDictionary.isValidWord(normalizedWord)) {
                    validWords.add(normalizedWord);
                }
            }

            if (validWords.isEmpty()) {
                throw new GameException.DictionaryLoadException(
                        "Словарь не содержит подходящих " + WordleDictionary.WORD_LENGTH + "-буквенных слов");
            }

            return new WordleDictionary(validWords);

        } catch (FileNotFoundException e) {
            throw new GameException.DictionaryLoadException("Файл словаря не найден: " + filename, e);
        } catch (IOException e) {
            throw new GameException.DictionaryLoadException("Ошибка чтения файла словаря: " + filename, e);
        }
    }
}