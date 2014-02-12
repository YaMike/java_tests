import java.util.Arrays;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.Comparable;

public class heapsort {
  public static boolean DEBUG = false;

  public static Integer[] genData(int size) {
    Integer[] data = new Integer[size];
    for (int i = 0; i < size; i++) {
      data[i] = i;
    }

    int index;
    for (int i = size - 1; i > 0; i--) {
      index = (int)(i * Math.random());
      int temp = data[index];
      data[index] = data[i];
      data[i] = temp;
    }
    return data;
  }

  public static void main(String[] args) {
    Integer[] data;
    try {
      data = genData(Integer.parseInt(args[0]));
    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println("Bad arguments!");
      return;
    }
    MaxHeap<Integer> mh = new MaxHeap<Integer>(data);
    if (DEBUG) {
      System.out.println("Generating random data array of size " + data.length + " with Fisher-Yates algorithm...");
      System.out.println("Before: " + mh);
    }
    long start = System.nanoTime();
    mh.BuildHeap();
    long elapsed = System.nanoTime() - start;
    if (DEBUG) {
      System.out.println("After:  " + mh);
    }
    System.out.println("Time ("+data.length+"):\t\t" + elapsed + " nsecs");
  }
}

class MaxHeap<T extends Comparable<? super T>> {

  public MaxHeap(T[] A) {
    this.A = A;
    System.arraycopy(A,0,this.A,0,A.length);
  }

  public void BuildHeap() {
    for (int i = A.length/2-1; i >= 0; i--) {
      maxHeapify(i);
    }
  }

  private int heapLeft(int i) {
    return 2*i;
  }

  private int heapRigth(int i) {
    return 2*i + 1;
  }

  private int heapParent(int i) {
    return i/2;
  }

  private void maxHeapify(int i) {
    int l = heapLeft(i),
        r = heapRigth(i);

    int largest = i;

    //System.out.println("i=" + i + ", data: " + this);
    if (l < A.length && A[l].compareTo(A[i]) > 0) {
      largest = l;
    }

    if (r < A.length && A[r].compareTo(A[largest]) > 0) {
      largest = r;
    }

    if (largest != i) {
      T temp = A[i];
      A[i] = A[largest];
      A[largest] = temp;
      maxHeapify(largest);
    }
  }

  @Override
  public String toString() {
    return new String(Arrays.toString(this.A));
  }

  private T[] A;
}
