import java.util.Arrays;
import java.lang.System;

public class Fast {

  private static final int    SEGM_MIN_COUNT = 3;
  private static final double EPS = 1e-6;

  private static void processCollinearPoints(Point p, Point[] points, int start, int count) {
    Point[] parr = new Point[count+1];
    parr[0] = p;

    System.arraycopy(points, start, parr, 1, count);
    Arrays.sort(parr);
    for (int i = 0; i < parr.length-1; i++) {
      StdOut.print(parr[i] + " -> ");
    }
    StdOut.println(parr[parr.length-1]);
    parr[0].drawTo(parr[parr.length-1]);
  }

  public static void main (String[] args) {
    StdDraw.setXscale(0,32768);
    StdDraw.setYscale(0,32768);

    String filename = args[0];
    In in = new In(filename);
    int N = in.readInt();

    Point[] points = new Point[N];
    for (int i = 0; i < points.length; i++) {
      points[i] = new Point(in.readInt(), in.readInt());
      points[i].draw();
    }

    boolean collinear = false;
    Point p,q;
    double prevSlope = .0, pqSlope = .0;

    for (int i = 0; i < points.length-1; i++) {
      p = points[i];
      Arrays.sort(points, i+1, points.length, points[i].SLOPE_ORDER);
      int collinearPointsCount = 0, start = i+1;
      prevSlope = p.slopeTo(points[i+1]);

      //StdOut.printf("*************************\n");
      for (int j = i + 1; j < points.length; j++) {
        q = points[j];
        pqSlope = p.slopeTo(q);
        if (   (Math.abs(pqSlope - prevSlope) < EPS)
            || (prevSlope == Double.POSITIVE_INFINITY && pqSlope == Double.POSITIVE_INFINITY)) {
          collinear = true;
        } else {
          collinear = false;
        }
        if (collinear) {
          collinearPointsCount++;
        }
        if ((!collinear || (j == (points.length-1))) && collinearPointsCount >= SEGM_MIN_COUNT) {
          //StdOut.printf("Founded (%s,j = %d,start=%d,count=%d)!\n", collinear ? "collinear" : "not collinear", j, start, collinearPointsCount);
          processCollinearPoints(p, points, start, collinearPointsCount);
          collinearPointsCount = 1;
        }
        if (!collinear) {
          start = j;
          collinearPointsCount = 1;
        }
        //StdOut.printf("Slope=%f,%s,j=%d,col=%s,cnt=%d\n", pqSlope, q, j, collinear ? "true" : "false", collinearPointsCount);
        prevSlope = pqSlope;
      }
    }
    StdDraw.show(0);
  }
}
