import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int[][] grid;
    private final WeightedQuickUnionUF groups;
    private int numberOfOpenSite;

    public Percolation(int n) {
        validate(n);
        grid = new int[n][n];
        groups = new WeightedQuickUnionUF(n * n + 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
        numberOfOpenSite = 0;
    }

    private void validate(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Arguments are invalid");
        }
    }

    private void validate(int row, int col) {
        int n = grid.length;
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("The arguments are invalid");
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        row -= 1;
        col -= 1;

        if (grid[row][col] == 0)
            numberOfOpenSite++;
        else
            return;
        if (row == 0) {
            groups.union(col + 1, 0);
        }
        if (row == grid.length - 1) {
            int n = grid.length;
            groups.union(n * n + 1, n * row + col + 1);
        }
        grid[row][col] = 1;
        int n = grid.length;
        if (row > 0 && grid[row - 1][col] == 1) {
            groups.union(n * row + col + 1, n * (row - 1) + col + 1);
        }
        if (row < n - 1 && grid[row + 1][col] == 1) {
            groups.union(n * row + col + 1, n * (row + 1) + col + 1);
        }
        if (col > 0 && grid[row][col - 1] == 1) {
            groups.union(n * row + col + 1, n * (row) + col);
        }
        if (col < n - 1 && grid[row][col + 1] == 1) {
            groups.union(n * row + col + 1, n * (row) + col + 2);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        row--;
        col--;
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        row--;
        col--;
        int n = grid.length;

        return groups.find(n * row + col + 1) == groups.find(0);
    }

    public int numberOfOpenSites() {
        return this.numberOfOpenSite;
    }

    public boolean percolates() {
        int n = grid.length;
        return groups.find(n * n + 1) == groups.find(0);
    }

    public static void main(String[] args) {
        // Main Program to check the correctness.
    }
}
