import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.util.Random;
import java.awt.Color;


public class Ball {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;

    // constructor
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
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

    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    public Velocity getVelocity() {
        return this.velocity;
    }

    public void moveOneStep() {
        if (this.center.getX() + this.radius + this.velocity.getDx() > Board.getWidthBoard()
                || this.center.getX() - this.radius + this.velocity.getDx() < 0) {
            this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
        }
        if (this.center.getY() + this.radius + this.velocity.getDy() > Board.getHeightBoard()
                || this.center.getY() - this.radius + this.velocity.getDy() < 0) {
            this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());
        }
        this.center = this.getVelocity().applyToPoint(this.center);
    }

}
