#include <cmath>
#include <vector>
#include <iostream>
#include <iterator>
#include <iomanip>
#include <inttypes.h>

using namespace std;

struct Test {
	uint64_t number;
	vector<string> words;

	Test(uint64_t number) {
		this->number = number;
		int words_count;
		cin >> words_count;
		words.reserve(words_count);
		string str;
		for (int i = 0; i < words_count; ++i) {
			cin >> str;
			words.push_back(str);
		}
#ifdef DEBUG
		cout << "test created: \"" << number << "\" ( ";
		for (vector<string>::iterator it = words.begin(); it != words.end(); ++it) {
			cout << *it << " ";
		}
		cout << ")" << endl;
#endif
	}
	void process() { 
#ifdef DEBUG
		cout << "processing " << number << endl; 
#endif
	}
};

int main(int argc, char *argv[]) {
	vector<Test> tests;

	while (!cin.eof()) {
		int64_t number;
		cin >> number;
		if (number == -1) { break; }
		tests.push_back(Test(number));
	}

	/* process data structures */
	for (vector<Test>::iterator it = tests.begin(); it != tests.end(); ++it) {
		it->process();
	}

	return 0;
}
