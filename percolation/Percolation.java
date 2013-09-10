public class Percolation {
  private enum SiteState { SS_EMPTY, SS_BLOCKED, SS_FILLED };
  private int N;
  private SiteState[] ssArray;
  private boolean percolates;
  private WeightedQuickUnionUF qu;

  public Percolation(int size)              // create N-by-N grid, with all sites blocked
  {
    String str = "Error at: Percolation("+size+")";
    if (size <= 0) {
      throw new java.lang.IllegalArgumentException(str);
    }
    N = size;
    ssArray = new SiteState[N*N];
    for (int i = 0; i < ssArray.length; i++) {
      ssArray[i] = SiteState.SS_BLOCKED;
    }
    qu = new WeightedQuickUnionUF(N);
    percolates = false;
  }

  private void infillState(int NewPos) {
    int i = NewPos/N, j = NewPos % N;
    if (   (NewPos < 0) 
        || (NewPos > (N*N-1)) 
        || ssArray[NewPos] != SiteState.SS_EMPTY) {
      return;
    }
    ssArray[NewPos] = SiteState.SS_FILLED;
    if (i == (N-1)) {
      percolates = true;
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

  public void open(int i_ext, int j_ext)         // open site (row i, column j) if it is not already
  {
    String str = "Error at: open("+i_ext+","+j_ext+")";
    if (i_ext < 1 || j_ext < 1 || i_ext > N || j_ext > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    int i = i_ext-1, j = j_ext - 1, CurPos = N*i+j;
    boolean runFilling = false;

    if (i == 0) {
      ssArray[CurPos] = SiteState.SS_FILLED;
      if (N == 1) percolates = true;
      runFilling = true;
      qu.union(0,i);
    } else {
      ssArray[CurPos] = SiteState.SS_EMPTY;
    }
    int[] UpdPos = {-1, -1, -1, -1};
    if (i > 0)     UpdPos[0] = CurPos-N;
    if (i < (N-1)) UpdPos[1] = CurPos+N;
    if (j > 0)     UpdPos[2] = CurPos-1;
    if (j < (N-1)) UpdPos[3] = CurPos+1;
    /* check if there any filled site around */
    for (int k = 0; k < 4; ++k) {
      if (UpdPos[k] >= 0 && ssArray[UpdPos[k]] == SiteState.SS_FILLED) {
        ssArray[CurPos]= SiteState.SS_FILLED;
        if (i == (N-1)) {
          percolates = true;
        }
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
  }

  public boolean isOpen(int i, int j)    // is site (row i, column j) open?
  {
    String str = "Error at: isOpen("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    return ssArray[(i-1)*N+j-1] != SiteState.SS_BLOCKED;
  }

  public boolean isFull(int i, int j)    // is site (row i, column j) full?
  {
    String str = "Error at: isFull("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    return ssArray[(i-1)*N+j-1] == SiteState.SS_FILLED;
  }

  public boolean percolates()            // does the system percolate?
  {
    return percolates;
  }
}

