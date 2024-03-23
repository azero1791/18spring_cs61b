import static org.junit.Assert.*;

import edu.princeton.cs.algs4.Queue;
import org.junit.Test;
public class RadixSortTester {

    @Test
    public void RadixSortTest() {
         Queue<String> queue = new Queue<String>();
        queue.enqueue("");
         queue.enqueue(" Ôåw¿Î&");


         String[] origin = new String[2];
         for (int i = 0; i < 2; i++) {
             origin[i] = queue.dequeue();
         }

         String[] answer = RadixSort.sort(origin);
         String[] expect = new String[2];
         queue.enqueue("");
         queue.enqueue(" Ôåw¿Î&");

        for (int i = 0; i < 2; i++) {
            expect[i] = queue.dequeue();
        }
         assertArrayEquals(expect, answer);


    }
}
