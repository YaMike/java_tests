import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.NullPointerException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int N;
  private Item[] items;

  private void resize(int max) {
    Item[] temp = (Item[]) new Object[max];
    for (int i = 0; i < N; i++) {
      temp[i] = items[i];
    }
    items = null;
    items = temp;
  }

  public RandomizedQueue() {         // construct an empty randomized queue
    items = (Item[]) new Object[2];
    N = 0;
  }

  public boolean isEmpty() {         // is the queue empty?
    return N == 0;
  }

  public int size() {                // return the number of items on the queue
    return N;
  }

  public void enqueue(Item item) {   // add the item
    if (item == null)  throw new NullPointerException();
    if (N == items.length) resize(2*items.length);
    items[N] = item;
    N++;
  }

  public Item dequeue() {            // delete and return a random item
    if (isEmpty()) throw new NoSuchElementException();
    int i = StdRandom.uniform(N);
    Item it = items[i];
    items[i] = items[--N];
    items[N] = null;
    if (N < items.length/4) resize(items.length/2);
    return it;
  }

  public Item sample() {             // return (but do not delete) a random item
    if (isEmpty()) throw new NoSuchElementException();
    return items[StdRandom.uniform(N)];
  }

  public Iterator<Item> iterator()   // return an independent iterator over items in random order
  {
    return new RandomIterator();
  }

  private class RandomIterator implements Iterator<Item> {
    private int[] elNums;
    private int elNumsCnt;
    public RandomIterator() {
      elNumsCnt = N;
      elNums = new int[elNumsCnt];
      for (int i = 0; i < elNumsCnt; i++) {
        elNums[i] = i;
      }
    }
    public boolean hasNext() { return elNumsCnt > 0; }
    public void remove() { throw new UnsupportedOperationException(); }
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      int idx = StdRandom.uniform(elNumsCnt);
      Item it = items[elNums[idx]];
      elNums[idx] = elNums[--elNumsCnt];
      return it;
    }
  }

  public static void main(String[] args) {
    RandomizedQueue<String> s = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (item.length() < 1) break;
      if (item.charAt(0) == '+') {
        s.enqueue(item.substring(1));
        StdOut.printf("Enqueue string: %s\n", item.substring(1));
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
