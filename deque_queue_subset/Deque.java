import java.util.NoSuchElementException;
import java.util.Iterator;
import java.lang.NullPointerException;

public class Deque<Item> implements Iterable<Item> {
  private int N;
  private Node first;
  private Node last;

  private final class Node {
    private Item item;
    private Node next;
    private Node prev;
    public Node(Item newItem) {
      item = newItem;
      next = null;
      prev = null;
    }
    public Node(Node aNode) {
      item = aNode.item;
      next = aNode.next;
      prev = aNode.prev;
    }
    public Item getItem() {
      return item;
    }
  }

  public Deque() {                   // construct an empty deque
    first = null;
    last = null;
    N = 0;
  }

  public boolean isEmpty()           // is the deque empty?
  {
    return first == null;
  }

  public int size() {                // return the number of items on the deque
    return N;
  }

  public void addFirst(Item item)    // insert the item at the front
  {
    if (item == null) throw new NullPointerException();
    Node node = new Node(item);
    if (N == 0) {
      last = node;
    } else {
      node.next = first;
      first.prev = node;
    }
    first = node;
    N++;
  }

  public void addLast(Item item)     // insert the item at the end
  {
    if (item == null) throw new NullPointerException();
    Node node = new Node(item);
    if (N == 0) {
      first = node;
    } else {
      node.prev = last;
      last.next = node;
    }
    last = node;
    N++;
  }

  public Item removeFirst()          // delete and return the item at the front
  {
    if (isEmpty()) throw new NoSuchElementException();
    Item removedItem = first.getItem();
    if (last == first) {
      last = null;
      first = null;
    } else {
      first = first.next;
      first.prev = null;
    }
    N--;
    return removedItem;
  }

  public Item removeLast()           // delete and return the item at the end
  {
    if (isEmpty()) throw new NoSuchElementException();
    Item removedItem = last.getItem();
    if (last == first) {
      last = null;
      first = null;
    } else {
      last = last.prev;
      last.next = null;
    }
    N--;
    return removedItem;
  }

  public Iterator<Item> iterator()   // return an iterator over items in order from front to end
  {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private Node current = first;
    public boolean hasNext() { return current != null; }
    public void remove()     { throw new UnsupportedOperationException(); }
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  public static void main(String[] args) {
    Deque<String> s = new Deque<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (item.length() < 2) break;
      if (item.charAt(0) == '+') {
        if (item.charAt(1) == 'b') {
          s.addFirst(item.substring(2));
        } else
        if (item.charAt(1) == 'e') {
          s.addLast(item.substring(2));
        } else {
          StdOut.printf("Finishing processing.\n");
          break;
        }
        StdOut.printf("Added string: %s\n", item.substring(2));
      } else
      if (item.charAt(0) == '-') {
        String removed_str = "";
        if (item.charAt(1) == 'b') {
          removed_str = s.removeFirst();
        } else
        if (item.charAt(1) == 'e') {
          removed_str = s.removeLast();
        } else {
          StdOut.printf("Finishing processing.\n");
          break;
        }
        StdOut.printf("Removed item: %s\n", removed_str);
      } else {
        StdOut.printf("Finishing processing.\n");
        break;
      }
      StdOut.printf("****************************\nDeque (%d):\n", s.size());
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
