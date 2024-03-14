package hw2;

import edu.princeton.cs.introcs.StdRandom;

public class PercolationStats {

    private Percolation p;
    private final double[] stats;
    private final int length;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        stats = new double[T];


        length = N;

        for (int i = 0; i < T; i++) {
            p = pf.make(N);
            stats[i] = test();
        }

    }

    private double test() {
        while (!p.percolates()) {
            int num = StdRandom.uniform(length * length);
            p.open(num % length, num / length);
        }
        return (double) p.numberOfOpenSites() / (length * length);
    }

    public double mean() {
        double sum = 0;
        for (double stat : stats) {
            sum += stat;
        }
        return sum / stats.length;
    }

    public double stddev() {
        double sum = 0;
        double m = mean();
        for (double stat : stats) {
            sum += (stat - m) * (stat - m);
        }
        return Math.sqrt(sum / (stats.length - 1));
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(stats.length);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(stats.length);
    }


}
