import java.util.Arrays;
import java.lang.ArrayIndexOutOfBoundsException;

public class heapsort {
  public static boolean DEBUG = false;

  public static int[] genData(int size) {
    int[] data = new int[size];
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
    int[] data;
    try {
      data = genData(Integer.parseInt(args[0]));
    } catch (ArrayIndexOutOfBoundsException e) {
      System.err.println("Bad arguments!");
      return;
    }
    System.out.println("Generating random data array of size " + data.length + " with Fisher-Yates algorithm...");
    MaxHeap mh = new MaxHeap(data);
    if (DEBUG) {
      System.out.println("Before: " + mh);
    }
    long start = System.nanoTime();
    mh.BuildHeap();
    long elapsed = System.nanoTime() - start;
    if (DEBUG) {
      System.out.println("After:  " + mh);
    }
    System.out.println("Time("+data.length+"):  " + elapsed + " nsecs");
  }
}

class MaxHeap {
  public MaxHeap(int[] A) {
    this.A = new int[A.length];
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
    if (l < A.length && A[l] > A[i]) {
      largest = l;
    }

    if (r < A.length && A[r] > A[largest]) {
      largest = r;
    }

    if (largest != i) {
      int temp = A[i];
      A[i] = A[largest];
      A[largest] = temp;
      maxHeapify(largest);
    }
  }

  @Override
  public String toString() {
    return new String(Arrays.toString(this.A));
  }

  private int[] A;
}
