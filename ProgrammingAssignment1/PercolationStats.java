import edu.princeton.cs.algs4.StdRandom;


public class PercolationStats {
    private final double[] means;
    private boolean mean_calc;
    private double mean_fnd;
    private double std_dev_fnd;
    private boolean std_dev_calc;

    public PercolationStats(int n, int trials) {
        validate(n, trials);
        means = new double[trials];
        mean_calc = false;
        std_dev_calc = false;
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
        if (mean_calc)
            return mean_fnd;
        for (double mean : means) {
            ans += mean;
        }
        mean_calc = true;
        mean_fnd = ans / (means.length);
        return mean_fnd;
    }

    public double stddev() {
        this.mean();
        double std_dev = 0;
        if (std_dev_calc)
            return std_dev_fnd;
        std_dev_calc = true;
        for (double mean : this.means) {
            std_dev += (mean - this.mean_fnd) * (mean - this.mean_fnd);
        }
        std_dev_fnd = Math.sqrt(std_dev / (means.length - 1));
        return std_dev_fnd;
    }

    public double confidenceLo() {
        this.stddev();
        return this.mean_fnd - (1.96 * this.std_dev_fnd / Math.sqrt(means.length));
    }

    public double confidenceHi() {
        this.stddev();
        return this.mean_fnd + (1.96 * this.std_dev_fnd / Math.sqrt(means.length));
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
