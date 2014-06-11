#include <vector>
#include <iostream>
#include <iterator>
#include <inttypes.h>
#include <algorithm>
#include <cmath>

using namespace std;

int main(int argc, char *argv[]) {
	unsigned int N, K, res;

	/* read input data */
	cin >> N; // number of digits
	cin >> K; // base

	/*process data */
	switch (N) {
		case 1:
			res = K;
			break;
		case 2:
			res = K*(K-1);
			break;
		default:
			// compute total numbers count
			unsigned int total_nums = pow(K,N-1)*(K-1);
			// compute count of "bad" numbers
			unsigned int bad_nums = 1;
			for (unsigned int i = 1; i <= N-2; i++) {
				bad_nums *= (K-i);
			}
			res = total_nums - bad_nums;
	}
	/* output result  */
	cout << res << endl;
	return 0;
}
