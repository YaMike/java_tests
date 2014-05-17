#include <vector>
#include <iostream>
#include <iterator>
#include <inttypes.h>
#include <map>
#include <list>
#include <sstream>

using namespace std;

static const map<char, char> sym2num= {
	{'i','1'},{'j','1'},
	{'a','2'},{'b','2'},{'c','2'},
	{'d','3'},{'e','3'},{'f','3'},
	{'g','4'},{'h','4'},
	{'k','5'},{'l','5'},
	{'m','6'},{'n','6'},
	{'p','7'},{'r','7'},{'s','7'},
	{'t','8'},{'u','8'},{'v','8'},
	{'w','9'},{'x','9'},{'y','9'},
	{'o','0'},{'q','0'},{'z','0'},
};

string toNumberString(const string &s) {
	string r;
	r.reserve(s.size());
	for (string::const_iterator it = s.cbegin(); it != s.cend(); ++it) {
		r.push_back(sym2num.at(*it));
	}
	return r;
}

template <typename T>
string toString(const T &t) {
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
			uint32_t pos;
			string word;
			uint32_t word_idx;
			Node *prev;
			vector<Node*> next;
			Node(Node *prev, uint32_t pos, string word, uint32_t idx) {
				this->pos = pos;
				this->word = word;
				this->word_idx = idx;
			};
			~Node() {
				for (vector<Node*>::iterator it = next.begin(); it != next.end(); ++it) {
					delete *it;
				}
			}
		};

		/* convert words to number strings */
		vector<string> words_num;
		words_num.reserve(words.size());

		cout << "Convert to words num:" << endl;
		for (vector<string>::iterator it = words.begin(); it != words.end(); it++) {
			words_num.push_back(toNumberString(*it));
			cout << words_num.back() << endl;
		}

		list<Node*> active_nodes;
		list<Node*> last_nodes;
		Node result_head(NULL, 0, "", 0);

		/* initial condition */
		string str_number(toString(number));
		uint32_t idx = 0;
		for (vector<string>::const_iterator words_it = words_num.cbegin(); words_it != words_num.cend(); words_it++, idx++) {
			if (words_it->compare(0, words_it->size(), str_number,0,words_it->size()) == 0) {
				Node *node = new Node(&result_head, 0, *words_it, idx++);
				active_nodes.push_back(node);
				result_head.next.push_back(node);
			}
		}

		/* building tree - new active nodes are added to back, active nodes to process are picked up at front */
		while (active_nodes.size() > 0) {

			/* pick up active node */
			Node *act_node = active_nodes.front();
			active_nodes.pop_front();

			/* add new active nodes */
			idx = 0;
			for (vector<string>::const_iterator w_it = words_num.cbegin(); w_it != words_num.cend(); w_it++, idx++) {
				if (w_it->compare(0, w_it->size(), str_number, act_node->pos, w_it->size()) == 0) {
					Node *new_node = new Node(act_node, act_node->pos + w_it->size(), *w_it, idx);
					act_node->next.push_back(new_node);
					active_nodes.push_back(new_node);
				}
			}
			
			if (act_node->next.size() == 0) {
				last_nodes.push_back(act_node);
			}
		}

		/* remove incomplete branches (total lenght of branch is smaller than "str_number" */
		Node *minimum_size_end_node = NULL;
		int min_size = str_number.size();
		for (list<Node*>::iterator it = last_nodes.begin(); it != last_nodes.end(); it++) {
			Node *last_node = *it;
			if (last_node->pos != str_number.size()) {
				Node *prev = last_node->prev;
				while (prev != NULL) {
					for (list<Node*>::iterator it = prev->next.begin(); it != prev->next.end(); it++) {
						if (*it == last_node) {
							prev->next.erase(it);
							break;
						}
					}
					delete last_node;
					if (prev->next.size() == 0) {
						prev = prev->prev;
					} else {
						prev = NULL;
					}
				}
			} else {
				/* find shortest branch */
				Node *prev = last_node->prev;
				int size = 0;
				while (prev != NULL) {
					size++;
					prev = prev->prev;
				}
				if (size < min_size) {
					min_size = size;
					minimum_size_end_node = last_node;
				}
			}
		}
	
		/* print result */
		cout << "minimum is " << size << endl;
		Node *prev = minimum_size_end_node;
		vector<string> result;
		result.push_back(words[minimum_size_end_node->idx];
		while (prev != null) {
			result.push_back(words[prev->idx]);
			prev = prev->prev;
		}
		for (vector<string>::reverse_iterator it = result.rbegin(); it != result.rend(); it++) {
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
