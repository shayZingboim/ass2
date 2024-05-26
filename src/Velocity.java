public class Velocity {
    private double dx;
    private double dy;

    // constructor
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.cos(Math.toRadians(angle));
        double dy = -(speed * Math.sin(Math.toRadians(angle)));
        return new Velocity(dx, dy);
    }

    // Take a point with position (x,y) and return a new point
// with position (x+dx, y+dy)
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }
}