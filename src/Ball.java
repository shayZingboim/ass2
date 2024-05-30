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

    public void moveBetweenLines(Rectangle rec) {
        Line[] lines = rec.frameToLine();
        int[] intersectFlags = {0, 0, 0, 0};
        int radX = this.radius;
        int radY = this.radius;
        if (this.velocity.getDx() < 0) {
            radX = radX * (-1);
        }
        if (this.velocity.getDy() < 0) {
            radY = radY * (-1);
        }
        Point nextPoint = new Point(this.getX() + this.velocity.getDx() + radX,
                this.getY() + this.velocity.getDy() + radY);
        Line checkline = new Line(this.getX(), this.getY(), nextPoint.getX(), nextPoint.getY());
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].isIntersecting(checkline)) {
                intersectFlags[i] = 1;
                if (i % 2 == 0) {
                    this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());
                } else {
                    this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
                }
            }
        }
        if (intersectFlags[0] + intersectFlags[1] + intersectFlags[2] + intersectFlags[3] == 2) {
            int flag1 = -1;
            int flag2 = -1;
            for (int i = 0; i < lines.length; i++) {
                if (intersectFlags[i] == 1) {
                    if (flag1 == -1) {
                        flag1 = i;
                    } else
                        flag2 = i;
                }
            }
            Point firsIntersect = checkline.intersectionWith(lines[flag1]);
            double firstDistance = firsIntersect.distance(this.center);
            Point secondIntersect = checkline.intersectionWith(lines[flag2]);
            double secondDistance = secondIntersect.distance(this.center);
            if (firstDistance > secondDistance + EPSILON) {
                if (flag1 % 2 == 0) {
                    this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());
                } else {
                    this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
                }
            } else {
                if (flag2 % 2 == 0) {
                    this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());
                } else {
                    this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
                }
            }
        }
        Point[] recVertex = rec.frameToPoint();
        Point newCenter = new Point(center.getX() + this.velocity.getDx(), center.getY() + this.velocity.getDy());
        if (newCenter.distance(recVertex[0]) - this.radius < EPSILON) {
            if (this.getX() < recVertex[0].getX()) {
                this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
            } else {
                this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());
            }
        }
        if (newCenter.distance(recVertex[1]) - this.radius < EPSILON) {
            if (this.getX() < recVertex[1].getX()) {
                this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
            } else {
                this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());
            }
        }
        if (newCenter.distance(recVertex[2]) - this.radius < EPSILON) {
            if (this.getX() < recVertex[2].getX()) {
                this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());
            } else {
                this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
            }
        }
        if (newCenter.distance(recVertex[3]) - this.radius < EPSILON) {
            if (this.getX() < recVertex[3].getX()) {
                this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());
            } else {
                this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());
            }
        }
    }
}
