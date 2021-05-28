import java.util.Arrays;
import java.util.Random;

/**
 * A class that implements PiggyBank
 *
 * @author Matt Brierley
 * @version 1/29/2019
 */

public class PiggyBank
{
    private BagInterface<Money> bucket;
    private int capacity;

    /**
     * The PiggyBank constructor creates this.bucket object and fills it with the given number of monies,
     * randomly choosing a Coin or a Bill object to create and add to this.bucket.
     */
    public PiggyBank(int numberOfMonies, int capacity)
    {
        this.bucket = new ResizableArrayBag<>();
        this.capacity = capacity;

        Random random = new Random();
        int numberOfMoneyTypes = 2;

        System.out.printf(">> Adding %d monies to your piggy bank <<\n", numberOfMonies);

        for(int i = 0; i < numberOfMonies; i++)
        {
            int moneyType = random.nextInt(numberOfMoneyTypes);

            if(moneyType == 0)
            {
                add(new Bill());
            }
            else
            {
                add(new Coin());
            }
        }
    }

    /**
     * Checks that capacity is not reached and then adds a new money to bucket
     */
    public void add(Money monies) throws PiggyBankFullException
    {
        if(this.bucket.getCurrentSize() < this.capacity)
        {
            this.bucket.add(monies);
            System.out.printf("Added $%.2f to the piggy bank\n", monies.getValue());
        }
        else
        {
            throw new PiggyBankFullException("No more room in the piggy bank! - additional monies will not be added to your piggy bank.");
        }
    }

    /**
     * Removes a random money from bucket if bucket is not empty, else throws a PiggyBankEmptyException
     */
    public Money remove() throws PiggyBankEmptyException
    {
        //TODO - add correct messaging
        if(!this.bucket.isEmpty())
        {
            return this.bucket.remove();
        }
        else
        {
            throw new PiggyBankEmptyException(" ");
        }
    }

    public boolean isEmpty()
    {
        return (this.bucket.isEmpty());
    }

    public boolean isFull()
    {
        return (this.bucket.getCurrentSize() >= this.capacity);
    }

    public int getCapacity()
    {
        return this.capacity;
    }

    public int getNumberOfMonies()
    {
        return this.bucket.getCurrentSize();
    }

    /**
     * shake method must utilize toArray method to get the access to the objects:
     * Object[] toShake = this.bucket.toArray();
     * For each element in the toShake array select randomly another element to swap it with. Once the objects
     * are shuffled in the toShake array they need to be added to this.bucket (which needs to be cleared first)
     */
    public void shake()
    {
        System.out.println("\n>> Shaking your piggy bank << ");
        Object[] toShake = this.bucket.toArray();
        Random random = new Random();

        for(int i = 0; i < toShake.length; i++)
        {
            Object temp = toShake[i];
            int swapIndex = random.nextInt(this.bucket.getCurrentSize());
            toShake[i] = toShake[swapIndex];
            toShake[swapIndex] = temp;
        }

        this.bucket.clear();

        //Not sure I can do this
        for(int i = 0; i < toShake.length; i++)
        {
            this.bucket.add((Money)toShake[i]);
        }
    }

    /**
     * while removing money from the piggy bank counts how many coins/bills landed HEADS,
     * displays the count and the dollar value, and returns the heads count.
     */
    public int emptyAndCountHeads()
    {
        System.out.println("\n>> Emptying your piggy bank <<");

        int headsCount = 0;
        int totalMonies = this.bucket.getCurrentSize();
        double headsValue = 0.0;

        while(!this.bucket.isEmpty())
        {
            Money removed = this.bucket.remove();
            removed.toss();

            if(removed.isHeads())
            {
                headsCount++;
                headsValue += removed.getValue();
            }

            System.out.println(removed.toString());
        }

        System.out.printf("%d out of %d coins/bills landed \"HEADS\"" +
                "\nThe total value of \"HEADS\" is $%.2f\n", headsCount, totalMonies, headsValue);
        return headsCount;
    }

    /**
     * •	PiggyBank toString method utilizes polymorphism:
     * o	Calls toArray to get the elements (see above)
     * o	Uses a for loop over the resulted array to compute the total - must use casting: total += ((Money) content[i]).getValue();
     * o	Calls Arrays.toString to get the content of the objects
     */
    public String toString()
    {
        Object[] bucketToArray = this.bucket.toArray();
        double total = 0.0;
        for(Object obj : bucketToArray)
        {
            total += ((Money) obj).getValue();
        }

        return ("The piggy bank can hold " + this.capacity + " coins/bills;\n" +
                "There are " + this.bucket.getCurrentSize() + " coins/bills in the piggy bank:" +
                Arrays.toString(bucketToArray) + "\nThe total of $" + total);
    }
}
