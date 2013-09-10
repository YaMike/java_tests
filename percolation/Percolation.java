public class Percolation {
  private boolean[] opened;
  private int N;
  private SiteState[] ssArray;
  private boolean percolates;
  private WeightedQuickUnionUF qu;
<<<<<<< HEAD
=======
  private boolean percolates;
  private int[] idx;
  private int idx_cnt;
>>>>>>> master

  public Percolation(int size)              // create N-by-N grid, with all sites blocked
  {
    String str = "Error at: Percolation("+size+")";
    if (size <= 0) {
      throw new java.lang.IllegalArgumentException(str);
    }
    N = size;
<<<<<<< HEAD
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
=======
    qu = new WeightedQuickUnionUF(N*N+1);
    opened = new boolean[N*N];
    idx = new int[N];
    idx_cnt = 0;
    percolates = false;
    for (int i = 0; i < opened.length; i++) {
      opened[i] = false;
>>>>>>> master
    }
  }

  public void open(int i_ext, int j_ext)         // open site (row i, column j) if it is not already
  {
    String str = "Error at: open("+i_ext+","+j_ext+")";
    if (i_ext < 1 || j_ext < 1 || i_ext > N || j_ext > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
<<<<<<< HEAD
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
=======
    int CurPos = (i-1)*N+j-1;
    opened[CurPos] = true;
    if (i == 1) {
      qu.union(N*N,CurPos);
    }
    if (i == N) {
      idx[idx_cnt++] = N*(N-1)+j-1;
>>>>>>> master
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
<<<<<<< HEAD
=======
    if (j < (N) && opened[CurPos+1]) {
      qu.union(CurPos,CurPos+1);
    }
    if (!percolates && (idx_cnt > 0)) {
      int top = qu.find(N*N);
      for (int k = 0; k < idx_cnt; ++k) {
	if (qu.find(idx[k]) == top) {
	  percolates = true;
	  break;
	}
      }
    }
>>>>>>> master
  }

  public boolean isOpen(int i, int j)    // is site (row i, column j) open?
  {
    String str = "Error at: isOpen("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
<<<<<<< HEAD
    return ssArray[(i-1)*N+j-1] != SiteState.SS_BLOCKED;
=======
    return opened[(i-1)*N+j-1];
>>>>>>> master
  }

  public boolean isFull(int i, int j)    // is site (row i, column j) full?
  {
    String str = "Error at: isFull("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
<<<<<<< HEAD
    return ssArray[(i-1)*N+j-1] == SiteState.SS_FILLED;
=======
    return qu.connected(N*N, (i-1)*N+j-1);
>>>>>>> master
  }

  public boolean percolates()            // does the system percolate?
  {
    return percolates;
  }
}

