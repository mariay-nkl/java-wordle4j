package ru.yandex.practicum;

import java.io.PrintWriter;
import java.util.*;

class WordleGame {

    private final String answer;
    private int remainingAttempts;
    private final WordleDictionary dictionary;
    private final PrintWriter logWriter;
    private final List<String> guesses = new ArrayList<>();
    private final List<String> results = new ArrayList<>();
    private final Set<Character> mustContainLetters = new HashSet<>();
    private final Set<Character> mustNotContainLetters = new HashSet<>();
    private final char[] knownPositions = new char[5];
    private final List<String> givenHints = new ArrayList<>();

    public WordleGame(WordleDictionary dictionary, PrintWriter logWriter) {
        this.dictionary = dictionary;
        this.logWriter = logWriter;
        this.answer = dictionary.getRandomWord();
        this.remainingAttempts = 6;
        Arrays.fill(knownPositions, '?');

        logWriter.println("Загадано слово: " + answer);
    }

    public String getAnswer() {
        return answer;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public String makeGuess(String guess) throws WordNotFoundInDictionaryException, InvalidWordException {
        String normalizedGuess = WordleDictionary.normalizeWord(guess);

        if (normalizedGuess.length() != 5) {
            throw new InvalidWordException("Слово должно содержать 5 букв");
        }

        for (int i = 0; i < normalizedGuess.length(); i++) {
            char c = normalizedGuess.charAt(i);
            if (!Character.isLetter(c) || c == 'ё') {
                throw new InvalidWordException("Слово должно содержать только русские буквы (кроме ё)");
            }
        }

        if (!dictionary.contains(normalizedGuess)) {
            throw new WordNotFoundInDictionaryException("Слово '" + normalizedGuess + "' не найдено в словаре");
        }

        if (guesses.contains(normalizedGuess)) {
            throw new InvalidWordException("Вы уже вводили это слово");
        }

        if (normalizedGuess.equals(answer)) {
            guesses.add(normalizedGuess);
            results.add("+++++");
            remainingAttempts--;
            logWriter.println("Игрок ввел: " + normalizedGuess + ", результат: " + "+++++");
            return "+++++";
        }

        String result = WordleDictionary.compareWords(normalizedGuess, answer);
        guesses.add(normalizedGuess);
        results.add(result);
        remainingAttempts--;

        updateLetterInfo(normalizedGuess, result);

        logWriter.println("Игрок ввел: " + normalizedGuess + ", результат: " + result);

        return result;
    }

    private void updateLetterInfo(String guess, String result) {
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            char resultChar = result.charAt(i);

            if (resultChar == '+') {
                knownPositions[i] = guessChar;
                mustContainLetters.add(guessChar);
            } else if (resultChar == '^') {
                mustContainLetters.add(guessChar);
            } else if (resultChar == '-') {
                boolean letterExistsElsewhere = false;
                for (int j = 0; j < guess.length(); j++) {
                    if (j != i && guess.charAt(j) == guessChar &&
                            (result.charAt(j) == '+' || result.charAt(j) == '^')) {
                        letterExistsElsewhere = true;
                        break;
                    }
                }

                if (!letterExistsElsewhere) {
                    mustNotContainLetters.add(guessChar);
                }
            }
        }
    }

    public String getHint() throws GameException {
        if (remainingAttempts <= 0) {
            throw new GameException("Игра завершена");
        }

        StringBuilder patternBuilder = new StringBuilder();
        for (char c : knownPositions) {
            patternBuilder.append(c);
        }
        String pattern = patternBuilder.toString();

        List<String> possibleWords = dictionary.getWordsByPattern(
                pattern, mustContainLetters, mustNotContainLetters);

        possibleWords.removeAll(guesses);
        possibleWords.removeAll(givenHints);

        if (possibleWords.isEmpty()) {
            return null;
        }

        Random random = new Random();
        String hint = possibleWords.get(random.nextInt(possibleWords.size()));
        givenHints.add(hint);

        logWriter.println("Предложена подсказка: " + hint);

        return hint;
    }

    public List<String> getGuesses() {
        return Collections.unmodifiableList(guesses);
    }

    public List<String> getResults() {
        return Collections.unmodifiableList(results);
    }
}