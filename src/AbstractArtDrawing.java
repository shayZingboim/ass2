import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

/**
 * The AbstractArtDrawing class generates a drawing of random lines and circles.
 */
public class AbstractArtDrawing {

    /**
     * The main method to run the abstract art drawing program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        AbstractArtDrawing art = new AbstractArtDrawing();
        art.drawBoard();
    }

    /**
     * Draws the board with random lines and circles indicating midpoints and intersections.
     */
    public void drawBoard() {
        GUI gui = new GUI("Random Circles Example", 400, 300);
        DrawSurface l = gui.getDrawSurface();
        l.setColor(Color.BLACK);

        // Generate 10 random lines and draw them
        Line[] lines = new Line[10];
        for (int i = 0; i < 10; i++) {
            lines[i] = generateRandomLine();
            drawLine(lines[i], l);
        }


        // Draw green triangles for intersecting triplets of lines.
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                for (int k = j + 1; k < 10; k++) {
                    if (lines[i].isIntersecting(lines[j], lines[k])
                            && lines[j].isIntersecting(lines[k])
                            && (lines[i].intersectionWith(lines[j]) != null)
                            && (lines[i].intersectionWith(lines[k]) != null)
                            && (lines[j].intersectionWith(lines[k]) != null)) {
                        l.setColor(Color.GREEN);

                        // Draw the triangle formed by the intersections.
                        Line line1 = new Line(lines[i].intersectionWith(lines[j]), lines[i].intersectionWith(lines[k]));
                        Line line2 = new Line(lines[j].intersectionWith(lines[k]), lines[i].intersectionWith(lines[j]));
                        Line line3 = new Line(lines[j].intersectionWith(lines[k]), lines[i].intersectionWith(lines[k]));
                        drawLine(line1, l);
                        drawLine(line2, l);
                        drawLine(line3, l);
                    }
                }
            }
        }
        // Draw blue circles at the midpoints of the lines.
        for (int i = 0; i < 9; i++) {
            l.setColor(Color.BLUE);
            l.fillCircle((int) lines[i].middle().getX(), (int) lines[i].middle().getY(), 3);

            // Check for intersections with other lines and draw red circles at intersections.
            for (int j = i + 1; j < 10; j++) {
                l.setColor(Color.RED);
                if (lines[i].intersectionWith(lines[j]) != null) {
                    l.fillCircle((int) lines[i].intersectionWith(lines[j]).getX(),
                            (int) lines[i].intersectionWith(lines[j]).getY(), 3);
                }
            }
        }

        // Display the drawing.
        gui.show(l);
    }

    /**
     * Generates a random line within the bounds of the DrawSurface.
     *
     * @return A new random Line object.
     */
    public static Line generateRandomLine() {
        Random rand = new Random();
        return new Line(rand.nextDouble() * 400 + 1, rand.nextDouble() * 300 + 1,
                rand.nextDouble() * 400 + 1, rand.nextDouble() * 300 + 1);
    }

    /**
     * Draws a line on the given DrawSurface.
     *
     * @param l The line to draw.
     * @param d The DrawSurface to draw on.
     */
    public static void drawLine(Line l, DrawSurface d) {
        d.drawLine((int) l.start().getX(), (int) l.start().getY(), (int) l.end().getX(), (int) l.end().getY());
    }
}
