/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Board {
    private int[][] mTiles;
    private boolean manhattanCalculated = false;
    private boolean hammingCalculated = false;
    private int manhDistance_cache = 0;
    private int hamDistance_cache = 0;

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
        if (tiles == null) {
            throw new RuntimeException("exit now");
        }
        mTiles = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            mTiles[i] = tiles[i].clone();
        }
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
        if (hammingCalculated) {
            return hamDistance_cache;
        }
        hamDistance_cache = 0;
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                // We don't count the 0-tile
                if (mTiles[i][j] == 0) {
                    continue;
                }
                if (mTiles[i][j] != goal(i, j)) {
                    hamDistance_cache += 1;
                }
            }
        }
        hammingCalculated = true;

        return hamDistance_cache;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattanCalculated) {
            return manhDistance_cache;
        }

        manhDistance_cache = 0;
        for (int i = 0; i < getN(); i++) {
            for (int j = 0; j < getN(); j++) {
                int goalValue = goal(i, j);
                // We don't count Manhattan distance for the 0-tile
                if (goalValue == 0) {
                    continue;
                }
                int[] coords = findTile(goalValue);

                manhDistance_cache += Math.abs(coords[0] - i) + Math.abs(coords[1] - j);
            }
        }
        manhattanCalculated = true;

        return manhDistance_cache;
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
        if (y == null) {
            throw new RuntimeException("comparing to null Board");
        }

        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board other;

        try {
            other = (Board) y;
        }
        catch (ClassCastException e) {
            return false;
        }

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

        if (X - 1 >= 0) {
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

        if (Y - 1 >= 0) {
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
        int seed = 0xdeadbeef;
        Random generator = new Random(seed);
        int[] src = new int[2];
        int[] dst = new int[2];
        src[0] = src[1] = dst[0] = dst[1] = 0;
        while (Arrays.equals(src, dst) || mTiles[src[0]][src[1]] == 0
                || mTiles[dst[0]][dst[1]] == 0) {
            src[0] = (int) (generator.nextDouble() * getN());
            src[1] = (int) (generator.nextDouble() * getN());
            dst[0] = (int) (generator.nextDouble() * getN());
            dst[1] = (int) (generator.nextDouble() * getN());
        }
        return swapTiles(src, dst);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
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

        for (Board nb : x.neighbors()) {
            StdOut.println(nb.toString());
        }

        StdOut.println("Create twin: ");
        Board x_t = x.twin();
        StdOut.println(x_t.toString());
    }
}
