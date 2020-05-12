/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {

    private final SearchNode root;
    private final SearchNode rootTwin;
    private ArrayList<Board> solution;
    private int minMoves;
    private boolean solvable;

    private class SearchNode implements Comparable<SearchNode> {

        private final Board currentBoard;
        private final SearchNode parent;
        private final int numberOfMoves;
        private final int manhattanPriority;
        // private int hammingPriority;

        public SearchNode(Board cN, SearchNode pN, int nOM) {
            this.currentBoard = cN;
            this.parent = pN;
            this.numberOfMoves = nOM;
            this.manhattanPriority = cN.manhattan() + numberOfMoves;
            // this.hammingPriority = cN.hamming() + numberOfMoves;
        }

        public int compareTo(SearchNode that) {
            if (this.manhattanPriority < that.manhattanPriority) return -1;
            if (this.manhattanPriority > that.manhattanPriority) return +1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();
        this.root = new SearchNode(initial, null, 0);
        this.rootTwin = new SearchNode(initial.twin(), null, 0);
        this.solution = new ArrayList<Board>();
        this.minMoves = 0;
        this.solvable = false;
        solve();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return minMoves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return solution;
    }

    private void solve() {
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        minPQ.insert(root);

        MinPQ<SearchNode> minPQTwin = new MinPQ<SearchNode>();
        minPQTwin.insert(rootTwin);

        SearchNode current = minPQ.delMin();
        SearchNode currentTwin = minPQTwin.delMin();

        while (!current.currentBoard.isGoal() && !currentTwin.currentBoard.isGoal()) {
            Iterable<Board> listOfNeighbours = current.currentBoard.neighbors();
            for (Board neighbour : listOfNeighbours) {
                if (current.parent == null || !neighbour.equals(current.parent.currentBoard)) {
                    minPQ.insert(new SearchNode(neighbour, current,
                                                current.numberOfMoves + 1));
                }
            }

            Iterable<Board> listOfNeighboursTwin = currentTwin.currentBoard.neighbors();
            for (Board neighbour : listOfNeighboursTwin) {
                if (currentTwin.parent == null || !neighbour
                        .equals(currentTwin.parent.currentBoard)) {
                    minPQTwin.insert(new SearchNode(neighbour, currentTwin,
                                                    currentTwin.numberOfMoves + 1));
                }
            }
            current = minPQ.delMin();
            currentTwin = minPQTwin.delMin();
        }
        if (current.currentBoard.isGoal()) {
            solvable = true;
            minMoves = current.numberOfMoves;
            while (current != null) {
                solution.add(current.currentBoard);
                current = current.parent;
            }
            Collections.reverse(solution);
        }
        else {
            minMoves = -1;
            solution = null;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // Nothing to test yet.
    }
}
