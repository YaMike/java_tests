import java.util.Arrays;

public class Board {
  private static int DIM_LIMIT = 128;
  private short[] blocks;

  private short[] copyBoard(int[][] src) {
		if (src == null) throw new NullPointerException();
    if (src.length > DIM_LIMIT) throw new IllegalArgumentException();
    byte len = (byte)(src.length);
    short[] dst = new short[len*len+1];
    dst[0] = (short)len;
    for (byte i = 0; i < len; i++) {
      assert src.length == len;
      for (byte j = 0; j < len; j++) {
        dst[1+i*len+j] = (short)src[i][j];
      }
    }
    return dst;
  }

  public Board(int[][] blocks) {          // construct a board from an N-by-N array of blocks
    this.blocks = copyBoard(blocks);
  }

	public Board(short[] blocks) {
		this.blocks = blocks;
	}

  // (where blocks[i][j] = block in row i, column j)
  public int dimension()                 // board dimension N
  {
    return blocks[0];
  }

  public int hamming() {                 // number of blocks out of place
    int outOfPlace = 0;
		short size = (short)(blocks[0]*blocks[0]);
    for (short i = 1; i <= size; i++) {
      if (blocks[i] != i && blocks[i] != 0) {
        outOfPlace++;
      }
    }
    return outOfPlace;
  }

  public int manhattan() {                // sum of Manhattan distances between blocks and goal
    int total = 0;
		short dim = blocks[0];
		short size = (short)(dim*dim);
    for (short i = 1; i <= size; i++) {
      if (blocks[i] != i && blocks[i] != 0) {
        short actRow = (short)((blocks[i]-1)/dim), actCol = (short)((blocks[i]-1)%dim),
              mustRow =(short)((i-1)/dim),         mustCol = (short)((i-1)%dim);
        total += Math.abs(mustRow - actRow) + Math.abs(mustCol - actCol);
      }
    }
    return total;
  }

  public boolean isGoal() {              // is this board the goal board?
		short dim = blocks[0];
    if (blocks[dim*dim] != 0) {
      return false;
    }

		short size = (short)(dim*dim);
    for (short i = 1; i <= size; i++) {
      if (blocks[i] != i) {
        return false;
      }
    }
    return true;
  }

  public Board twin() {                    // a board obtained by exchanging two adjacent blocks in the same row
		short dim = blocks[0];
		short[] twin_blocks = new short[dim*dim+1];
		System.arraycopy(blocks, 0, twin_blocks, 0, blocks.length);
		return new Board(twin_blocks);
  }

  public boolean equals(Object y)        // does this board equal y?
  {
    if (y == null) return false;
    if (y == this) return true;
    if (y.getClass() != this.getClass()) return false;
    Board that = (Board)y;
    if (this.blocks.length != that.blocks.length) return false;
    if (!Arrays.equals(this.blocks, that.blocks)) return false;
    return true;
  }

  public Iterable<Board> neighbors()     // all neighboring boards
  {
		Queue<Board> q = new Queue<Board>();
		/*TODO*/
		return q;
  }

  public String toString()               // string representation of the board (in the output format specified below)
  {
		StringBuilder sb = new StringBuilder();
		short dim = blocks[0];
		sb.append(String.format("%d\n", dim));
		short size = (short)(dim*dim);
    for (short i = 1; i < size; i++) {
			sb.append(String.format("%2d%s", blocks[i], i % dim == 0 ? "\n" : " "));
    }
		return sb.toString();
  }
}
