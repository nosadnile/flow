package net.nosadnile.flow.app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class AppMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Flow by NoSadNile");
        JLabel label = new JLabel("Why are you here. This isn't how plugins work. Just go. LMAO.", SwingConstants.CENTER);
        label.setFont(new Font(Font.SANS_SERIF, label.getFont().getStyle(), label.getFont().getSize()));
        label.setForeground(new Color(255, 255, 255));
        JPanel center = new JPanel(new GridLayout(0, 1));
        center.add(label);
        center.setBackground(new Color(31, 33, 32));
        frame.add(center);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InputStream iconInputStream = AppMain.class.getClassLoader().getResourceAsStream("icons/icon.png");
        BufferedImage iconBuffered = null;
        try {
            iconBuffered = ImageIO.read(iconInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setIconImage(iconBuffered);
        frame.getContentPane().setBackground(new Color(31, 33, 32));
        frame.setVisible(true);
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setResizable(false);
        frame.setSize(600, 400);
        System.out.println("Execution finished.");
    }
}
