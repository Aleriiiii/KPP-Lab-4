package ua.khnu.kpp;

import java.io.IOException;
import java.util.Scanner;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        System.out.println("=== Демонстрація ЛР № 4 (Робота із файлами) ===");
        FileManager manager = new FileManager();

        try {
            // Додамо кілька гарантованих елементів для зручності перевірки пошуку
            manager.addElement("Alpha123");
            manager.addElement("Beta456");
            
            // Генеруємо решту випадкових даних
            manager.generateRandomData(5);

            // Зберігаємо у 1.txt
            manager.saveToFile();

            // Читаємо з файлу
            System.out.println("\n--- Вміст файлу 1.txt ---");
            for (String line : manager.readFromFile()) {
                System.out.println(line);
            }
            System.out.println("------------------------\n");

            // Імітація введення користувача для 3-го рівня (наприклад, шукаємо "Alpha123, WrongItem, Beta456")
            System.out.println("Тестування пошуку (введіть через кому, наприклад: Alpha123, Beta456):");
            try (Scanner scanner = new Scanner(System.in)) {
                manager.searchAndSaveMatches(scanner);
            }

        } catch (IOException e) {
            System.err.println("Постала помилка при роботі з файлами: " + e.getMessage());
        }
    }
}