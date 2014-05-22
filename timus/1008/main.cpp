#include <vector>
#include <stack>
#include <iostream>
#include <iterator>
#include <inttypes.h>
#include <sstream>
#include <algorithm>
#include <cmath>
#include <cstring>
#include <cstdlib>

using namespace std;

struct Point {
	uint32_t x;
	uint32_t y;
	Point(): x(0), y(0) {}
	Point(uint32_t x, uint32_t y) {
		this->x = x;
		this->y = y;
	}
};

struct OffPoint: public Point {
	string s;
	OffPoint(string str, uint32_t x, uint32_t y): Point(x,y), s(str) { }
};

int main(int argc, char *argv[]) {

	vector<Point> points;
	uint32_t points_count;

	/* read input data */
	cin >> points_count;
	points.resize(points_count);

	uint32_t min_x = points_count, min_y = points_count,
					 max_x = 0, max_y = 0;

	for (uint32_t i = 0; i < points_count; ++i) {
		cin >> points[i].x >> points[i].y;
		cout << "read coors: [" << points[i].x << "," << points[i].y << "]" << endl;
		if (points[i].x < min_x) { min_x = points[i].x; }
		if (points[i].y < min_y) { min_y = points[i].y; }
		if (points[i].x > max_x) { max_x = points[i].x; }
		if (points[i].y > max_y) { max_y = points[i].y; }
	}

	/* transform data to image */

	max_x++;
	max_y++;
	uint32_t image[max_x*max_y];
	memset(&image, 0, max_x*max_y*sizeof(uint32_t));

	cout << "max x:" << max_x << " max_y:" << max_y << endl;
	for (vector<Point>::iterator it = points.begin(); it != points.end(); it++) {
		cout << "coors: " << it->x << ", " << it->y << endl;
		image[it->x + max_x*it->y] = 1;
	}

	cout << "image:" << endl;
	for (uint32_t i = 0; i < max_x; ++i) {
		for (uint32_t j = 0; j < max_y; ++j) {
			char sym = '*';
			switch (image[i+max_x*j]) {
				case 0: sym = 'o'; break;
				case 1: sym = '1'; break;
				case 2: sym = '2'; break;
				default: sym = 'x'; break;
			}
			cout << " " << sym;
		}
		cout << endl;
	}

	/* process data */
	stack<Point> neighbors;
	stringstream result;
	image[min_x+max_x*min_y] = 2;
	result << min_x << " " << min_y << endl;
	neighbors.push(Point(min_x, min_y));

	vector<OffPoint> offsets;
	offsets.push_back(OffPoint("R",1,0));
	offsets.push_back(OffPoint("T",0,1));
	offsets.push_back(OffPoint("L",-1,0));
	offsets.push_back(OffPoint("B",0,-1));

	while (0 != neighbors.size()) {
		Point active_point = neighbors.top();
		neighbors.pop();
		cout << "active point: " << active_point.x << ", "<< active_point.y << endl;
		for (vector<OffPoint>::const_iterator it = offsets.cbegin(); it != offsets.cend(); it++) {
			uint32_t x = active_point.x+it->x, y = active_point.y+it->y;
			cout << "analyze " << it->s << ": [" << x << ", " << y << "]";
			if (x <= max_x && y <= max_y && image[x+max_x*y] == 1) {
				neighbors.push(Point(active_point.x+it->x, active_point.y + it->y));
				image[x+max_x*y] = 2;
				result << it->s;
				cout << ": found";
			}
			cout << endl;
		}
		result << "," << endl;
	}
	result << "." << endl;

	cout << result.str() << endl;

	/* output result  */
	return 0;
}
