import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;

public class AbstractArtDrawing {
    public static void main(String[] args) {
        AbstractArtDrawing art = new AbstractArtDrawing();
        art.drawBoard();
    }

    public void drawBoard() {
        GUI gui = new GUI("Random Circles Example", 400, 300);
        DrawSurface l = gui.getDrawSurface();
        l.setColor(Color.BLACK);
        Line[] lines = new Line[10];
        for (int i = 0; i < 10; i++) {
            lines[i] = generateRandomLine();
            drawLine(lines[i], l);
        }
        for (int i = 0; i < 10; i++) {
            l.setColor(Color.BLUE);
            l.fillCircle((int) lines[i].middle().getX(), (int) lines[i].middle().getY(), 3);
            for (int j = 0; j < 10; j++) {
                l.setColor(Color.RED);
                if (lines[i].intersectionWith(lines[j]) != null) {
                    l.fillCircle((int) lines[i].intersectionWith(lines[j]).getX(),
                            (int) lines[i].intersectionWith(lines[j]).getY(), 3);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                for (int k = j + 1; k < 10; k++) {
                    if (lines[i].isIntersecting(lines[j], lines[k])
                            && lines[j].isIntersecting(lines[k])
                            && (lines[i].intersectionWith(lines[j]) != null)
                            && (lines[i].intersectionWith(lines[k]) != null)
                            && (lines[j].intersectionWith(lines[k]) != null)) {
                        l.setColor(Color.GREEN);
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
        gui.show(l);
    }

    public static Line generateRandomLine() {
        Random rand = new Random();
        return new Line(rand.nextDouble(400) + 1, rand.nextDouble(300) + 1,
                rand.nextDouble(400) + 1, rand.nextDouble(300) + 1);
    }

    public static void drawLine(Line l, DrawSurface d) {
        d.drawLine((int) l.start().getX(), (int) l.start().getY(), (int) l.end().getX(), (int) l.end().getY());
    }
}
