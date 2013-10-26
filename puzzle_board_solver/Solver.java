public class Solver {
  private Node last = null;

  private class Node implements Comparable<Node> {
    private final Board board;
    private final Node previous;
    private final int moves;
    private final int priority;

    public Node(Board b, Node p) {
      board = b;
      previous = p;
      if (previous != null) {
        moves = previous.moves + 1;
      } else {
        moves = 0;
      }
      priority = board.manhattan() + moves;
    }

    @Override
    public int compareTo(Node that) {
      return this.priority - that.priority;
    }
  }

  private Node propagate(MinPQ<Node> pq) {
    if (pq.isEmpty()) {
      return null;
    }
    Node outNode = pq.delMin();
    if (outNode.board.isGoal()) {
      return outNode;
    }
    for (Board b: outNode.board.neighbors()) {
      if (outNode.previous == null || !b.equals(outNode.previous.board)) {
        pq.insert(new Node(b,outNode));
      }
    }
    return null;
  }

  public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)

    MinPQ<Node> mainPQ = new MinPQ<Node>();
    MinPQ<Node> twinPQ = new MinPQ<Node>();

    mainPQ.insert(new Node(initial,null));
    twinPQ.insert(new Node(initial.twin(),null));

    boolean notSolved = true;
    while (notSolved) {
      notSolved = ((null == (last = propagate(mainPQ))) && null == propagate(twinPQ));
    }
  }

  public boolean isSolvable() {           // is the initial board solvable?
    return last != null;
  }

  public int moves() {                    // min number of moves to solve initial board; -1 if no solution
    if (last != null) {
      return last.moves;
    } else {
      return -1;
    }
  }

  public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if no solution
    if (null == last) {
      return null;
    }
    Stack<Board> solutionStack = new Stack<Board>();
    for (Node node = last; node != null; node = node.previous) {
      solutionStack.push(node.board);
    }
    return solutionStack;
  }

  public static void main(String[] args) {// solve a slider puzzle (given below)
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}
