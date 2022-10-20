import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final static double CONF95 = 1.96;
    private final int trials;
    private double stddev;
    private double confLow;
    private double confHi;
    private double mean;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        double totalSites = n * n;
        this.trials = trials;
        double[] eachTrialSum = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int randomRow = StdRandom.uniformInt(1, n + 1);
                int randomCol = StdRandom.uniformInt(1, n + 1);
                p.open(randomRow, randomCol);
            }
            eachTrialSum[i] = p.numberOfOpenSites() / totalSites;
        }
        this.mean = StdStats.mean(eachTrialSum);
        this.stddev = StdStats.stddev(eachTrialSum);
        this.confLow = calcConfidenceLo();
        this.confHi = calcConfidenceHi();
    }

    public double mean() {
        return this.mean;
    }


    public double confidenceLo() {
        return this.confLow;
    }
    public double confidenceHi() {
        return this.confHi;
    }
    public double stddev() {
        if (trials == 1) {
            return Double.NaN;
        } else {
            return this.stddev;
        }
    }

    private double calcConfidenceLo() {
        return this.mean - ((CONF95 * this.stddev) / Math.sqrt(trials));
    }

    private double calcConfidenceHi() {
        return this.mean + ((CONF95 * this.stddev) / Math.sqrt(trials));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats s = new PercolationStats(n, t);
        StdOut.println("mean                    = "+s.mean());
        StdOut.println("stddev                  = "+s.stddev());
        StdOut.println("95% confidence interval = ["+s.confidenceLo()+", "+s.confidenceHi()+"]");
    }
}