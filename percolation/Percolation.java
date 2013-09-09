public class Percolation {
  private enum SiteState { SS_EMPTY, SS_BLOCKED, SS_FILLED };
  private int N;
  private WeightedQuickUnionUF qu;
  private WeightedQuickUnionUF qu_open;
  static private final boolean PercDebug = false;

  public Percolation(int size)              // create N-by-N grid, with all sites blocked
  {
    String str = "Error at: Percolation("+size+")";
    if (size <= 0) {
      throw new java.lang.IllegalArgumentException(str);
    }
    N = size;
    qu = new WeightedQuickUnionUF(N*N+2);
    qu_open = new WeightedQuickUnionUF(N*N+1);
  }

  private void infillState(int NewPos) {
    int i = NewPos/N, j = NewPos % N;
    if (   (NewPos < 0) 
        || (NewPos > (N*N-1)) 
        || !qu_open.connected(N*N, NewPos)
        || qu.connected(N*N, NewPos)) {
      return;
    }
    if (PercDebug) {
      StdOut.printf("infill %d %d 32\n", i, j);
      printOut();
    }
    qu.union(N*N, NewPos);
    if (i == (N-1)) {
      qu.union(N*N+1,NewPos);
    }
    /* check for neighbors states */
    /* up */
    if (i > 0) {
      infillState (NewPos-N);
    }
    /* down */
    if (i < (N-1)) {
      infillState (NewPos+N);
    }
    /* left */
    if (j > 0) {
      infillState (NewPos-1);
    }
    /* right */
    if (j < (N-1)) {
      infillState (NewPos+1);
    }
  }

  public void open(int i, int j)         // open site (row i, column j) if it is not already
  {
    String str = "Error at: open("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    int CurPos = (i-1)*N+j-1;
    boolean runFilling = false;
    qu_open.union(N*N, CurPos);
    if (PercDebug) {
      StdOut.printf("Open %d %d 89\n", CurPos/N, CurPos % N);
      printOut();
    }
    if (i == 1) {
      qu.union(N*N, CurPos);
      runFilling = true;
      if (PercDebug) {
        StdOut.printf("set %d %d 97\n", CurPos/N, CurPos % N);
        printOut();
      }
    }
    int[] UpdPos = {-1, -1, -1, -1};
    if (i > 1)     UpdPos[0] = CurPos-N;
    if (i < (N)) UpdPos[1] = CurPos+N;
    if (j > 1)     UpdPos[2] = CurPos-1;
    if (j < (N)) UpdPos[3] = CurPos+1;
    /* check if there any filled site around */
    for (int k = 0; k < 4; ++k) {
      if (UpdPos[k] > 0 && qu_open.connected(N*N, UpdPos[k]) && qu.connected(N*N, UpdPos[k])) {
        qu.union(N*N, CurPos);
        runFilling = true;
        break;
      }
    }
    /* recursively update site states if necessary */
    if (runFilling) {
      for (int k = 0; k < 4; ++k) {
        if (UpdPos[k] > 0) {
          infillState(UpdPos[k]);
        }
      }
    }
    if (i == N && qu.connected(N*N, CurPos)) {
      qu.union(N*N+1, CurPos);
    }
  }

  public boolean isOpen(int i, int j)    // is site (row i, column j) open?
  {
    String str = "Error at: isOpen("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    return qu_open.connected(N*N,(i-1)*N+j-1);
  }

  public boolean isFull(int i, int j)    // is site (row i, column j) full?
  {
    String str = "Error at: isFull("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    return qu.connected(N*N, (i-1)*N+j-1);
  }

  private void printOut() {
    if (PercDebug) {
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        if (qu.connected(N*N,i*N+j)) {
          StdOut.printf("O");
        } else
          if (qu_open.connected(N*N,i*N+j)) {
            StdOut.printf(".");
          } else {
            StdOut.printf("#");
          }
      }
      StdOut.printf("\n");
    }
    StdOut.printf("\n");
    }
  }

  public boolean percolates()            // does the system percolate?
  {
    return qu.connected(N*N, N*N+1);
  }
}

