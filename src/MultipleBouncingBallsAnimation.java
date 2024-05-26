import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.util.Random;
import java.awt.Color;


public class MultipleBouncingBallsAnimation {
    public static void main(String[] args) {
        Ball[] balls = new Ball[args.length];
        int num;
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
            balls[i] = new Ball(new Point(rand.nextDouble(Board.getWidthBoard() - num) + num,
                    rand.nextDouble(Board.getHeightBoard() - num) + num), num, Color.BLACK);
            Velocity vel = Velocity.fromAngleAndSpeed(45, Math.max(50 / 3, (100 - num) / 3));
            balls[i].setVelocity(vel);
        }
        drawBalls(balls);
    }

    public static void drawBalls(Ball[] balls) {
        GUI gui = new GUI("bouncing balls", Board.getWidthBoard(), Board.getHeightBoard());
        Sleeper sleeper = new Sleeper();
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }
    }
}
