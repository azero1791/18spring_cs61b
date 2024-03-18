package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    boolean findCycle;
    Maze maze;
    int s;

    int cycleV;
    boolean cycleMark;
    int tmpEdgeTo[];

    public MazeCycles(Maze m) {
        super(m);
        findCycle = false;
        cycleMark = false;
        maze = m;
        s = maze.xyTo1D(5, 5);

        for (int v = 0; v < maze.V(); v++) {
            distTo[v] = Integer.MAX_VALUE;
        }
        tmpEdgeTo = new int[edgeTo.length];
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        edgeTo[s] = s;
        distTo[s] = 0;
        dfsC(s);
    }

    // Helper methods go here
    private void dfsC(int v) {

        marked[v] = true;
        announce();

        for (int w : maze.adj(v)) {
            if (marked[w] && w != tmpEdgeTo[v]) {
                cycleV = w;
                findCycle = true;
                cycleMark = true;
                return;
            }   else if (!marked[w]) {
                tmpEdgeTo[w] = v;
                distTo[w] = distTo[v] + 1;
                dfsC(w);

                if (findCycle) {
                    if (cycleMark && marked[w]) {
                        if (w == cycleV) {
                            cycleMark = false;
                        }   else {
                            edgeTo[w] = tmpEdgeTo[w];
                        }
                    }
                    return;
                }
            }
        }


    }
}

