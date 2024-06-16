import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.Random;

/**
 * The MultipleFramesBouncingBallsAnimation class represents an animation of bouncing balls within multiple frames.
 */
public class MultipleFramesBouncingBallsAnimation {

    /**
     * The main method to start the animation.
     *
     * @param args the command line arguments: the radius of the balls.
     */
    public static void main(String[] args) {
        Ball[] balls = new Ball[args.length];
        Rectangle rec1 = new Rectangle(0, 0, Board.getWidthBoard(), Board.getHeightBoard(), Color.white);
        Rectangle rec2 = new Rectangle(50, 50, 450, 450, Color.GRAY);
        Rectangle rec3 = new Rectangle(450, 450, 150, 150, Color.YELLOW);
        int radius;
        int halfBall = args.length / 2;
        Random rand = new Random();
        if (args.length == 0) {
            System.out.println("Invalid arguments, no arguments given");
            return;
        }
        // Create balls with specified radius
        for (int i = 0; i < args.length; i++) {
            try {
                radius = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument: " + args[i]);
                return;
            }

            // Validate ball radius
            if (radius <= 2 || radius > Math.min(Board.getHeightBoard(), Board.getWidthBoard())
                    || radius >= (Board.getWidthBoard() - 500) / 2) {
                System.out.println("Invalid argument: " + args[i]);
                return;
            }

            // Place the first half of balls in the grey rectangle.
            if (i < halfBall) {
                // Ensure balls are placed within the grey rectangle.
                do {
                    Color color = randColor();
                    balls[i] = new Ball(new Point(rand.nextDouble(500 - 2 * radius - 50) + (radius + 50),
                            rand.nextDouble(500 - 2 * radius - 50) + (radius + 50)), radius, Color.BLACK);
                    double speed = Velocity.mapSpeed(balls[i].getSize(), 2, 50, 1, 5);
                    Velocity vel = Velocity.fromAngleAndSpeed(40, speed);
                    balls[i].setVelocity(vel);
                } while (balls[i].getX() + radius > 450 && balls[i].getY() + radius > 450);
            } else {
                // Ensure balls are placed outside the rectangles.
                do {
                    Color color = randColor();
                    balls[i] = new Ball(new Point(rand.nextDouble(Board.getWidthBoard() - 2 * radius)
                            + radius, rand.nextDouble(Board.getHeightBoard() - 2 * radius) + radius),
                            radius, Color.BLUE);
                    double speed = Velocity.mapSpeed(balls[i].getSize(), 2, 50, 1, 5);
                    Velocity vel = Velocity.fromAngleAndSpeed(38, speed);
                    balls[i].setVelocity(vel);
                } while ((balls[i].getX() - radius < 600 && balls[i].getX() + radius > 450
                        && balls[i].getY() + radius > 450 && balls[i].getY() - radius < 600)
                        || (balls[i].getX() - radius < 500 && balls[i].getX() + radius > 50
                        && balls[i].getY() - radius < 500 && balls[i].getY() + radius > 50));
            }
        }
        for (int i = 0; i < halfBall; i++) {
            balls[i].setBounds((int) rec2.getX(), (int) rec2.getX() + rec2.getWidth(),
                    (int) rec2.getY(), (int) rec2.getY() + rec2.getHeight());
        }
        for (int i = halfBall; i < balls.length; i++) {
            balls[i].setBounds(0, Board.getWidthBoard(), 0, Board.getHeightBoard());
        }
        // Draw and animate the balls within frames.
        drawBalls(balls, rec2, rec3);
    }

    /**
     * Draws and animates the balls within frames.
     *
     * @param balls an array of Ball objects to be animated.
     * @param rec2  the first rectangle frame.
     * @param rec3  the second rectangle frame.
     */
    public static void drawBalls(Ball[] balls, Rectangle rec2, Rectangle rec3) {
        GUI gui = new GUI("Bouncing Frame Balls", Board.getWidthBoard(), Board.getHeightBoard());
        Sleeper sleeper = new Sleeper();
        // Animation loop.
        while (true) {
            DrawSurface d = gui.getDrawSurface();
            //Draw grey rectangle.
            rec2.drawRectangle(d);
            // Draw yellow rectangle.
            rec3.drawRectangle(d);
            // Move and draw each ball.
            for (int i = 0; i < balls.length / 2; i++) {
                moveBetweenLine(balls[i], rec3);
            }
            for (int i = balls.length / 2; i < balls.length; i++) {
                moveBetweenLine(balls[i], rec2);
                moveBetweenLine(balls[i], rec3);
            }
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }
        // Show the surface on the GUI.
        gui.show(d);

        // Pause for 50 milliseconds before the next iteration.
        sleeper.sleepFor(50);
    }
}

/**
 * Generates a random color.
 *
 * @return A randomly generated Color object.
 */
public static Color randColor() {
    Random rand = new Random();
    return new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
}

public static void moveBetweenLine(Ball ball, Rectangle rec) {
    if (ball.isIntersectingRec(rec)) {
        if (ball.closestIntersection(rec).getY() - ball.getY() == 0) {
            ball.setVelocity(-ball.getVelocity().getDx(), ball.getVelocity().getDy());
        } else if (ball.closestIntersection(rec).getX() - ball.getX() == 0) {
            ball.setVelocity(ball.getVelocity().getDx(), -ball.getVelocity().getDy());
        } else {
            ball.setVelocity(-ball.getVelocity().getDx(), -ball.getVelocity().getDy());
        }
    }
}
}
