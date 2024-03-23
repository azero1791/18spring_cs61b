import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Queue;
import org.junit.Test;
public class RadixSortTester {

    @Test
    public void RadixSortTest() {
         Queue<String> queue = new Queue<String>();

         queue.enqueue("qwfaw");
        queue.enqueue(" ");
         queue.enqueue("asfwr");

         queue.enqueue("zcsdwda");
         queue.enqueue("qgwet");
         queue.enqueue("gwetwe");
         queue.enqueue("plokwor");
         String[] origin = new String[7];
         for (int i = 0; i < 7; i++) {
             origin[i] = queue.dequeue();
         }

         String[] answer = RadixSort.sort(origin);
         String[] expect = new String[7];
         queue.enqueue(" ");
         queue.enqueue("asfwr");

        queue.enqueue("gwetwe");
         queue.enqueue("plokwor");
        queue.enqueue("qgwet");
         queue.enqueue("qwfaw");
         queue.enqueue("zcsdwda");
        for (int i = 0; i < 7; i++) {
            expect[i] = queue.dequeue();
        }
         assertArrayEquals(expect, answer);


    }
}
