import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.util.Random;
import java.awt.Color;

public class BouncingBallAnimation {
    public static void main(String[] args) {
        int[] newArgs = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            newArgs[i] = Integer.parseInt(args[i]);
        }
        if (newArgs[0] - 30 < 0 || newArgs[0] + 30 > Board.getWidthBoard()
                || newArgs[1] - 30 < 0 || newArgs[1] + 30 > Board.getHeightBoard()) {
            System.out.println("Invalid arguments");
            return;
        }
        drawAnimation(new Point(newArgs[0], newArgs[1]), newArgs[2], newArgs[3]);
    }

    static private void drawAnimation(Point start, double dx, double dy) {
        GUI gui = new GUI("title", Board.getWidthBoard(), Board.getHeightBoard());
        Sleeper sleeper = new Sleeper();
        Ball ball = new Ball(start, 30, java.awt.Color.BLACK);
        ball.setVelocity(dx, dy);
        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50); // wait for 50 milliseconds.
        }
    }
}