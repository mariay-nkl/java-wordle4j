package ru.yandex.practicum;

import java.util.*;

public class WordleDictionary {
    private final List<String> words;
    private final Random random = new Random();

    public WordleDictionary(List<String> words) {
        this.words = new ArrayList<>(words);
    }

    public List<String> getWords() {
        return Collections.unmodifiableList(words);
    }

    public int size() {
        return words.size();
    }

    public boolean isEmpty() {
        return words.isEmpty();
    }

    public boolean contains(String word) {
        if (word == null) return false;
        String normalized = normalizeWord(word);
        return words.contains(normalized);
    }

    public String getRandomWord() {
        if (words.isEmpty()) {
            throw new IllegalStateException("Словарь пуст");
        }
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getWordsByPattern(String pattern, Set<Character> mustContain, Set<Character> mustNotContain) {
        List<String> result = new ArrayList<>();

        for (String word : words) {
            if (word.length() != pattern.length()) continue;

            boolean matches = true;
            for (int i = 0; i < pattern.length(); i++) {
                char p = pattern.charAt(i);
                if (p != '?' && p != word.charAt(i)) {
                    matches = false;
                    break;
                }
            }
            if (!matches) continue;

            boolean hasAll = true;
            if (mustContain != null) {
                for (char c : mustContain) {
                    if (word.indexOf(c) == -1) {
                        hasAll = false;
                        break;
                    }
                }
            }
            if (!hasAll) continue;

            boolean hasExcluded = false;
            if (mustNotContain != null) {
                for (char c : mustNotContain) {
                    if (word.indexOf(c) != -1) {
                        hasExcluded = true;
                        break;
                    }
                }
            }
            if (hasExcluded) continue;

            result.add(word);
        }
        return result;
    }

    public static String normalizeWord(String word) {
        if (word == null) return null;
        return word.toLowerCase()
                .replace('ё', 'е')
                .trim();
    }

    public static boolean isValidWord(String word) {
        if (word == null || word.length() != 5) return false;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c < 'а' || c > 'я' || c == 'ё') {
                return false;
            }
        }
        return true;
    }

    public static String compareWords(String guess, String answer) {
        if (guess == null || answer == null) {
            throw new IllegalArgumentException("Слова не могут быть null");
        }

        if (guess.length() != answer.length()) {
            throw new IllegalArgumentException("Слова должны быть одинаковой длины");
        }

        int n = guess.length();
        char[] result = new char[n];
        char[] answerChars = answer.toCharArray();
        char[] guessChars = guess.toCharArray();
        boolean[] usedInAnswer = new boolean[n];
        boolean[] usedInGuess = new boolean[n];

        for (int i = 0; i < n; i++) {
            if (guessChars[i] == answerChars[i]) {
                result[i] = '+';
                usedInAnswer[i] = true;
                usedInGuess[i] = true;
            }
        }

        for (int i = 0; i < n; i++) {
            if (usedInGuess[i]) continue;

            char guessChar = guessChars[i];
            boolean found = false;

            for (int j = 0; j < n; j++) {
                if (!usedInAnswer[j] && answerChars[j] == guessChar) {
                    result[i] = '^';
                    usedInAnswer[j] = true;
                    found = true;
                    break;
                }
            }

            if (!found) {
                result[i] = '-';
            }
        }

        return new String(result);
    }
}