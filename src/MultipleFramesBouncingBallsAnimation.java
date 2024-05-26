import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {
    public static void main(String[] args) {
        Ball[] balls = new Ball[args.length];
        int num;
        int halfBall = args.length / 2;
        Random rand = new Random();
        for (int i = 0; i < args.length; i++) {
            try {
                num = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument: " + args[i]);
                return;
            }
            if (num <= 0 || num > Math.min(Board.getHeightBoard(), Board.getWidthBoard())) {
                System.out.println("Invalid argument: " + args[i]);
                return;
            }
            if (i <= (halfBall - 1)) {
                do {
                    balls[i] = new Ball(new Point(rand.nextDouble(500 - num - 50) + (num + 50),
                            rand.nextDouble(500 - num - 50) + (num + 50)), num, Color.BLACK);
                    Velocity vel = Velocity.fromAngleAndSpeed(0, 0);
                    balls[i].setVelocity(vel);
                } while (balls[i].getX() + num > 450 && balls[i].getY() + num > 450);
            } else {
                do {
                    balls[i] = new Ball(new Point(rand.nextDouble(Board.getWidthBoard() - 2 * num) + num,
                            rand.nextDouble(Board.getHeightBoard() - 2 * num) + num), num, Color.BLACK);
                    Velocity vel = Velocity.fromAngleAndSpeed(0, 0);
                    balls[i].setVelocity(vel);
                } while ((balls[i].getX() - num < 600 && balls[i].getX() + num > 450
                        && balls[i].getY() + num > 450 && balls[i].getY() - num < 600)
                        || (balls[i].getX() - num < 500 && balls[i].getX() + num > 50
                        && balls[i].getY() - num < 500 && balls[i].getY() + num > 50));
            }
        }
        drawBalls(balls);
    }

    public static void drawBalls(Ball[] balls) {
        GUI gui = new GUI("bouncing frame balls", Board.getWidthBoard(), Board.getHeightBoard());
        Sleeper sleeper = new Sleeper();
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            Rectangle.drawRectangle(d, 50, 50, 450, 450, Color.GRAY);
            Rectangle.drawRectangle(d, 450, 450, 150, 150, Color.YELLOW);
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }
    }
}
