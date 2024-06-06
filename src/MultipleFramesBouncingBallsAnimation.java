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
            if (radius <= 0 || radius > Math.min(Board.getHeightBoard(), Board.getWidthBoard())
                    || radius >= (Board.getWidthBoard() - 500) / 2) {
                System.out.println("Invalid argument: " + args[i]);
                return;
            }

            // Place the first half of balls in the grey rectangle.
            if (i <= (halfBall - 1)) {
                // Ensure balls are placed within the grey rectangle.
                do {
                    Color color = randColor();
                    balls[i] = new Ball(new Point(rand.nextDouble(500 - 2 * radius - 50) + (radius + 50),
                            rand.nextDouble(500 - 2 * radius - 50) + (radius + 50)), radius, Color.BLACK);
                    Velocity vel = Velocity.fromAngleAndSpeed(40, Math.max(50 / 5, (100 - radius) / 5));
                    balls[i].setVelocity(vel);
                } while (balls[i].getX() + radius > 450 && balls[i].getY() + radius > 450);
            } else {
                // Ensure balls are placed outside the rectangles.
                do {
                    Color color = randColor();
                    balls[i] = new Ball(new Point(rand.nextDouble(Board.getWidthBoard() - 2 * radius) + radius,
                            rand.nextDouble(Board.getHeightBoard() - 2 * radius) + radius), radius, Color.BLUE);
                    Velocity vel = Velocity.fromAngleAndSpeed(38, Math.max(50 / 5, (100 - radius) / 5));
                    balls[i].setVelocity(vel);
                } while ((balls[i].getX() - radius < 600 && balls[i].getX() + radius > 450
                        && balls[i].getY() + radius > 450 && balls[i].getY() - radius < 600)
                        || (balls[i].getX() - radius < 500 && balls[i].getX() + radius > 50
                        && balls[i].getY() - radius < 500 && balls[i].getY() + radius > 50));
            }
        }
        // Draw and animate the balls within frames.
        drawBalls(balls, rec1, rec2, rec3);
    }

    /**
     * Draws and animates the balls within frames.
     *
     * @param balls an array of Ball objects to be animated.
     * @param rec1  the board.
     * @param rec2  the first rectangle frame.
     * @param rec3  the second rectangle frame.
     */
    public static void drawBalls(Ball[] balls, Rectangle rec1, Rectangle rec2, Rectangle rec3) {
        GUI gui = new GUI("Bouncing Frame Balls", Board.getWidthBoard(), Board.getHeightBoard());
        Sleeper sleeper = new Sleeper();

        // Animation loop.
        while (true) {
            DrawSurface d = gui.getDrawSurface();

            // Draw grey rectangle.
            rec2.drawRectangle(d);
            // Move and draw each ball.
            for (int i = 0; i < balls.length; i++) {
                if (i < balls.length / 2) {
                    balls[i].moveBetweenLine(rec2, true);
                } else {
                    balls[i].moveBetweenLine(rec2, false);
                    balls[i].moveBetweenLine(rec3, false);
                    balls[i].moveBetweenLine(rec1, true);
                }
                //ball.moveOneStep();
                balls[i].drawOn(d);
            }
            //Draw yellow rectangle.
            rec3.drawRectangle(d);
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
}
