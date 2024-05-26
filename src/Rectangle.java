import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The Rectangle class represents a rectangle with specified position, width, height, and color.
 */
public class Rectangle {
    private int x;       // The x-coordinate of the top-left corner of the rectangle
    private int y;       // The y-coordinate of the top-left corner of the rectangle
    private int width;   // The width of the rectangle
    private int height;  // The height of the rectangle

    /**
     * Constructor to create a new rectangle with specified position, width, and height.
     *
     * @param x      the x-coordinate of the top-left corner.
     * @param y      the y-coordinate of the top-left corner.
     * @param width  the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Draws a rectangle on the given DrawSurface with specified position, width, height, and color.
     *
     * @param d      the DrawSurface to draw on.
     * @param x      the x-coordinate of the top-left corner.
     * @param y      the y-coordinate of the top-left corner.
     * @param width  the width of the rectangle.
     * @param height the height of the rectangle.
     * @param color  the color of the rectangle.
     */
    public static void drawRectangle(DrawSurface d, int x, int y, int width, int height, Color color) {
        d.setColor(color);                           // Set the drawing color to the specified color
        d.fillRectangle(x, y, width, height);        // Draw the filled rectangle on the DrawSurface
    }
}
