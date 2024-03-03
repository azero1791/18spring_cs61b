package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);

        assertEquals(10, arb.capacity());

        boolean expectEmpty = true;
        assertEquals(expectEmpty, arb.isEmpty());

        arb.enqueue(1);
        arb.enqueue(3);
        arb.enqueue(2);
        arb.enqueue(7);

        int expectSize1 = 4;
        assertEquals(expectSize1, arb.fillCount());

        arb.dequeue();
        arb.dequeue();
        int expectSize2 = 2;
        assertEquals(expectSize2, 2);

        for (int i = 0; i < 8; i++) {
            arb.enqueue(i + 2);
        }

        boolean expectFull = true;
        assertEquals(expectEmpty, arb.isFull());
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
