/*
 * =====================================================================================
 *
 *       Filename:  linearmedian.c
 *
 *    Description:  median of medians algorithm
 *
 *        Version:  1.0
 *        Created:  02/21/2014 08:24:37 AM
 *       Revision:  none
 *       Compiler:	java
 *
 *         Author:	Michael Likholet <m.likholet@ya.ru>
 *        Company:  -
 *
 * =====================================================================================
 */

import java.util.Arrays;
import java.util.Random;
import java.lang.Exception;
import javatest.util.RandomData;

class MedianOfMedians {
	public static boolean DEBUG=false;

	public static void main(String[] args) {
		long start, stop;
		start = System.nanoTime();
		Integer[] data;

		try {
			System.out.println("Generating " + args[0] + " numbers");
			data = RandomData.genData(Integer.parseInt(args[0]));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Cannot generate data for current parameters. Terminating.");
			return;
		}

		stop = System.nanoTime();
		System.out.println("Generating took " + ((stop-start)/1000) + " usecs");

		MedianSelection<Integer> ms = new MedianSelection<Integer>(data);
		start = System.nanoTime();

		int median = ms.findMedian();

		stop = System.nanoTime();
		System.out.println("Computed median = " + median + ", time taken = " + ((stop-start)/1000) + " usecs");
	}
}

class MedianSelection<T extends Comparable<? super T>> {
	private static boolean DEBUG=false;
	private T[] A;
	private Random rand;

	public MedianSelection(T[] A) {
		this.rand = new Random();
		this.A = A;
	}

	private void swap(int i, int j) {
		T t = A[i];
		A[i] = A[j];
		A[j] = t;
	}

	private int findIdxMedianOf5(int l) {
		int a = l,
				b = l + 1,
				c = l + 2,
				d = l + 3,
				e = l + 4;

		if (a > b)  { b = l; a = l + 1; }
		if (d > e)  { e = l + 3; d = l + 4; }
		if (a > d)  {
			int t;
			t = a; a = d; d = t;
			t = b; b = e; e = t;
		}
		if (c < b) {
			if (d < c) {
				if (c < e) {
					return c;
				} else {
					return e;
				}
			} else {
				if (b < d) {
					return b;
				} else {
					return d;
				}
			}
		} else {
			if (b < d) {
				if (c < d) {
					return c;
				} else {
					return d;
				}
			} else {
				if (b < e) {
					return b;
				} else {
					return e;
				}
			}
		}
	}

	private T selectMedian(int l, int r, int kth) {
		int idx, sz = r - l;
		T minVal;
		for (int i = 0; i <= kth; ++i) {
			idx = i;
			minVal = A[i];
			for (int j = i+1; j <= sz; ++j) {
				if (minVal.compareTo(A[j]) < 0) {
					idx = j;
					minVal = A[j];
				}
			}
			swap(i, idx);
		}
		return A[kth];
	}

	private T findMedian(int l, int r, int kth) {
		int groups_cnt = (r-l+1)/5;
		for (int i = 0; i < groups_cnt; ++i) {
			swap(l+i, findIdxMedianOf5(l+5*i));
		}
		return selectMedian(l, r, kth);
	}

	public T findMedian() {
		return findMedian(0, A.length-1, A.length/2);
	}
}


