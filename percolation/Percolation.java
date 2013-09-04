public class Percolation {
  private enum SiteState { SS_EMPTY, SS_BLOCKED, SS_FILLED };
  private SiteState[] ssArray;
  private int N;
  private WeightedQuickUnionUF qu;

  public Percolation(int size)              // create N-by-N grid, with all sites blocked
  {
		if (size <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
    N = size;
    ssArray = new SiteState[N*N+2];
    for (int i = 0; i < ssArray.length; ++i)
        ssArray[i] = SiteState.SS_BLOCKED;
    ssArray[N*N] = SiteState.SS_EMPTY;
    ssArray[N*N+1] = SiteState.SS_EMPTY;
    qu = new WeightedQuickUnionUF(N*N+2);
  }

  public void open(int i, int j)         // open site (row i, column j) if it is not already
  {
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException();
    }
    ssArray[(i-1)*N+j-1] = SiteState.SS_EMPTY;
    /* connect top or bottom site with a begin or end */
    if (i == 1) {
      qu.union(N*N, j-1); 
    }
    if (i == N) {
      qu.union(N*N+1, N*N-N+j-1); 
    }
    /* check for neighbors */
    /* up */
    if (i > 1
        && ssArray[(i-2)*N+j-1] == SiteState.SS_EMPTY) {
      qu.union((i-1)*N+j-1, (i-2)*N+j-1);
    }
    /* down */
    if (i < N
      && ssArray[i*N+j-1] == SiteState.SS_EMPTY) {
      qu.union((i-1)*N+j-1, (i)*N+j-1);
    }
    /* left */
    if (j > 1
      && ssArray[(i-1)*N+j-2] == SiteState.SS_EMPTY) {
      qu.union((i-1)*N+j-1, (i-1)*N+j-2);
    }
    /* rigth */
    if (j < N
      && ssArray[(i-1)*N+j] == SiteState.SS_EMPTY) {
      qu.union((i-1)*N+j-1, (i-1)*N+j);
    }
  }

  public boolean isOpen(int i, int j)    // is site (row i, column j) open?
  {
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException();
    }
    return ssArray[i*N+j-1] == SiteState.SS_EMPTY;
  }

  public boolean isFull(int i, int j)    // is site (row i, column j) full?
  {
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException();
    }
    return ssArray[i*N+j-1] == SiteState.SS_FILLED;
  }

/*
   *private void printout() {
   *  for (int i = 0; i < N; ++i) {
   *    for (int j = 0; j < N; ++j) {
   *      if (qu.connected(i*N+j, N*N)) {
   *        StdOut.printf("%s", ssArray[i*N+j] == SiteState.SS_EMPTY ? "O" :
   *            ssArray[i*N+j] == SiteState.SS_BLOCKED ? "#" :
   *            ssArray[i*N+j] == SiteState.SS_FILLED ? "." :
   *            "*");
   *      } else
   *      if (qu.connected(i*N+j, N*N+1)) {
   *        StdOut.printf("%s", ssArray[i*N+j] == SiteState.SS_EMPTY ? "." :
   *            ssArray[i*N+j] == SiteState.SS_BLOCKED ? "#" :
   *            ssArray[i*N+j] == SiteState.SS_FILLED ? "O" :
   *            "*");
   *      } else {
   *        StdOut.printf("%s", ssArray[i*N+j] == SiteState.SS_EMPTY ? "." :
   *            ssArray[i*N+j] == SiteState.SS_BLOCKED ? "#" :
   *            ssArray[i*N+j] == SiteState.SS_FILLED ? "O" :
   *            "*");
   *      }
   *    }
   *    StdOut.printf("\n");
   *  }
   *  StdOut.printf("\n");
   *}
 */

  public boolean percolates()            // does the system percolate?
  {
    //printout();
    return qu.connected(N*N, N*N+1);
  }
}

