/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Main Driver class
 * Name: Peter Kwaterski
 * Last Updated: 9/13/2024
 */
package kwaterskip;

import java.util.Arrays;
import java.util.Scanner;

public class Driver {

    public static final int MIN_DICE = 2;
    public static final int MAX_DICE = 10;

    public static void main(String[] args){
        boolean good = false;
        int[] input;
        Die[] dice = null;
        do{
            input = getInput();
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
        } while(!good);
        int[] rolls = rollDice(dice, input[1], input[2]);
        int max = findMax(rolls);
        System.out.println(Arrays.toString(rolls));
        System.out.println(max);
        report(input[0], rolls, max);
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

    private static int findMax(int[] rolls){
        int max = 0;
        for (int roll : rolls) {
            if (roll > max) {
                max = roll;
            }
        }
        return max;
    }

    private static void report(int numDice, int[] rolls, int max) {
        final int scaleRatio = 10;
        int scale = max / scaleRatio;
        if(scale == 0){
            scale = 1;
        }
        System.out.println(max+" "+scale);
        int highestNumber = rolls.length + numDice;
        int highestDigits = (int) Math.log10(highestNumber) + 1;

        int minSpaces = 4;
        int maxRollDigits = (int) Math.log10(max) + 1;

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
            System.out.printf("%d%s: %d%s%s%n", i + numDice, firstSpaces, rolls[i], spaces, stars.toString());
        }
    }

}