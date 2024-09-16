/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Main Driver class
 * Name: Peter Kwaterski
 * Last Updated: 9/15/2024
 */
package kwaterskip;

import java.util.Scanner;

/**
 * Prompts the user for the number of dice to be rolled,
 * the number of sides on each die,
 * and the number of times the dice are to be rolled.
 * The totals are then added and a formatted result is printed showing the breakdown
 * of the amount of times that each total was rolled by the dice.
 * Checks the input to ensure it matches what is expected
 * and is within the specified limits
 * Throws exceptions when the inputs do not match
 * Catches and deals with the exceptions
 * Continues prompting for input until it matches the specifications
 */
public class Driver {

    /**
     * States that the minimum number of dice is 2 so that addition has to happen
     */
    public static final int MIN_DICE = 2;

    /**
     * States that the maximum number of dice allowed is 10 to place a cap
     */
    public static final int MAX_DICE = 10;

    public static void main(String[] args){
        boolean good = false;
        int[] input;
        Die[] dice = null;

        //Loop to ensure that all bad inputs are fixed before continuing
        do{
            input = getInput();
            //Checks to ensure that the die are rolled at least once and that the dice are legal
            //throws an exception otherwise
            try{
                dice = createDice(input[0], input[1]);
                if(input[2] == 0){
                    throw new DieNotRolledException();
                }
                good = true;
            } catch(IllegalArgumentException e){
                System.out.println("Bad die creation: Illegal number of sides: " + input[1]);
            } catch (DieNotRolledException e){
                System.out.println(e.getMessage());
            }
            //Ensures the number of dice are between the min and max
            try{
                if(input[0] < MIN_DICE || input[0] > MAX_DICE){
                    throw new IllegalArgumentException();
                }
            } catch(IllegalArgumentException e) {
                System.out.println("Illegal number of die: " + input[0] +
                        "Number of ie must be between 2 and 10");
                good = false;
            }
        } while(!good);
        int[] rolls = rollDice(dice, input[1], input[2]);
        int max = findMax(rolls);
        report(input[0], rolls, max);
    }

    /**
     * Prompts the user for the inputs, first the number of dice to be rolled
     * Then the number of sides on each die
     * and finally the number of rolls
     * Checks to make sure that there are 3 inputs and that they are all
     * Integers and throws an exception otherwise
     * Caches any thrown exceptions, stating why the input was bad
     * Then prompts the user again for new inputs
     * @return the three integers input by the user
     * @throws IllegalArgumentException If there are not 3 inputs the exception is thrown
     * the user is then told how many inputs were expected and how many they entered
     * @throws NumberFormatException if an input is not an integer this exception is thrown
     * the user is told that inputs must be whole numbers
     */
    private static int[] getInput(){
        int[] result = new int[3];
        Scanner reader = new Scanner(System.in);
        boolean good = false;
        //Loop to catch some of the bad inputs before passing to main
        //Continues prompting until these requirements are met
        do {
            System.out.println("""
                    Please enter the number of dice to roll, \
                    how many sides the dice have,
                    and how many rolls to complete, separating the values by a space.
                    Example: "2 6 1000"
                    """);
            System.out.print("Enter configuration:");
            String[] entries = reader.nextLine().split(" ");
            //Checks the number of inputs and the type of input
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

    /**
     * Creates an array with the number of dice specified by the user
     * with the number of sides specified by the user
     * @param numDice the number of dice to be created
     * @param numSides the number of sides each die will have
     * @return a Die[] of all the dice created
     */
    private static Die[] createDice(int numDice, int numSides){
        Die[] dice = new Die[numDice];
        for(int i = 0; i < numDice; i++){
            dice[i] = new Die(numSides);
        }
        return dice;
    }

    /**
     * Goes through each Die in the dice array and rolls each one using
     * The roll() method from the die class
     * The total of the values from all the die is calculated
     * The index of the resulting array corresponding to the total
     * is increased by one.
     * This is repeated for the amount of times rolls indicated by the user
     * @param dice an array containing all the die objects created based on the user input
     * @param numSides the number of sides each die has
     * @param numRolls the amount of times that the die are to be rolled
     * @return and int array keeping track of the number of times that
     * all the possible totals are rolled
     * @throws DieNotRolledException If the user inputs zero for the number of rolls
     * The Exception is thrown indicating that the dice have not been rolled
     */
    private static int[] rollDice(Die[] dice, int numSides, int numRolls){
        int[] result = new int[dice.length*numSides-dice.length+1];
        if(numRolls == 0){
            throw new DieNotRolledException();
        }
        for(int i = 0; i < numRolls; i++){
            int total = 0;
            for (Die die : dice) {
                die.roll();
                total += die.getCurrentValue();
            }
            result[total-dice.length]++;
        }
        return result;
    }

    /**
     * Goes through the rolls array to find the maximum amount of times that a total was rolled
     * @param rolls the array returned by the rollDice method containing the amount of
     *              time that each total is rolled
     * @return the maximum amount of times the same total was rolled
     */
    private static int findMax(int[] rolls){
        int max = 0;
        for (int roll : rolls) {
            if (roll > max) {
                max = roll;
            }
        }
        return max;
    }

    /**
     * Prints a formatted breakdown of the results of the rollDice method
     * Shows how many times each total was rolled
     * Also creates a dot plot showing the relative number of times that each total was rolled
     * @param numDice the number of dice created
     * @param rolls the array resulting from rollDice
     * @param max the maximum number of times a single total was rolled
     */
    private static void report(int numDice, int[] rolls, int max) {
        final int scaleRatio = 10;
        int scale = max / scaleRatio;
        if(scale == 0){
            scale = 1;
        }
        int highestNumber = rolls.length + numDice;
        int highestDigits = (int) Math.log10(highestNumber) + 1;

        int minSpaces = 4;
        int maxRollDigits = (int) Math.log10(max) + 1;

        //Ensures proper spacing in the result based on length of numbers
        //Both in the total and the number of times rolled
        for (int i = 0; i < rolls.length; i++) {
            int numStars = rolls[i] / scale;
            int rollNumDigits = 1;
            if(rolls[i] != 0){
                rollNumDigits = (int) Math.log10(rolls[i]) + 1;
            }
            int numberDigits = (int) Math.log10(i + numDice) + 1;
            String firstSpaces = " ".repeat(highestDigits - numberDigits);
            String spaces = " ".repeat(Math.max(maxRollDigits +
                    minSpaces - rollNumDigits + 1, minSpaces));
            StringBuilder stars = new StringBuilder();
            for (int k = 0; k < numStars; k++) {
                stars.append("*");
            }
            System.out.printf("%d%s: %d%s%s%n", i +
                    numDice, firstSpaces, rolls[i], spaces, stars);
        }
    }

}