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
	uint32_t total_weight = 0;

	stones.reserve(30);
	/* read input data */
	cin >> stones_count;
	for (uint32_t i = 0; i < stones_count; i++) {
		uint32_t stone_weight;
		cin >> stone_weight;
		stones.push_back(stone_weight);
		total_weight += stone_weight;
	}

	/*process data */
	static uint32_t selected_stones, i = 0, combinations = pow(2,stones_count);
	static int32_t min_difference = total_weight;
	static int32_t sum = 0;

	for (selected_stones = 0; selected_stones != combinations; selected_stones++) {
		for (sum =0, i = 0; i < stones_count; i++) {
			sum += (selected_stones >> i) & 0x1 ? stones[i] : -stones[i];
		}
		min_difference = min(abs(sum), min_difference);
	}
	cout << dec << min_difference << endl;
	return 0;
}
