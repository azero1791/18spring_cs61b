public class LinkedListDeque<T> {

    private class Node {
        private T item;
        private Node pre;
        private Node next;

        public Node(T item, Node pre, Node next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** add item to the first position of this deque */
    public void addFirst(T item) {
        sentinel.next = new Node(item, sentinel, sentinel.next);
        sentinel.next.next.pre = sentinel.next;
        size += 1;
    }

    /** add item to the last position of this deque*/
    public void addLast(T item) {
        sentinel.pre = new Node(item, sentinel.pre, sentinel);
        sentinel.pre.pre.next = sentinel.pre;
        size += 1;
    }

    /** return whether size of this deque is 0 */
    public boolean isEmpty() {
        return size == 0;
    }

    /** return size of this deque */
    public int size() {
        return size;
    }

    /** print this deque whose items is separated by space */
    public void printDeque() {
        Node curNode = sentinel.next;
        while (curNode.next != sentinel) {
            System.out.print(curNode.item + " ");
            curNode = curNode.next;
        }
        System.out.print(curNode.item);
    }

    /** remove the first item of this deque */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.pre = sentinel;
        size -= 1;
        return first;
    }

    /** remove the last item of this deque */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T last = sentinel.pre.item;
        sentinel.pre = sentinel.pre.pre;
        sentinel.pre.next = sentinel;
        size -= 1;
        return last;
    }

    /** return ith item of this deque if i < size else return last item*/
    public T get(int i) {
        if (i >= size) {
            return null;
        }
        Node curNode = sentinel.next;
        while (i > 0) {
            curNode = curNode.next;
            i -= 1;
        }

        return curNode.item;
    }

    /** getHelper function for recursion */
    private T getHelper(Node curNode, int i) {
        if (i >= size) {
            return null;
        }
        if (i == 0) {
            return curNode.item;
        } else {
            return getHelper(curNode.next, i - 1);
        }
    }
    /** get using recursion */
    public T getRecursive(int i) {
        return getHelper(sentinel.next, i);
    }

}
