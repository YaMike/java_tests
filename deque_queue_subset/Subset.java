class Subset {
  public static void main(String[] args)
  {
    int k = 0;
    if (args.length > 0) {
      try {
        k = Integer.parseInt (args[0]);
      } catch (NumberFormatException e) {
        System.err.println("Argument "+" must be an integer.");
        System.exit(1);
      }
    }

    RandomizedQueue<String> rq = new RandomizedQueue<String>();

    while (!StdIn.isEmpty()) {
      String s = StdIn.readString();
      rq.enqueue(s);
    }

    for (int i = 0; i < k; i++) {
      StdOut.println(rq.dequeue());
    }
  }
}
