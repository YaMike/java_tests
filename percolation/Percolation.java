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

	private int perlocate_to (int i, int j)
	{
		if (ssMatrix[i][j] == SiteState.SS_FILLED) {
			return i;
		}
		/* left side */
		if (j > 0 && ssMatrix[i][j-1] != SiteState.SS_BLOCKED) {
			return perlocate_to (i, j-1);
		}
		/* rigth side */
		if (j < (ssMatrix.length - 1) && ssMatrix[i][j+1] != SiteState.SS_BLOCKED) {
			return perlocate_to (i, j+1);
		}
		/* bottom side */
		if (i < (ssMatrixSize - 1) && ssMatrix[i+1][j] != SiteState.SS_BLOCKED) {
			return perlocate_to (i + 1, j);
		}
		return i;
	}

	public boolean percolates()            // does the system percolate?
	{
		/* fill in first row empty sites */
		for (int j = 0; j < ssMatrix.length; ++j) {
			if (ssMatrix[0][j] == SiteState.SS_EMPTY) {
				ssMatrix[0][j] = SiteState.SS_FILLED;
			}
		}
		/* begin flood */
		int level = 0, flood_level = 0;
		for (int i_filled = 0; i_filled < ssMatrix.length; ++i_filled) {
			if (ssMatrix[0][i_filled] == SiteState.SS_FILLED) {
				if (flood_level < (level = perlocate_to (0, i_filled))) {
					flood_level = level;
				}
			}
		}
		if (flood_level == (ssMatrixSize - 1)) {
			return true;
		} else {
			return false;
		}
	}
}
