#include <vector>
#include <iostream>
#include <iterator>
#include <inttypes.h>
#include <map>
#include <list>
#include <set>
#include <sstream>
#include <numeric>
#include <algorithm>
#include <cmath>

using namespace std;

int main(int argc, char *argv[]) {

	vector<uint32_t> stones;
	uint32_t stones_count;

	/* read input data */
	cin >> stones_count;
	for (uint32_t i = 0; i < stones_count; i++) {
		uint32_t stone_weight;
		cin >> stone_weight;
		stones.push_back(stone_weight);
	}

	/*process data */
	static uint32_t total_weigth = accumulate(stones.begin(), stones.end(), 0);
	static set<uint32_t> full_bucket_weight;

	struct Node {

		Node(uint32_t w, vector<uint32_t> child_weights) {

			vector<uint32_t> new_child_weights;

			for (vector<uint32_t>::iterator it = child_weights.begin(); it != child_weights.end(); it++) {
				if (it != child_weights.begin()) {
					new_child_weights.insert(new_child_weights.end(), child_weights.begin(), it);
				}
				if (it != child_weights.end()-1) {
					new_child_weights.insert(new_child_weights.end(), it+1, child_weights.end());
				}

				if (*it + w < total_weigth/2) {
					Node(*it + w, new_child_weights);
				} else {
					full_bucket_weight.insert(w);
				}
				new_child_weights.clear();
			}
		}
	};

	{
		Node head(0,stones);
	}
	uint32_t min_difference = total_weigth, w1, w2, wd;
	double diff = 0;
	for (set<uint32_t>::iterator it = full_bucket_weight.begin(); it != full_bucket_weight.end(); it++) {
		diff = abs(total_weigth/2 - *it);
		if (diff < min_difference) {
			min_difference = diff;
			w1 = *it;
			w2 = total_weigth - *it;
			wd = w2 - w1;
		}
	}
#ifdef DEBUG
	cout << "w 1 = " << w1 << ", w 2 = " << w2 << ", wd = ";
#endif
	cout << wd << endl;
	return 0;
}
