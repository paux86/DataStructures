/**
 * A class that implements the Bill object, an extension of Money
 *
 * @author Matt Brierley
 * @version 1/28/2019
 */

public class Bill extends Money
{
    public final static int[] DENOMINATION_VALUE = { 1, 2, 5, 10, 20, 50, 100 };
    public final static String[] DENOMINATION_NAME = { "WASHINGTON", "JEFFERSON", "LINCOLN", "HAMILTON", "JACKSON", "GRANT", "FRANKLIN" };
    private final static int NUMBER_OF_DENOMINATIONS = 7;

    public Bill()
    {
        super(NUMBER_OF_DENOMINATIONS);
    }

    /**
     * Returns DENOMINATION_VALUE at denomination index
     */
    public double getValue()
    {
        return (DENOMINATION_VALUE[getDenomination()]);
    }

    public String toString()
    {
        //TODO
        return (DENOMINATION_NAME[getDenomination()] + " landed " + (this.isHeads() ? "HEADS" : "TAILS"));
    }
}
