/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayDeque;
import java.util.Comparator;


public class Solver {
    private static final boolean DEV_MODE = false;
    private boolean DBG_processingGood = true;

    private Node mRoot;
    private boolean mFinishedProcessing = false;
    private Iterable<Board> mCachedSolution = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        mRoot = new Node(initial, null);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution() != null;
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

    private Node processNextNode(MinPQ<Node> pQ) {
        Node currentNode = pQ.delMin();
        Board currentBoard = currentNode.getBoard();

        if (DEV_MODE) {
            StdOut.println("Processing: " + (DBG_processingGood ? "Original Puzzle" : "Evil Twin"));
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
        return currentNode;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (mFinishedProcessing) {
            return mCachedSolution;
        }


        MinPQ<Node> goodPQ = new MinPQ<Node>();

        MinPQ<Node> badPQ = new MinPQ<Node>();

        goodPQ.insert(mRoot);
        badPQ.insert(new Node(mRoot.getBoard().twin(), null));

        while (!goodPQ.isEmpty() || !badPQ.isEmpty()) {
            DBG_processingGood = true;
            Node currentGoodNode = processNextNode(goodPQ);
            DBG_processingGood = false;
            Node currentBadNode = processNextNode(badPQ);


            Board currentGoodBoard = currentGoodNode.getBoard();
            Board currentBadBoard = currentBadNode.getBoard();

            if (currentBadBoard.isGoal()) {
                mFinishedProcessing = true;
                mCachedSolution = null;
                return mCachedSolution;
            }

            if (currentGoodBoard.isGoal()) {
                ArrayDeque<Board> chain = new ArrayDeque<Board>();
                while (currentGoodNode != null) {
                    currentGoodBoard = currentGoodNode.getBoard();
                    chain.addFirst(currentGoodBoard);
                    currentGoodNode = currentGoodNode.getParent();
                }

                mFinishedProcessing = true;
                mCachedSolution = chain.clone();
                return mCachedSolution;
            }
        }

        return null;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
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

    private class Node implements Comparator<Node>, Comparable<Node> {
        private Node mParent;
        private Board mBoard;
        private int mSteps;

        public Node(Board board, Node parent) {
            mBoard = board;
            mParent = parent;
            if (parent == null) {
                mSteps = 0;
            }
            else {
                mSteps = parent.mSteps + 1;
            }
        }

        public int getSteps() {
            return mSteps;
        }

        public int getWeight() {
            return mBoard.manhattan() + mSteps;
        }

        public int compare(Node a, Node b) {
            return a.getWeight() - b.getWeight();
        }

        public int compareTo(Node x) {
            return compare(this, x);
        }

        public Node getParent() {
            return mParent;
        }

        public Board getBoard() {
            return mBoard;
        }
    }
}
