/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * Die class
 * Name: Peter Kwaterski
 * Last Updated: 10/12/2024
 */
package kwaterskip;

import java.util.Random;

public class Die {

    public final int MINSIDES = 2;
    public final int MAXSIDES = 100;

    private final int numSides;
    private int currentValue;
    private Random random;

    public Die(int numSides){
        if(numSides < MINSIDES || numSides > MAXSIDES){
            throw new IllegalArgumentException();
        }
        this.numSides = numSides;
    }

    public int getCurrentValue(){
        if(currentValue < MINSIDES || currentValue > MAXSIDES){
            throw new DieNotRolledException();
        }
        int curr = currentValue;
        currentValue = 0;
        return curr;
    }

    public void roll(){
        currentValue = random.nextInt(numSides-MINSIDES + 1) + MINSIDES;
    }

}