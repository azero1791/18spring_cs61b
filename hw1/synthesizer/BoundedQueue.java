package synthesizer;

public interface BoundedQueue<T> extends Iterable<T> {
    /**
     *  return the size of the buffer
     */
    int capacity();

    /**
     *  return the number of items currently in the buffer
     */
    int fillCount();

    /**
     * add item x to the end
     */
    void enqueue(T x);

    /**
     * delete and return item from the front of the buffer
     */
    T dequeue();

    /**
     * return (but do not delete) the front item of the buffer
     */
    T peek();

    /**
     * is the buffer empty (or fillCount equals zero)?
     */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /**
     * is the buffer full (or fillCount is the same as capacity)?
     */
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
