import edu.princeton.cs.algs4.StdRandom;


public class PercolationStats {
    private final double[] means;
    private boolean meanCalc;
    private double meanFnd;
    private double stdDevFnd;
    private boolean stdDevCalc;

    public PercolationStats(int n, int trials) {
        validate(n, trials);
        means = new double[trials];
        meanCalc = false;
        stdDevCalc = false;
        while (trials > 0) {
            trials--;
            Percolation perc = new Percolation(n);
            int[] order = new int[n * n];
            for (int i = 0; i < n * n; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
            int i = 0;
            while (!perc.percolates()) {
                int j = order[i];
                perc.open(j / n + 1, j % n + 1);
                i += 1;
            }
            double mean = ((perc.numberOfOpenSites()) * (1.0)) / (n * n);
            means[trials] = mean;
        }
    }

    public double mean() {
        double ans = 0.0;
        if (meanCalc)
            return meanFnd;
        for (double mean : means) {
            ans += mean;
        }
        meanCalc = true;
        meanFnd = ans / (means.length);
        return meanFnd;
    }

    public double stddev() {
        this.mean();
        double stdDev = 0;
        if (stdDevCalc)
            return stdDevFnd;
        stdDevCalc = true;
        for (double mean : this.means) {
            stdDev += (mean - this.meanFnd) * (mean - this.meanFnd);
        }
        stdDevFnd = Math.sqrt(stdDev / (means.length - 1));
        return stdDevFnd;
    }

    public double confidenceLo() {
        this.stddev();
        return this.meanFnd - (1.96 * this.stdDevFnd / Math.sqrt(means.length));
    }

    public double confidenceHi() {
        this.stddev();
        return this.meanFnd + (1.96 * this.stdDevFnd / Math.sqrt(means.length));
    }

    private void validate(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Arguments are invalid");
        }
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats pc = new PercolationStats(n, trials);
        System.out.println("mean = " + pc.mean());
        System.out.println("stddev = " + pc.stddev());
        System.out.println("95% confidence Interval = [" + pc.confidenceLo() + ", " + pc.confidenceHi() + ']');
    }
}
