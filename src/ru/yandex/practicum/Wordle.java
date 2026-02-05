package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        PrintWriter logWriter = null;

        try {
            logWriter = new PrintWriter(new FileWriter("wordle.log", StandardCharsets.UTF_8));
            logWriter.println("Игра Wordle запущена");

            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            WordleDictionary dictionary = loader.loadDictionary("words_ru.txt");

            logWriter.println("Словарь загружен, слов: " + dictionary.size());

            WordleGame game = new WordleGame(dictionary, logWriter);

            Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());

            System.out.println("Добро пожаловать в игру Wordle!");
            System.out.println("Угадайте слово из 5 букв. У вас есть 6 попыток.");
            System.out.println("Символы в подсказке:");
            System.out.println("  + - буква есть и стоит на правильном месте");
            System.out.println("  ^ - буква есть, но не на этом месте");
            System.out.println("  - - буквы нет в слове");
            System.out.println("Для подсказки нажмите Enter без ввода слова.");
            System.out.println();

            boolean gameEnded = false;

            while (!gameEnded) {
                System.out.print("Введите слово (осталось попыток: " + game.getRemainingAttempts() + "): ");
                String input = scanner.nextLine().trim();

                try {
                    if (input.isEmpty()) {
                        String hint = game.getHint();
                        if (hint != null) {
                            System.out.println("Подсказка: " + hint);
                        } else {
                            System.out.println("Подходящих слов не найдено");
                        }
                        continue;
                    }

                    String result = game.makeGuess(input);
                    System.out.println("Результат: " + result);

                    if (result.equals("+++++")) {
                        System.out.println("Поздравляем! Вы угадали слово!");
                        gameEnded = true;
                    }

                    if (game.getRemainingAttempts() <= 0) {
                        System.out.println("Попытки закончились. Загаданное слово: " + game.getAnswer());
                        gameEnded = true;
                    }

                } catch (GameException.WordNotFoundInDictionaryException e) {
                    System.out.println("Слово не найдено в словаре. Попробуйте другое слово.");
                    logWriter.println("Игрок ввел слово не из словаря: " + input);
                } catch (GameException.InvalidWordException e) {
                    System.out.println(e.getMessage());
                    logWriter.println("Некорректное слово: " + input + " - " + e.getMessage());
                } catch (GameException e) {
                    System.out.println("Ошибка игры: " + e.getMessage());
                    logWriter.println("Игровая ошибка: " + e.getMessage());
                }

                System.out.println();
            }

            scanner.close();
            logWriter.println("Игра завершена");

        } catch (GameException.DictionaryLoadException e) {
            System.err.println("Ошибка загрузки словаря: " + e.getMessage());
            if (logWriter != null) {
                logWriter.println("Ошибка загрузки словаря: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            if (logWriter != null) {
                logWriter.println("Ошибка ввода-вывода: " + e.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
            if (logWriter != null) {
                logWriter.println("Неожиданная ошибка: " + e.getMessage());
                e.printStackTrace(logWriter);
            }
        } finally {
            if (logWriter != null) {
                logWriter.close();
            }
        }
    }
}