package Pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER = 20;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    public GamePanel() {
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        //События отправляются только в компонент, который имеет фокус. KeyEvent будет отправлен на панель только в том случае,
        //если она "focusable".
        this.addKeyListener(new AL()); //объект нашего класса AL
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void newBall() {
        //мы хотим чтобы мяч начинал движение с середины экрана, передаём точные координаты конструктору
       // ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),(GAME_HEIGHT/2)-(BALL_DIAMETER/2),BALL_DIAMETER,BALL_DIAMETER);
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
    }

    public void newPaddles() {
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        //у игрока 1 слева, поэтому x=0, у игрока 2 справа
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
        // GAME_WIDTH-PADDLE_WIDTH чтобы сместить максимально вправо.
        //(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2) начальная y позиция

    }
    public void paint(Graphics g) {
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics); //!!!!передаём графику в draw, который лежит в этом классе, он передаёт ее
        //методам draw остальных классов как параметр
        g.drawImage(image,0,0,this);
                //в верхнем левом углу коррдинаты 0 0
        

    }
    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        Toolkit.getDefaultToolkit().sync(); //улучшает анимацию
    }

    public void move() {
        //будет вызывать move() после каждого цикла лупа. Это ускорит их движение
        paddle1.move();
        paddle2.move();
        ball.move();
    }


    public void checkCollision() {
        //отбивает мяч от платформы
        //метод класса rectangle
        if (ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity); //xVelocity негативная если мяч летит влево
            ball.xVelocity++;//скорость повышается для сложности
            if (ball.yVelocity > 0)
                ball.yVelocity++; //если летит вниз то можно ускорить
            else
                ball.yVelocity--;

            //летит вправо
            ball.setXDirection(ball.xVelocity);
            ball.setYDirection(ball.yVelocity);
        }

        if (ball.intersects(paddle2)) {
            ball.xVelocity = Math.abs(ball.xVelocity); //xVelocity негативная если мяч летит влево
            ball.xVelocity++;//скорость повышается для сложности
            if (ball.yVelocity > 0)
                ball.yVelocity++; //если летит вниз то можно ускорить
            else
                ball.yVelocity--;
        //летит влево
        ball.setXDirection(-ball.xVelocity);
        ball.setYDirection(-ball.yVelocity);
    }
        //отскакивающий мяч
        if (ball.y <=0) {
            ball.setYDirection(-ball.yVelocity);
        }
        if(ball.y >= GAME_HEIGHT-BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }
        //останавливает платформы по бокам экранa
        //вверх
        if(paddle1.y<=0) {
            paddle1.y=0;
        }
        //вниз
        if(paddle1.y >=(GAME_HEIGHT-PADDLE_HEIGHT)) {
            paddle1.y=GAME_HEIGHT-PADDLE_HEIGHT;
        }
        if(paddle2.y<=0) {
            paddle2.y=0;
        }
        if(paddle2.y >=(GAME_HEIGHT-PADDLE_HEIGHT)) {
            paddle2.y=GAME_HEIGHT-PADDLE_HEIGHT;
        }

        //даёт игроку балл и создаёт новый мяч и платформы
        if(ball.x <=0) {
            score.player2++;
            newPaddles();
            newBall();
        }

        if(ball.x >= GAME_WIDTH-BALL_DIAMETER) {
            score.player1++;
            newPaddles();
            newBall();
        }

    }
    public void run() {
        //game loop
        long lastTime = System.nanoTime(); //время работы JVM, этим можно измерить время выполнения кода
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            if(delta >=1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }

    }

    //action listener

    public class AL extends KeyAdapter {
        //переназначаем методы KeyAdapter. AL потом используется в конструкторе панели this.addKeyListener(new AL());
        public void keyPressed(KeyEvent e) {
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }

    }

}
