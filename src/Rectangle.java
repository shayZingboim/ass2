import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

import java.awt.*;
import java.util.Random;

public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public static void drawRectangle(DrawSurface d, int x, int y, int width, int height, Color color) {
        d.setColor(color);
        d.fillRectangle(x, y, width, height);
    }
}
