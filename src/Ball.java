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
        this.velocity = new Velocity(0, 0);
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

    /**
     * Moves the ball one step according to its velocity, and bounces off walls if it hits them.
     */
    public void moveOneStep() {
        // Check for collision with the left or right walls
        if (this.center.getX() + this.radius + this.velocity.getDx() > Board.getWidthBoard()
                || this.center.getX() - this.radius + this.velocity.getDx() < 0) {
            this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
        }
        // Check for collision with the top or bottom walls
        if (this.center.getY() + this.radius + this.velocity.getDy() > Board.getHeightBoard()
                || this.center.getY() - this.radius + this.velocity.getDy() < 0) {
            this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());
        }
        // Update the center of the ball according to the velocity
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    public void moveBetweenLimit(Rectangle rectangle1) {
        Line[] lines1 = rectangle1.frameToLine();
        Line[] lines2 = new Line[lines1.length];
        lines2[0] = new Line(lines1[0].start().getX() - this.getSize(), lines1[0].start().getY() - this.getSize(),
                lines1[0].end().getX() + this.getSize(), lines1[0].end().getY() - this.getSize());
        lines2[1] = new Line(lines1[1].start().getX() - this.getSize(), lines1[1].start().getY() + this.getSize(),
                lines1[1].end().getX() - this.getSize(), lines1[1].end().getY() - this.getSize());
        lines2[2] = new Line(lines1[2].start().getX() - this.getSize(), lines1[2].start().getY() + this.getSize(),
                lines1[2].end().getX() + this.getSize(), lines1[2].end().getY() + this.getSize());
        lines2[3] = new Line(lines1[3].start().getX() + this.getSize(), lines1[3].start().getY() + this.getSize(),
                lines1[3].end().getX() + this.getSize(), lines1[3].end().getY() - this.getSize());
        double numDx = this.velocity.getDx();
        double numDy = this.velocity.getDy();
        Line checkLine;
        if (numDx > 0 && numDy > 0) {
            checkLine = new Line(this.getX(), this.getY(),
                    this.getX() + this.getSize() + this.velocity.getDx(),
                    this.getY() + this.getSize() + this.velocity.getDy());
        } else if (numDx > 0 && numDy < 0) {
            checkLine = new Line(this.getX(), this.getY(),
                    this.getX() + this.getSize() + this.velocity.getDx(),
                    this.getY() - this.getSize() + this.velocity.getDy());
        } else if (numDx < 0 && numDy > 0) {
            checkLine = new Line(this.getX(), this.getY(),
                    this.getX() - this.getSize() + this.velocity.getDx(),
                    this.getY() + this.getSize() + this.velocity.getDy());
        } else {
            checkLine = new Line(this.getX(), this.getY(),
                    this.getX() - this.getSize() + this.velocity.getDx(),
                    this.getY() - this.getSize() + this.velocity.getDy());
        }
        for (int i = 0; i < lines1.length; i++) {
            if (checkLine.isIntersecting(lines2[i])) {
                checkLine = new Line(this.getX(), this.getY(),
                        this.getX() + this.getSize() - this.velocity.getDx(),
                        this.getY() + this.getSize() + this.velocity.getDy());
                if (checkLine.isIntersecting(lines2[i])) {
                    checkLine = new Line(this.getX(), this.getY(),
                            this.getX() + this.getSize() + this.velocity.getDx(),
                            this.getY() + this.getSize() - this.velocity.getDy());
                    if (checkLine.isIntersecting(lines2[i])) {
                        this.setVelocity((-1) * this.velocity.getDx(), (-1) * this.velocity.getDy());
                    } else this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());

                } else {
                    this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
                }
            }
        }
    }


}
