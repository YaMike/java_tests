class RandomizedQueue<Item> implements Iterable<Item> {
  public RandomizedQueue()           // construct an empty randomized queue
  {
  }

  public boolean isEmpty()           // is the queue empty?
  {
  }

  public int size()                  // return the number of items on the queue
  {
  }

  public void enqueue(Item item)     // add the item
  {
  }

  public Item dequeue()              // delete and return a random item
  {
  }

  public Item sample()               // return (but do not delete) a random item
  {
  }

  public Iterator<Item> iterator()   // return an independent iterator over items in random order
  {
    return new RandomIterator();
  }

  public static void main(String[] args) {
    RandomizedQueue<String> s = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (item.length() < 1) break;
      if (item.charAt(0) == '+') {
        s.enqueue(item.substring(1));
        StdOut.printf("Enqueue string: %s\n", item.substring(2));
      } else
      if (item.charAt(0) == '-') {
        String removed_str = "";
        removed_str = s.dequeue();
        StdOut.printf("Dequeue item: %s\n", removed_str);
      } else {
        StdOut.printf("Finishing processing.\n");
        break;
      }
      StdOut.printf("****************************\nRandomizedQueue (%d):\n", s.size());
      for (String str: s) {
        StdOut.printf("%s\n", str);
      }
      StdOut.printf("****************************\n");
    }
    StdOut.printf("Number of elems = %d :\n", s.size());
    for (String str: s) {
      StdOut.printf("%s\n", str);
    }
  }  
}
