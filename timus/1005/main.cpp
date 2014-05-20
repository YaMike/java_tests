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
	static uint32_t total_weight = accumulate(stones.begin(), stones.end(), 0);
	static vector<uint32_t> new_child_weights;
	new_child_weights.reserve(stones.size());

	static uint32_t min_difference = total_weight, wd;
	static double diff = 0;

	struct Node {
		Node(uint32_t w, vector<uint32_t> child_weights) {
			for (vector<uint32_t>::iterator it = child_weights.begin(); it != child_weights.end(); it++) {

				new_child_weights.clear();

				if (it != child_weights.begin()) {
					new_child_weights.insert(new_child_weights.end(), child_weights.begin(), it);
				}
				if (it != child_weights.end()-1) {
					new_child_weights.insert(new_child_weights.end(), it+1, child_weights.end());
				}

				if (*it + w < total_weight/2) {
					Node(*it + w, new_child_weights);
				} else {
					diff = abs(total_weight/2 - w);
					if (diff < min_difference) {
						min_difference = diff;
						wd = total_weight - 2*w;
					}
				}
			}
		}
	};

	Node head(0,stones);
	cout << wd << endl;
	return 0;
}
