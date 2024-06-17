import biuoop.DrawSurface;
import java.awt.Color;

/**
 * The Ball class represents a ball with a center point, radius, color, and velocity.
 */
public class Ball {
    private Point center; // The center point of the ball
    private int radius; // The radius of the ball
    private Color color; // The color of the ball
    private Velocity velocity; // The velocity of the ball
    private int left, right, top, bottom; // Boundaries for the ball's movement
    public static final double EPSILON = 0.00000001; // Small constant for comparison precision

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

    /**
     * Sets the boundaries for the ball's movement.
     *
     * @param x the left boundary.
     * @param y the right boundary.
     * @param z the top boundary.
     * @param s the bottom boundary.
     */
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

    /**
     * Moves the ball one step according to its velocity,
     * and reflects its velocity if it reaches the boundaries.
     */
    public void moveOneStep() {
        // Reflect velocity if ball reaches horizontal boundaries
        if ((this.getX() - this.radius <= left) || (this.getX() + this.radius >= right)) {
            setVelocity(-this.velocity.getDx(), this.velocity.getDy());
        }
        // Reflect velocity if ball reaches vertical boundaries
        if ((this.getY() - this.radius <= top) || (this.getY() + this.radius >= bottom)) {
            setVelocity(this.velocity.getDx(), -this.velocity.getDy());
        }
        // Move ball according to its velocity
        this.center = this.velocity.applyToPoint(this.center);
    }

    /**
     * Finds the closest intersection point of the ball with a given rectangle.
     *
     * @param rec the rectangle to check for intersection.
     * @return the closest intersection point of the ball with the rectangle.
     */
    public Point closestIntersection(Rectangle rec) {
        // Define points of the rectangle
        Point startOfRec = new Point(rec.getX(), rec.getY());
        Point endOfRec = new Point(rec.getX() + rec.getWidth(), rec.getY() + rec.getHeight());
        // Initialize the closest point as ball's center
        Point closest = new Point(this.getX(), this.getY());

        // Adjust the closest point to stay within rectangle bounds
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

    /**
     * Checks if the ball is intersecting with a given rectangle.
     *
     * @param rec the rectangle to check for intersection.
     * @return true if the ball intersects with the rectangle, false otherwise.
     */
    public boolean isIntersectingRec(Rectangle rec) {
        // Create a line from ball's center to its closest intersection point with the rectangle
        Line checkLine = new Line(this.center, closestIntersection(rec));
        // Check if the length of the line is less than or equal to the ball's radius
        if (checkLine.length() <= this.radius) {
            return true;
        }
        return false;
    }
}