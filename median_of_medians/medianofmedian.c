/*
 * =====================================================================================
 *
 *       Filename:  linearmedian.c
 *
 *    Description:  median of medians algorithm
 *
 *        Version:  1.0
 *        Created:  02/18/2014 01:24:37 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:	Michael Likholet <m.likholet@ya.ru>
 *        Company:  -
 *
 * =====================================================================================
 */

#define _POSIX_C_SOURCE 200111L
#define _XOPEN_SOURCE 1000

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <sys/types.h>

#define DEBUG 0

#if DEBUG == 1
static void print_data(char *name, int *data, int sz) {
	printf("%s: [", name);
	for (int i = 0; i < sz; i++) {
		printf("%d ", data[i]);
	}
	printf("]\n");
}
#endif

static void swap_ints(int *data, int i, int j) {
	int temp = data[i];
	data[i] = data[j];
	data[j] = temp;
}

static long timespec_diff_us(struct timespec *start, struct timespec *end) {
	return (end->tv_sec*1000000+end->tv_nsec/1000) -
		(start->tv_sec*1000000+start->tv_nsec/1000);
}

static int *alloc_random_data(int sz) {
	srandom(random());
	int *data = malloc(sz*sizeof(int));

	for (int i = 0; i < sz; ++i) {
		data[i] = i;
	}

	/* Shuffle by Fisher-Yates algorithm */
	int j;
	for (int i = sz - 1; i > 0; --i) {
		swap_ints(data, (int)(i-1) * drand48(), i);
		print_data("swapped", data, sz);
	}

	return data;
}

#define TRUE	1
#define FALSE	0
#define RETURN_IDX	TRUE
#define RETURN_VALUE (!RETURN_IDX)
int compute_median_of_five(int *data, int l) {
	register int	a = data[l],	 a_idx = l,
					 b = data[l+1], b_idx = l+1,
					 c = data[l+2], c_idx = l+2,
					 d = data[l+3], d_idx = l+3,
					 e = data[l+4], e_idx = l+4;

	if (a < b) { 
		register int t = a; a = b; b = t;			// exchange a && b
		t = a_idx; a_idx = b_idx; b_idx = t;	// exchange a_idx && b_idx
	}
	if (d < e) {
		register int t = d; d = e; e = t;			// exchange d && e
		t = d_idx; d_idx = e_idx; e_idx = t;	// exchange d_idx && e_idx
	}
	if (a < d) { 
		register int t;
		t = a; a = d; d = t;									// exchange a && d
		t = a_idx; a_idx = d_idx; d_idx = t;	// exchange a_idx && d_idx
		t = b; b = e; e = t;									// exchange b && e
		t = b_idx; b_idx = e_idx; e_idx = t;	// exchange b_idx && e_idx
	}
	if (c < b) {
		if (d < c) {
			if (c < e) {
#if RETURN_IDX == TRUE
				return c_idx;
#else
				return c;
#endif
			} else { /* c > e */
#if RETURN_IDX == TRUE
				return e_idx;
#else
				return e;
#endif
			}
		} else { /* d > c */
			if (b < d) {
#if RETURN_IDX == TRUE
				return b_idx;
#else
				return b;
#endif
			} else {
#if RETURN_IDX == TRUE
				return d_idx;
#else
				return d;
#endif
			}
		}
	} else { /* c > b */
		if (b < d) {
			if (c < d) {
#if RETURN_IDX == TRUE
				return c_idx;
#else
				return c;
#endif
			} else { /* c > d */
#if RETURN_IDX == TRUE
				return d_idx;
#else
				return d;
#endif
			}
		} else { /* b > d */
			if (b < e) {
#if RETURN_IDX == TRUE
				return b_idx;
#else
				return b;
#endif
			} else { /* b > e */
#if RETURN_IDX == TRUE
				return e_idx;
#else
				return e;
#endif
			}
		}
	}
}
#undef RETURN_VALUE
#undef RETURN_IDX
#undef FALSE
#undef TRUE

int select_median(int *data, int l, int r, int kth) {
	int idx, minValue, sz = r - l;
	printf("select_median: r=%d, l=%d, kth=%d\n", l, r, kth);
	for (int i = 0; i <= kth; i++) {
		idx = i;
		minValue = data[i];
		for (int j = i+1; j <= sz; ++j) {
			if (minValue > data[j]) {
				idx = j;
				minValue = data[j];
			}
		}
		swap_ints(data, i, idx);
	}
	return data[kth];
}

int find_median(int *data, int l, int r, int sz) {
	int groups_cnt = (r-l+1)/5;
	printf("groups_cnt=%d, l=%d, r=%d\n", groups_cnt, l, r);
	print_data("before compute 5", data, sz);
	for (int j = 0; j < groups_cnt; j++) {
		swap_ints(data, l+j, compute_median_of_five(data, l + 5*j));
		/*data[l+j] = compute_median_of_five(data, l + 5*j);*/
		printf("start = %d, ", l+5*j);
		print_data("after compute 5", data, sz);
	}
	return select_median(data, l, l + groups_cnt, groups_cnt/2);
}

int main(int argc, char *argv[]) {

	struct timespec start, end;
	int sz = 0, *data = NULL;

	switch (argc) {
		case 2:
			printf("Generating %d numbers\n", sz = atoi(argv[1]));
			clock_gettime(CLOCK_MONOTONIC, &start);
			data = alloc_random_data(sz);
			print_data("allocated", data, sz);
			clock_gettime(CLOCK_MONOTONIC, &end);
			printf("Generating took %ld usecs\n", timespec_diff_us(&start, &end));
			break;
		default:
			printf("Bad input arguments!\n");
			exit(-1);
	}

	printf("Computing median...\n");
	clock_gettime(CLOCK_MONOTONIC, &start);
	int median = find_median(data, 0, sz-1, sz);
	clock_gettime(CLOCK_MONOTONIC, &end);

	free(data);

	printf("Median = %d\n", median);
	printf("Running time = %ld usecs\n", timespec_diff_us(&start, &end));

	return 0;
}
