/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * DieNotRolledException class
 * Name: Peter Kwaterski
 * Last Updated: 10/13/2024
 */
package kwaterskip;

public class DieNotRolledException extends RuntimeException  {

    public String getMessage(){
        return "Illegal number of sides: ";
    }
}
