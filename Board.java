/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;


public class Board {
    private int[][] mTiles;

    private int getN() {
        return mTiles.length;
    }

    private Board swapTiles(int[] src, int[] dst) {
        int[][] newTiles = new int[mTiles.length][];
        for (int i = 0; i < mTiles.length; i++) {
            newTiles[i] = mTiles[i].clone();
        }
        newTiles[dst[0]][dst[1]] = mTiles[src[0]][src[1]];
        newTiles[src[0]][src[1]] = mTiles[dst[0]][dst[1]];
        return new Board(newTiles);
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
        mTiles = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            mTiles[i] = tiles[i].clone();
        }
    }

    public Board clone() {
        Board new_board = new Board(mTiles);
        return new_board;
    }

    // string representation of this board
    public String toString() {
        String repr = "" + getN() + "\n";
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                repr += "" + mTiles[i][j] + " ";
            }
            repr += "\n";
        }

        return repr;
    }

    // board dimension n
    public int dimension() {
        return getN();
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                if (mTiles[i][j] != goal(i, j)) {
                    distance += 1;
                }
            }
        }
        return distance;
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
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                if (mTiles[i][j] != goal(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board other = (Board) y;

        if (other.dimension() != dimension()) {
            return false;
        }

        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                if (other.mTiles[i][j] != mTiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[] coords = findTile(0);
        int X = coords[0];
        int Y = coords[1];

        ArrayList<Board> neighbies = new ArrayList<Board>();

        if (X - 1 > 0) {
            int[] newCoords = new int[2];
            newCoords[0] = X - 1;
            newCoords[1] = Y;
            neighbies.add(swapTiles(coords, newCoords));
        }

        if (X + 1 < getN()) {
            int[] newCoords = new int[2];
            newCoords[0] = X + 1;
            newCoords[1] = Y;
            neighbies.add(swapTiles(coords, newCoords));
        }

        if (Y - 1 > 0) {
            int[] newCoords = new int[2];
            newCoords[0] = X;
            newCoords[1] = Y - 1;
            neighbies.add(swapTiles(coords, newCoords));
        }

        if (Y + 1 < getN()) {
            int[] newCoords = new int[2];
            newCoords[0] = X;
            newCoords[1] = Y + 1;
            neighbies.add(swapTiles(coords, newCoords));
        }

        return neighbies;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] src = new int[2];
        int[] dst = new int[2];
        src[0] = src[1] = dst[0] = dst[1] = 0;
        while (Arrays.equals(src, dst)) {
            src[0] = (int) (Math.random() * getN());
            src[1] = (int) (Math.random() * getN());
            dst[0] = (int) (Math.random() * getN());
            dst[1] = (int) (Math.random() * getN());
        }
        return swapTiles(src, dst);
    }

    public static int[][] readTiles(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        return tiles;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = readTiles(args[0]);
        int[][] tiles_mod = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            tiles_mod[i] = tiles[i].clone();
        }
        tiles_mod[0][0] = 77;
        Board x = new Board(tiles);
        Board x_c = new Board(tiles);
        Board y = new Board(tiles_mod);

        StdOut.println("Manhattan: " + x.manhattan());
        StdOut.println("Hamming: " + x.hamming());
        StdOut.println("String Repr: " + x.toString());
        StdOut.println("Equal to self? " + (x.equals(x_c)));
        StdOut.println("Equal to small mod? " + (x.equals(y)));
        StdOut.println("Neighbors:");

        for (Board n : x.neighbors()) {
            StdOut.println(n.toString());
        }

        StdOut.println("Create swap: ");
        Board x_t = x.twin();
        StdOut.println(x_t.toString());
    }
}
