import java.util.ArrayList;

public class Node {

    public ArrayList<Node> children = new ArrayList<>();
    public Node parent;
    public int[][] board;
    public int x = 0;
    public int y = 0;

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public Node(int[][] board) {
        this.board = board;
    }

    public boolean isValidRow(int row) {
        int n = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == 1) {
                n++;
            }
        }
        return (n == 1);
    }

    public int heuristicRow(int row) {
        int pairs = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = i + 1; j < board.length; j++) {
                if (board[row][i] == 1 && board[row][j] == 1) {
                    pairs++;
                }
            }
        }
        return pairs;
    }


    public boolean isValidColumn(int column) {
        int n = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i][column] == 1) {
                n++;
            }
        }
        return (n == 1);
    }

    public int heuristicColumn(int column) {
        int pairs = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = i + 1; j < board.length; j++) {
                if (board[i][column] == 1 && board[j][column] == 1) {
                    pairs++;
                }
            }
        }
        return pairs;
    }

    public int heuristicDiagonal1(int column, int row) {
        int pairs = 0;
        int length = board.length;
        int zerothRow;
        int zerothColumn;
        int nthRow;
        int nthColumn;
        if (row < column) {
            zerothRow = 0;
            zerothColumn = column - row;
            nthRow = length - zerothColumn - 1;
            nthColumn = length - 1;
        } else if (column < row) {
            zerothColumn = 0;
            zerothRow = row - column;
            nthRow = length - 1;
            nthColumn = length - zerothRow - 1;
        } else {
            zerothColumn = 0;
            zerothRow = 0;
            nthColumn = length - 1;
            nthRow = length - 1;
        }
        int[][] boardArray = board;
        while (zerothRow <= nthRow && zerothColumn <= nthColumn) {
            if (boardArray[zerothRow][zerothColumn] == 1) {
                pairs++;
            }
            zerothColumn++;
            zerothRow++;
        }
        return (pairs * (pairs - 1)) / 2;
    }

    public int heuristicDiagonal2(int column, int row) {
        int pairs = 0;
        int length = board.length;
        int zerothRow;
        int zerothColumn;
        int nthRow;
        int nthColumn;
        if (row + column < length - 1) {
            zerothRow = (column + row);
            zerothColumn = 0;
            nthRow = zerothColumn;
            nthColumn = zerothRow;
        } else if (column + row > length - 1) {
            zerothColumn = (column + row) - (length - 1);
            zerothRow = length - 1;
            nthColumn = zerothRow;
            nthRow = zerothColumn;
        } else {
            zerothColumn = 0;
            zerothRow = length - 1;
            nthColumn = zerothRow;
            nthRow = zerothColumn;
        }
        int[][] boardArray = board;
        while (zerothRow >= nthRow && zerothColumn <= nthColumn) {
            if (boardArray[zerothRow][zerothColumn] == 1) {
                pairs++;
            }
            zerothColumn++;
            zerothRow--;
        }
        return (pairs * (pairs - 1)) / 2;
    }

    public int heuristicBoard() {
        int rowPairs = 0;
        int columnPairs = 0;
        int diagonal1Pairs = 0;
        int diagonal2Pairs = 0;
        for (int i = 0; i < board.length; i++) {
            rowPairs += heuristicRow(i);
            columnPairs += heuristicColumn(i);
            diagonal1Pairs += heuristicDiagonal1(0, i);
            diagonal2Pairs += heuristicDiagonal2(0, i);
        }
        return rowPairs + columnPairs + diagonal1Pairs + diagonal2Pairs;
    }


    public boolean ifRowContains(int row) {
        int[][] currentBoard = board;
        for (int i = 0; i < currentBoard.length; i++) {
            if (currentBoard[row][i] == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean ifColumnContains(int column) {
        int[][] currentBoard = board;
        for (int i = 0; i < currentBoard.length; i++) {
            if (currentBoard[i][column] == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isBoardValid() {
        boolean bRCount = false;
        boolean bCCount = false;
        boolean bDCount = false;
        int rCount = 0;
        int cCount = 0;
        int dCount = 0;

        for (int i = 0; i < board.length; i++) {
            if (isValidRow(i)) {
                rCount++;
            }
        }
        if (rCount == 8) {
            bRCount = true;
        }
        for (int j = 0; j < board.length; j++) {
            if (isValidColumn(j)) {
                cCount++;
            }
        }
        if (cCount == 8) {
            bCCount = true;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (ifDiagonalContains(j, i)) {
                    dCount++;
                }
            }
        }
        if (dCount == 64) {
            bDCount = true;
        }
        return bRCount && bCCount && bDCount;
    }

    public void printResult(int[][] board) {
        for (int row = 0; row < board.length; row++)//Cycles through rows
        {
            for (int col = 0; col < board[row].length; col++)//Cycles through columns
            {
                System.out.printf("%5d", board[row][col]); //change the %5d to however much space you want
            }
            System.out.println(); //Makes a new row
        }
        System.out.println("========================================");
    }

    public void moveUp(int row, int column) {
        if (row != 0) {
            if (board[row][column] == 1) {
                int[][] cb = new int[8][8];
                copyBoard(cb, board);
                cb[row - 1][column] = 1;
                cb[row][column] = 0;

                Node child = new Node(cb);
                children.add(child);
                child.parent = this;
                //printResult(cb);
            }
        }
    }

    public void moveDown(int row, int column) {
        if (row != 7) {
            if (board[row][column] == 1) {
                int[][] cb = new int[8][8];
                copyBoard(cb, board);
                cb[row + 1][column] = 1;
                cb[row][column] = 0;

                Node child = new Node(cb);
                children.add(child);
                child.parent = this;
                //printResult(cb);
            }
        }
    }

    public void expandNode() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 1) {
                    x = i;
                    y = j;
                    moveUp(x, y);
                    moveDown(x, y);
                }
            }
        }

    }

    public boolean isSameBoard(int[][] b) {
        boolean sameBoard = true;
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (board[i][j] != b[i][j]) {
                    sameBoard = false;
                    break;
                }
            }
        }
        return sameBoard;
    }

    public void copyBoard(int[][] a, int[][] b) {
        for (int i = 0; i < b.length; i++) {
            System.arraycopy(b[i], 0, a[i], 0, b.length);
        }
    }


    public boolean ifDiagonalContains(int column, int row) {
        return ifDiagonal1Contains(column, row) && ifDiagonal2Contains(column, row);
    }

    public boolean ifDiagonal1Contains(int column, int row) {
        int counter = 0;
        int length = board.length;
        int zerothRow;
        int zerothColumn;
        int nthRow;
        int nthColumn;
        if (row < column) {
            zerothRow = 0;
            zerothColumn = column - row;
            nthRow = length - zerothColumn - 1;
            nthColumn = length - 1;
        } else if (column < row) {
            zerothColumn = 0;
            zerothRow = row - column;
            nthRow = length - 1;
            nthColumn = length - zerothRow - 1;
        } else {
            zerothColumn = 0;
            zerothRow = 0;
            nthColumn = length - 1;
            nthRow = length - 1;
        }
        int[][] boardArray = board;
        while (zerothRow <= nthRow && zerothColumn <= nthColumn) {
            if (boardArray[zerothRow][zerothColumn] == 1) {
                counter++;
            }
            if (zerothRow == nthRow && zerothColumn == nthColumn && (counter == 1 || counter == 0)) {
                return true;
            }
            zerothColumn++;
            zerothRow++;
        }
        return false;
    }

    public boolean ifDiagonal2Contains(int column, int row) {
        int counter = 0;
        int length = board.length;
        int zerothRow;
        int zerothColumn;
        int nthRow;
        int nthColumn;
        if (row + column < length - 1) {
            zerothRow = (column + row);
            zerothColumn = 0;
            nthRow = zerothColumn;
            nthColumn = zerothRow;
        } else if (column + row > length - 1) {
            zerothColumn = (column + row) - (length - 1);
            zerothRow = length - 1;
            nthColumn = zerothRow;
            nthRow = zerothColumn;
        } else {
            zerothColumn = 0;
            zerothRow = length - 1;
            nthColumn = zerothRow;
            nthRow = zerothColumn;
        }
        int[][] boardArray = board;
        while (zerothRow >= nthRow && zerothColumn <= nthColumn) {
            if (boardArray[zerothRow][zerothColumn] == 1) {
                counter++;
            }
            if (zerothRow == nthRow && zerothColumn == nthColumn && (counter == 1 || counter == 0)) {
                return true;
            }
            zerothColumn++;
            zerothRow--;
        }
        return false;
    }

}
