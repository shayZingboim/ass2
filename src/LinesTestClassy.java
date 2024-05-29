
import java.awt.geom.Line2D;

/**
 * A class to test the functionality of the base.Line class.
 */
public class LinesTestClassy {
    /**
     * Some test scenarios for the base.Line class.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        boolean allTestsPassed = true;

        allTestsPassed &= testParallelLines();
        allTestsPassed &= testIntersectionWithParallelLines();
        allTestsPassed &= testLinesWithCommonPoints();
        allTestsPassed &= testLinesSevenAndEight();
        allTestsPassed &= testLinesWithoutCommonPoints();
        allTestsPassed &= testEqualLines();
        allTestsPassed &= testLinesWithDifferentSlopes();
        allTestsPassed &= testRandomIsIntersecting();
        allTestsPassed &= testIsIntersectingSimpler();

        if (allTestsPassed) {
            System.out.println("All tests passed successfully.");
        } else {
            System.out.println("Some tests failed. Review the output for details.");
        }
    }

    /**
     * Test scenarios for parallel lines.
     * @return true if all tests pass, false otherwise
     */
    private static boolean testParallelLines() {
        Line lineParallelToY = new Line(1, 0, 1, 5); // Parallel to Y-axis
        Line lineParallelToX = new Line(0, 1, 5, 1); // Parallel to X-axis

        boolean testPassed = true;

        if (!lineParallelToY.isIntersecting(new Line(1, 0, 1, -3))) {
            System.out.println("Test failed: Parallel lines with common points - expected true, got false");
            System.out.println("Intersection point: " + lineParallelToY.intersectionWith(new Line(1, 0, 1, -3)));
            testPassed = false;
        }
        if (!lineParallelToX.isIntersecting(new Line(0, 1, -3, 1))) {
            System.out.println("Test failed: Parallel lines with common points - expected true, got false");
            System.out.println("Intersection point: " + lineParallelToX.intersectionWith(new Line(0, 1, -3, 1)));
            testPassed = false;
        }
        if (lineParallelToY.isIntersecting(new Line(2, 0, 2, 5))) {
            System.out.println("Test failed: Parallel lines without common points - expected false, got true");
            testPassed = false;
        }
        if (lineParallelToX.isIntersecting(new Line(0, 2, 5, 2))) {
            System.out.println("Test failed: Parallel lines without common points - expected false, got true");
            testPassed = false;
        }

        return testPassed;
    }

    /**
     * Test scenarios for intersection with parallel lines.
     * @return true if all tests pass, false otherwise
     */
    private static boolean testIntersectionWithParallelLines() {
        Line lineParallelToY = new Line(1, 0, 1, 5); // Parallel to Y-axis
        Line lineParallelToX = new Line(0, 1, 5, 1); // Parallel to X-axis

        boolean testPassed = true;

        if (!lineParallelToY.intersectionWith(new Line(1, 0, 1, -3)).equals(new Point(1, 0))) {
            System.out.println("Test failed: Intersection point with parallel lines "
                    + "- expected (1.0, 0.0), got different point");
            testPassed = false;
        }
        if (!lineParallelToX.intersectionWith(new Line(0, 1, -3, 1)).equals(new Point(0, 1))) {
            System.out.println("Test failed: Intersection point with parallel lines "
                    + "- expected (0.0, 1.0), got different point");
            testPassed = false;
        }

        return testPassed;
    }

    /**
     * Test scenarios for lines with common points.
     * @return true if all tests pass, false otherwise
     */
    private static boolean testLinesWithCommonPoints() {
        Line line1 = new Line(1, 0, 1, 5);
        Line line2 = new Line(1, 0, 1, 5);

        boolean testPassed = true;

        if (!line1.equals(line2)) {
            System.out.println("Test failed: Lines are equal - expected true, got false");
            testPassed = false;
        }
        if (line1.intersectionWith(line2) != null) {
            System.out.println("Test failed: Lines overlap completely - expected null, got point");
            System.out.println("Intersection point: " + line1.intersectionWith(line2));
            testPassed = false;
        }

        return testPassed;
    }

    /**
     * Test scenarios for lines seven and eight.
     * @return true if all tests pass, false otherwise
     */
    private static boolean testLinesSevenAndEight() {
        Line line7 = new Line(0, 0, 2, 2);
        Line line8 = new Line(2, 2, 4, 0);

        boolean testPassed = true;

        if (!line7.intersectionWith(line8).equals(new Point(2, 2))) {
            System.out.println("Test failed: Intersection point of lines 7 and 8 - expected (2.0, 2.0),"
                    + " got different point");
            System.out.println("Intersection point: " + line7.intersectionWith(line8));
            testPassed = false;
        }

        return testPassed;
    }

    /**
     * Test scenarios for lines without common points.
     * @return true if all tests pass, false otherwise
     */
    private static boolean testLinesWithoutCommonPoints() {
        Line line3 = new Line(0, 0, 2, 2);
        Line line4 = new Line(3, 3, 5, 5);

        boolean testPassed = true;

        if (line3.isIntersecting(line4)) {
            System.out.println("Test failed: Lines do not intersect - expected false, got true");
            testPassed = false;
        }

        return testPassed;
    }

    /**
     * Test scenarios for lines with equal slopes.
     * @return true if all tests pass, false otherwise
     */
    private static boolean testEqualLines() {
        Line line10 = new Line(0, 0, 2, 2); // Line with slope 1
        Line line11 = new Line(1, 1, 3, 3); // Line with slope 1, parallel to line10

        boolean testPassed = true;

        if (!line10.isIntersecting(line11)) {
            System.out.println("Test failed: Lines with equal slopes and parallel - expected true, got false");
            testPassed = false;
        }
        if (line10.intersectionWith(line11) != null) {
            System.out.println("Test failed: Lines with equal slopes and parallel - expected null, got point");
            System.out.println("Intersection point: " + line10.intersectionWith(line11));
            testPassed = false;
        }

        return testPassed;
    }

    /**
     * Test scenarios for lines with different slopes.
     * @return true if all tests pass, false otherwise
     */
    private static boolean testLinesWithDifferentSlopes() {
        Line line14 = new Line(0, 0, 2, 2); // Line with slope 1 and y-intercept 0
        Line line24 = new Line(0, 1, 2, 3); // Line with slope 1 and y-intercept 1

        boolean testPassed = true;

        if (line14.isIntersecting(line24)) {
            System.out.println("Test failed: Lines with different y-intercepts - expected false, got true");
            testPassed = false;
        }
        if (line14.intersectionWith(line24) != null) {
            System.out.println("Test failed: Lines with different y-intercepts - expected null, got point");
            System.out.println("Intersection point: " + line14.intersectionWith(line24));
            testPassed = false;
        }

        return testPassed;
    }

    /**
     * Test scenarios for random isIntersecting.
     * @return true if all tests pass, false otherwise
     */
    private static boolean testRandomIsIntersecting() {
        boolean testPassed = true;
        int from = -2, to = 5;
        for (int x1 = from; x1 < to; x1++) {
            for (int y1 = from; y1 < to; y1++) {
                for (int x2 = from; x2 < to; x2++) {
                    for (int y2 = from; y2 < to; y2++) {
                        for (int x3 = from; x3 < to; x3++) {
                            for (int y3 = from; y3 < to; y3++) {
                                for (int x4 = from; x4 < to; x4++) {
                                    for (int y4 = from; y4 < to; y4++) {
                                        if ((x1 == x2 && y1 == y2 && x3 == x4 && y3 == y4) && (x1 != x3 || y1 != y3)) {
                                            continue;
                                        }
                                        try {
                                            Line line1 = new Line(x1, y1, x2, y2);
                                            Line line2 = new Line(x3, y3, x4, y4);
                                            Line2D.Double line1D = new Line2D.Double(x1, y1, x2, y2);
                                            Line2D.Double line2D = new Line2D.Double(x3, y3, x4, y4);
                                            boolean expected = line1D.intersectsLine(line2D);
                                            boolean actual = line1.isIntersecting(line2);
                                            if (expected != actual) {
                                                System.out.println("Test failed: Random isIntersecting - expected "
                                                        + expected + ", got " + actual);
                                                System.out.println("Line 1: (" + x1 + ", " + y1
                                                        + "), (" + x2 + ", " + y2 + ")");
                                                System.out.println("Line 2: (" + x3 + ", " + y3
                                                        + "), (" + x4 + ", " + y4 + ")");
                                                testPassed = false;
                                            }
                                        } catch (IllegalArgumentException e) {
                                            if (e.getMessage().equals("The start and end points are the same")) {
                                                continue;
                                            }
                                            testPassed = false;
                                        } catch (Exception e) {
                                            System.out.println("Test failed: Random isIntersecting - exception thrown");
                                            System.out.println("Line 1: (" + x1 + ", " + y1
                                                    + "), (" + x2 + ", " + y2 + ")");
                                            System.out.println("Line 2: (" + x3 + ", " + y3
                                                    + "), (" + x4 + ", " + y4 + ")");
                                            testPassed = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return testPassed;
    }

    /**
     * Test scenarios for isIntersecting with simpler lines.
     * @return true if all tests pass, false otherwise
     */
    private static boolean testIsIntersectingSimpler() {
        int x1 = 0, y1 = -2, x2 = 0, y2 = 0;
        int x3 = 0, y3 = -1, x4 = 0, y4 = 1;
        Line line1 = new Line(x1, y1, x2, y2);
        Line line2 = new Line(x3, y3, x4, y4);
        Line2D.Double line1D = new Line2D.Double(x1, y1, x2, y2);
        Line2D.Double line2D = new Line2D.Double(x3, y3, x4, y4);
        boolean expected = line1D.intersectsLine(line2D);
        boolean actual = line1.isIntersecting(line2);
        boolean testPassed = true;

        if (expected != actual) {
            System.out.println("Test failed: isIntersecting with simpler lines - expected "
                    + expected + ", got " + actual);
            testPassed = false;
        }

        return testPassed;
    }
}