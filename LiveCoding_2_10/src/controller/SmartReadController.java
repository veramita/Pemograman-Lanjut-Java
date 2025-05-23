package controller;

import model.*;
import view.*;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SmartReadController {
    private SmartReadView view;
    private Inventory inventory;
    private int currentItem = 1;
    private int totalItems = 0;
    private double total = 0.0;
    private List<String> transactionLines = new ArrayList<>();
    private List<Map<String, String>> confirmedItems = new ArrayList<>();

    public SmartReadController(SmartReadView view, Inventory inventory) {
        this.view = view;
        this.inventory = inventory;

        view.processButton.addActionListener(e -> processItem());
        view.confirmButton.addActionListener(e -> confirmItem());
        view.viewButton.addActionListener(e -> viewOrder());
        view.finishButton.addActionListener(e -> finishOrder());
        view.newButton.addActionListener(e -> startNewOrder());
        view.exitButton.addActionListener(e -> exitApplication());
    }

    private void processItem() {
        try {
            if (currentItem == 1) {
                totalItems = Integer.parseInt(view.fieldNumItems.getText().trim());
                if (totalItems <= 0) {
                    view.transactionLog.setText("Jumlah item harus lebih dari 0.");
                    return;
                }
            }

            String id = view.fieldBookID.getText().trim();
            int qty = Integer.parseInt(view.fieldQuantity.getText().trim());

            Book book = inventory.getBook(id);
            if (book == null) {
                view.transactionLog.setText("Buku tidak ditemukan.");
                return;
            }

            double subtotal = book.price * qty;
            double pajak = subtotal * 0.06;

            Map<String, String> itemDetails = new HashMap<>();
            itemDetails.put("id", book.id);
            itemDetails.put("title", book.title);
            itemDetails.put("price", String.format("%.2f", book.price));
            itemDetails.put("qty", String.valueOf(qty));
            itemDetails.put("subtotal", String.format("%.2f", subtotal)); 
            String line = String.format("%s, %s, %.2f, %d, %.2f, $%.2f", book.id, book.title, book.price, qty, pajak, subtotal);

            view.fieldItemInfo.setText(line);
            view.transactionLog.setText("Item #" + currentItem + " siap dikonfirmasi.");
            view.processButton.setEnabled(false);
            view.confirmButton.setEnabled(true);

        } catch (NumberFormatException e) {
            view.transactionLog.setText("Masukkan angka yang valid untuk jumlah atau kuantitas.");
        }
    }

    private void confirmItem() {
        String itemInfo = view.fieldItemInfo.getText();
        if (itemInfo.isEmpty()) {
            view.transactionLog.setText("Tidak ada item untuk dikonfirmasi.");
            return;
        }

        transactionLines.add(itemInfo);

        try {
            String[] parts = itemInfo.split("\\$");
            if (parts.length > 1) {
                double itemSubtotal = Double.parseDouble(parts[1].trim());
                total += itemSubtotal;
            }
        } catch (NumberFormatException e) {
            view.transactionLog.setText("Gagal menghitung subtotal item.");
        }

        view.fieldSubtotal.setText(String.format("$%.2f", total));
        view.transactionLog.setText("Item #" + currentItem + " dikonfirmasi.");
        
        Map<String, String> confirmedItem = new HashMap<>();
        String[] infoParts = itemInfo.split(" ");
        if (infoParts.length >= 7) {
            confirmedItem.put("id", infoParts[1]);
            confirmedItem.put("title", infoParts[2]); 
            confirmedItem.put("price", infoParts[3]);
            confirmedItem.put("qty", infoParts[4]);
            confirmedItem.put("subtotal", infoParts[6].replace("$", ""));
            confirmedItems.add(confirmedItem);
        }

        currentItem++;
        if (currentItem <= totalItems) {
            view.processButton.setText("Process Item #" + currentItem);
            view.confirmButton.setText("Confirm Item #" + currentItem);
            view.label2.setText("Enter Book ID for item #" + currentItem + ":");
            view.label3.setText("Enter quantity for item #" + currentItem + ":");
            view.label4.setText("Item #" + currentItem + " info:");
            view.label5.setText("Order subtotal for " + (currentItem - 1) + " item(s):");
        } else {
            view.label5.setText("Order subtotal for " + totalItems + " item(s):");
        }

        view.confirmButton.setEnabled(false);
        view.processButton.setEnabled(true);

        if (currentItem > totalItems) {
            view.processButton.setEnabled(false);
            view.confirmButton.setEnabled(false);
            view.viewButton.setEnabled(true);
            view.finishButton.setEnabled(true);
        } else {
            view.processButton.setText("Process Item #" + currentItem);
            view.confirmButton.setText("Confirm Item #" + currentItem);
        }

        view.fieldBookID.setText("");
        view.fieldQuantity.setText("");
        view.fieldItemInfo.setText("");
    }

    private void viewOrder() {
        view.transactionLog.setText(String.join("\n", transactionLines));
        view.messageLabel.setText("Total: Rp" + String.format("%.2f", total));
    }

    private String generateTransactionID() {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        int count = 0;

        File file = new File("transactions.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith(today)) {
                        count++;
                    }
                }
            } catch (IOException e) {
                view.transactionLog.setText("Gagal membaca file transaksi.");
            }
        }
        return String.format("%s%03d", today, count + 1);
    }

    private void finishOrder() {
        String transactionID = generateTransactionID();
        double taxRate = 0.06;
        double taxAmount = total * taxRate;
        double orderTotal = total + taxAmount;

        StringBuilder message = new StringBuilder();
        message.append("Message\n\n");
        message.append("Date: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yy HH:mm:ss a 'GMT-04:00'"))).append("\n"); // Adjust GMT if needed
        message.append("Number of line items: ").append(transactionLines.size()).append("\n\n");
        message.append("ID / Title / Price / Qty / Disc % / Subtotal:\n");

        for (int i = 0; i < transactionLines.size(); i++) {
            message.append(transactionLines.get(i)).append("\n");
        }

        message.append("\nOrder subtotal: $").append(String.format("%.2f", total)).append("\n");
        message.append("Tax rate: ").append(String.format("%.0f%%", taxRate * 100)).append("\n");
        message.append("Tax amount: $").append(String.format("%.2f", taxAmount)).append("\n");
        message.append("Order total: $").append(String.format("%.2f", orderTotal)).append("\n\n");
        message.append("Thanks for shopping at E-BookStore!");

        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt", true))) {
            //<id_transaksi>,<kode buku>,<Judul>,<Harga satuan>,<jumlah beli>,<pajak>,<harga akhir>,<tanggal transaksi>
            for (String line : transactionLines) {
                writer.println(transactionID + ", " + line + ", " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        } catch (IOException e) {
            view.transactionLog.setText("Gagal menyimpan transaksi.");
            return;
        }

        JOptionPane.showMessageDialog(view, message.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startNewOrder() {
        view.dispose(); // Tutup jendela lama
        SwingUtilities.invokeLater(() -> {
            SmartReadView newView = new SmartReadView(); // Membuka jendela baru
            SmartReadController newController = new SmartReadController(newView, inventory);
        });
    }

    private void exitApplication() {
        System.exit(0);
    }
}