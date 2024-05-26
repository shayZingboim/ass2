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
     * @param args the command line arguments: the radii of the balls.
     */
    public static void main(String[] args) {
        Ball[] balls = new Ball[args.length];
        int num;
        int halfBall = args.length / 2;
        Random rand = new Random();

        // Create balls with specified radii
        for (int i = 0; i < args.length; i++) {
            try {
                num = Integer.parseInt(args[i]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid argument: " + args[i]);
                return;
            }

            // Validate ball radius
            if (num <= 0 || num > Math.min(Board.getHeightBoard(), Board.getWidthBoard())) {
                System.out.println("Invalid argument: " + args[i]);
                return;
            }

            // Place balls in different frames
            if (i <= (halfBall - 1)) {
                // Ensure balls are placed within the left frame
                do {
                    balls[i] = new Ball(new Point(rand.nextDouble(500 - num - 50) + (num + 50),
                            rand.nextDouble(500 - num - 50) + (num + 50)), num, Color.BLACK);
                    Velocity vel = Velocity.fromAngleAndSpeed(0, 0);
                    balls[i].setVelocity(vel);
                } while (balls[i].getX() + num > 450 && balls[i].getY() + num > 450);
            } else {
                // Ensure balls are placed within the right frame
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

        // Draw and animate the balls within frames
        drawBalls(balls);
    }

    /**
     * Draws and animates the balls within frames.
     *
     * @param balls an array of Ball objects to be animated.
     */
    public static void drawBalls(Ball[] balls) {
        GUI gui = new GUI("Bouncing Frame Balls", Board.getWidthBoard(), Board.getHeightBoard());
        Sleeper sleeper = new Sleeper();

        // Animation loop
        while (true) {
            DrawSurface d = gui.getDrawSurface();

            // Draw frame rectangles
            Rectangle.drawRectangle(d, 50, 50, 450, 450, Color.GRAY);
            Rectangle.drawRectangle(d, 450, 450, 150, 150, Color.YELLOW);

            // Move and draw each ball
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }

            // Show the surface on the GUI
            gui.show(d);

            // Pause for 50 milliseconds before the next iteration
            sleeper.sleepFor(50);
        }
    }
}
