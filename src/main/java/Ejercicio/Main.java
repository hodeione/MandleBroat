package Ejercicio;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MandelBrotRendeer mandelBrotRendeer = new MandelBrotRendeer();
            mandelBrotRendeer.setSize(800, 600);
            mandelBrotRendeer.setVisible(true);
            mandelBrotRendeer.setLocationRelativeTo(null);
            mandelBrotRendeer.setResizable(false);
        });
    }
}
