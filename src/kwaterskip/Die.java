/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Die class
 * Name: Peter Kwaterski
 * Last Updated: 10/13/2024
 */
package kwaterskip;

import java.util.Random;

public class Die {

    public static final int MIN_SIDES = 2;
    public static final int MAX_SIDES = 100;

    private final int numSides;
    private int currentValue;
    private Random random;

    public Die(int numSides){
        if(numSides < MIN_SIDES || numSides > MAX_SIDES){
            throw new IllegalArgumentException();
        }
        this.numSides = numSides;
    }

    public int getCurrentValue(){
        if(currentValue < MIN_SIDES || currentValue > MAX_SIDES){
            throw new DieNotRolledException();
        }
        int curr = currentValue;
        currentValue = 0;
        return curr;
    }

    public void roll(){
        currentValue = random.nextInt(numSides-MIN_SIDES + 1) + MIN_SIDES;
    }

}