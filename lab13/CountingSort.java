/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 *
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * set items to 0 of given array
     */
    private static void setZero(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 0;
        }
    }

    /**
     * count numbers of items of given array
     */

    private static void countNumber(int[] arr, int[] positive, int[] negative) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 0) {
                positive[arr[i]] += 1;
            }   else {
                negative[-arr[i]] += 1;
            }
        }
    }

    /**
     * put positive array and negative array into the answer array
     */
    private static int[] getSorted(int[] positive, int[] negative, int length) {
        int[] answer = new int[length];
        int curIndex = 0;
        for (int i = negative.length - 1; i >= 0; i--) {
            if (negative[i] != 0) {
                for (int j = 0; j < negative[i]; j++) {
                    answer[curIndex++] = -i;
                }
            }
        }

        for (int i = 0; i < positive.length; i++) {
            if (positive[i] != 0) {
                for (int j = 0; j < positive[i]; j++) {
                    answer[curIndex++] = i;
                }
            }
        }
        return answer;
    }
    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */
    public static int[] betterCountingSort(int[] arr) {
        // TODO make counting sort work with arrays containing negative numbers.
        final int LENGTH = (int) (2 * Math.pow(10, 8) + 1);
        int[] positive = new int[LENGTH];
        int[] negative = new int[LENGTH];

        setZero(positive);
        setZero(negative);

        countNumber(arr, positive, negative);

        int[] answer = getSorted(positive, negative, arr.length);
        return answer;
    }
}
