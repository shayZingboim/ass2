import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.util.Random;
import java.awt.Color;

/**
 * The MultipleBouncingBallsAnimation class represents an animation of multiple bouncing balls.
 */
public class MultipleBouncingBallsAnimation {
    /**
     * The main method to start the animation.
     *
     * @param args the command line arguments: the radii of the balls.
     */
    public static void main(String[] args) {
        Ball[] balls = new Ball[args.length];
        int num;
        Random rand = new Random();

        // Create balls with specified radius
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

            // Initialize ball with random position
            balls[i] = new Ball(new Point(rand.nextDouble(Board.getWidthBoard() - num) + num,
                    rand.nextDouble(Board.getHeightBoard() - num) + num), num, Color.BLACK);

            // Set ball velocity
            Velocity vel = Velocity.fromAngleAndSpeed(45, Math.max(50 / 3, (100 - num) / 3));
            balls[i].setVelocity(vel);
        }

        // Draw and animate the balls
        drawBalls(balls);
    }

    /**
     * Draws and animates the balls.
     *
     * @param balls an array of Ball objects to be animated.
     */
    public static void drawBalls(Ball[] balls) {
        GUI gui = new GUI("Bouncing Balls", Board.getWidthBoard(), Board.getHeightBoard());
        Sleeper sleeper = new Sleeper();

        // Animation loop
        while (true) {
            DrawSurface d = gui.getDrawSurface();

            // Move and draw each ball
            for (Ball ball : balls) {
                ball.moveOneStep();
                ball.drawOn(d);
            }

            // Show the surface on the GUI
            gui.show(d);

            // Pause for 50 milliseconds
            sleeper.sleepFor(50);
        }
    }
}
