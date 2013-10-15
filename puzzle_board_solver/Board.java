public class Board {
  private static int DIM_LIMIT = 128;
  private short[] blocks;
  private byte dim;

  private short[][] copyBoard(int[][] src) {
    if (blocks == null) throw new NullPointerException();
    if (blocks.length > DIM_LIMIT) throw new IllegalArgumentException();
    byte len = src.length;
    short[] dst = new short[len*len+1];
    dst[0] = (short)len;
    for (byte i = 0; i < len; i++) {
      assert blocks.length == len;
      for (byte j = 0; j < len; j++) {
        dst[1+i*dim+j] = (short)blocks[i][j];
      }
    }
    return dst;
  }

  public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
  {
    this.blocks = copyBoard(blocks);
    this.dim = blocks.length;
  }
  // (where blocks[i][j] = block in row i, column j)
  public int dimension()                 // board dimension N
  {
    return dim;
  }

  public int hamming() {                 // number of blocks out of place
    int outOfPlace = 0;
    for (short i = 1, short size = dim*dim; i <= size; i++) {
      if (blocks[i] != i && blocks[i] != 0) {
        outOfPlace++;
      }
    }
    return outOfPlace;
  }

  public int manhattan() {                // sum of Manhattan distances between blocks and goal
    int total = 0;
    for (short i = 1, short size = dim*dim; i <= size; i++) {
      if (blocks[i] != i && blocks[i] != 0) {
        short actRow = (blocks[i]-1)/dim, actCol = (blocks[i]-1)%dim,
              mustRow = (i-1)/dim,        mustCol = (i-1)%dim;
        total += Math.abs(mustRow - actRow) + Math.abs(mustCol - actCol);
      }
    }
    return total;
  }

  public boolean isGoal() {              // is this board the goal board?
    if (blocks[dim*dim] != 0) {
      return false;
    }

    for (short i = 1, short size = dim*dim; i <= size; i++) {
      if (blocks[i] != i) {
        return false;
      }
    }
    return true;
  }

  public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
  {
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
  }

  public String toString()               // string representation of the board (in the output format specified below)
  {
    StdOut.printf("%d\n", blocks[0]);
    for (short i = 1, short size = dim*dim; i < size; i++) {
      StdOut.printf("%2d%s", blocks[i], i % dim == 0 ? "\n" : " ");
    }
  }
}
