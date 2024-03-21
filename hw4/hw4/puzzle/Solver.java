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


    private Queue<WorldState> BMS;
    private MinPQ<SearchNode> candidates;
    private SearchNode s;
    private int minMoves;

    private boolean find;

    private int marked;

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

        candidates.insert(s);
        BEFS();
    }

    private class PComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return (node1.moves + node1.state.estimatedDistanceToGoal()) - (node2.moves + node2.state.estimatedDistanceToGoal());
        }
    }

    /**
     * using best first search to get the fast and correct path
     */

    private void BEFS() {

        while (!candidates.isEmpty()) {
            SearchNode curBest = candidates.delMin();
            BMS.enqueue(curBest.state);

            if (curBest.state.isGoal()) {
                minMoves = curBest.moves;
                return;
            }   else {
                for (WorldState neighborState : curBest.state.neighbors()) {
                    candidates.insert(new SearchNode(neighborState, curBest.moves + 1, curBest));
                }
            }
        }
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

