public class Line {
    private Point start;
    private Point end;
    private Point inPoint = null;
    public static final double EPSILON = 0.00000001;

    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    // Return the length of the line
    public double length() {
        return start.distance(end);
    }

    // Returns the middle point of the line
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    // Returns the start point of the line
    public Point start() {
        return this.start;
    }

    // Returns the end point of the line
    public Point end() {
        return this.end;
    }

    // Returns the intersection point
    public Point getInPoint() {
        return this.inPoint;
    }

    // Returns true if the lines intersect, false otherwise
    public boolean isIntersecting(Line other) {
        double slope1, slope2, n1, n2, xIntersection, yIntersection;
        if (this.end.getX() == this.start.getX()
                && other.end.getX() == other.start.getX()
                && this.start.getX() != other.start.getX()) {
            return false;
        }
        if (this.end.getX() == this.start.getX() && other.end.getX() == other.start.getX()) {
            if ((Math.min(this.start.getY(), this.end.getY()) > Math.max(other.start.getY(), other.end.getY()))
                    || (Math.min(other.start.getY(), other.end.getY()) > Math.max(this.start.getY(), this.end.getY()))) {
                return false;
            }
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
            return true;
        }
        if (this.end.getX() == this.start.getX()) {
            slope2 = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
            n2 = other.start.getY() - slope2 * other.start.getX();
            xIntersection = this.start.getX();
            yIntersection = slope2 * xIntersection + n2;
            if ((xIntersection <= Math.max(other.start.getX(), other.end.getX()))
                    && (xIntersection >= Math.min(other.start.getX(), other.end.getX()))
                    && (yIntersection <= Math.max(this.start.getY(), this.end.getY()))
                    && (yIntersection <= Math.max(other.start.getY(), other.end.getY()))
                    && (yIntersection >= Math.min(this.start.getY(), this.end.getY()))
                    && (yIntersection >= Math.min(other.start.getY(), other.end.getY()))) {
                this.inPoint = new Point(xIntersection, yIntersection);
                return true;
            }
            return false;
        }
        if (other.end.getX() == other.start.getX()) {
            slope1 = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
            n1 = this.start.getY() - slope1 * this.start.getX();
            xIntersection = other.start.getX();
            yIntersection = slope1 * xIntersection + n1;
            if (xIntersection <= Math.max(this.start.getX(), this.end.getX())
                    && (xIntersection >= Math.min(this.start.getX(), this.end.getX()))
                    && (yIntersection <= Math.max(this.start.getY(), this.end.getY()))
                    && (yIntersection <= Math.max(other.start.getY(), other.end.getY()))
                    && (yIntersection >= Math.min(this.start.getY(), this.end.getY()))
                    && (yIntersection >= Math.min(other.start.getY(), other.end.getY()))) {
                this.inPoint = new Point(xIntersection, yIntersection);
                return true;
            }
            return false;
        }


        if (this.end.getY() == this.start.getY()) {
            slope1 = 0;
        } else {
            slope1 = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        }
        if (other.end.getY() == other.start.getY()) {
            slope2 = 0;
        } else {
            slope2 = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
        }
        n1 = this.start.getY() - slope1 * this.start.getX();
        n2 = other.start.getY() - slope2 * other.start.getX();
        if (slope1 == slope2) {
            if (n1 != n2) {
                return false;
            }
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
                return true;
            }
            return false;
        }
        xIntersection = (n2 - n1) / (slope1 - slope2);
        yIntersection = xIntersection * slope1 + n1;
        if (xIntersection <= Math.max(this.start.getX(), this.end.getX())
                && (xIntersection <= Math.max(other.start.getX(), other.end.getX()))
                && (xIntersection >= Math.min(this.start.getX(), this.end.getX()))
                && (xIntersection >= Math.min(other.start.getX(), other.end.getX()))
                && (yIntersection <= Math.max(this.start.getY(), this.end.getY()))
                && (yIntersection <= Math.max(other.start.getY(), other.end.getY()))
                && (yIntersection >= Math.min(this.start.getY(), this.end.getY()))
                && (yIntersection >= Math.min(other.start.getY(), other.end.getY()))) {
            this.inPoint = new Point(xIntersection, yIntersection);
            return true;
        }
        return false;
    }

    // Returns true if this 2 lines intersect with this line, false otherwise
    public boolean isIntersecting(Line other1, Line other2) {
        if (this.isIntersecting(other1) && this.isIntersecting(other2)) {
            return true;
        }
        return false;
    }

    // Returns the intersection point if the lines intersect and null otherwise.
    public Point intersectionWith(Line other) {
        if (this.isIntersecting(other)) {
            if (this.getInPoint() != null) {
                Point intersection = new Point(this.getInPoint().getX(), this.getInPoint().getY());
                this.inPoint = null;
                return intersection;
            }
        }
        return null;
    }

    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        return ((this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start)));
    }
}