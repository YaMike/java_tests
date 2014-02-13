import java.util.Arrays;
import java.util.Random;
import java.lang.ArrayIndexOutOfBoundsException;
import java.lang.Comparable;

public class quickmedian {

  public static boolean DEBUG=false;

  /*
   * Generate array of unique random numbers
   */
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

    QuickMedian<Integer> qm = new QuickMedian<Integer>(data);
    if (DEBUG) {
      System.out.println("Generating random data array of size " + data.length + " with Fisher-Yates algorithm...");
      System.out.println("Before: " + qm);
    }

    long start = System.nanoTime();
    int median = qm.findMedian();
    if (DEBUG) {
      System.out.println("After median search:  " + qm);
    }
    long elapsed = System.nanoTime() - start;
    System.out.println("Median = " + median);

    System.out.println("Time ("+data.length+"):\t\t" + elapsed + " nsecs");
  }
}

class QuickMedian<T extends Comparable<? super T>> {

  private T[] A;
  private Random rand;

  public QuickMedian(T[] A) {
    this.rand = new Random();
    this.A = A;
  }

  public T findMedian() {
    return findMedian(0, A.length-1, A.length/2);
  }

  public T findMedian(int l, int r, int kth) {
    if (l == r) {
      return A[l];
    }
    int pivot = randomizedPartition(l,r),
        k = pivot - l;
    if (kth == k) {
      return A[k];
    } else
    if (kth < k) {
      return findMedian(l,pivot-1,kth);
    } else {
      return findMedian(pivot+1,r,kth-pivot);
    }
  }

  private void swap(int i, int j) {
    T t = A[i];
    A[i] = A[j];
    A[j] = t;
  }

  private int randomizedPartition(int l, int r) {
    int pivot = rand.nextInt(r-l+1) + l;
    swap(r,pivot);
    return Partition(l,r);
  }

  private int Partition(int l, int r) {
    T t = A[r];
    int i = l, j = r - 1;
    for ( ; i < j; i++) {
      if (A[i].compareTo(t) >= 0) {
        for ( ;A[j].compareTo(t) > 0; j--);
        swap(i,j--);
      }
    }
    swap(i,r);
    return i;
  }
  @Override
  public String toString() {
    return new String(Arrays.toString(this.A));
  }
}

