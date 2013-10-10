import java.util.Arrays;
import java.lang.System;

public class Fast {

  private static final int     MIN_COUNT = 3;
  private static final double  EPS = 1e-6;
  private static final boolean DEBUG=false;

  private static void processCollinearPoints(Point p, Point[] points, int start, int count) {
    Point[] parr = new Point[count+1];
    parr[0] = p;

    System.arraycopy(points, start, parr, 1, count);
    Arrays.sort(parr);
    if (DEBUG) {
      StdOut.printf("Origin point =%s, parr[0]=%s\n", p, parr[count]);
      /*
       *for (Point s: parr) {
       *  StdOut.print(s+"->");
       *}
       */
    }
    if (0 != parr[0].compareTo(p)) return;
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

    Point[] points = new Point[N],
            origins = new Point[N];
    for (int i = 0; i < origins.length; i++) {
      origins[i] = new Point(in.readInt(), in.readInt());
      origins[i].draw();
    }
    if (DEBUG) {
      StdOut.printf("origins:\n");
      for (Point s: origins) {
        StdOut.printf("%s ",s);
      }
      StdOut.printf("\n");
    }

    boolean collinear = false;
    Point p,q;
    double prevSlope = .0, pqSlope = .0;
    Arrays.sort(origins);
    System.arraycopy(origins,0,points,0,points.length);

    for (int i = 0; i < points.length-1; i++) {
      p = origins[i];
      if (DEBUG) {
        StdOut.printf("i = %d\n", i);
      }
      System.arraycopy(origins, i, points, 0, origins.length-i-1);
      if (DEBUG) {
        StdOut.printf("Origins copy (used 0 - %d):\n", points.length-i-1);
        for (Point s: origins) {
          StdOut.printf("%s ",s);
        }
        StdOut.printf("\n");
      }
      Arrays.sort(points, 0, points.length-i, p.SLOPE_ORDER);
      if (DEBUG) {
        StdOut.printf("Sorted origins copy (used 0 - %d):\n", points.length-i-1);
        for (Point s: points) {
          StdOut.printf("%s ",s);
        }
        StdOut.printf("\n");
      }

      int collinearPointsCount = 1, start = 0;
      prevSlope = p.slopeTo(points[i+1]);
      if (i > 0 && origins[i+1].compareTo(origins[i])==0) continue;

      if (DEBUG) {
        StdOut.printf("*************************\n");
      }
      for (int j = 0; j < points.length-i; j++) {
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
        if ((!collinear || (j == (origins.length-i-1))) && collinearPointsCount >= MIN_COUNT) {
          if (DEBUG) {
            StdOut.printf("Founded (%s,j = %d,start=%d,count=%d)!\n", collinear ? "collinear" : "not collinear", j, start, collinearPointsCount);
          }
          processCollinearPoints(p, points, start, collinearPointsCount-1);
          collinearPointsCount = 1;
        }
        if (!collinear) {
          start = j;
          collinearPointsCount = 1;
        }
        if (DEBUG) {
          StdOut.printf("Slope=%f,%s,j=%d,col=%s,cnt=%d\n", pqSlope, q, j, collinear ? "true" : "false", collinearPointsCount);
        }
        prevSlope = pqSlope;
      }
    }
    StdDraw.show(0);
  }
}
