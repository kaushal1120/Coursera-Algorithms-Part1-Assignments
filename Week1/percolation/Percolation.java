import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private final int gridSize;
    private int numberOfOpenSites;
    private final WeightedQuickUnionUF wQUUF;
    private final WeightedQuickUnionUF backwashWQUUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        gridSize = n;
        wQUUF = new WeightedQuickUnionUF(gridSize * gridSize + 2);
        backwashWQUUF = new WeightedQuickUnionUF(gridSize * gridSize + 1);
        grid = new boolean[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][i] = false;
            }
        }
    }

    private void validateIndices(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize)
            throw new IllegalArgumentException();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndices(row, col);
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            numberOfOpenSites++;

            if (row == 1) {
                wQUUF.union(col - 1, gridSize * gridSize);
                backwashWQUUF.union(col - 1, gridSize * gridSize);
            }
            if (row == gridSize)
                wQUUF.union(col - 1 + gridSize * gridSize - gridSize, gridSize * gridSize + 1);

            if (col > 1 && grid[row - 1][col - 2]) {
                wQUUF.union((row - 1) * gridSize + (col - 1), (row - 1) * gridSize + (col - 2));
                backwashWQUUF
                        .union((row - 1) * gridSize + (col - 1), (row - 1) * gridSize + (col - 2));
            }
            if (row > 1 && grid[row - 2][col - 1]) {
                wQUUF.union((row - 1) * gridSize + (col - 1), (row - 2) * gridSize + (col - 1));
                backwashWQUUF
                        .union((row - 1) * gridSize + (col - 1), (row - 2) * gridSize + (col - 1));
            }
            if (col < gridSize && grid[row - 1][col]) {
                wQUUF.union((row - 1) * gridSize + (col - 1), (row - 1) * gridSize + (col));
                backwashWQUUF.union((row - 1) * gridSize + (col - 1), (row - 1) * gridSize + (col));
            }
            if (row < gridSize && grid[row][col - 1]) {
                backwashWQUUF.union((row - 1) * gridSize + (col - 1), (row) * gridSize + (col - 1));
                wQUUF.union((row - 1) * gridSize + (col - 1), (row) * gridSize + (col - 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndices(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndices(row, col);
        return isOpen(row, col) && backwashWQUUF
                .connected((row - 1) * gridSize + (col - 1), gridSize * gridSize);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wQUUF.connected(gridSize * gridSize, gridSize * gridSize + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // No need of test client.
    }
}
