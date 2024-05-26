import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

/**
 * The BouncingBallAnimation class represents an animation of a bouncing ball.
 */
public class BouncingBallAnimation {

    /**
     * The main method to start the animation.
     *
     * @param args the command line arguments: initial x position, initial y position, dx, dy.
     */
    public static void main(String[] args) {
        int[] newArgs = new int[args.length];
        // Parse command line arguments to integers
        for (int i = 0; i < args.length; i++) {
            newArgs[i] = Integer.parseInt(args[i]);
        }

        // Check if the ball's initial position is within the valid range
        if (newArgs[0] - 30 < 0 || newArgs[0] + 30 > Board.getWidthBoard()
                || newArgs[1] - 30 < 0 || newArgs[1] + 30 > Board.getHeightBoard()) {
            System.out.println("Invalid arguments");
            return;
        }

        // Start the animation with the given initial position and velocity
        drawAnimation(new Point(newArgs[0], newArgs[1]), newArgs[2], newArgs[3]);
    }

    /**
     * Draws and animates a bouncing ball.
     *
     * @param start the starting point of the ball.
     * @param dx    the velocity in the x direction.
     * @param dy    the velocity in the y direction.
     */
    private static void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("Bouncing Ball Animation", Board.getWidthBoard(), Board.getHeightBoard());
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start, 30, Color.BLACK);
        ball.setVelocity(dx, dy);

        // Animation loop
        while (true) {
            ball.moveOneStep();              // Move the ball one step
            DrawSurface d = gui.getDrawSurface();  // Get the drawing surface
            ball.drawOn(d);                  // Draw the ball on the surface
            gui.show(d);                     // Show the surface on the GUI
            sleeper.sleepFor(50);            // Wait for 50 milliseconds
        }
    }
}
