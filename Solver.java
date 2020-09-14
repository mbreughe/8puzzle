/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;

public class Solver {
    public static final boolean DEV_MODE = false;

    private Node mRoot;
    private MinPQ<Node> pQ;
    private Iterable<Board> mCachedSolution = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        mRoot = new Node(initial, null);
        pQ = new MinPQ<Node>();

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

        int count = 0;
        for (Board n : solution()) {
            count += 1;
        }
        
        // Subtract one for the initial state
        count -= 1;

        return count;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        if (mCachedSolution != null) {
            return mCachedSolution;
        }

        pQ.insert(mRoot);

        while (!pQ.isEmpty()) {
            Node currentNode = pQ.delMin();
            Board currentBoard = currentNode.getBoard();

            if (DEV_MODE) {
                StdOut.println("Examining Board: ");
                StdOut.println(currentBoard);
                StdOut.println("Is goal? " + currentBoard.isGoal());
                StdOut.println("Manhattan: " + currentBoard.manhattan());
            }


            if (currentBoard.isGoal()) {
                ArrayDeque<Board> chain = new ArrayDeque<Board>();
                while (currentNode != null) {
                    currentBoard = currentNode.getBoard();
                    chain.addFirst(currentBoard.clone());
                    currentNode = currentNode.getParent();
                }
                mCachedSolution = chain.clone();
                return chain;
            }

            else {
                if (DEV_MODE) {
                    StdOut.println("Adding neighbours");
                }

                for (Board n : currentBoard.neighbors()) {
                    if (DEV_MODE) {
                        StdOut.println("Adding following board: ");
                        StdOut.println(n);
                        StdOut.println("PQ size before insert: " + pQ.size());
                    }

                    pQ.insert(new Node(n, currentNode));
                }
            }
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
