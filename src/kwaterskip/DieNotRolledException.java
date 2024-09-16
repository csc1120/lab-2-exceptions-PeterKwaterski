/*
 * Course: CSC1020
 * Lab 2 - Exceptions
 * DieNotRolledException class
 * Name: Peter Kwaterski
 * Last Updated: 10/15/2024
 */
package kwaterskip;

/**
 * Creates a child class of RunTimeException indicating that a die has not been rolled
 */
public class DieNotRolledException extends RuntimeException {

    public String getMessage(){
        return "The Die Have Not Been Rolled!";
    }
}
