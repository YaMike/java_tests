import java.util.ArrayList;

public class PercolationStats {
  private static int T;
  private static int N;
  private double[] testCases;
  private double mean;
  private double stddev;
  private double confLo;
  private double confHi;

  private class Pair {
    private int row;
    private int col;
    public int getRow() { return row;}
    public int getCol() { return col;}
    public Pair(int r, int c) {
      row = r;
      col = c;
    }
    public Pair(Pair p) {
      row = p.row;
      col = p.col;
    }
  };
  private class Pairs {
    private ArrayList<Pair> p;
    private int taken;
    public Pairs(int n) {
      taken = 0;
      p = new ArrayList<Pair>();
      int row = 1, col = 1, count = n*n;
      for(int i = 0; i < count; ++i) {
        p.add(new Pair(row, col));
        col++;
        if((col-1) % n == 0) {
          row++;
          col = 1;
        }
      }
    }
    public Pair takeOne() {
      int iElem = StdRandom.uniform(0, p.size()-1);
      Pair lp = new Pair(p.get(iElem));
      p.remove(iElem);
      taken++;
      return lp;
    }
    public int count() {
      return p.size();
    }
    public int elTaken() {
      return taken;
    }
  };

  public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
  {
    if(N <= 0 || T <= 0) {
      String str = "Error at main: N="+N+", T="+T+"\n";
      throw new java.lang.IllegalArgumentException(str); 
    }
    Pair lp;
    int row, col;
    testCases = new double[T];
    for(int t = 0; t < T; ++t) {
      testCases[t] = 0.0;
    }
    for(int t = 0; t < T; ++t) {
      Pairs ps = new Pairs(N);
      Percolation p = new Percolation(N);
      while(!p.percolates()) {
        if(ps.count() <= 1) break;
        lp = ps.takeOne();
        p.open(lp.getRow(),lp.getCol());
      }
      testCases[t]=((double)ps.elTaken())/(N*N);
      StdOut.printf("%f\n", testCases[t]);
    }
    mean=StdStats.mean(testCases);
    stddev=StdStats.stddev(testCases);
    confLo=mean-1.96*stddev*1/Math.sqrt(T);
    confHi=mean-1.96*stddev*1/Math.sqrt(T);
  }

  public double mean()                     // sample mean of percolation threshold
  {
    return mean;
  }
  public double stddev()                   // sample standard deviation of percolation threshold
  {
    return stddev;
  }
  public double confidenceLo()             // returns lower bound of the 95% confidence interval
  {
    return confLo;
  }
  public double confidenceHi()             // returns upper bound of the 95% confidence interval
  {
    return confHi;
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
    PercolationStats PS = new PercolationStats (N, T);
    StdOut.printf ("mean                    = %f\n", PS.mean());
    StdOut.printf ("stddev                  = %f\n", PS.stddev());
    StdOut.printf ("95%% confidence interval = %f, %f\n", PS.confidenceLo(), PS.confidenceHi());
  }
}
