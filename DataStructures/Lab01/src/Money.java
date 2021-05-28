import java.util.Random;

/**
 * An abstract class that implements a Money object.
 *
 * @author Matthew Brierley
 * @version 1/28/19
 */
public abstract class Money
{
    private int denomination;
    private boolean heads;

    /**
     * Generates the denomination randomly based on the numberOfDemoninations passed to it
     * Sets heads to false
     */
    public Money(int numberOfDenominations)
    {
        //TODO Project3 - done

        Random random = new Random();
        this.denomination = random.nextInt(numberOfDenominations);

        this.heads = false;
    }

    public int getDenomination()
    {
        //TODO Project3 - done

        return this.denomination;
    }

    public abstract double getValue();

    /**
     * Simulate a money toss in which the coin/paper bill lands either heads up or tails up
     */
    public void toss()
    {
        //TODO Project3 - done

        Random random = new Random();
        this.heads = random.nextBoolean();
    }

    public boolean isHeads()
    {
        //TODO Project3 - done

        return this.heads;
    }

    public abstract String toString();
}
