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
	private static boolean DEBUG=false;

  private T[] A;
  private Random rand;

  public QuickMedian(T[] A) {
    this.rand = new Random();
    this.A = A;
  }

  public T findMedian() {
    return findMedian(0, A.length-1, (A.length-1)/2);
  }

  public T findMedian(int l, int r, int order) {
    if (l == r) {
      return A[l];
    }
    if (DEBUG) System.out.println(this.toString());
    int pivot = randomizedPartition(l,r);
    if (DEBUG) System.out.println("l = " + l + ", r = " + r + ", pivot = " + pivot);
    if (order == pivot) {
      return A[pivot];
    } else
    if (order < pivot) {
      return findMedian(l,pivot-1,order);
    } else {
      return findMedian(pivot+1,r,order);
    }
  }

  private void swap(int i, int j) {
    T t = A[i];
    A[i] = A[j];
    A[j] = t;
  }

  private int randomizedPartition(int l, int r) {
		if (DEBUG) System.out.println("l="+l+",r="+r);
    int pivot = rand.nextInt(r-l+1) + l;
    if (DEBUG) System.out.println("pivot = " + pivot + ", A[pivot] = " + A[pivot]);
    swap(r,pivot);
    return Partition(l,r,pivot);
  }

  private int Partition(int l, int r, int pivot) {
    T t = A[r];
    int i = l, j = l-1;
    if (DEBUG) System.out.println("swap:l="+l+",r="+r+":" + this.toString());
    for ( ; i < r; i++) {
      if (A[i].compareTo(t) <= 0) {
        swap(i,++j);
        if (DEBUG) System.out.println("swap: ["+i+"<->"+j+"]:" + this.toString());
      }
    }
    swap(j+1,r);
    if (DEBUG) System.out.println("swap: return="+(j+1)+":" + this.toString());
    return j+1;
  }
  @Override
  public String toString() {
    return new String(Arrays.toString(this.A));
  }
}

