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
        this.velocity = new Velocity(0, 0);  // Initialize velocity to zero
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
        if (this.center.getX() + this.radius + this.velocity.getDx() > Board.getWidthBoard()) {
            // Hit the right wall
            //     this.center = this.getVelocity().applyToPoint(new Point(Board.getWidthBoard() - this.radius,
            //           this.center.getY()));
            this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy()); // Reverse x velocity
        }
        if (this.center.getX() - this.radius + this.velocity.getDx() < 0) {
            // Hit the left wall
            // this.center = this.getVelocity().applyToPoint(new Point(this.radius, this.center.getY()));
            this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy()); // Reverse x velocity
        }
        // Check for collision with the top or bottom walls
        if (this.center.getY() + this.radius + this.velocity.getDy() > Board.getHeightBoard()) {
            // Hit the bottom wall
            //this.center = this.getVelocity().applyToPoint(new Point(this.center.getX(),
            //      Board.getHeightBoard() - this.radius));
            this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy()); // Reverse y velocity
        }
        if (this.center.getY() - this.radius + this.velocity.getDy() < 0) {
            // Hit the top wall
            //this.center = this.getVelocity().applyToPoint(new Point(this.center.getX(), this.radius));
            this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy()); // Reverse y velocity
        }
        // Update the center of the ball according to the velocity
        this.center = this.getVelocity().applyToPoint(this.center);
    }

    public void moveBetweenLine(Rectangle rec, boolean in) {
        if (in) {
            // Check for collision with the left or right walls
            if (this.center.getX() + this.radius + this.velocity.getDx() > (rec.getX() + rec.getWidth())) {
                // Hit the right wall
                //     this.center = this.getVelocity().applyToPoint(new Point(Board.getWidthBoard() - this.radius,
                //           this.center.getY()));
                this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy()); // Reverse x velocity
            }
            if (this.center.getX() - this.radius + this.velocity.getDx() < rec.getX()) {
                // Hit the left wall
                // this.center = this.getVelocity().applyToPoint(new Point(this.radius, this.center.getY()));
                this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy()); // Reverse x velocity
            }
            // Check for collision with the top or bottom walls
            if (this.center.getY() + this.radius + this.velocity.getDy() > (rec.getY() + rec.getHeight())) {
                // Hit the bottom wall
                //this.center = this.getVelocity().applyToPoint(new Point(this.center.getX(),
                //      Board.getHeightBoard() - this.radius));
                this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy()); // Reverse y velocity
            }
            if (this.center.getY() - this.radius + this.velocity.getDy() < rec.getY()) {
                // Hit the top wall
                //this.center = this.getVelocity().applyToPoint(new Point(this.center.getX(), this.radius));
                this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy()); // Reverse y velocity
            }
            // Update the center of the ball according to the velocity
            this.center = this.getVelocity().applyToPoint(this.center);

        } else {
            // Convert rectangle sides to lines
            Line[] lines = rec.frameToLine();
            int[] intersectFlags = {0, 0, 0, 0};  // Flags to track intersections with the lines
            int radX = this.radius;
            int radY = this.radius;

            // Adjust direction of radius based on velocity
            if (this.velocity.getDx() < 0) {
                radX = radX * (-1);
            }
            if (this.velocity.getDy() < 0) {
                radY = radY * (-1);
            }

            // Predict the next position of the ball
            Point nextPoint = new Point(this.getX() + this.velocity.getDx() + radX,
                    this.getY() + this.velocity.getDy() + radY);
            Line checkline = new Line(this.getX(), this.getY(), nextPoint.getX(), nextPoint.getY());
            // Check for intersection with each line of the rectangle
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].isIntersecting(checkline)) {
                    double step = this.center.distance(lines[i].intersectionWith(checkline));
                    intersectFlags[i] = 1;
                    if (i % 2 == 0) {
                        for (double j = 0; j < step; j++) {
                            if ((this.center.distance(lines[i].intersectionWith(checkline)) - EPSILON) > this.radius) {
                                this.center = new Point(this.center.getX() + (1 / step) * velocity.getDx(),
                                        this.center.getY() + (1 / step) * velocity.getDy());
                            }
                        }
                        this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());  // Reverse y velocity
                    } else {
                        for (double j = 0; j < step; j++) {
                            if ((this.center.distance(lines[i].intersectionWith(checkline)) - EPSILON) > this.radius) {
                                this.center = new Point(this.center.getX() + (1 / step) * velocity.getDx(),
                                        this.center.getY() + (1 / step) * velocity.getDy());
                            }
                        }
                        this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());  // Reverse x velocity
                    }
                }
            }
            // If the ball intersects with two lines (corner case)
            if (intersectFlags[0] + intersectFlags[1] + intersectFlags[2] + intersectFlags[3] == 2) {
                int flag1 = -1;
                int flag2 = -1;
                for (int i = 0; i < lines.length; i++) {
                    if (intersectFlags[i] == 1) {
                        if (flag1 == -1) {
                            flag1 = i;
                        } else {
                            flag2 = i;
                        }
                    }
                }

                Point firstIntersect = checkline.intersectionWith(lines[flag1]);
                double firstDistance = firstIntersect.distance(this.center);
                Point secondIntersect = checkline.intersectionWith(lines[flag2]);
                double secondDistance = secondIntersect.distance(this.center);

                if (firstDistance > secondDistance + EPSILON) {
                    if (flag1 % 2 == 0) {
                        this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());  // Reverse y velocity
                    } else {
                        this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());  // Reverse x velocity
                    }
                } else {
                    if (flag2 % 2 == 0) {
                        this.setVelocity(this.velocity.getDx(), (-1) * this.velocity.getDy());  // Reverse y velocity
                    } else {
                        this.setVelocity((-1) * this.velocity.getDx(), this.velocity.getDy());  // Reverse x velocity
                    }
                }
            }
        }
    }
}