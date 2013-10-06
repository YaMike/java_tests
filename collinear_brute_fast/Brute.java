import java.util.Arrays;

public class Brute {
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

    Point[] linePoints = new Point[4];
    double[] lineSlope = new double[3];

    for (int i = 0; i < points.length; i++) {
      linePoints[0] = points[i];

      for (int j = i + 1; j < points.length; j++) {
        linePoints[1] = points[j];
        lineSlope[0] = linePoints[1].slopeTo(linePoints[0]);

        for (int k = j + 1; k < points.length; k++) {
          linePoints[2] = points[k];
          lineSlope[1] = linePoints[2].slopeTo(linePoints[1]);
          if (lineSlope[0] != lineSlope[1]) continue;

          for (int l = k + 1; l < points.length; l++) {
            linePoints[3] = points[l];
            lineSlope[2] = linePoints[3].slopeTo(linePoints[2]);
            if (lineSlope[1] != lineSlope[2]) continue;

            Arrays.sort(linePoints);

            for (int n = 0; n < linePoints.length-1; n++) {
              StdOut.print(linePoints[n] + " -> ");
            }
            StdOut.println(linePoints[linePoints.length-1]);
            linePoints[0].drawTo(linePoints[linePoints.length-1]);
          }
        }
      }
    }
    StdDraw.show(0);
  }
}
