package lab11.graphs;

import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private boolean targetFound;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        targetFound = false;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        for (int v = 0; v < maze.V(); v++) {
            distTo[v] = Integer.MAX_VALUE;
        }

        Queue<Integer> fringe = new ArrayDeque<>();
        fringe.add(s);

        edgeTo[s] = s;
        distTo[s] = 0;
        announce();

        while (!fringe.isEmpty()) {
            int v = fringe.poll();
            marked[v] = true;
            announce();

            if (v == t) {
                targetFound = true;
                return;
            }

            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    announce();
                    fringe.add(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

