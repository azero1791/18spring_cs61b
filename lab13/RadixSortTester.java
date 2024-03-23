import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Queue;
import org.junit.Test;
public class RadixSortTester {

    @Test
    public void RadixSortTest() {
         Queue<String> queue = new Queue<String>();
         queue.enqueue("qwfaw");
         queue.enqueue("asfwr");
         queue.enqueue("zcsdwda");
         queue.enqueue("egwet");
         queue.enqueue("gwetwe");
         queue.enqueue("plokwor");
         String[] origin = new String[6];
         for (int i = 0; i < 6; i++) {
             origin[i] = queue.dequeue();
         }

         String[] answer = RadixSort.sort(origin);
         String[] expect = new String[6];
         queue.enqueue("asfwr");
         queue.enqueue("egwet");
        queue.enqueue("gwetwe");
         queue.enqueue("plokwor");

         queue.enqueue("qwfaw");
         queue.enqueue("zcsdwda");
        for (int i = 0; i < 6; i++) {
            expect[i] = queue.dequeue();
        }
         assertArrayEquals(expect, answer);


    }
}
