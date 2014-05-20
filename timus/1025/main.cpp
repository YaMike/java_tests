#include <vector>
#include <iostream>
#include <iterator>
#include <inttypes.h>
#include <algorithm>
#include <cmath>

using namespace std;

int main(int argc, char *argv[]) {

	vector<uint32_t> groups;
	uint32_t groups_count;

	/* read input data */
	cin >> groups_count;
	groups.resize(groups_count);
	for (uint32_t i = 0; i < groups_count; ++i) {
		cin >> groups[i];
	}

	/*process data */
	sort(groups.begin(), groups.end());

	uint32_t voters = 0;
	for (vector<uint32_t>::iterator it = groups.begin(); it != groups.begin() + groups.size()/2 + 1; ++it) {
		voters += *it/2 + 1;
	}
	/* output result  */
	cout << voters << endl;
	return 0;
}
