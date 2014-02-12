import java.util.Arrays;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.Comparable;

public class heapsort {
  public static boolean DEBUG=false;

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
    if (DEBUG) {
      System.out.println("After BuildHeap:  " + mh);
    }
		mh.GetSortedArray();
    if (DEBUG) {
      System.out.println("After unwrapping heap:  " + mh);
    }
    long elapsed = System.nanoTime() - start;

    System.out.println("Time ("+data.length+"):\t\t" + elapsed + " nsecs");
  }
}

class MaxHeap<T extends Comparable<? super T>> {

  public MaxHeap(T[] A) {
		this.sz = A.length;
    this.A = A;
  }

	public void BuildHeap() {
		BuildHeap(A.length);
	}

  public void BuildHeap(int size) {
    for (int i = size/2-1; i >= 0; i--) {
      maxHeapify(i, size);
    }
  }

	public T[] GetSortedArray() {
		for (int i = A.length-1; i > 0; i--) {
			T last = A[0];
			A[0] = A[i];
			A[i] = last;
			BuildHeap(i);
		}
		return A;
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

  private void maxHeapify(int i, int size) {
    int l = heapLeft(i),
        r = heapRigth(i);

    int largest = i;

    if (l < size && A[l].compareTo(A[i]) > 0) {
      largest = l;
    }

    if (r < size && A[r].compareTo(A[largest]) > 0) {
      largest = r;
    }

    if (largest != i) {
      T temp = A[i];
      A[i] = A[largest];
      A[largest] = temp;
      maxHeapify(largest, size);
    }
  }

  @Override
  public String toString() {
    return new String(Arrays.toString(this.A));
  }

  private T[] A;
	private int sz;
}
