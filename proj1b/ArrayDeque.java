public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[16];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    private void resize(int capacity) {

        boolean resizeOver = false;
        T[] newItems = (T[]) new Object[capacity];
        int newCurIndex = 0;
        for (int i = nextFirst + 1; i < items.length; i++) {
            if (i + 1 == nextLast) {
                newItems[newCurIndex++] = items[i];
                resizeOver = true;
                break;
            }
            newItems[newCurIndex++] = items[i];
        }
        if (!resizeOver) {
            System.arraycopy(items, 0, newItems, newCurIndex, nextLast);
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    /** add first item to this deque */
    @Override
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
    @Override
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
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** return size of this arrayDeque*/
    @Override
    public int size() {
        return size;
    }

    /** print this arrayDeque whose items are separated by space */
    @Override
    public void printDeque() {
        if (size == 0) {
            return;
        }
        for (int i = nextFirst; i < items.length; i++) {
            System.out.print(items[i] + " ");
        }
        for (int i = 0; i < nextLast; i++) {
            System.out.print(items[i] + " ");
        }
    }

    /** remove first item of this arrayDeque */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (size * 4 < items.length) {
            resize(size * 2);
        }
        if (nextFirst == items.length - 1) {
            nextFirst = -1;
        }
        nextFirst += 1;
        T first = items[nextFirst];
        size -= 1;
        return first;
    }

    /** remove last item of this items */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (size * 4 < items.length) {
            resize(size * 2);
        }
        if (nextLast == 0) {
            nextLast = items.length;
        }
        nextLast -= 1;
        T last = items[nextLast];
        size -= 1;
        return last;
    }

    /** return ith item of this arrayDeque */
    @Override
    public T get(int i) {
        if (size == 0) {
            return null;
        }
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
