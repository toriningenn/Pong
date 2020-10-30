package Pong;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle {

    int id; //для игрока 1 и игрока 2
    int yVelocity;
    int speed = 10;
    //скорость с которой двигается платформа

    public Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {
        super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT); //super в данном случае будет конструктором из rectangle, где уже есть
        //все эти значения кроме id, поэтому они автоматически есть и у Paddle
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        switch (id) {
            //только первая педаль
            case 1:
                //если нажимаем W - запускается этот код
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(-speed); //вверх
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(speed);
                    move();
                }
                break;
            case 2:
                //если нажимаем W - запускается этот код
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(-speed); //вверх
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(speed);
                    move();
                }
                break;
        }

    }

    public void keyReleased(KeyEvent e) {
        switch (id) {
            case 1:
                //0 чтобы не двигалась
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(0); //вверх
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(0);
                    move();
                }
                break;
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(0); //вверх
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(0);
                    move();
                }
                break;
        }
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;

    }

    public void move() {
        y= y + yVelocity;
    }

    public void draw(Graphics g) {
        if (id == 1)
            g.setColor(Color.blue);
        else
            g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }
}
