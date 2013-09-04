public class Percolation {
  private enum SiteState { SS_EMPTY, SS_BLOCKED, SS_FILLED };
  private SiteState[] ssArray;
  private int N;
  private WeightedQuickUnionUF qu;
  static private final boolean PercDebug = false;

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

  private void infillState(int OldPos, int NewPos) {
    if ((NewPos < 0)
	|| (NewPos > (N*N-1))
	|| (ssArray[NewPos] != SiteState.SS_EMPTY)) {
      return;
    }
    int i = NewPos/N, j = NewPos % N;
    ssArray[NewPos] = SiteState.SS_FILLED;
    if (PercDebug) {
      StdOut.printf("infill %d %d\n", i, j);
      printOut();
    }
    qu.union(OldPos, NewPos);
    /* check for neighbors states */
    /* up */
    if (i > 0) {
      infillState(OldPos, NewPos-N);
    }
    /* down */
    if (i < (N-1)) {
      infillState(OldPos, NewPos+N);
    }
    /* left */
    if (j > 0) {
      infillState(OldPos, NewPos-1);
    }
    /* right */
    if (j < (N-1)) {
      infillState(OldPos, NewPos+1);
    }
  }

  private int checkNeighbor(int CurPos, int NewPos) {
    int UpdPos = -1;
    if (ssArray[NewPos] != SiteState.SS_BLOCKED) {
      qu.union(CurPos, NewPos);
      if (ssArray[NewPos] == SiteState.SS_FILLED
	  || ssArray[CurPos] == SiteState.SS_FILLED) {
	UpdPos = NewPos;
	ssArray[CurPos] = SiteState.SS_FILLED;
	if (PercDebug) {
	  StdOut.printf("infill %d %d\n", CurPos/N, CurPos % N);
	  printOut();
	}
	ssArray[NewPos] = SiteState.SS_FILLED;
	if (PercDebug) {
	  StdOut.printf("infill %d %d\n", NewPos/N, NewPos % N);
	  printOut();
	}
      } else {
	ssArray[NewPos] = SiteState.SS_EMPTY;
      }
    }
    return UpdPos;
  }

  public void open(int i, int j)         // open site (row i, column j) if it is not already
  {
    String str = "Error at: open("+i+","+j+")";
    if (i < 1 || j < 1 || i > N || j > N) {
      throw new java.lang.IndexOutOfBoundsException(str);
    }
    int CurPos = (i-1)*N+j-1;
    int[] UpdPos = {-1, -1, -1, -1};
    ssArray[CurPos] = SiteState.SS_EMPTY;
    if (PercDebug) {
      StdOut.printf("Open %d %d\n", i-1, j-1);
      printOut();
    }
    /* connect top or bottom site with a begin or end */
    if (i == 1) {
      qu.union(N*N, CurPos);
      ssArray[CurPos] = SiteState.SS_FILLED;
      if (PercDebug) {
	StdOut.printf("set %d %d\n", CurPos/N, CurPos % N);
	printOut();
      }
    }
    if (i == N) {
      qu.union(N*N+1, CurPos); 
    }
    /* check for neighbors */
    /* up */
    if (i > 1) {
      UpdPos[0] = checkNeighbor(CurPos, CurPos-N);
    }
    /* down */
    if (i < N) {
      UpdPos[1] = checkNeighbor(CurPos, CurPos+N);
    }
    /* left */
    if (j > 1) {
      UpdPos[2] = checkNeighbor(CurPos, CurPos-1);
    }
    /* rigth */
    if (j < N) {
      UpdPos[3] = checkNeighbor(CurPos, CurPos+1);
    }
    /* recursively update site states if necessary */
    for (int k = 0; k < 4; ++k) {
      infillState(CurPos, UpdPos[k]);
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

  private void printOut() {
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
    return qu.connected(N*N, N*N+1);
  }
}

