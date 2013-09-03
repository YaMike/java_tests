public class Percolation {
	private enum SiteState {SS_EMPTY, SS_BLOCKED, SS_FILLED};
	private SiteState[][] ssMatrix;
	private int ssMatrixSize;

	public Percolation(int N)              // create N-by-N grid, with all sites blocked
	{
		ssMatrixSize = N;
		ssMatrix = new SiteState[N][N];
		for (int i = 0; i < ssMatrix.length; ++i)
			for (int j = 0; j < ssMatrix[i].length; ++j)
				ssMatrix[i][j] = SiteState.SS_BLOCKED;
	}
	public void open(int i, int j)         // open site (row i, column j) if it is not already
	{
		ssMatrix[i][j] = SiteState.SS_EMPTY;
	}

	public boolean isOpen(int i, int j)    // is site (row i, column j) open?
	{
		return ssMatrix[i][j] == SiteState.SS_EMPTY;
	}

	public boolean isFull(int i, int j)    // is site (row i, column j) full?
	{
		return ssMatrix[i][j] == SiteState.SS_FILLED;
	}

	private int percolate_to (int i, int j)
	{
		ssMatrix[i][j] = SiteState.SS_FILLED;
		if (i == ssMatrixSize-1) return i;
		/* left side */
		if ((j > 0) && (ssMatrix[i][j-1] == SiteState.SS_EMPTY)) {
			return percolate_to (i, j-1);
		}
		/* rigth side */
		if ((j < (ssMatrixSize-2)) && (ssMatrix[i][j+1] == SiteState.SS_EMPTY)) {
			return percolate_to (i, j+1);
		}
		/* bottom side */
		if ((i < (ssMatrixSize-1)) && (ssMatrix[i+1][j] == SiteState.SS_EMPTY)) {
			return percolate_to (i+1, j);
		}
		return i;
	}

	public void printout () {
		for (int i = 0; i < ssMatrix.length;++i) {
			for (int j = 0; j < ssMatrix[i].length;++j) {
				StdOut.printf("%s", ssMatrix[i][j] == SiteState.SS_EMPTY ? "." :
													  ssMatrix[i][j] == SiteState.SS_BLOCKED ? "#" :
													  ssMatrix[i][j] == SiteState.SS_FILLED ? "O" :
													  "*");
			}
			StdOut.printf("\n");
		}
		StdOut.printf("\n");
	}

	public boolean percolates()            // does the system percolate?
	{
		/* fill in first row empty sites */
		for (int i = 0; i < ssMatrix.length; ++i) {
			for (int j = 0; j < ssMatrix.length; ++j) {
				if (i == 0 && isOpen(0,j)) {
					ssMatrix[0][j] = SiteState.SS_FILLED;
				} else 
				if (i > 0 && isFull(i,j)) {
					ssMatrix[i][j] = SiteState.SS_EMPTY;
				}
			}
		}
		/* begin flood */
		int flood_level = 0;
		for (int i_filled = 0; i_filled < ssMatrix.length; ++i_filled) {
			if (isFull(0,i_filled)) {
				if (ssMatrixSize-1 == (flood_level = percolate_to (0, i_filled))) {
					break;
				}
			}
		}
		//printout();
		if (flood_level == (ssMatrixSize - 1)) {
			return true;
		} else {
			return false;
		}
	}
}

