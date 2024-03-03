package synthesizer;

import java.util.Iterator;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {

    protected int capacity; // the current size
    protected int fillCount; // the max size

    /**
     * return the max size
     * @return
     */
    public int capacity() {
        return capacity;
    }

    /**
     * return whether the buffer is full
     * @return
     */
    public int fillCount() {
        return fillCount;
    }

    /**
     * return whether the buffer is empty
     * @return
     */
    public boolean isEmpty() {
        return fillCount() == 0;
    }

    /**
     * return whether the buffer is full
     * @return
     */
    public boolean isFull() {
        return capacity() == fillCount();
    }
}
