package hw2;

import static org.junit.Assert.*;

import org.junit.Test;
import edu.princeton.cs.introcs.StdRandom;

public class TestPercolationStats {

    @Test
    public void testPercolationStats() {
        int N = StdRandom.uniform(10, 20);
        int T = StdRandom.uniform(30, 80);
        PercolationStats ps = new PercolationStats(N, T, new PercolationFactory());

        assertTrue(ps.mean() > 0.59 && ps.mean() < 0.60);
        System.out.println("N: " + N);
        System.out.println("T: " + T);
        System.out.println("mean: " + ps.mean());
        System.out.println("stddec: " + ps.stddev());
        System.out.println("confidence: [" + ps.confidenceLow() + "," + ps.confidenceHigh() + "]");
    }
}
