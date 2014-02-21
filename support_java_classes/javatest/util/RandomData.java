package javatest.util;

import java.lang.Math;

public class RandomData {
  /*
   * Generate array of unique random numbers
   */
  public static Integer[] genData(int size) {
    Integer[] data = new Integer[size];
    for (int i = 0; i < size; i++) {
      data[i] = i;
    }

		/* Fisher-Yates shuffle */
		int index, temp;
    for (int i = size - 1; i > 0; i--) {
      index = (int)(i * Math.random());
      temp = data[index];
      data[index] = data[i];
      data[i] = temp;
    }

    return data;
  }
}
