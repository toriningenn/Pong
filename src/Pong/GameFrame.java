package Pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    //только рамка
    GamePanel panel;

    public GameFrame()  {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Pong Game");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); //приспособится по размерам под панель
        this.setVisible(true);
        this.setLocationRelativeTo(null); //расположить посередине экрана
    }
}
