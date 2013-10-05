/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER;       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
        SLOPE_ORDER = new SlopeOrder();
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
      if (this.x == that.x) {
        if (this.y == that.y) {
          return Double.NEGATIVE_INFINITY;
        }
        return Double.POSITIVE_INFINITY;
      }
      if (this.y == that.y) {
        return .0;
      }
      return ((double)this.y-that.y)/(this.x-that.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
      if (this.y == that.y) {
        return this.x - that.x;
      }
      return this.y - that.y;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private class SlopeOrder implements Comparator<Point> {
      public int compare(Point a, Point b) {
        double slope_a = Point.this.slopeTo(a),
               slope_b = Point.this.slopeTo(b),
               difference = slope_a - slope_b,
               eps = 0e10-6;
        if (Math.abs(difference) < eps) {
          return 0;
        }
        return Double.compare(slope_a, slope_b);
      }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
