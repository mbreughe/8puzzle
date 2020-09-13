/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private Node mRoot;
    private MinPQ<Node> pQ;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        mRoot = new Node(initial, null);
        pQ = new MinPQ<Node>();
        pQ.insert(mRoot);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        StdOut.println("WARNING: UNIMPLEMENTED!");
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        int[][] tiles = Board.readTiles(args[0]);
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
