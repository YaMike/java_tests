import java.util.Arrays;

public class Board {
  private static final boolean DEBUG = false;
  private static int DIM_LIMIT = 128;
  private short[] blocks;
  private byte dim;

  private short[] copyBoard(int[][] src) {
    if (src == null) throw new NullPointerException();
    if (src.length > DIM_LIMIT) throw new IllegalArgumentException();
    byte len = (byte)(src.length);
    short[] dst = new short[len*len+1];
    dst[0] = 0;
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
    this.dim = (byte)blocks.length;
  }

  private Board(byte dim, short[] blocks) {
    this.blocks = blocks;
    this.dim = dim;
  }

  // (where blocks[i][j] = block in row i, column j)
  public int dimension() {               // board dimension N
    return dim;
  }

  public int hamming() {                 // number of blocks out of place
    int outOfPlace = 0;
    short size = (byte)(dim*dim);
    for (short i = 1; i <= size; i++) {
      if (blocks[i] != i && blocks[i] != 0) {
        outOfPlace++;
      }
    }
    return outOfPlace;
  }

  public int manhattan() {                // sum of Manhattan distances between blocks and goal
    int total = 0;
    short size = (short)(dim*dim);
    for (short i = 1; i <= size; i++) {
      if (blocks[i] != i && blocks[i] != 0) {
        short actRow = (short)((blocks[i]-1)/dim), actCol = (short)((blocks[i]-1)%dim),
              mustRow =(short)((i-1)/dim),         mustCol = (short)((i-1)%dim);
        total += Math.abs(mustRow - actRow) + Math.abs(mustCol - actCol);
      }
    }                                                                                              if (DEBUG) System.out.println("manhattan="+total);
    return total;
  }

  public boolean isGoal() {              // is this board the goal board?
    if (blocks[dim*dim] != 0) {                                                                    if (DEBUG) System.out.println("not goal 1");
      return false;
    }

    short size = (short)(dim*dim);
    for (short i = 1; i < size; i++) {
      if (blocks[i] != i) {                                                                        if (DEBUG) System.out.println("why not goal: "+Arrays.toString(blocks));
        return false;
      }
    }                                                                                              if (DEBUG) System.out.println("why goal: "+Arrays.toString(blocks)); 
    return true;
  }

  public Board twin() {                    // a board obtained by exchanging two adjacent blocks in the same row
    short[] twin_blocks = new short[dim*dim+1];
    System.arraycopy(blocks, 0, twin_blocks, 0, blocks.length);

    short value = 0;
    short size = (short)(dim*dim);
    short column = 0;
    twin_blocks[0] = 0;

    for (int i = 1; i <= size; ++i) {
      column = (short)((i - 1) % dim);
      if (   twin_blocks[i] != 0 
          && column > 0 
          && twin_blocks[i-1] != 0) {
        short tempValue = twin_blocks[i];
        twin_blocks[i] = twin_blocks[i-1];
        twin_blocks[i-1] = tempValue;
        break;
      }
    }
    return new Board(dim, twin_blocks);
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

  private short[] swapElems(short elem1, short elem2) {

    short[] blocksCopy = new short[this.blocks.length];
    System.arraycopy(this.blocks, 0, blocksCopy, 0, this.blocks.length);
    if (DEBUG) System.out.println("elem1="+elem1+",elem2="+elem2);
    short tempValue = blocksCopy[elem1];
    blocksCopy[elem1] = blocksCopy[elem2];
    blocksCopy[elem2] = tempValue;

    return blocksCopy;
  }

  public Iterable<Board> neighbors() {   // all neighboring boards
    Queue<Board> q = new Queue<Board>();

    short size = (short)(dim*dim);
    short pos = 1;

    for (; pos < size; ++pos) {
      if (0 == blocks[pos]) { break; }
    }

    short row = (short)((pos-1)/dim);
    short col = (short)((pos-1)%dim);
    if (DEBUG) System.out.println("pos="+pos+", row="+row+", col="+col+", dim="+dim);

    if (row > 0) {                                                                                 if (DEBUG) System.out.println("if (row > 0)");
      q.enqueue(new Board(dim, swapElems((short)(pos-dim), pos)));
    }
    if (row < (dim-1)) {                                                                           if (DEBUG) System.out.println("if (row < (dim-1))");
      q.enqueue(new Board(dim, swapElems(pos, (short)(pos+dim))));
    }
    if (col > 0) {                                                                                 if (DEBUG) System.out.println("if (col > 0)");
      q.enqueue(new Board(dim, swapElems((short)(pos-1), pos)));
    }
    if (col < (dim-1)) {                                                                           if (DEBUG) System.out.println("if (col < (dim-1))");
      q.enqueue(new Board(dim, swapElems(pos, (short)(pos+1))));
    }
    return q;
  }

  public String toString() {             // string representation of the board (in the output format specified below)

    StringBuilder sb = new StringBuilder();
    sb.append(String.format("%d\n", dim));
    short size = (short)(dim*dim);
    for (short i = 1; i <= size; i++) {
      sb.append(String.format("%2d%s", blocks[i], i % dim == 0 ? "\n" : " "));
    }
    return sb.toString();
  }
}
