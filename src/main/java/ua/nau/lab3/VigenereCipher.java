package ua.nau.lab3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class VigenereCipher {
    private static final String ALPHABET = "АБВГҐДЕЄЖЗИІЇЙКЛМНОПРСТУФХЦЧШЩЬЮЯ";
    private static final int ALPHABET_SIZE = ALPHABET.length();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        System.out.println("Шифр Віженера");
        System.out.println("1. Шифрувати");
        System.out.println("2. Дешифрувати");
        System.out.print("Виберіть опцію: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Введіть ім'я вхідного файлу: ");
        String inputFileName = scanner.nextLine();

        System.out.print("Введіть ім'я вихідного файлу: ");
        String outputFileName = scanner.nextLine();

        System.out.print("Введіть ключове слово: ");
        String key = scanner.nextLine().toUpperCase();

        try {
            String text = readFromFile(inputFileName);
            String result;

            if (option == 1) {
                result = encrypt(text, key);
                System.out.println("Текст успішно зашифровано.");
            } else if (option == 2) {
                result = decrypt(text, key);
                System.out.println("Текст успішно розшифровано.");
            } else {
                System.out.println("Некоректна опція.");
                scanner.close();
                return;
            }

            writeToFile(outputFileName, result);
            System.out.println("Результат збережено у файлі: " + outputFileName);

            String exampleText = "Я СТУДЕНТ УНІВЕРСИТЕТУ";
            String exampleKey = "ПРІЗВИЩЕ";

            System.out.println("\nПриклад:");
            System.out.println("Відкритий текст: " + exampleText);
            System.out.println("Ключ: " + exampleKey);
            String encryptedExample = encrypt(exampleText, exampleKey);
            System.out.println("Зашифрований текст: " + encryptedExample);
            String decryptedExample = decrypt(encryptedExample, exampleKey);
            System.out.println("Розшифрований текст: " + decryptedExample);

        } catch (IOException e) {
            System.out.println("Помилка при роботі з файлами: " + e.getMessage());
        }

        scanner.close();
    }

    public static String encrypt(String text, String key) {
        text = text.toUpperCase();
        StringBuilder result = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int textIndex = ALPHABET.indexOf(c);

            if (textIndex != -1) {
                // If character is in our alphabet, encrypt it
                int keyIndex = ALPHABET.indexOf(key.charAt(j % keyLength));
                int encryptedIndex = (textIndex + keyIndex) % ALPHABET_SIZE;
                result.append(ALPHABET.charAt(encryptedIndex));
                j++;
            } else {
                // If character is not in our alphabet (space, punctuation, etc.), leave it as is
                result.append(c);
            }
        }

        return result.toString();
    }

    public static String decrypt(String text, String key) {
        text = text.toUpperCase();
        StringBuilder result = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int textIndex = ALPHABET.indexOf(c);

            if (textIndex != -1) {
                // If character is in our alphabet, decrypt it
                int keyIndex = ALPHABET.indexOf(key.charAt(j % keyLength));
                int decryptedIndex = (textIndex - keyIndex + ALPHABET_SIZE) % ALPHABET_SIZE;
                result.append(ALPHABET.charAt(decryptedIndex));
                j++;
            } else {
                // If character is not in our alphabet (space, punctuation, etc.), leave it as is
                result.append(c);
            }
        }

        return result.toString();
    }

    private static String readFromFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private static void writeToFile(String fileName, String content) throws IOException {
        Path path = Paths.get(fileName);
        Files.writeString(path, content, StandardCharsets.UTF_8);
    }
}
