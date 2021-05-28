import java.util.*;

/**
 *
 * @author Matt Brierley
 * @version 4/1/2019
 */
public class MatchingGame
{
    private ArrayList<Integer> theNumbers;
    private final int MAX_NUMBER_OF_SHUFFLES = 5;
    private final int MIN_NUMBER = 10;
    private final int MAX_NUMBER = 99;
    private Random generator;

    public MatchingGame(int numberAmount)
    {
        this.theNumbers = new ArrayList<>();
        initializeList(numberAmount);
    }

    /**
     * Initialize the list with the count of random 2 digit numbers.
     *
     */
    private void initializeList(int numberAmount)
    {
        // TODO Project 6a - done
        this.generator = new Random(11);
        ListIterator<Integer> iter = this.theNumbers.listIterator();

        // generate the numbers and add them to theNumbers using iterator
        for(int i = 0; i < numberAmount; i++)
        {
            iter.add(this.generator.nextInt(MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER);
        }
    }

    /**
     * See whether two numbers are removable.
     * @param first the first 2 digit integer value
     * @param second the second 2 digit integer value
     * @return true if the first and second match
     */
    private boolean removablePair(Integer first, Integer second)
    {
        // TODO Project 6c - done

        // implement this method
        //System.out.printf("Comparing: %d %d\n", first, second);
        return (first / 10 == second / 10) || (first / 10 == second % 10)
                || (first % 10 == second / 10) || (first % 10 == second % 10);
    }

    /**
     * Implements one pass when called by play method
     * Scans over the list and removes any pairs of values that are removable.
     * @return true if any pair of integers was removed
     */
    private boolean scanAndRemovePairs()
    {
        // TODO Project 6d - done
        boolean removedAPair = false;
        ListIterator<Integer> scan = this.theNumbers.listIterator();

        Integer first = null;
        Integer second = null;

        // implement the method
        // this method calls helper method removablePair to see if there is a match

        while(scan.hasNext())
        {
            first = scan.next();

            //must have 2 elements to compare
            if(scan.hasNext())
            {
                second = scan.next();

                if(removablePair(first, second))
                {
                    scan.remove();
                    scan.previous();
                    scan.remove();

                    removedAPair = true;
                    System.out.printf("\tRemoved: %d %d\n", first, second);
                }
                //if a pair is not removed, move iterator back to second element, so it can be compared on the next loop
                else
                {
                    scan.previous();
                }
            }
        }

        return removedAPair;
    }

    private void displayTheNumbers()
    {
        // TODO Project 6b - done

        // using an instance of Iterator display the content of theNumbers
        // notify the user if the list is empty

        ListIterator<Integer> iter = this.theNumbers.listIterator();

        if(!iter.hasNext())
        {
            System.out.println("The list is empty.");
        }
        else
        {
            while(iter.hasNext())
            {
                System.out.print(iter.next() + " ");
            }
            System.out.println();
        }
    }

    public void play()
    {
        int pass = 0;
        int numberOfShuffles = 0;
        boolean repeat;

        System.out.println("Starting with: ");
        displayTheNumbers();

        do
        {
            repeat = false;
            while (scanAndRemovePairs())
            {
                pass++;
                System.out.println("The list after pass #" + pass);
                displayTheNumbers();
            }
            System.out.println("No more pairs to remove.");
            // do we have at least 3 numbers in the list?
            if (this.theNumbers.size() > 2)
            {
                if (numberOfShuffles < MAX_NUMBER_OF_SHUFFLES)
                {
                    numberOfShuffles++;
                    System.out.println("Shuffling the numbers.");
                    Collections.shuffle(this.theNumbers, this.generator);
                    System.out.println("The list after shuffling #" + numberOfShuffles);
                    displayTheNumbers();
                    repeat = true;
                }
            }
        }while(repeat);

        if (this.theNumbers.isEmpty())
        {
            System.out.println("\n*** Winner!!! ***");
        }
        else
        {
            System.out.println("\n*** Better luck next time! ***");
        }
    }

    public static void main(String[] args)
    {
        final int MIN_NUMBER_OF_ELEMENTS = 10;
        Scanner scan = new Scanner(System.in);
        int numberAmount;

        do
        {
            System.out.println("How many numbers (no less than " + MIN_NUMBER_OF_ELEMENTS + ")?");
            numberAmount = scan.nextInt();
        }while(numberAmount < MIN_NUMBER_OF_ELEMENTS);

        MatchingGame game = new MatchingGame(numberAmount);
        game.play();
    }
}
