package SU_Kuznetsova1_P1;

import java.util.Scanner;

public class TicTacToe {

    //2D array representing TicTacToe board
    private int[][] board;
    //size of board array
    private final int SIZE;
    //number of players
    private final int NUMBEROFPLAYERS;
    //array to hold player String values
    private char[] playerName;
    //array to hold turn statistics of players
    private int[] turnStat;
    //array to hold row sum
    private int[] totalR;
    //array to hold column sum
    private int[] totalC;
    //array to hold diagonal sum
    private int[] totalD;
    //declare variables for row and col
    private int row, col, tieStat;

    /**
     * No-arg constructor
     */
    public TicTacToe() {
        SIZE = 3;
        NUMBEROFPLAYERS = 2;
        playerName = new char[NUMBEROFPLAYERS];
        playerName[0] = 79;
        playerName[1] = 88;
        board = new int[SIZE][SIZE];
        turnStat = new int[NUMBEROFPLAYERS];
        totalR = new int[SIZE];
        totalC = new int[SIZE];
        totalD = new int[SIZE];
        row = 0;
        col = 0;
        tieStat = 0;
    }


    public int getBoard(int row, int col) {
        return board[row][col];
    }

    public int getSIZE() {
        return SIZE;
    }

    /**
     * getPlayerArray returns the 1D array that holds player information
     *
     * @return playerArray  1D array
     */
    public char[] getPlayerName() {
        return playerName;
    }

    /**
     * winLoop does...
     */
    public void playGame(Scanner keyboard) {
        boolean someoneWonOrTie;

        do {
            //players take turns
            someoneWonOrTie = playerTurns(keyboard);

        } while (!someoneWonOrTie);

        displayStatistics();
        clearBoard();

    }

    public boolean playerTurns(Scanner keyboard) {
        //initialize boolean variable to be able to terminate loop
        boolean someoneWonOrTie = false;

        printBoard();
        //players take turns through for-loop
        for (int i = 0; i < NUMBEROFPLAYERS && !someoneWonOrTie; i++) {
            //prompt user
            System.out.println(playerName[i] + ", it is your turn.");
            //call method to gather, validate, and set inputs
            checkAndSetInputs(keyboard);
            setBoard(row, col, i);
            //check if board has winner
            if (checkWinner(row, col, i)) {
                System.out.println("\n" + playerName[i] + " won the game!");
                turnStat[i]++;
                someoneWonOrTie = true;
            } else if (!doesBoardHaveSpace()) {
                System.out.println("No winner - it was a tie!");
                tieStat++;
                someoneWonOrTie = true;
            }

            printBoard();

        }
        return someoneWonOrTie;
    }

    public void displayStatistics() {
        System.out.println("Game Stats");
        for (int i = 0; i < NUMBEROFPLAYERS; i++) {
            System.out.println(playerName[i] + " has won " + turnStat[i]
                    + " games.");
        }
        System.out.println("There have been " + tieStat + " ties.");

    }


    public void checkAndSetInputs(Scanner keyboard) {
        boolean spotTaken;
        int input;

        do {
            //get row
            System.out.print("Which row? #: ");
            input = keyboard.nextInt();
            //validate row
            while (input > SIZE) {
                System.out.print("Invalid. Try again. #: ");
                input = keyboard.nextInt();
            }
            row = input;


            //get column
            System.out.print("Which column? #: ");
            input = keyboard.nextInt();
            //validate column
            while (input > SIZE) {
                System.out.print("Invalid. Try again. #: ");
                input = keyboard.nextInt();
            }
            col = input;

            if (getBoard(row, col) != 0) {
                System.out.println("Spot taken! Try again.");
                spotTaken = true;
            } else {
                spotTaken = false;
            }

        } while (spotTaken);

    }

    public boolean checkWinner(int row, int col, int i) {


        //check winner in latest row
        if (getTotalR(row) == SIZE * playerName[i]) {
            return true;
        }
        //check winner in latest column
        else if (getTotalC(col) == SIZE * playerName[i]) {
            return true;
        }
        //check winner in diagonals
        //diagonal 1
        else if (getTotalD1() == SIZE * playerName[i]) {
            return true;
        }
        //diagonal 2
        else if (getTotalD2() == SIZE * playerName[i]) {
            return true;
        } else {
            return false;
        }

    }

    public boolean doesBoardHaveSpace() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (getBoard(i, j) == 0)
                    return true;
            }
        }
        return false;
    }


    public void setBoard(int row, int col, int i) {
        //set player on the board
        board[row][col] = playerName[i];
    }

    /**
     * getTotalR calculates the sum of a specific row
     *
     * @param rowNum integer value representing row number
     * @return int      sum of row
     */
    public int getTotalR(int rowNum) {

        //outer loop to go through all rows
        for (int row = rowNum; row <= rowNum; row++) {
            //initialize accumulator
            totalR[rowNum] = 0;
            //sum individual row
            for (int col = 0; col < board[rowNum].length; col++) {
                totalR[rowNum] += board[row][col];
            }
        }
        return totalR[rowNum];
    }

    /**
     * getTotalC calculates the sum of a specific column
     *
     * @return int[]     sum of columns
     */
    public int getTotalC(int colNum) {

        //outer loop to go through all
        for (int col = colNum; col <= colNum; col++) {
            //initialize accumulator
            totalC[colNum] = 0;
            //sum individual row
            for (int row = 0; row < board.length; row++) {
                totalC[colNum] += board[row][col];
            }
        }
        //return column sums
        return totalC[colNum];
    }

    public int getTotalD1() {
        //initialize variables to hold diagonal sums
        int totalD1 = 0, valOne = 0;

        //sum the first diagonal, from top right corner to bottom left
        //outer loop is to go through the rows
        for (int row = 0; row < board.length; row++) {
            //inner loop is to pick specific columns
            for (int col = (board.length - row - 1); col >= 0; col -= SIZE)
                //accumulator holds specific column sums
                totalD1 += board[row][col];
        }
        return totalD1;
    }

    public int getTotalD2() {
        //initialize variables to hold diagonal sums
        int totalD2 = 0;

        //sum the second diagonal, from top left corner to bottom right
        //outer loop is to go through the rows
        for (int row = 0; row < board.length; row++) {
            //inner loop is to pick specific columns
            for (int col = row; col >= 0; col -= SIZE)
                //accumulator holds specific column sums
                totalD2 += board[row][col];
        }

        return totalD2;
    }

    public void printBoard() {
        //print board statements here
        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print("\t" + i);
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i);
            for (int j = 0; j < SIZE; j++) {
                switch (getBoard(i, j)) {
                    case 0:
                        System.out.print("\t  |");
                        break;
                    case 79:
                        System.out.print("\tO |");
                        break;
                    case 88:
                        System.out.print("\tX |");
                        break;
                }
            }
            System.out.println();
            for (int j = 0; j < SIZE; j++) {
                //String str =
                System.out.print("  ---");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void clearBoard() {
        //clear the board
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
    }

}