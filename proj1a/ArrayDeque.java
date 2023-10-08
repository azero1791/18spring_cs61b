
public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[16];
        nextFirst = 1;
        nextLast = 2;
        size = 0;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int firstSize = size - 1 - nextFirst;
        System.arraycopy(items, nextFirst + 1, newItems, 0, firstSize);
        int lastSize = size - firstSize;
        System.arraycopy(items, 0, newItems, firstSize, lastSize);
        items = newItems;
    }

    /** add first item to this deque */
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
        items[nextFirst] = item;
        nextFirst -= 1;
        size += 1;
    }

    /** add last item to this deque*/
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        if (nextLast >= items.length) {
            nextLast = 0;
        }
        items[nextLast] = item;
        nextLast += 1;
        size += 1;
    }

    /** return whether this arrayDeque is empty */
    public boolean isEmpty() {
        return size == 0;
    }

    /** return size of this arrayDeque*/
    public int size() {
        return size;
    }

    /** print this arrayDeque whose items are separated by space */
    public void printDeque() {
        for (int i = nextFirst; i < items.length; i++) {
            System.out.print(items[i] + " ");
        }
        for (int i = 0; i < nextLast; i++) {
            System.out.print(items[i] + " ");
        }
    }

    /** remove first item of this arrayDeque */
    public T removeFirst() {
        if (nextFirst >= items.length) {
            nextFirst = 0;
        }
        nextFirst += 1;
        T first = items[nextFirst];
        size -= 1;
        return first;
    }

    /** remove last item of this items */
    public T removeLast() {
        if (nextLast < 0) {
            nextLast = items.length - 1;
        }
        nextLast -= 1;
        T last = items[nextLast];
        size -= 1;
        return last;
    }

    /** return ith item of this arrayDeque */
    public T get(int i) {
        if (i >= size) {
            return null;
        }
        int curIndex = i + nextFirst + 1;
        if (curIndex >= items.length) {
            curIndex -= items.length;
        }

        return items[curIndex];
    }

}