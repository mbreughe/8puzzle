/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Board {
    private int[][] mTiles;

    private int getN() {
        return mTiles.length;
    }

    private int goal(int i, int j) {
        int goal = (1 + i * getN() + j);
        // 0 needs to be the very last tile:
        goal = goal % (getN() * getN());
        return goal;
    }

    private int[] findTile(int x) {
        int[] a = new int[2];
        a[0] = a[1] = -1;
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                if (mTiles[i][j] == x) {

                    a[0] = i;
                    a[1] = j;
                    return a;
                }
            }
        }
        return a;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        mTiles = tiles;
    }

    // string representation of this board
    public String toString() {
        return "unimplemented";
    }

    // board dimension n
    public int dimension() {
        return -1;
    }

    // number of tiles out of place
    public int hamming() {

        return -1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                int[] coords = findTile(goal(i, j));

                distance += Math.abs(coords[0] - i) + Math.abs(coords[1] - j);
            }
        }

        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return this;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
