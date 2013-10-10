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
import java.util.Arrays;
import java.lang.System;

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
      return ((double) this.y-that.y)/(this.x-that.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    @Override
    public int compareTo(Point that) {
      if (that == null) throw new NullPointerException();
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
        return Double.compare(slopeTo(a), slopeTo(b));
      }
    }

    // unit test
    public static void main(String[] args) {
      final int POINTS_COUNT = 8;
      final int POINTS_MAXABS = 10;
      Point[] aPoints = new Point[POINTS_COUNT],
              bPoints = new Point[POINTS_COUNT];

      for (int i = 0; i < aPoints.length; i++) {
        aPoints[i] = new Point(POINTS_MAXABS-StdRandom.uniform(POINTS_MAXABS), POINTS_MAXABS-StdRandom.uniform(2*POINTS_MAXABS));
      }

      StdOut.printf("\nInitial array:\n");
      for (Point s: aPoints) {
        StdOut.printf("Slope %f, point %s\n", aPoints[0].slopeTo(s), s);
      }

      /* comparable interface test */
      StdOut.printf("\nSorted array by natural order:\n");
      System.arraycopy(aPoints, 0, bPoints, 0, aPoints.length);
      Arrays.sort(bPoints);
      for (Point s: bPoints) {
        StdOut.printf("Slope %f, point %s\n", bPoints[0].slopeTo(s), s);
      }

      /* comparator interface test */
      StdOut.printf("\nSorted by slope order for first elem:\n");
      Arrays.sort(aPoints, 1, POINTS_COUNT, aPoints[0].SLOPE_ORDER);
      for (Point s: aPoints) {
        StdOut.printf("Slope %f, point %s\n", aPoints[0].slopeTo(s), s);
      }

      /* simple wasteful tests */
      for (int i = 0; i < aPoints.length; i++) {
        for (int j = i; j < aPoints.length; j++) {
          StdOut.printf("Check for slopeTo() between p[%d]%s and p[%d]%s: %f\n", 
              i, aPoints[i].toString(), j, aPoints[j].toString(), aPoints[i].slopeTo(aPoints[j]));
          StdOut.printf("Check for compare() between p[%d]%s and p[%d]%s: %d\n", 
              i, aPoints[i].toString(), j, aPoints[j].toString(), aPoints[i].SLOPE_ORDER.compare(aPoints[i],aPoints[j]));
        }
      }
    }
}
