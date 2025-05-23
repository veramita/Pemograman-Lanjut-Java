package model;

import java.io.*;
import java.util.*;

public class Inventory {
    private Map<String, Book> books = new HashMap<>();

    public Inventory(String filePath) {
        loadInventory(filePath);
    }

    private void loadInventory(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3) {
                    books.put(parts[0], new Book(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
        } catch (IOException e) {
            System.err.println("Gagal membaca file inventory.");
        }
    }

    public Book getBook(String id) {
        return books.get(id);
    }
}