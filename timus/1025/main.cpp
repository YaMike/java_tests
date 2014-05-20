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
#ifdef DEBUG
		cout << groups[i] << endl;
#endif
	}

#ifdef DEBUG
	cout << "Groups count: " << groups.size() << endl;
#endif

	/*process data */
	sort(groups.begin(), groups.end());

#ifdef DEBUG
	cout << "Sorted:"<< endl;
	for_each(groups.begin(), groups.end(), [](uint32_t _n) {
			cout << _n << endl;
			});
#endif

	uint32_t voters = 0;
	for (vector<uint32_t>::iterator it = groups.begin(); it != groups.begin() + groups.size()/2 + 1; ++it) {
		voters += *it/2 + 1;
#ifdef DEBUG
		cout << "voters: " << voters << ", group: " << *it << endl;
#endif
	}
	cout << voters << endl;
	return 0;
}
