public class Percolation {
  private int N;
  private WeightedQuickUnionUF quConnected;
  private WeightedQuickUnionUF quFull;
  private boolean[] opened;

  public Percolation(int size)              // create N-by-N grid, with all sites blocked
  {
    if (size <= 0) {
      throw new java.lang.IllegalArgumentException();
    }
    N = size;
    quConnected = new WeightedQuickUnionUF(N*N+2);
    quFull = new WeightedQuickUnionUF(N*N+1);
    opened = new boolean[N*N];
    for (int i = 0; i < opened.length; i++) opened[i] = false;
  }

  public void open(int iExt, int jExt)         // open site (row i, column j) if it is not already
  {
    if (iExt < 1 || jExt < 1 || iExt > N || jExt > N) {
      throw new java.lang.IndexOutOfBoundsException();
    }
    int i = iExt-1, j = jExt - 1, curPos = N*i+j;
    opened[curPos] = true;

    if (i == 0) {
      quConnected.union(N*N, curPos);
      quFull.union(N*N, curPos);
    }
    if (i == N-1) {
      quConnected.union(N*N+1, curPos);
    }
    int actPos = 0;
    actPos = curPos-N;
    if (i > 0 && opened[actPos]) {
      quConnected.union(curPos, actPos);
      quFull.union(curPos, actPos);
    }
    actPos = curPos+N;
    if (i < (N-1) && opened[actPos]) {
      quConnected.union(curPos, actPos);
      quFull.union(curPos, actPos);
    }
    actPos = curPos-1;
    if (j > 0 && opened[actPos]) {
      quConnected.union(curPos, actPos);
      quFull.union(curPos, actPos);
    }
    actPos = curPos+1;
    if (j < (N-1) && opened[actPos]) {
      quConnected.union(curPos, actPos);
      quFull.union(curPos, actPos);
    }
  }

  public boolean isOpen(int i, int j)    // is site (row i, column j) open?
  {
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException();
    }
    return opened[(i-1)*N+j-1];
  }

  public boolean isFull(int i, int j)    // is site (row i, column j) full?
  {
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException();
    }
    return quFull.connected(N*N, (i-1)*N+j-1);
  }

  public boolean percolates()            // does the system percolate?
  {
    return quConnected.connected(N*N, N*N+1);
  }
}

