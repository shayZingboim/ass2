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
     * @param args the command line arguments: the radius of the balls.
     */
    public static void main(String[] args) {
        Ball[] balls = new Ball[args.length];
        int radius;
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
            if (radius <= 0 || radius > Math.min(Board.getHeightBoard(), Board.getWidthBoard())) {
                System.out.println("Invalid argument: " + args[i]);
                return;
            }

            // Initialize ball with random position
            balls[i] = new Ball(new Point(rand.nextDouble(Board.getWidthBoard() - 2 * radius) + radius,
                    rand.nextDouble(Board.getHeightBoard() - 2 * radius) + radius), radius, Color.BLACK);

            // Set ball velocity
            double speed = Velocity.mapSpeed(balls[i].getSize(), 2, 50, 1, 5);
            Velocity vel = Velocity.fromAngleAndSpeed(40, speed); // Set velocity at 40 degrees
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
                //Set the bounds to be the board.
                ball.setBounds(0, Board.getWidthBoard(), 0, Board.getHeightBoard());
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
