public class LinkedListDeque<Item> {
    private class Node {
        public Item item;
        public Node pre;
        public Node next;

        public Node(Item item, Node pre, Node next) {
            this.item = item;
            this.pre = pre;
            this.next = next;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null,null);
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /** add item to the first position of this deque */
    public void addFirst(Item item) {
       sentinel.next = new Node(item, sentinel, sentinel.next);
       size += 1;
    }

    /** add item to the last position of this deque*/
    public void addLast(Item item) {
        sentinel.pre = new Node(item, sentinel.pre, sentinel);
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
    public Item removeFirst() {
        Item first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return first;
    }

    /** remove the last item of this deque */
    public Item removeLast() {
        Item last = sentinel.pre.item;
        sentinel.pre = sentinel.pre.pre;
        size -= 1;
        return last;
    }

    /** return ith item of this deque if i < size else return last item*/
    public Item get(int i) {
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
    private Item getHelper(Node curNode, int i) {
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
    public Item getRecursive(int i) {
        return getHelper(sentinel.next, i);
    }
}