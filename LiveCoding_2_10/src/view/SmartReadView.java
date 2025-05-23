package view;

import javax.swing.*;
import java.awt.*;

public class SmartReadView extends JFrame {
    public JTextField fieldNumItems, fieldBookID, fieldQuantity, fieldItemInfo, fieldSubtotal;
    public JButton processButton, confirmButton, viewButton, finishButton, newButton, exitButton;
    public JTextArea transactionLog;
    public JLabel messageLabel;
    public JLabel label2, label3, label4, label5;


    public SmartReadView() {
        setTitle("ReadBook - Order Form");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.DARK_GRAY);

        Font font = new Font("Arial", Font.BOLD, 14);
        Color textColor = Color.ORANGE;

        JLabel label1 = new JLabel("Enter number of Items in this order:");
        label1.setBounds(30, 30, 300, 25);
        label1.setFont(font);
        label1.setForeground(textColor);
        add(label1);
        fieldNumItems = new JTextField();
        fieldNumItems.setBounds(350, 30, 300, 25);
        add(fieldNumItems);

        label2 = new JLabel("Enter Book ID for item #1:");
        label2.setBounds(30, 65, 300, 25);
        label2.setFont(font);
        label2.setForeground(textColor);
        add(label2);
        fieldBookID = new JTextField();
        fieldBookID.setBounds(350, 65, 300, 25);
        add(fieldBookID);

        label3 = new JLabel("Enter quantity for item #1:");
        label3.setBounds(30, 100, 300, 25);
        label3.setFont(font);
        label3.setForeground(textColor);
        add(label3);
        fieldQuantity = new JTextField();
        fieldQuantity.setBounds(350, 100, 300, 25);
        add(fieldQuantity);

        label4 = new JLabel("Item #1 info:");
        label4.setBounds(30, 135, 300, 25);
        label4.setFont(font);
        label4.setForeground(textColor);
        add(label4);
        fieldItemInfo = new JTextField();
        fieldItemInfo.setBounds(350, 135, 300, 25);
        fieldItemInfo.setEditable(false);
        add(fieldItemInfo);

        label5 = new JLabel("Order subtotal for 0 item(s):");
        label5.setBounds(30, 170, 300, 25);
        label5.setFont(font);
        label5.setForeground(textColor);
        add(label5);
        fieldSubtotal = new JTextField();
        fieldSubtotal.setBounds(350, 170, 300, 25);
        fieldSubtotal.setEditable(false);
        add(fieldSubtotal);

        processButton = new JButton("Process Item #1");
        processButton.setBounds(30, 220, 150, 30);
        add(processButton);

        confirmButton = new JButton("Confirm Item #1");
        confirmButton.setBounds(190, 220, 150, 30);
        add(confirmButton);

        viewButton = new JButton("View Order");
        viewButton.setBounds(350, 220, 100, 30);
        add(viewButton);

        finishButton = new JButton("Finish Order");
        finishButton.setBounds(460, 220, 120, 30);
        add(finishButton);

        newButton = new JButton("New Order");
        newButton.setBounds(590, 220, 100, 30);
        add(newButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(590, 260, 100, 30);
        add(exitButton);

        // Area untuk menampilkan log transaksi
        transactionLog = new JTextArea();
        transactionLog.setEditable(false);
        transactionLog.setBackground(Color.LIGHT_GRAY);
        transactionLog.setLineWrap(true); 
        transactionLog.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(transactionLog);
        scrollPane.setBounds(30, 260, 540, 90); 
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
        add(scrollPane);

        // Label untuk menampilkan pesan
        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.BLACK);
        messageLabel.setBounds(20, 470, 540, 20);
        add(messageLabel);

        // Atur visibilitas awal tombol
        confirmButton.setEnabled(false);
        viewButton.setEnabled(false);
        finishButton.setEnabled(false);

        setVisible(true);
    }
}
