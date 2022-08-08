/*
 * Ekaterina Kuznetsova
 * CPSC 5002, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package SU_Kuznetsova1_P1;
import java.util.Scanner; //needed for user input

/**
 * The TicTacToe (TTT) class holds all the methods and data structures needed
 * to run a game of TicTacToe between two players; methods include playing the
 * game until someone wins/ties, iterating between the players, validating
 * user inputs, checking for a winner, checking if the board is full,
 * displaying the board, displaying game statistics (player wins and ties),
 * and clearing the board; additional methods are 'helper' methods.
 *
 * @author Ekaterina Kuznetsova
 * @version 1.0
 */
public class TicTacToe {
    //private fields:
    //2D array representing TicTacToe board
    private int[][] board;
    //size of board array
    private final int SIZE;
    //number of players
    private final int NUMBER_OF_PLAYERS;
    //array to hold player char values
    private char[] playerName;
    //array to hold number of wins for each player
    private int[] winStat;
    //integer values for row, column, and number of ties between players
    private int player1, player2, row, col, tieStat;

    /**
     * No-argument constructor initializes the necessary variables for TTT game.
     */
    public TicTacToe() {
        //initialize size of board array
        SIZE = 3;
        //initialize number of players
        NUMBER_OF_PLAYERS = 2;
        //initialize player 1D array
        playerName = new char[NUMBER_OF_PLAYERS];
        //player 0 represents character O with unicode of 79
        playerName[0] = 79;
        //player 0 represents character O with unicode of 88
        playerName[1] = 88;
        //initialize 2D array to represent TTT board
        board = new int[SIZE][SIZE];
        //initialize 1D array to hold wining statistics of players
        winStat = new int[NUMBER_OF_PLAYERS];
        //initialize variable for rows (will be used for user input)
        row = 0;
        //initialize variable for rows (will be used for user input)
        col = 0;
        //initialize variable for rows (will be used for user input)
        tieStat = 0;
    }

    /**
     * playGame method sets up the game by displaying the empty TTT board,
     * repeating player turns until a player wins or there's a tie, displays
     * game statistics at the end of the game, and clears the board before
     * the next game.
     */
    public void playGame(Scanner keyboard) {
        //declare a variable to track a win/tie through the while-loop
        boolean someoneWonOrTie;

        //print empty board
        printBoard();

        //begin do-while loop so players keep taking turns until
        //a winner or tie
        do {
            //players take turns
            someoneWonOrTie = playerTurns(keyboard);
        //conclude while loop with a boolean check
        } while (!someoneWonOrTie);

        //display game statistics
        displayStatistics();
        //set the board contents to zero
        clearBoard();

    }

    /**
     * playerTurns method loops between each player; the method calls necessary
     * methods for receiving/validating inputs, setting the inputs to the board,
     * checking for a winner and if the board is full after each player's turn
     *
     * @param keyboard      Scanner object for user input
     * @return boolean      whether or not there's a winner or a tie
     */
    public boolean playerTurns(Scanner keyboard) {
        //initialize boolean variable to be able to terminate loop
        boolean someoneWonOrTie = false;


        //players take turns through for-loop
        for (int i = 0; i < NUMBER_OF_PLAYERS && !someoneWonOrTie; i++) {

            //prompt user
            System.out.println(playerName[i] + ", it is your turn.");
            //call method to gather, validate, and set inputs
            checkAndSetInputs(keyboard);
            placePlayer(i);

            //check if board has winner by calling checkWinner method
            if (checkWinner(i)) {
                //display a message if there's a win
                System.out.println("\n" + playerName[i] + " won the game!");
                //keep track of player's win
                winStat[i]++;
                //leave loop
                someoneWonOrTie = true;

            //check if there is a tie (the board is full but no winner)
            } else if (!doesBoardHaveSpace()) {
                //if the board is full, display message
                System.out.println("No winner - it was a tie!");
                //keep count of ties
                tieStat++;
                //leave loop
                someoneWonOrTie = true;
            }

            //print the updated board after player took a turn
            printBoard();
        }
        //return boolean
        return someoneWonOrTie;
    }


    /**
     * checkAndSetInputs method collects user input for rows and columns;
     * before assigning the input to row/column, method checks if the input
     * is an integer, if it is in range of board size, and if the chosen
     * row/column location is taken already or not.
     *
     * @param keyboard      Scanner object for user inputs
     */
    public void checkAndSetInputs(Scanner keyboard) {
        boolean spotTaken;
        int input;

        //run a do-while loop to get user input for an empty location
        do {
            //continue do-while loop while input is out of range
            do{
                //prompt row
                System.out.print("Which row? #: ");
                //check if it is not a number input
                while (!keyboard.hasNextInt()) {
                    //prompt to enter a number
                    System.out.print("That's not a number! Try again: ");
                    keyboard.next();
                }
                //receive input
                input = keyboard.nextInt();
            //while input is in range of array & no negative numbers
            }while (input > SIZE - 1 || input < 0);

            //assign input to row
            row = input;


            //continue do-while loop while input is out of range
            do{
                //prompt col
                System.out.print("Which column? #: ");
                //check if it is not a number input
                while (!keyboard.hasNextInt()) {
                    //prompt to enter a number
                    System.out.print("That's not a number! Try again: ");
                    keyboard.next();
                }
                //receive input
                input = keyboard.nextInt();
            //while input is in range of array & no negative numbers
            }while (input > SIZE - 1 || input < 0);

            //assign input to row
            col = input;

            //check if the row/col location is already taken
            if (getBoardElement(row, col) != 0) {
                System.out.println("Spot taken! Try again.");
                spotTaken = true;
            } else {
                spotTaken = false;
            }
        //repeat the loop if the spot is taken
        } while (spotTaken);

    }

    /**
     * checkWinner method checks if the sum of user-selected row, column,
     * or the diagonal equals the product of the size of the board and the
     * unicode number value of player's character (79 for O and 88 for X);
     * if true, then that player is a winner.
     *
//     * @param row   row value selected by user
//     * @param col   col value selected by user
     * @param playerIndex     int index value representing player
     * @return boolean  whether or not winner exists
     */
    public boolean checkWinner(int playerIndex) {

        //playerName[i] represents unicode value of current player
        //size is int value of board, equals row length

        //check winner in the latest row
        if (rowSum(row) == SIZE * playerName[playerIndex]) {
            return true;
        }
        //check winner in the latest column
        else if (colSum(col) == SIZE * playerName[playerIndex]) {
            return true;
        }
        //check winner in diagonals
        //diagonal 1
        else if (diagSum1() == SIZE * playerName[playerIndex]) {
            return true;
        }
        //diagonal 2
        else if (diagSum2() == SIZE * playerName[playerIndex]) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * doesBoardHaveSpace method traverses through the board array
     * and checks if any of the locations on board equal 0 (board is
     * not full) otherwise the board is full and there's a tie
     *
     * @return boolean  whether or not board have space left
     */
    public boolean doesBoardHaveSpace() {
        //loop through rows
        for (int i = 0; i < SIZE; i++) {
            //loop through columns
            for (int j = 0; j < SIZE; j++) {
                //check if any locations are 0
                if (getBoardElement(i, j) == 0)
                    //return boolean if the board has space
                    return true;
            }
        }
        //return boolean if the board does not have space
        return false;
    }


    /**
     * setBoard method sets the element of the selected location
     * to the current player char value
     *
     * @param playerIndex     int index value representing player
     */
    public void placePlayer(int playerIndex) {
        //set player on the board
        board[row][col] = playerName[playerIndex];
    }

    /**
     * getTotalR calculates the sum of a specific row
     *
     * @param rowNum    integer value representing row number
     * @return int      sum of row
     */
    public int rowSum(int rowNum) {
        //initialize accumulator
        int summedRow = 0;

        //outer loop to go through all rows
        for (int row = rowNum; row <= rowNum; row++) {
            //sum individual row
            for (int col = 0; col < board[rowNum].length; col++) {
                //accumulator holds specific row sums
                summedRow += board[row][col];
            }
        }
        //return the sum of the row
        return summedRow;
    }

    /**
     * getTotalC calculates the sum of a specific column
     *
     * @param colNum    integer value representing col number
     * @return int[]    sum of columns
     */
    public int colSum(int colNum) {
        //initialize accumulator
        int summedCol = 0;
        //outer loop to go through all
        for (int col = colNum; col <= colNum; col++) {
            //sum individual row
            for (int row = 0; row < board.length; row++) {
                //accumulator holds specific column sums
                summedCol += board[row][col];
            }
        }
        //return column sums
        return summedCol;
    }

    /**
     * diagSum1 calculates the sum of diagonal from top right corner to
     * bottom left
     */
    public int diagSum1() {
        //initialize variables to hold diagonal sums
        int totalD1 = 0, valOne = 0;

        //sum diagonal from top right corner to bottom left
        //outer loop is to go through the rows
        for (int row = 0; row < board.length; row++) {
            //inner loop is to pick specific columns
            for (int col = (board.length - row - 1); col >= 0; col -= SIZE)
                //accumulator holds specific diagonal sums
                totalD1 += board[row][col];
        }
        //return diagonal sum
        return totalD1;
    }

    /**
     * diagSum2 calculates the sum of diagonal from top left corner to
     * bottom right
     */
    public int diagSum2() {
        //initialize variables to hold diagonal sums
        int totalD2 = 0;

        //sum the second diagonal, from top left corner to bottom right
        //outer loop is to go through the rows
        for (int row = 0; row < board.length; row++) {
            //inner loop is to pick specific columns
            for (int col = row; col >= 0; col -= SIZE)
                //accumulator holds specific diagonal sums
                totalD2 += board[row][col];
        }
        //return diagonal sum
        return totalD2;
    }

    /**
     * the getBoardElement method takes in a row and column value
     * and returns the value of the element in that location
     *
     * @param row   row value selected by user
     * @param col   col value selected by user
     * @return int  integer value at the designated element
     */
    public int getBoardElement(int row, int col) {
        //return element value
        return board[row][col];
    }

    /**
     * printBoard method prints out the TTT board 2D array with the
     * element values displayed
     */
    public void printBoard() {
        //print a space for visual clarity before the board
        System.out.println();

        //print the header of the board, the numbers of the columns
        for (int i = 0; i < SIZE; i++) {
            System.out.print("\t" + i);
        }
        System.out.println();
        //print the numbers of rows
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i);
            //print the board elements
            for (int j = 0; j < SIZE; j++) {
                //depending on the element contents, print appropriate display
                switch (getBoardElement(i, j)) {
                    //if element has a 0, print nothing
                    case 0:
                        System.out.print("\t  |");
                        break;
                    //if element has a 79 (unicode value for 'O')
                    case 'O':
                        System.out.print("\tO |");
                        break;
                    //if element has a 88 (unicode value for 'X')
                    case 'X':
                        System.out.print("\tX |");
                        break;
                }
            }
            //between each row, print dashes for visual clarity
            System.out.println();
            for (int j = 0; j < SIZE; j++) {
                System.out.print("  ---");
            }
            //go to next line
            System.out.println();
        }
        //print a space for visual clarity after the board
        System.out.println();
    }

    /**
     * displayStatistics method prints how many games each player won
     * and how many ties there have been
     */
    public void displayStatistics() {
        //print game stats header
        System.out.println("Game Stats");
        //loop through each player and print number of wins
        for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            System.out.println(playerName[i] + " has won " + winStat[i]
                    + " games.");
        }
        //print number of ties
        System.out.println("There have been " + tieStat + " ties.");
    }

    /**
     * clearBoard method sets each element of the 2D board array to
     * zero after the end of the current game in case the player
     * wishes to play again
     */
    public void clearBoard() {
        //loop through rows
        for (int i = 0; i < SIZE; i++) {
            //loop through columns
            for (int j = 0; j < SIZE; j++) {
                //set element to zero
                board[i][j] = 0;
            }
        }
    }



}