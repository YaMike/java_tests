/*
 * =====================================================================================
 *
 *       Filename:  quickmedian.c
 *
 *    Description:  Quick median algorithm (C)
 *
 *        Version:  1.0
 *        Created:  02/14/2014 12:07:32 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  Michael L. <m.likholet@ya.ru>
 *        Company:  
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

static void swap_ints(int *, int, int);
int randomized_partition(int*, int, int);
int partition(int*, int, int);

int find_median(int *data, int l, int r, int order) {
	if (l == r) {
		return data[l];
	}

	int pivot = randomized_partition(data, l, r);

	if (order == pivot) {
		return data[order];
	} else if (order < pivot) {
		return find_median(data, l, pivot-1, order);
	} else { /* if (order > pivot) */
		return find_median(data, pivot+1, r, order);
	}
}

int randomized_partition(int *data, int l, int r) {
	int pivot = (int)(drand48()*(r-l+1)) + l;
	swap_ints(data, pivot, r);
	return partition(data, l, r);
}

int partition(int *data, int l, int r) {
	int t = data[r],
			i = l, j = l;

	for ( ; i < r; i++) {
		if (data[i] < t) {
			swap_ints(data, i, j++);
		}
	}
	swap_ints(data, j, r);
	return j;
}

static void swap_ints(int *data, int i, int j) {
	int temp = data[i];
	data[i] = data[j];
	data[j] = temp;
}

static long timespec_diff_us(struct timespec *start, struct timespec *end) {
	return (end->tv_sec*1000000+end->tv_nsec/1000) -
				 (start->tv_sec*1000000+start->tv_nsec/1000);
}

int *alloc_random_data(int sz) {
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

