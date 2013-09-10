public class Percolation {
  private int N;
  private WeightedQuickUnionUF qu_connected;
  private WeightedQuickUnionUF qu_full;
  private boolean[] opened;

  public Percolation(int size)              // create N-by-N grid, with all sites blocked
  {
    String str = "Error at: Percolation("+size+")";
    if (size <= 0) {
      throw new java.lang.IllegalArgumentException(str);
    }
    N = size;
    qu_connected = new WeightedQuickUnionUF(N*N+2);
    qu_full = new WeightedQuickUnionUF(N*N+1);
    opened = new boolean[N*N];
    for (int i = 0; i < opened.length; opened[i++] = false);
  }

  public void open(int i_ext, int j_ext)         // open site (row i, column j) if it is not already
  {
    String str = "Error at: open("+i_ext+","+j_ext+")";
    if (i_ext < 1 || j_ext < 1 || i_ext > N || j_ext > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    int i = i_ext-1, j = j_ext - 1, CurPos = N*i+j;
    opened[CurPos] = true;

    if (i == 0) {
      qu_connected.union(N*N,CurPos);
      qu_full.union(N*N,CurPos);
    } else
    if (i == N-1) {
      qu_connected.union(N*N+1,CurPos);
    }
    int Pos = 0;
    if (i > 0 && opened[Pos=CurPos-N]) {
      qu_connected.union(CurPos, Pos);
      qu_full.union(CurPos, Pos);
    }
    if (i < (N-1) && opened[Pos=CurPos+N]) {
      qu_connected.union(CurPos, Pos);
      qu_full.union(CurPos, Pos);
    }
    if (j > 0 && opened[Pos=CurPos-1]) {
      qu_connected.union(CurPos, Pos);
      qu_full.union(CurPos, Pos);
    }
    if (j < (N-1) && opened[Pos=CurPos+1]) {
      qu_connected.union(CurPos, Pos);
      qu_full.union(CurPos, Pos);
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
    return qu_full.connected(N*N,(i-1)*N+j-1);
  }

  public boolean percolates()            // does the system percolate?
  {
    return qu_connected.connected(N*N,N*N+1);
  }
}

