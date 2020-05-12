/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;

public class Board {

    private final int[][] board;
    private final int n;
    private int blankPositionX;
    private int blankPositionY;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles[0].length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
                if (board[i][j] == 0) {
                    blankPositionX = i;
                    blankPositionY = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        String boardRepresentation = String.valueOf(n);
        for (int i = 0; i < n; i++) {
            boardRepresentation = boardRepresentation.concat("\n");
            for (int j = 0; j < n; j++) {
                boardRepresentation = boardRepresentation.concat("\t")
                                                         .concat(String.valueOf(board[i][j]));
            }
        }
        return boardRepresentation;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != (1 + j) + (i * n))
                    distance++;
            }
        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0)
                    distance += Math.abs((board[i][j] - 1) % n - j) + Math
                            .abs((board[i][j] - 1) / n - i);
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (board[n - 1][n - 1] != 0)
            return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != 0 && board[i][j] != (1 + j) + (i * n))
                    return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass() != this.getClass())
            return false;
        Board y1 = (Board) y;
        if (y1.dimension() != this.dimension())
            return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != y1.board[i][j])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbours = new ArrayList<Board>();
        int[][] boardCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                boardCopy[i][j] = board[i][j];
        }

        if (blankPositionX != 0) {
            boardCopy[blankPositionX][blankPositionY] = boardCopy[blankPositionX
                    - 1][blankPositionY];
            boardCopy[blankPositionX - 1][blankPositionY] = 0;
            neighbours.add(new Board(boardCopy));
            boardCopy[blankPositionX - 1][blankPositionY]
                    = boardCopy[blankPositionX][blankPositionY];
            boardCopy[blankPositionX][blankPositionY] = 0;
        }

        if (blankPositionX != n - 1) {
            boardCopy[blankPositionX][blankPositionY] = boardCopy[blankPositionX
                    + 1][blankPositionY];
            boardCopy[blankPositionX + 1][blankPositionY] = 0;
            neighbours.add(new Board(boardCopy));
            boardCopy[blankPositionX + 1][blankPositionY]
                    = boardCopy[blankPositionX][blankPositionY];
            boardCopy[blankPositionX][blankPositionY] = 0;
        }

        if (blankPositionY != 0) {
            boardCopy[blankPositionX][blankPositionY] = boardCopy[blankPositionX][blankPositionY
                    - 1];
            boardCopy[blankPositionX][blankPositionY - 1] = 0;
            neighbours.add(new Board(boardCopy));
            boardCopy[blankPositionX][blankPositionY - 1]
                    = boardCopy[blankPositionX][blankPositionY];
            boardCopy[blankPositionX][blankPositionY] = 0;
        }

        if (blankPositionY != n - 1) {
            boardCopy[blankPositionX][blankPositionY] = boardCopy[blankPositionX][blankPositionY
                    + 1];
            boardCopy[blankPositionX][blankPositionY + 1] = 0;
            neighbours.add(new Board(boardCopy));
            boardCopy[blankPositionX][blankPositionY + 1]
                    = boardCopy[blankPositionX][blankPositionY];
            boardCopy[blankPositionX][blankPositionY] = 0;
        }
        return neighbours;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int x = 0;
        int y = 0;
        int xNew = 0;
        int yNew = 1;
        if (blankPositionX == 0) {
            xNew++;
            x++;
        }
        int[][] boardCopy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                boardCopy[i][j] = board[i][j];
        }
        boardCopy[x][y] = board[xNew][yNew];
        boardCopy[xNew][yNew] = board[x][y];
        return new Board(boardCopy);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // Nothing to test yet.
    }
}
