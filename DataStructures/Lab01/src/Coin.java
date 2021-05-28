/**
 * A class that implements the Coin object, an extension of Money
 *
 * @author Matt Brierley
 * @version 1/28/2019
 */

public class Coin extends Money
{
    public final static int[] DENOMINATION_VALUE = {1, 5, 10, 25, 50};
    public final static String[] DENOMINATION_NAME = { "PENNY", "NICKEL", "DIME", "QUARTER", "HALF_DOLLAR"};
    private static final int NUMBER_OF_DENOMINATIONS = 5;

    public Coin()
    {
        super(NUMBER_OF_DENOMINATIONS);
    }

    /**
     * Returns value of DENOMINATION_VALUE at denomination index converted to dollars
     */
    public double getValue()
    {
        //TODO - need to test this, not sure how integer math / conversions work in Java
        return ((double)DENOMINATION_VALUE[getDenomination()] / 100.0);
    }

    public String toString()
    {
        //TODO
        return (DENOMINATION_NAME[getDenomination()] + " landed " + (this.isHeads() ? "HEADS" : "TAILS"));
    }
}
