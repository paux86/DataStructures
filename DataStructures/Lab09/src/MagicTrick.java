import java.util.*;

/**
 * MagicTrick is a program that will guess a number that user is thinking of.
 *
 * @author Matt Brierley
 * @version 3/30/2019
 */
public class MagicTrick
{
    public final int NUM_OF_SEQUENCES = 5;
    public final int NUMBERS_PER_SEQUENCE = 16;
    private ArrayList<Integer>[] sequences;

    public MagicTrick()
    {
        // TODO Project 5 - done

        // create this.sequences object
        // using a for loop create ArrayList object for each slot and fill it with appropriate values

        this.sequences = new ArrayList[NUM_OF_SEQUENCES];

        int bitMask = 1;

        for(int i = 0; i < NUM_OF_SEQUENCES; i++)
        {
            this.sequences[i] =  new ArrayList<>();

            //bitmask each potential number to be added and compare with zero to determine if bit is set
            int potentialNum = 1;
            while(this.sequences[i].size() < NUMBERS_PER_SEQUENCE)
            {
                if((potentialNum & bitMask) != 0)
                {
                    this.sequences[i].add(potentialNum);
                }

                potentialNum++;
            }

            bitMask = bitMask << 1;
        }
    }

    public void displaySequences()
    {
        // TODO Project 5 - done

        for(int i = 0; i < NUM_OF_SEQUENCES; i++)
        {
            System.out.printf("Sequence %d : ", i+1);
            System.out.println(this.sequences[i].toString());
        }
    }

    public void guessNumber(String[] answer)
    {
        // TODO Project 5 - done
        int result = 0;
        for(int i = 0; i < answer.length; i++)
        {
            //Result equals sum of first value of each sequence that contains the chosen number
            result += this.sequences[Integer.parseInt(answer[i]) - 1].get(0);
        }

        System.out.println("Your number is " + result + " :)");
    }

    public static void main(String[] args)
    {
        MagicTrick magic = new MagicTrick();
        System.out.println("Think of a number between 1 and 31\n");
        magic.displaySequences();

        System.out.println("\nList all the sequences that your number is in (ie. 1 3)");
        Scanner scan = new Scanner(System.in);
        magic.guessNumber(scan.nextLine().split(" "));
    }
}