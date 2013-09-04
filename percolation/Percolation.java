public class Percolation {
  private enum SiteState { SS_EMPTY, SS_BLOCKED, SS_FILLED };
  private SiteState[] ssArray;
  private int N;
  private WeightedQuickUnionUF qu;

  public Percolation(int size)              // create N-by-N grid, with all sites blocked
  {
    String str = "Error at: Percolation("+size+")";
    if (size <= 0) {
      throw new java.lang.IllegalArgumentException(str);
    }
    N = size;
    ssArray = new SiteState[N*N+2];
    for (int i = 0; i < ssArray.length; ++i)
        ssArray[i] = SiteState.SS_BLOCKED;
    ssArray[N*N] = SiteState.SS_EMPTY;
    ssArray[N*N+1] = SiteState.SS_EMPTY;
    qu = new WeightedQuickUnionUF(N*N+2);
  }

  private void infill_state(int pos) {
    if ((pos < 0)
	|| (pos > (N-1))
	|| (ssArray[pos] != SiteState.SS_EMPTY)) {
      return;
    }
    int i = pos/N, j = pos%N;
    ssArray[pos] = SiteState.SS_FILLED;
    /* check for neighbors states */
    /* up */
    if (i > 0) {
      infill_state(pos-N);
    }
    /* down */
    if (i < (N-1)) {
      infill_state(pos+N);
    }
    /* left */
    if (j > 0) {
      infill_state(pos-1);
    }
    /* right */
    if (j < (N-1)) {
      infill_state(pos+1);
    }
  }

  private int check_neighbor(int cur_pos, int new_pos) {
    int upd_pos = -1;
    if (ssArray[new_pos] != SiteState.SS_BLOCKED) {
      qu.union(cur_pos, new_pos);
      if (ssArray[new_pos] == SiteState.SS_FILLED
	  || ssArray[cur_pos] == SiteState.SS_FILLED) {
	upd_pos = cur_pos+N;
	ssArray[cur_pos] = SiteState.SS_FILLED;
	ssArray[new_pos] = SiteState.SS_FILLED;
      } else {
	ssArray[new_pos] = SiteState.SS_EMPTY;
      }
    }
    return upd_pos;
  }

  public void open(int i, int j)         // open site (row i, column j) if it is not already
  {
    String str = "Error at: open("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    int cur_pos = (i-1)*N+j-1;
    int upd_pos[] = {-1,-1,-1,-1}, upd_cnt = 0;
    ssArray[cur_pos] = SiteState.SS_EMPTY;
    /* connect top or bottom site with a begin or end */
    if (i == 1) {
      qu.union(N*N, cur_pos);
      ssArray[cur_pos] = SiteState.SS_FILLED;
    }
    if (i == N) {
      qu.union(N*N+1, cur_pos); 
    }
    /* check for neighbors */
    /* up */
    if (i > 1) {
      upd_pos[0] = check_neighbor(cur_pos, cur_pos-N);
    }
    /* down */
    if (i < N) {
      upd_pos[1] = check_neighbor(cur_pos, cur_pos+N);
    }
    /* left */
    if (j > 1) {
      upd_pos[2] = check_neighbor(cur_pos, cur_pos-1);
    }
    /* rigth */
    if (j < N) {
      upd_pos[3] = check_neighbor(cur_pos, cur_pos+1);
    }
    /* recursively update site states if necessary */
    for (int k = 0; k < 4; ++k) {
      infill_state(upd_pos[k]);
    }
  }

  public boolean isOpen(int i, int j)    // is site (row i, column j) open?
  {
    String str = "Error at: isOpen("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    return ssArray[(i-1)*N+j-1] == SiteState.SS_EMPTY;
  }

  public boolean isFull(int i, int j)    // is site (row i, column j) full?
  {
    String str = "Error at: isFull("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    return ssArray[(i-1)*N+j-1] == SiteState.SS_FILLED;
  }

  private void printout() {
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
	switch (ssArray[i*N+j]) {
	  case SS_FILLED:
	    StdOut.printf("O");
	    break;
	  case SS_EMPTY:
	    StdOut.printf(".");
	    break;
	  case SS_BLOCKED:
	    StdOut.printf("#");
	    break;
	  default:
	    StdOut.printf("*");
	}
      }
      StdOut.printf("\n");
    }
    StdOut.printf("\n");
  }

  public boolean percolates()            // does the system percolate?
  {
    printout();
    return qu.connected(N*N, N*N+1);
  }
}

