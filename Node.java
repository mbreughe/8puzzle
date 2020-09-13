/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Node {
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

    public int getWeight() {
        return mBoard.manhattan() + mSteps;
    }

}
