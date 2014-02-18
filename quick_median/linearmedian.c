/*
 * =====================================================================================
 *
 *       Filename:  linearmedian.c
 *
 *    Description:  Linear-time median (median of medians algorithm)
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
	}

	return data;
}

//TODO: fix return value - must be index, not value
int compute_median_of_five(int *data, int l) {
	register int	a = data[l], 
								b = data[l+1],
								c = data[l+2],
								d = data[l+3],
								e = data[l+4];

	if (a < b) { register int t = a; a = b; b = t; }
	if (d < e) { register int t = d; d = e; e = t; }
	if (a < d) { 
		register int t;
		t = a; a = d; d = t;
		t = b; b = e; e = t;
	}
	if (c < b) {
		if (d < c) {
			if (c < e) {
				return c;
			} else { /* c > e */
				return e;
			}
		} else { /* d > c */
			if (b < d) {
				return b;
			} else {
				return d;
			}
		}
	} else { /* c > b */
		if (b < d) {
			if (c < d) {
				return c;
			} else { /* c > d */
				return d;
			}
		} else { /* b > d */
			if (b < e) {
				return b;
			} else { /* b > e */
				return e;
			}
		}
	}
}

int select_median(int *data, int l, int r, int kth) {
	int idx, minValue, sz = r - l;
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
	for (int j = 0; j < groups_cnt; j++) {
		data[l+j] = compute_median_of_five(data, l + 5*j);
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
			clock_gettime(CLOCK_MONOTONIC, &end);
			printf("Generating took %ld usecs\n", timespec_diff_us(&start, &end));
			break;
		default:
			printf("Bad input arguments!\n");
			exit(-1);
	}

	printf("Computing median...\n");
	clock_gettime(CLOCK_MONOTONIC, &start);
	int median = find_median(data, 0, sz-1, sz/2);
	clock_gettime(CLOCK_MONOTONIC, &end);

	free(data);

	printf("Median = %d\n", median);
	printf("Running time = %ld usecs\n", timespec_diff_us(&start, &end));

	return 0;
}
