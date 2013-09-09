public class Percolation {
  private boolean[] opened;
  private int N;
  private WeightedQuickUnionUF qu;

  public Percolation(int size)              // create N-by-N grid, with all sites blocked
  {
    String str = "Error at: Percolation("+size+")";
    if (size <= 0) {
      throw new java.lang.IllegalArgumentException(str);
    }
    N = size;
    qu = new WeightedQuickUnionUF(N*N+2);
    opened = new boolean[N*N+1];
    for (int i = 0; i < N; i++) {
      opened[i] = false;
    }
  }

  public void open(int i, int j)         // open site (row i, column j) if it is not already
  {
    String str = "Error at: open("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    int CurPos = (i-1)*N+j-1;
    opened[CurPos] = true;
    if (i == 1) {
      qu.union(N*N,CurPos);
    }

    if (i == N) {
      qu.union(N*N+1,CurPos);
    }
    
    if (i > 1 && opened[CurPos-N]) {
      qu.union(CurPos,CurPos-N);
    }
    if (i < (N) && opened[CurPos+N]) {
      qu.union(CurPos,CurPos+N);
    }
    if (j > 1 && opened[CurPos-1]) {
      qu.union(CurPos,CurPos-1);
    }
    if (j < (N) && opened[CurPos+1]) {
      qu.union(CurPos,CurPos+1);
    }
  }

  public boolean isOpen(int i, int j)    // is site (row i, column j) open?
  {
    String str = "Error at: isOpen("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    return opened[(i-1)*N+j-1];
  }

  public boolean isFull(int i, int j)    // is site (row i, column j) full?
  {
    String str = "Error at: isFull("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    return qu.connected(N*N, (i-1)*N+j-1);
  }

  public boolean percolates()            // does the system percolate?
  {
    return qu.connected(N*N,N*N+1);
  }
}

