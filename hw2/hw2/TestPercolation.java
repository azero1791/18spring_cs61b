package hw2;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.introcs.StdRandom;

public class TestPercolation {

    @Test
    public void testPercolatoin() {
        Percolation p = new Percolation(10);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertFalse(p.isOpen(i, j));
            }
        }

    }

    @Test
    public void testTransCor() {
        Percolation p = new Percolation(10);
        int expect = 56;
        assertEquals(expect, p.transCor(5, 6));
    }

    @Test
    public void testOutOfBound() {
        Percolation p = new Percolation(10);
        assertTrue(p.outOfBound(-1, 12));

        for (int i = 0; i < 100; i++) {
            int row = StdRandom.uniform(10);
            int col = StdRandom.uniform(10);
            assertFalse(p.outOfBound(row, col));
        }

    }

    @Test
    public void testOpenAndOpenSize() {
        int N = 0;
        while (N == 0) {
            N = StdRandom.uniform(15);
        }

        Percolation p = new Percolation(N);
        int openTimes = StdRandom.uniform(N * N);
        boolean[][] mark = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                mark[i][j] = false;
            }
        }
        int expectOpenSize = 0;
        for (int i = 0; i < openTimes; i++) {
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            p.open(row, col);
            if (!mark[row][col]) {
                expectOpenSize += 1;
            }
            assertEquals(expectOpenSize, p.numberOfOpenSites());
            mark[row][col] = true;
            assertTrue(p.isOpen(row, col));
        }



        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (!mark[row][col]) {
                    assertFalse(p.isOpen(row, col));
                }

            }
        }

    }

    @Test
    public void testIsFull() {
        Percolation p = new Percolation(10);
        for (int i = 0; i < 10; i++) {
            p.open(i, 0);
        }
        assertTrue(p.percolates());
    }

    @Test
    public void testIsConnected() {
        Percolation p = new Percolation(10);
        for (int i = 0; i < 10; i++) {

            /**
             * button is connected to a specific site
             */
            assertTrue(p.isConnected(9, i, 9, 11));

            /**
             * top is connected to a specific site
             */
            assertTrue(p.isConnected(0, i, 9, 10));

        }
        p.open(0, 0);
        for (int i = 1; i < 10; i++) {
            p.open(i, 0);
            assertTrue(p.isConnected(i, 0, i - 1, 0));
        }
    }


}
