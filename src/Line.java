/**
 * Represents a line segment defined by two points.
 */
public class Line {
    private Point start; // Starting point of the line
    private Point end; // Ending point of the line
    private Point inPoint = null; // Intersection point with another line, if exist 1 (more than 1 will be null).
    public static final double EPSILON = 0.00000001; // Small constant to handle floating-point comparison

    /**
     * Constructs a line given two points.
     *
     * @param start The starting point of the line.
     * @param end   The ending point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a line given the coordinates of two points.
     *
     * @param x1 The x-coordinate of the starting point.
     * @param y1 The y-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y2 The y-coordinate of the ending point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     *
     * @return The length of the line.
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return The middle point of the line.
     */
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /**
     * Returns the starting point of the line.
     *
     * @return The starting point of the line.
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the ending point of the line.
     *
     * @return The ending point of the line.
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns the intersection point of this line with another line, if there is, else return null.
     *
     * @return The intersection point, or null if there is no intersection.
     */
    public Point getInPoint() {
        return this.inPoint;
    }

    /**
     * Determines if this line intersects with another line.
     *
     * @param other The other line to check for intersection.
     * @return True if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        double slope1, slope2, n1, n2, xIntersection, yIntersection;
        // Case 1: Both lines are vertical but not overlapping
        if (this.end.getX() == this.start.getX() && other.end.getX() == other.start.getX()
                && this.start.getX() != other.start.getX()) {
            return false; // Parallel vertical lines with different x-coordinates do not intersect
        }
        // Case 2: Both lines are vertical and possibly overlapping
        if (this.end.getX() == this.start.getX() && other.end.getX() == other.start.getX()) {
            // Check if the y-ranges of the vertical lines overlap
            if ((Math.min(this.start.getY(), this.end.getY()) > Math.max(other.start.getY(), other.end.getY()))
                    || (Math.min(other.start.getY(), other.end.getY())
                    > Math.max(this.start.getY(), this.end.getY()))) {
                return false; // The vertical lines do not overlap in the y-axis
            }
            // Handle overlapping vertical lines and set the intersection point
            if (((Math.abs(Math.max(this.start.getY(), this.end.getY()))
                    - (Math.min(other.start.getY(), other.end.getY()))) <= EPSILON)
                    && !((Math.abs(Math.max(other.start.getY(), other.end.getY()))
                    - (Math.min(this.start.getY(), this.end.getY()))) <= EPSILON)) {
                if (this.start.getY() >= this.end.getY()) {
                    this.inPoint = new Point(this.start.getX(), this.start.getY());
                } else {
                    this.inPoint = new Point(this.end.getX(), this.end.getY());
                }
            }
            if (!((Math.abs(Math.max(this.start.getY(), this.end.getY()))
                    - (Math.min(other.start.getY(), other.end.getY()))) <= EPSILON)
                    && ((Math.abs(Math.max(other.start.getY(), other.end.getY()))
                    - (Math.min(this.start.getY(), this.end.getY()))) <= EPSILON)) {
                if (other.start.getY() >= other.end.getY()) {
                    this.inPoint = new Point(other.start.getX(), other.start.getY());
                } else {
                    this.inPoint = new Point(other.end.getX(), other.end.getY());
                }
            }
            return true; // The vertical lines overlap
        }
        // Case 3: This line is vertical and the other not.
        if (this.end.getX() == this.start.getX()) {
            // Calculate the slope and y-intercept of the other line.
            slope2 = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
            n2 = other.start.getY() - slope2 * other.start.getX();
            // Calculate the intersection point.
            xIntersection = this.start.getX();
            yIntersection = slope2 * xIntersection + n2;
            // Check if the intersection point is within both line segments.
            if ((xIntersection <= Math.max(other.start.getX(), other.end.getX()))
                    && (xIntersection >= Math.min(other.start.getX(), other.end.getX()))
                    && (yIntersection <= Math.max(this.start.getY(), this.end.getY()))
                    && (yIntersection <= Math.max(other.start.getY(), other.end.getY()))
                    && (yIntersection >= Math.min(this.start.getY(), this.end.getY()))
                    && (yIntersection >= Math.min(other.start.getY(), other.end.getY()))) {
                this.inPoint = new Point(xIntersection, yIntersection);
                return true;
            }
            return false; // Intersection point is outside the line segments
        }
        // Case 4: Other line is vertical and this not.
        if (other.end.getX() == other.start.getX()) {
            // Calculate the slope and y-intercept of this line
            slope1 = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
            n1 = this.start.getY() - slope1 * this.start.getX();
            // Calculate the intersection point
            xIntersection = other.start.getX();
            yIntersection = slope1 * xIntersection + n1;
            // Check if the intersection point lies within both line segments
            if (xIntersection <= Math.max(this.start.getX(), this.end.getX())
                    && (xIntersection >= Math.min(this.start.getX(), this.end.getX()))
                    && (yIntersection <= Math.max(this.start.getY(), this.end.getY()))
                    && (yIntersection <= Math.max(other.start.getY(), other.end.getY()))
                    && (yIntersection >= Math.min(this.start.getY(), this.end.getY()))
                    && (yIntersection >= Math.min(other.start.getY(), other.end.getY()))) {
                this.inPoint = new Point(xIntersection, yIntersection);
                return true;
            }
            return false; // Intersection point is outside the line segments.
        }
        // Calculate the slopes of both lines (handle horizontal lines) for the other cases.
        if (this.end.getY() == this.start.getY()) {
            slope1 = 0; // Horizontal line
        } else {
            slope1 = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        }
        if (other.end.getY() == other.start.getY()) {
            slope2 = 0; // Horizontal line
        } else {
            slope2 = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
        }
        // Calculate the y-intercepts of both lines
        n1 = this.start.getY() - slope1 * this.start.getX();
        n2 = other.start.getY() - slope2 * other.start.getX();
        // Case 5: Lines are parallel but not coincident
        if (slope1 == slope2) {
            if (n1 != n2) {
                return false; // Parallel lines with different y-intercepts do not intersect.
            }
            // Case 6: Lines are coincident (overlapping)
            if (((this.start.getX() <= Math.max(other.start.getX(), other.end.getX())
                    && this.start.getX() >= Math.min(other.start.getX(), other.end.getX()))
                    || (this.end.getX() <= Math.max(other.start.getX(), other.end.getX())
                    && this.end.getX() >= Math.min(other.start.getX(), other.end.getX())))
                    || (((other.start.getX() <= Math.max(this.start.getX(), this.end.getX()))
                    && other.start.getX() >= Math.min(this.start.getX(), this.end.getX()))
                    || (other.end.getX() <= Math.max(this.start.getX(), this.end.getX())
                    && other.end.getX() >= Math.min(this.start.getX(), this.end.getX())))) {
                if (((Math.abs(Math.max(this.start.getX(), this.end.getX()))
                        - (Math.min(other.start.getX(), other.end.getX()))) <= EPSILON)
                        && !((Math.abs(Math.max(other.start.getX(), other.end.getX()))
                        - (Math.min(this.start.getX(), this.end.getX()))) <= EPSILON)) {
                    if (this.start.getX() >= this.end.getX()) {
                        this.inPoint = new Point(this.start.getX(), this.start.getY());
                    } else {
                        this.inPoint = new Point(this.end.getX(), this.end.getY());
                    }
                }
                if (!((Math.abs(Math.max(this.start.getX(), this.end.getX()))
                        - (Math.min(other.start.getX(), other.end.getX()))) <= EPSILON)
                        && ((Math.abs(Math.max(other.start.getX(), other.end.getX()))
                        - (Math.min(this.start.getX(), this.end.getX()))) <= EPSILON)) {
                    if (other.start.getX() >= other.end.getX()) {
                        this.inPoint = new Point(other.start.getX(), other.start.getY());
                    } else {
                        this.inPoint = new Point(other.end.getX(), other.end.getY());
                    }
                }
                return true; // The lines are coincident and overlap.
            }
            return false; // The lines are parallel but do not overlap.
        }
        // Case 7: General case where lines may intersect.
        // Calculate the intersection point.
        xIntersection = (n2 - n1) / (slope1 - slope2);
        yIntersection = slope1 * xIntersection + n1;
        // Check if the intersection point lies within both line segments.
        if ((xIntersection <= Math.max(this.start.getX(), this.end.getX()))
                && (xIntersection >= Math.min(this.start.getX(), this.end.getX()))
                && (yIntersection <= Math.max(this.start.getY(), this.end.getY()))
                && (yIntersection >= Math.min(this.start.getY(), this.end.getY()))
                && (xIntersection <= Math.max(other.start.getX(), other.end.getX()))
                && (xIntersection >= Math.min(other.start.getX(), other.end.getX()))
                && (yIntersection <= Math.max(other.start.getY(), other.end.getY()))
                && (yIntersection >= Math.min(other.start.getY(), other.end.getY()))) {
            this.inPoint = new Point(xIntersection, yIntersection);
            return true;
        }
        return false; // Intersection point is outside the line segments.
    }

    /**
     * Determines if this line intersects with both given lines.
     *
     * @param other1 The first other line to check for intersection.
     * @param other2 The second other line to check for intersection.
     * @return True if this line intersects with both given lines, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * Returns the intersection point if this line intersects with another line.
     *
     * @param other The other line to check for intersection.
     * @return The intersection point if the lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (this.isIntersecting(other)) {
            if (this.getInPoint() != null) {
                Point intersection = new Point(this.getInPoint().getX(), this.getInPoint().getY());
                this.inPoint = null; // Clear the intersection point for subsequent calls
                return intersection;
            }
        }
        return null;
    }

    /**
     * Compares this line with another line for equality.
     *
     * @param other The other line to compare with.
     * @return True if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        return ((this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start)));
    }
}
