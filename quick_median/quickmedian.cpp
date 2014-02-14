/*
 * =====================================================================================
 *
 *       Filename:  quickmedian.cpp
 *
 *    Description:  Quick median (C++)
 *
 *        Version:  1.0
 *        Created:  02/14/2014 04:44:03 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  Michael Likholet <m.likholet@ya.ru>
 *        Company:  -
 *
 * =====================================================================================
 */

#include <vector>
#include <iterator>
#include <iostream>
#include <string>

#include <cstdlib>

class QuickMedian {
	public:
		QuickMedian(std::vector<int> vec): data(vec) {}
		~QuickMedian() {}

		int findMedian(int order);
		int findMedian(int l, int r, int order);

	private:
		std::vector<T> data;

		int Partition(int l, int r);
		int randomizedPartition(int l, int r);
		int swap(int i, int j);
}

int QuickMedian::findMedian(int order) {
	return findMedian(0, data.size(), data.size()/2);
}

int QuickMedian::findMedian(int l, int r, int order) {
	
}

int QuickMedian::Partition(int l, int r) {

}

int QuickMedian::randomizedPartition(int l, int r) {
}

int QuickMedian::swap(int i, int j) {
	int temp = data[i];
	data[i] = data[j];
	data[j] = temp;
}

class FisherYatsShuffle {
	public:

		FisherYatsShuffle() {

		}

		~FisherYatsShuffle() {

		}

	private:
		std::vector<int> data;
}

int main(int argc, char *argv[]) {

	long time;

	switch (argc) {
		case 2:
			std::cout << "Generating data..." << std::endl;

			std::cout << "Generating took " << time << " usecs" << std::endl;
			break;
		default:
			exit(-1);
	}

	

	std::cout << "Median = " << median << std::endl;
	std::cout << "Time = " << time << " usecs" << std::endl;

	return 0;
}
