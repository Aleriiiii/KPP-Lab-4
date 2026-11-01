package ua.khnu.kpp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public final class FileManager {
    private final List<String> collection;
    private static final String FILE_1 = "1.txt";
    private static final String FILE_2 = "2.txt";

    public FileManager() {
        this.collection = new ArrayList<>();
    }

    // 1. Генерація випадкових значень (символи та цифри) у колекцію
    public void generateRandomData(int count) {
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        
        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder();
            int length = 5 + random.nextInt(4); // довжина від 5 до 8 символів
            for (int j = 0; j < length; j++) {
                sb.append(characters.charAt(random.nextInt(characters.length())));
            }
            collection.add(sb.toString());
        }
        System.out.println("Згенеровано елементів у колекцію: " + collection.size());
    }

    // Додавання ручного елемента (для тестів чи демонстрації)
    public void addElement(String element) {
        collection.add(element);
    }

    // 2. Створення файлу 1.txt та запис колекції
    public void saveToFile() throws IOException {
        File file = new File(FILE_1);
        try (FileWriter writer = new FileWriter(file, false)) {
            for (String item : collection) {
                writer.write(item + "\n");
            }
        }
        System.out.println("Дані успішно записано у файл " + FILE_1);
    }

    // 3. Читання з файлу та підрахунок кількості стрічок
    public List<String> readFromFile() throws IOException {
        List<String> fileLines = new ArrayList<>();
        File file = new File(FILE_1);
        
        if (!file.exists()) {
            System.out.println("Файл " + FILE_1 + " не знайдено!");
            return fileLines;
        }

        try (FileReader reader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileLines.add(line);
            }
        }
        
        System.out.println("Кількість стрічок у файлі " + FILE_1 + ": " + fileLines.size());
        return fileLines;
    }

    // 4. Пошук елементів, введених через кому (3-й рівень), та запис у 2.txt
    public void searchAndSaveMatches(Scanner scanner) throws IOException {
        System.out.println("Введіть елементи через кому для пошуку (наприклад: item1, item2):");
        String input = scanner.nextLine();
        
        if (input == null || input.isBlank()) {
        	System.out.println("Порожній ввід!");
            return;
        }

        // Розбиваємо стрічку на окремі змінні за допомогою комі та прибираємо пробіли
        String[] searchTerms = input.split(",");
        List<String> fileLines = readFromFile();
        List<String> matchResults = new ArrayList<>();

        for (String term : searchTerms) {
            String trimmedTerm = term.trim();
            boolean found = false;
            
            for (int i = 0; i < fileLines.size(); i++) {
                if (fileLines.get(i).equals(trimmedTerm)) {
                    // Номер стрічки (рахуємо з 1)
                    int lineNumber = i + 1;
                    matchResults.add("Елемент '" + trimmedTerm + "' знайдено на стрічці №" + lineNumber);
                    found = true;
                }
            }
            if (!found) {
                matchResults.add("Елемент '" + trimmedTerm + "' НЕ знайдено у файлі.");
            }
        }

        // Записуємо результати пошуку у файл 2.txt
        try (FileWriter writer = new FileWriter(FILE_2, false)) {
            for (String result : matchResults) {
                writer.write(result + "\n");
                System.out.println(result);
            }
        }
        System.out.println("Результати пошуку збережено у файл " + FILE_2);
    }
}