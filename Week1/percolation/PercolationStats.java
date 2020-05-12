import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final int trialNo;
    private final double meanValue;
    private final double stddevValue;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        trialNo = trials;
        double[] thresholds = new double[trialNo];
        for (int i = 0; i < trialNo; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            thresholds[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
        meanValue = StdStats.mean(thresholds);
        stddevValue = StdStats.stddev(thresholds);
    }

    // sample mean of percolation threshold
    public double mean() {
        return meanValue;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddevValue;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return meanValue - CONFIDENCE_95 * stddevValue / Math.sqrt(trialNo);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return meanValue + CONFIDENCE_95 * stddevValue / Math.sqrt(trialNo);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        System.out.println(n + t);
        // No test client.
    }
}
