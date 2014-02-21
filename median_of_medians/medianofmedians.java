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
	public static boolean DEBUG=true;

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
	}
}

class MedianSelection<T extends Comparable<? super T>> {
	private static boolean DEBUG=true;
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
}


