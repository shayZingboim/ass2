import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;


public class Ball {
    private Point center;
    private int radius;
    private Color color;

    // constructor
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }

    // accessors
    public int getX() {
        return (int) this.center.getX();
    }

    public int getY() {
        return (int) this.center.getY();
    }

    public int getSize() {
        return this.radius;
    }

    public java.awt.Color getColor() {
        return this.color;
    }

    // draw the ball on the given DrawSurface
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    ;
}