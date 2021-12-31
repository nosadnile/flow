package net.nosadnile.flow.app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class AppMain {
    private static JFrame app;
    private static JPanel main;

    public static void main(String[] args) throws IOException {
        app = new JFrame("Flow by NoSadNile");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(600, 400);
        JFrame.setDefaultLookAndFeelDecorated(true);
        main = new JPanel();
        main.setBackground(new Color(31, 33, 32));
        app.add(main);
        InputStream iconInputStream = AppMain.class.getClassLoader().getResourceAsStream("icons/icon.png");
        BufferedImage iconBuffered = ImageIO.read(iconInputStream);
        app.setIconImage(iconBuffered);
        app.getContentPane().setBackground(new Color(31, 33, 32));
        app.setVisible(true);
    }
}
