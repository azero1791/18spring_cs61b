package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.Comparator;

public class Solver {
    private class SearchNode {
        private WorldState state;
        private int moves;
        private SearchNode pre;


        public SearchNode(WorldState state, int moves, SearchNode pre) {
            this.state = state;
            this.moves = moves;
            this.pre = pre;
        }
    }

    private class PComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return (node1.moves + node1.state.estimatedDistanceToGoal()) - (node2.moves + node2.state.estimatedDistanceToGoal());
        }
    }
    private Queue<WorldState> BMS;
    private MinPQ<SearchNode> candidates;
    private SearchNode s;
    private int minMoves;

    private boolean find;
    private int timesEq;

    /**
     * solve the puzzles directly
     * @param initial
     */
    public Solver(WorldState initial) {
        candidates = new MinPQ<>(new PComparator());
        BMS = new Queue<>();
        s = new SearchNode(initial, 0, null);
        minMoves = Integer.MAX_VALUE;
        find = false;

        timesEq = 0;
        timesEq = 1;
        candidates.insert(s);
        BEFS();
    }

    /**
     * get the number of times enqueued
     */

    public int getTimesEq() {
        return timesEq;
    }
    /**
     * using best first search to get the fast and correct path
     */
    public void BEFS() {
        if (candidates.isEmpty()) {
            return ;
        }
        if (find) {
            return ;
        }

        SearchNode cur = candidates.delMin();
        BMS.enqueue(cur.state);

        if (cur.state.isGoal()) {

            minMoves = cur.moves;
            find = true;
            return;
        }

        for (WorldState neighbor : cur.state.neighbors()) {
            SearchNode neighborNode = new SearchNode(neighbor, cur.moves + 1, cur);

            if (!isGrandparent(neighborNode, cur)) {
                timesEq += 1;
                candidates.insert(neighborNode);
            }
        }
        BEFS();
    }

    /**
     * check whether left is grandparent of right
     */
    private boolean isGrandparent(SearchNode left, SearchNode right) {
        while (right != null) {
            if (right.state.equals(left.state)) {
                return true;
            }
            right = right.pre;
        }
        return false;
    }

    /**
     * get the min number of moves to goal
     * @return
     */
    public int moves() {
        return minMoves;
    }

    /**
     * return the final best move sequence
     */
    public Iterable<WorldState> solution() {
        return BMS;
    }
}

