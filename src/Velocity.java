//Shay Zingboim ID 208497255 Yair Kupershtock ID 322889015

/**
 * Represents the velocity of an object, defined by its change in position (dx, dy).
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructs a Velocity object with the specified changes in position.
     *
     * @param dx The change in the x-coordinate.
     * @param dy The change in the y-coordinate.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Creates a Velocity object from an angle and a speed.
     *
     * @param angle The angle in degrees.
     * @param speed The speed.
     * @return A Velocity object representing the given angle and speed.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        // Calculate dx and dy from the angle and speed
        double dx = speed * Math.cos(Math.toRadians(angle));
        // Negative because in most graphics systems, y increases downward
        double dy = -(speed * Math.sin(Math.toRadians(angle)));
        return new Velocity(dx, dy);
    }

    /**
     * Applies this velocity to a given point, resulting in a new point.
     *
     * @param p The original point.
     * @return A new point with updated coordinates.
     */
    public Point applyToPoint(Point p) {
        // Return a new point with position (x + dx, y + dy)
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Returns the change in the x-coordinate.
     *
     * @return The change in the x-coordinate.
     */
    public double getDx() {
        return dx;
    }

    /**
     * Returns the change in the y-coordinate.
     *
     * @return The change in the y-coordinate.
     */
    public double getDy() {
        return dy;
    }

    /**
     * Maps a speed value based on the radius of an object within a specified range.
     *
     * @param radius   the radius of the object.
     * @param min      the minimum radius for mapping speed.
     * @param max      the maximum radius for mapping speed.
     * @param speedMin the minimum speed value to map to.
     * @param speedMax the maximum speed value to map to.
     * @return the mapped speed value based on the radius.
     */
    public static double mapSpeed(double radius, double min, double max, double speedMin, double speedMax) {
        // If radius is less than the minimum, return the maximum speed
        if (radius < min) {
            return speedMax;
        }
        // If radius is greater than the maximum, return the minimum speed
        if (radius > max) {
            return speedMin;
        }
        // Calculate the mapped speed based on linear interpolation between speedMin and speedMax
        return speedMax + (radius - min) * (speedMin - speedMax) / (max - min);
    }
}
