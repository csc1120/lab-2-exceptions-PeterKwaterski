/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Main Driver class
 * Name: Peter Kwaterski
 * Last Updated: 9/13/2024
 */
package kwaterskip;

import java.util.Scanner;

public class Driver {

    public static final int MIN_DICE = 2;
    public static final int MAX_DICE = 10;

    public static void main(String[] args){
        boolean good = false;
        int[] input;
        Die[] dice;
        do{
            input = getInput();
            try{
                dice = createDice(input[0], input[1]);
                good = true;
            } catch(IllegalArgumentException e){
                System.out.println("Bad die creation: Illegal number of sides: " + input[1]);
            }
        } while(!good);
    }

    private static int[] getInput(){
        int[] result = new int[3];
        Scanner reader = new Scanner(System.in);
        boolean good = false;
        do {
            System.out.println("""
                    Please enter the number of dice to roll, \
                    how many sides the dice have,
                    and how many rolls to complete, separating the values by a space.
                    Example: "2 6 1000"
                    """);
            System.out.print("Enter configuration:");
            String[] entries = reader.nextLine().split(" ");
            try {
                if (entries.length != 3) {
                    throw new IllegalArgumentException();
                }
                for (int i = 0; i < 3; i++) {
                    result[i] = Integer.parseInt(entries[i]);
                }
                good = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: All values must be whole numbers.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: Expected 3 " +
                        "values but only received " + entries.length);
            }
        } while(!good);
        return result;
    }

    private static Die[] createDice(int numDice, int numSides){
        Die[] dice = new Die[numDice];
        for(int i = 0; i < numDice; i++){
            dice[i] = new Die(numSides);
        }
        return dice;
    }

    private int[] rollDice(Die[] dice, int numSides, int numRolls){
        return null;
    }

    private int findMax(int[] rolls){
        int max = 0;
        for (int roll : rolls) {
            if (roll > max) {
                max = roll;
            }
        }
        return max;
    }

    private void report(int numDice, int[] rolls, int max){

    }
}