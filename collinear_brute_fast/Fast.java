import java.util.Arrays;
import java.lang.System;

public class Fast {

  private static final int    MIN_COUNT = 4;
  private static final double EPS = 1e-8;

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

    for (int i = 0; i < points.length-1; i++) {
      Point p = points[i];
      Arrays.sort(points, i+1, points.length-1, points[i].SLOPE_ORDER);
      double prevSlope = .0;
      int count = 1, start = 0;
      StdOut.printf("*************************\n");

      for (int j = i + 1; j < points.length; j++) {
        Point q = points[j];
        double pqSlope = p.slopeTo(q);
        boolean collinear = Math.abs(pqSlope - prevSlope) < EPS;
        StdOut.printf("slope = %f\n", pqSlope);
        if (collinear) {
          StdOut.printf("Collinear! Slope = %f, count = %d\n", pqSlope, count+1);
          start = j;
          count++;
        }
        if (count >= MIN_COUNT && !collinear) {
          StdOut.printf("Founded!\n");
          Point[] parr = new Point[count];
          parr[0] = p;
          System.arraycopy(points, start, parr, 1, count-1);
          Arrays.sort(parr);

          for (int n = 0; n < parr.length-1; n++) {
            StdOut.print(parr[n] + " -> ");
          }
          StdOut.println(parr[parr.length-1]);
          parr[0].drawTo(parr[parr.length-1]);

          count = 1;
        }
        if (!collinear) {
          count = 1;
        }
        prevSlope = pqSlope;
      }
    }
    StdDraw.show(0);
  }
}
