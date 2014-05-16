#include <vector>
#include <iostream>
#include <iterator>
#include <inttypes.h>
#include <map>
#include <sstream>

using namespace std;

static const map<char,int> sym2num= {
	{'i',1},{'j',1},
	{'a',2},{'b',2},{'c',2},
	{'d',3},{'e',3},{'f',3},
	{'g',4},{'h',4},
	{'k',5},{'l',5},
	{'m',6},{'n',6},
	{'p',7},{'r',7},{'s',7},
	{'t',8},{'u',8},{'v',8},
	{'w',9},{'x',9},{'y',9},
	{'o',0},{'q',0},{'z',0},
};

template <typename T>
std::string toString(const T &t) {
	ostringstream ss;
	ss << t;
	return ss.str();
};

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
		/* criterea:
		 * - length (exactly words_count syms/nums)
		 * - acceptable chars from mapping sym2num in array
		 * - build multi-tree, each node is a word with vector of child nodes
		 */
		struct Node {
			char sym;
			vector<Node*> next;
		};

		string number(toString(number));

		for (string::iterator it = number.begin(); it != number.end(); ++it) {
			cout << *it << endl;
		}

		cout << "processed" << endl;
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
