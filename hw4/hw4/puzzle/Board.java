package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private final int BLANK = 0;
    private final int[][] cur;
    private final int[][] goal;
    private int size;
    public Board(int[][] tiles) {

        size = tiles.length;
        cur = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cur[row][col] = tiles[row][col];
            }
        }
        goal = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                goal[row][col] = row * 10 + col;
            }
        }
    }

    private int[][] getTiles() {
        return cur;
    }

    /**
     * get specific tile
     * @param row
     * @param col
     * @return
     */
    public int tileAt(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new IndexOutOfBoundsException("Please input row and col between 0 and size - 1");
        }
        return cur[row][col];
    }

    public int size() {
        return size;
    }

    /**
     * @source from john:https://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**
     * calculate all hamming distances between cur and goal
     * @return
     */
    public int hamming() {
        int count = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (cur[row][col] != goal[row][col] && cur[row][col] != BLANK) {
                    count += 1;
                }
            }
        }
        return count;
    }

    /**
     * calculate single manhattan distance between some position
     */
    private int singleManhatton(int curRow, int curCol, int goalRow, int goalCol) {
        return Math.abs(curRow - goalRow) + Math.abs(curCol - goalCol);
    }

    /**
     * calculate all manhattan distances between cur and goal board
     */

    public int manhattan() {
        int count = 0;
        int curRow = 0, curCol = 0;
        int goalRow = 0, goalCol = 0;
        for (int number = 1; number < size * size; number++) {
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {

                    if (cur[row][col] == number) {
                        curRow = row;
                        curCol = col;
                    }
                    if (goal[row][col] == number) {
                        goalRow = row;
                        goalCol = col;
                    }

                }
            }

            count += singleManhatton(curRow, curCol, goalRow, goalCol);
        }

        return count;

    }

    /**
     * estimate distance to goal
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * determine whether contents of two board are the same
     */
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (!y.getClass().equals(this.getClass())) {
            return false;
        }
        Board target = (Board) y;
        if (target.size() != size) {

        }
        int[][] tiles = target.getTiles();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (cur[row][col] != tiles[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
