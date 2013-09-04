import java.util.ArrayList;

public class PercolationStats {
  private double[] test_cases;
  private double mean;
  private double stddev;
  static private int T;
  static private int N;

  private int empty_count = 0;

  private class pair {
    public int row;
    public int col;
    public pair (int r, int c) {
      row = r;
      col = c;
    }
    public pair (pair p) {
      row = p.row;
      col = p.col;
    }
  };
  private class pairs {
    private ArrayList<pair> p;
    private int taken;
    public pairs (int n) {
      taken = 0;
      p = new ArrayList<pair>();
      int row = 1, col = 1, count = n*n;
      for (int i = 0; i < count; ++i) {
        p.add(new pair(row,col));
        col++;
        if ((col-1) % n == 0) {
          row++;
          col = 1;
        }
      }
    }
    public pair take_one () {
      int i_elem = StdRandom.uniform(0,p.size()-1);
      pair lp = new pair(p.get(i_elem));
      p.remove(i_elem);
      taken++;
      return lp;
    }
    public int count () {
      return p.size();
    }
    public int el_taken () {
      return taken;
    }
  };

  public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
  {
    pair lp;

    int row, col;
    test_cases = new double[T];
    for (int t = 0; t < T; ++t) {
      pairs ps = new pairs (N);
      Percolation p = new Percolation(N);
      while (!p.percolates()) {
        if (ps.count() <= 1) break;
        lp = ps.take_one ();
        p.open(lp.row,lp.col);
      }
      test_cases[t] = ((double)(ps.el_taken())/(N*N));
    }
  }

  public double mean()                     // sample mean of percolation threshold
  {
    return mean = StdStats.mean(test_cases);
  }
  public double stddev()                   // sample standard deviation of percolation threshold
  {
    return stddev = StdStats.stddev(test_cases);
  }
  public double confidenceLo()             // returns lower bound of the 95% confidence interval
  {
    return (mean - 1.96*stddev/Math.sqrt(T));
  }
  public double confidenceHi()             // returns upper bound of the 95% confidence interval
  {
    return (mean + 1.96*stddev/Math.sqrt(T));
  }
  public static void main(String[] args)   // test client, described below
  {
    if (args.length > 0) {
      try {
        N = Integer.parseInt (args[0]);
        T = Integer.parseInt (args[1]);
      } catch (NumberFormatException e) {
        System.err.println ("Argument "+" must be an integer.");
        System.exit(1);
      }
    }
    if (N <= 0 || T <= 0) {
      throw new java.lang.IllegalArgumentException(); 
    }
    /*
     *StdOut.printf ("Grid size: %d\n", N);
     *StdOut.printf ("Number of experiments: %d\n", T);
     */
    PercolationStats PS = new PercolationStats (N, T);
    StdOut.printf ("mean                    = %f\n", PS.mean());
    StdOut.printf ("stddev                  = %f\n", PS.stddev());
    StdOut.printf ("95%% confidence interval = %f, %f\n", PS.confidenceLo(), PS.confidenceHi());
  }
}
