/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {

    /**
     * find the longest size of the item of given array
     */
    private static int findLongest(String[] asciis) {
        int longest = Integer.MIN_VALUE;
        for (int i = 0; i < asciis.length; i++) {
            if (asciis[i].length() > longest) {
                longest = asciis[i].length();

            }
        }
        return longest;
    }
    /**
     * align each String of given array
     */
    private static String[] alignStrings(String[] asciis, int length) {
        String[] answer = new String[asciis.length];
        for (int i = 0; i < asciis.length; i++) {
            if (asciis[i].length() < length) {
                int comLength = length - asciis[i].length();
                StringBuffer buffer = new StringBuffer();
                for (int j = 0; j < comLength; j++) {
                    buffer.append((char) 1);
                }
                answer[i] = asciis[i] + buffer.toString();
            }   else {
                answer[i] = asciis[i];
            }

        }
        return answer;
    }

    /**
     * put corresponding digit string of given array
     */
    private static void putSingleString(String[] answer, String[] preAnswer, String[] alignAnswer, String[] preAlignAnswer, int charNum, int d, int charLength, int curIndex) {

        for (int i = 0; i < preAlignAnswer.length; i++) {
            if (preAlignAnswer[i].charAt(charLength - 1 - d) == charNum) {
                answer[curIndex] = preAnswer[i];
                alignAnswer[curIndex] = preAlignAnswer[i];
                curIndex++;
            }
        }
    }
    /**
     * stable sort on digit of the given array
     */
    private static void stableSortOnDigit(String[] answer, String[] alignAnswer, int d, int charLength) {
        int curIndex = 0;
        char[] countBucket = new char[257];
        for (int i = 0; i < alignAnswer.length; i++) {
            int index = charLength - 1 - d;
            char c = alignAnswer[i].charAt(index);
            countBucket[(int) c]++;
        }
        String[] preAnswer = new String[answer.length];
        System.arraycopy(answer, 0, preAnswer, 0, answer.length);

        String[] preAlignAnswer = new String[answer.length];
        System.arraycopy(alignAnswer, 0, preAlignAnswer, 0, preAnswer.length);
        for (int i = 0; i < countBucket.length; i++) {
            if (countBucket[i] != 0) {

                putSingleString(answer, preAnswer, alignAnswer, preAlignAnswer, i, d, charLength, curIndex);
                curIndex += countBucket[i];
            }
        }
        return;
    }

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort

        int longest = findLongest(asciis);
        String[] answer = new String[asciis.length];
        System.arraycopy(asciis, 0, answer, 0, asciis.length);
        String[] alignStrings = alignStrings(asciis, longest);

        for (int d = 0; d < longest; d++) {
            stableSortOnDigit(answer, alignStrings, d, longest);
        }
        return answer;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
