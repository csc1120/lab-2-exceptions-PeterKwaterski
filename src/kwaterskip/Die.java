/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Die class
 * Name: Peter Kwaterski
 * Last Updated: 10/16/2024
 */
package kwaterskip;

import java.util.Random;

/**
 * The Class for the Die Object
 * Contains the Die Constructor as well as methods to roll the die
 * and to get the rolled value of the die
 */
public class Die {

    /**
     * An int that states the minimum number of sides a die
     * object is allowed to have
     * is set to 2 in order that a die cannot only roll a singular number
     */
    public static final int MIN_SIDES = 2;

    /**
     *An int that states the maximum number of sides a die
     *object is allowed to have
     *is set to 100 to place a cap
     */
    public static final int MAX_SIDES = 100;

    private final int numSides;
    private int currentValue;
    private final Random random = new Random();

    /**
     * The Constructor for Die Objects
     * Checks to make sure that the number of sides is between
     * MIN_SIDES and MAX_SIDES
     * @param numSides the number of sides that the die has
     * @throws IllegalArgumentException throws if numSides is not between MIN and MAX_SIDES
     */
    public Die(int numSides){
        if(numSides < MIN_SIDES || numSides > MAX_SIDES){
            throw new IllegalArgumentException();
        }
        this.numSides = numSides;
    }

    /**
     * Gets the current value of the die (the number that was just rolled)
     * Then sets the rolled number to 0 indicating it needs to be rolled
     * @return The current rolled number
     * @throws DieNotRolledException if the die has not been rolled then the exception
     * is thrown showing that it has not been rolled
     */
    public int getCurrentValue(){
        if(currentValue < 1){
            throw new DieNotRolledException();
        }
        int curr = currentValue;
        currentValue = 0;
        return curr;
    }

    /**
     * Randomly rolls the die
     * The random value will be between 1 and the number of sides
     */
    public void roll(){
        currentValue = random.nextInt(numSides) + 1;
    }

}