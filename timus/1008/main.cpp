#include <vector>
#include <iostream>
#include <iterator>
#include <inttypes.h>
#include <algorithm>
#include <cmath>

using namespace std;
struct Point {
	int x;
	int y;
};

int main(int argc, char *argv[]) {

	vector<Point> points;
	uint32_t points_count;

	/* read input data */
	cin >> points_count;
	groups.resize(points_count);

	uint32_t min_x = points_count, min_y = points_count;

	for (uint32_t i = 0; i < points_count; ++i) {
		cin >> points[i].x >> points[i].y;
		if (points[i].x < min_x) { min_x = points[i].x; }
		if (points[i].y < min_y) { min_y = points[i].y; }
	}

	/*process data */
	/* output result  */
	return 0;
}
