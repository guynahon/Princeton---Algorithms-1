import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSitesSum;
    private final int n;
    private final boolean[] sites;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufNoButton;

    public Percolation(int n) {
        this.n = n;
        if (this.n <= 0) {
            throw new IllegalArgumentException();
        }
        sites = new boolean[n*n+2];
        uf = new WeightedQuickUnionUF(n*n+2);
        ufNoButton = new WeightedQuickUnionUF(n*n+1);
        sites[0] = true;
        sites[n*n+1] = true;
        openSitesSum = 0;
    }

    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) {
        } else {
            sites[twoToOne(row, col)] = true;
            openSitesSum++;
            if (row + 1 <= n && isOpen(row + 1, col)) {
                ufNoButton.union(twoToOne(row, col), twoToOne(row + 1, col));
                uf.union(twoToOne(row, col), twoToOne(row + 1, col));
            }
            if (row - 1 > 0 && isOpen(row - 1, col)) {
                ufNoButton.union(twoToOne(row, col), twoToOne(row - 1, col));
                uf.union(twoToOne(row, col), twoToOne(row - 1, col));
            }
            if (col + 1 <= n && isOpen(row, col + 1)) {
                ufNoButton.union(twoToOne(row, col), twoToOne(row, col + 1));
                uf.union(twoToOne(row, col), twoToOne(row, col + 1));
            }
            if (col - 1 > 0 && isOpen(row, col - 1)) {
                ufNoButton.union(twoToOne(row, col), twoToOne(row, col - 1));
                uf.union(twoToOne(row, col), twoToOne(row, col - 1));
            }
            if (row == 1) {
                ufNoButton.union(twoToOne(row, col), 0);
                uf.union(twoToOne(row, col), 0);
            }
            if (row == n) {
                uf.union(twoToOne(row, col), n*n+1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return sites[twoToOne(row, col)];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return isOpen(row, col) && connectedNoBottom(twoToOne(row, col), 0);
    }

    public int numberOfOpenSites() {
        return openSitesSum;
    }

    public boolean percolates() {
        return connected(0, n*n+1);
    }
    private boolean connectedNoBottom(int p, int q) {
        return ufNoButton.find(p) == ufNoButton.find(q);
    }
    private boolean connected(int p, int q) {
        return uf.find(p) == uf.find(q);
    }
    private int twoToOne(int row, int col) {
        return (row - 1) * n + (col - 1) + 1;
    }
    private void validate(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
    }
}
