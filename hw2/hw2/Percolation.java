package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.Test;
import static org.junit.Assert.*;

public class Percolation {

    private WeightedQuickUnionUF grid;
    private int length;
    private boolean[] openCheck;
    private int openSize;

    private int top;
    private int button;
    private boolean[] mark;

    /**create a N-by-N grid with all sites initially blocked**/
    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        length = N;
        grid = new WeightedQuickUnionUF(N * N + 2);

        top = N * N;
        button = N * N + 1;

        // take the last first site as the mark to connect to top
        for (int i = 0; i < length; i++) {
            grid.union(i, top);
        }

        // take the last second site as the mark to connect to top
        for (int i = 0; i < length; i++) {
            grid.union(i + length * (length - 1), button);
        }

        openCheck = new boolean[N * N];
        mark = new boolean[N * N];
        for (int i = 0; i < openCheck.length; i++) {
            openCheck[i] = false;
            mark[i] = false;
        }
        openSize = 0;
    }

    /** translate 2-dimension to 1-dimension **/
    public int transCor(int row, int col) {
        return row * length + col;
    }

    /** check whether the index is out of bound **/
    public boolean outOfBound(int row, int col) {
        if (row < 0 || row >= length || col < 0 || col >= length) {
            return true;
        }
        return false;
    }

    public void apply(int row, int col) {
        int cor = transCor(row, col);
        int up = transCor(row - 1, col);
        int down = transCor(row + 1, col);
        int left = transCor(row, col - 1);
        int right = transCor(row, col + 1);
        if (!outOfBound(row - 1, col) && isOpen(row - 1, col)) {
            grid.union(cor, up);
        }
        if (!outOfBound(row + 1, col) && isOpen(row + 1, col)) {
            grid.union(cor, down);
        }
        if (!outOfBound(row, col - 1) && isOpen(row, col - 1)) {
            grid.union(cor, left);
        }
        if (!outOfBound(row, col + 1) && isOpen(row, col + 1)) {
            grid.union(cor, right);
        }
    }

    /** union sites close to them **/
    public void unionHelper(int row, int col) {

        if (outOfBound(row, col) || mark[transCor(row, col)] || !isOpen(row, col)) {
            return;
        }

        apply(row, col);
        mark[transCor(row, col)] = true;
        unionHelper(row + 1, col);
        unionHelper(row - 1, col);
        unionHelper(row, col - 1);
        unionHelper(row, col + 1);

    }

    public void exceptionCheck(int row, int col) {
        if (row < 0 || row > length || col < 0 || col > length) {
            throw new IndexOutOfBoundsException();
        }
    }

    /** open the specific site in the grid **/
    public void open(int row, int col) {

        exceptionCheck(row, col);
        int cor = transCor(row, col);

        if (!isOpen(row, col)) {
            openCheck[cor] = true;

            openSize += 1;

            unionHelper(row, col);

            resumeMark();
        }

    }

    public boolean isConnected(int row1, int col1, int row2, int col2) {
        return grid.connected(transCor(row1, col1), transCor(row2, col2));
    }

    private void resumeMark() {
        for (int i = 0; i < mark.length; i++) {
            mark[i] = false;
        }
    }
    /** check the specific site is open **/
    public boolean isOpen(int row, int col) {
        exceptionCheck(row, col);
        int cor = transCor(row, col);
        return openCheck[cor];
    }

    /** check the specific site is full **/
    public boolean isFull(int row, int col) {
        exceptionCheck(row, col);

        if (!isOpen(row, col)) {
            return false;
        }
        int cor = transCor(row, col);
        return grid.connected(cor, length * length);
    }

    /** return the number of open sites **/
    public int numberOfOpenSites() {
        return openSize;
    }

    /** does the system percolate **/
    public boolean percolates() {
        return grid.connected(top, button);
    }

}

