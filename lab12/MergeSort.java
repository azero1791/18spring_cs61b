import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        // Your code here!

        if (items.isEmpty()) {
            return null;
        }

        Queue<Queue<Item>> answer = new Queue<Queue<Item>>();
        for (Item item : items) {
            Queue<Item> queueItem = new Queue<>();
            queueItem.enqueue(item);
            answer.enqueue(queueItem);
        }
        return answer;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        // Your code here!
        Queue<Item> answer = new Queue<Item>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            answer.enqueue(getMin(q1, q2));
        }

        while (!q1.isEmpty()) {
            answer.enqueue(q1.dequeue());
        }

        while (!q2.isEmpty()) {
            answer.enqueue(q2.dequeue());
        }

        return answer;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        // Your code here!
        if (items == null || items.isEmpty()) {
            return items;
        }

        Queue<Queue<Item>> answer = makeSingleItemQueues(items);

        while (answer.size() != 1) {
            Queue<Item> q1 = answer.dequeue();
            Queue<Item> q2 = answer.dequeue();
            answer.enqueue(mergeSortedQueues(q1, q2));
        }
        return answer.dequeue();
    }

    public static void main(String[] args) {
        Queue<String> test = new Queue<>();

        test.enqueue("afwa");
        test.enqueue("twawfw");
        test.enqueue("feaffw");

        System.out.println("The original unsorted queue is: " + test);
        Queue<String> answer = MergeSort.mergeSort(test);
        System.out.println("After mergeSort, The original queue is: " + test);
        System.out.println("After mergeSort, The answer queue is: " + answer);
    }
}
