import java.util.Arrays;
import java.lang.System;

public class Fast {

  private static final int     MIN_COUNT = 3;
  private static final double  EPS = 1e-6;
  private static final boolean DEBUG=true;

  private static void processCollinearPoints(Point p, Point[] points, int start, int count) {
    Point[] parr = new Point[count+1];
    parr[0] = p;

    System.arraycopy(points, start, parr, 1, count);
    Arrays.sort(parr);
    if (DEBUG) {
      /*
       *StdOut.printf("Origin point =%s, parr[0]=%s\n", p, parr[count]);
       */
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

  private static void printPointsArray(String name, Point[] arr) {
      StdOut.printf("%s:\n", name);
      for (Point s: arr) {
        StdOut.printf("%s ",s);
      }
      StdOut.printf("\n\n");
  }

  public static void main (String[] args) {
    StdDraw.setXscale(0,32768);
    StdDraw.setYscale(0,32768);

    String filename = args[0];
    In in = new In(filename);
    int N = in.readInt();

    Point[] origins = new Point[N];
    for (int i = 0; i < origins.length; i++) {
      origins[i] = new Point(in.readInt(), in.readInt());
      origins[i].draw();
    }

    boolean collinear = false;
    Point p,q;
    double prevSlope = .0, pqSlope = .0;
    Arrays.sort(origins);
    if (DEBUG) { printPointsArray("origins", origins); }

    for (int i = 0; i < origins.length-MIN_COUNT; i++) {
      if (DEBUG) { StdOut.printf("\n*************************\n"); }
      p = origins[i];
      if (DEBUG) {
        StdOut.printf("i = %d\norigin point = %s\n", i, p);
      }
      int count = origins.length-i-1;
      Point[] points = new Point[count];
      System.arraycopy(origins, i+1, points, 0, count);
      if (DEBUG) { printPointsArray("Origins copy", points); }
      Arrays.sort(points, p.SLOPE_ORDER);
      if (DEBUG) { printPointsArray("Sorted origins copy", points); }

      int slopesCount = 1, start = 0;
      if (i > 0 && origins[i+1].compareTo(origins[i])==0) continue;

      for (int j = 0; j < points.length; j++) {
        q = points[j];
        pqSlope = p.slopeTo(q);
        if (j == 0) {
          prevSlope = pqSlope;
          StdOut.printf("Slope=%f,%s,j=%d,col=%s,slopes cnt=%d\n", pqSlope, q, j, "unknown", slopesCount);
          continue;
        }
        if (   (Math.abs(pqSlope - prevSlope) < EPS)
            || (prevSlope == Double.POSITIVE_INFINITY && pqSlope == Double.POSITIVE_INFINITY)) {
          collinear = true;
        } else {
          collinear = false;
        }
        if (collinear) {
          if (slopesCount == 0) slopesCount = 1;
          slopesCount++;
        }
        if (DEBUG) {
          StdOut.printf("Slope=%f,%s,j=%d,col=%s,slopes cnt=%d\n", pqSlope, q, j, collinear ? "true" : "false", slopesCount);
        }
        if ((!collinear || (j == (points.length-1))) && slopesCount >= MIN_COUNT) {
          if (DEBUG) {
            StdOut.printf("\n\\o/ Founded (%s,j = %d,start=%d, slopes count=%d)!\n", collinear ? "collinear" : "not collinear", j, start, slopesCount);
          }
          processCollinearPoints(p, points, start, slopesCount-1);
          slopesCount = 0;
        }
        if (!collinear) {
          start = j;
          slopesCount = 0;
        }
        prevSlope = pqSlope;
      }
    }
    StdDraw.show(0);
  }
}
