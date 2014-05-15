#include <cmath>
#include <vector>
#include <iostream>
#include <iterator>
#include <iomanip>

using namespace std;

int main(int argc, char *argv[]) {
	istream_iterator<double> begin(cin), end;
	vector<double> v(begin, end);
	vector<double>::reverse_iterator it;
	cout << setiosflags(ios::fixed);
	cout.precision(4);
	for (it = v.rbegin(); it != v.rend(); ++it) {
		std::cout << sqrt(*it) << '\n';
	}
	return 0;
}
