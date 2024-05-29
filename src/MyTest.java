public class MyTest {
    public static void main(String[] args) {
        Line line1 = new Line(-2, -2, 1, -1);
        Line line2 = new Line(1, -1, 1, -1);
        if (line1.isIntersecting(line2)) {
            System.out.println("True");
            Point res = line1.intersectionWith(line2);
            if (res != null) {
                System.out.println("The intersection is: (" + res.getX() + ", " + res.getY() + ")");
            } else {
                System.out.println("Null");
            }
        } else {
            System.out.println("False");
        }
    }
}
