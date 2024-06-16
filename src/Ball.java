import biuoop.DrawSurface;

import java.awt.Color;

/**
 * The Ball class represents a ball with a center point, radius, color, and velocity.
 */
public class Ball {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private int left, right, top, bottom;
    public static final double EPSILON = 0.00000001;

    /**
     * Constructor to create a new ball with a specified center, radius, and color.
     *
     * @param center the center point of the ball.
     * @param r      the radius of the ball.
     * @param color  the color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);  // Initialize velocity to zero
        this.left = 0;
        this.right = 0;
        this.top = 0;
        this.bottom = 0;
    }

    /**
     * Gets the x-coordinate of the ball's center.
     *
     * @return the x-coordinate.
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Gets the y-coordinate of the ball's center.
     *
     * @return the y-coordinate.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Gets the radius of the ball.
     *
     * @return the radius.
     */
    public int getSize() {
        return this.radius;
    }

    public void setBounds(int x, int y, int z, int s) {
        this.left = x;
        this.right = y;
        this.top = z;
        this.bottom = s;
    }

    /**
     * Gets the color of the ball.
     *
     * @return the color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw on.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the new velocity.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball using dx and dy components.
     *
     * @param dx the change in x-coordinate per time step.
     * @param dy the change in y-coordinate per time step.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Gets the current velocity of the ball.
     *
     * @return the velocity.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    public void moveOneStep() {
        if ((this.getX() - this.radius <= left) || (this.getX() + this.radius >= right)) {
            setVelocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        if ((this.getY() - this.radius <= top) || (this.getY() + this.radius >= bottom)) {
            setVelocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        this.center = this.velocity.applyToPoint(this.center);
    }

    public Point closestIntersection(Rectangle rec) {
        Point startOfRec = new Point(rec.getX(), rec.getY());
        Point endOfRec = new Point(rec.getX() + rec.getWidth(), rec.getY() + rec.getHeight());
        Point closest = new Point(this.getX(), this.getY());
        if (closest.getX() < startOfRec.getX()) {
            closest.setX(startOfRec.getX());
        } else if (closest.getX() > endOfRec.getX()) {
            closest.setX(endOfRec.getX());
        }
        if (closest.getY() < startOfRec.getY()) {
            closest.setY(startOfRec.getY());
        } else if (closest.getY() > endOfRec.getY()) {
            closest.setY(endOfRec.getY());
        }
        return closest;
    }

    public boolean isIntersectingRec(Rectangle rec) {
        Line checkLine = new Line(this.center, closestIntersection(rec));
        if (checkLine.length() <= this.radius) {
            return true;
        }
        return false;
    }
}