/*
 * Ekaterina Kuznetsova
 * CPSC 5002, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package SU_Kuznetsova1_P1;
import java.util.Scanner; //needed for user input

/**
 * The P1 class plays a game of TicTacToe between two players; the program
 * keeps track and prints the winner of the game.
 *
 * @author Ekaterina Kuznetsova
 * @version 1.0
 */
public class P1 {
    /**
     * The main method creates a Scanner object, runs the basic methods of
     * the game, and closes the Scanner object; the basic methods include
     * a welcome method, a method running the game that the user can repeat,
     * and a goodbye method.
     *
     * @param args A string array containing the command line arguments.
     */
    public static void main(String[] args) {
        //create scanner object for user input
        Scanner keyboard = new Scanner(System.in);
        //declare variable to hold user response
        String repeat;

        //welcome message
        welcome();

        TicTacToe game = new TicTacToe();

        //do-while loop for user to repeat game
        do {
            game.playGame(keyboard);
            //prompt user for repeat of game
            System.out.print("\nRepeat the game? Enter yes to repeat: ");
            //consume next line character
            keyboard.nextLine();
            //get user input
            repeat = keyboard.nextLine();
        //conclude do-while loop with verification of user response
        } while (repeat.equalsIgnoreCase("yes"));

        //goodbye message
        goodbye();

        //close scanner
        keyboard.close();
    }

    /**
     * Welcome method welcomes the user and introduces the program
     */
    public static void welcome() {
        //create welcome string
        String welcome = "Welcome to TicTacToe!";
        //print string in middle of 80 characters
        System.out.printf("%19s\n", welcome);
        //print message
        System.out.println("This is a game of TicTacToe between two players!" +
                "\nYou will take turns placing an X or O in a grid.\n" +
                "The computer will keep track of the winner.");
    }

    /**
     * goodbye method prints a farewell message and thanks the user
     */
    public static void goodbye() {
        //print goodbye message
        System.out.println("Thanks for playing! Goodbye for now!");
    }

}


