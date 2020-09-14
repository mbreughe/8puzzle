/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Comparator;

public class Node implements Comparator<Node>, Comparable<Node> {
    private Node mParent;
    private Board mBoard;
    private int mSteps;

    public Node(Board board, Node parent) {
        mBoard = board.clone();
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
        return mBoard.clone();
    }
}
