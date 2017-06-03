import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private int experiment_count;
	private double fractions[];
	// perform trials independent experiments on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if(n<=0 || trials<=0)
			 throw new java.lang.IllegalArgumentException
				("n & trials can not be less than zero.");
		experiment_count = trials;
		fractions = new double[experiment_count];
		for(int i = 0; i < experiment_count; i++) {
			Percolation temp = new Percolation(n);
			int opensites = 0;
			while (!temp.percolates()) {
				int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!temp.isOpen(row, col)) {
                    temp.open(row, col);
                    opensites++;
                }
            }
            fractions[i] = (double) opensites / (n * n);
		}
	}
	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(fractions);
	}
	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(fractions);
	}
	// low  endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - ((1.96 * stddev()) / Math.sqrt(experiment_count));
	}
	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + ((1.96 * stddev()) / Math.sqrt(experiment_count));
	}
	public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats s = new PercolationStats(N, T);

        System.out.println("mean:\t\t\t\t = " + s.mean());
        System.out.println("stddev:\t\t\t\t = " + s.stddev());
        System.out.println("95% confidence interval:\t = " + s.confidenceLo() + ", " + s.confidenceHi());
    }
}
