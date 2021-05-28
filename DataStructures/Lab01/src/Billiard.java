import java.util.*;

/**
 * This class determines how long it would take to remove billiard balls from a table,
 * where ball n is replaced by n balls with randomly generated numbers between 1 and n-1.
 *
 * @version 1/27/2019
 * @updatedBy Matt Brierley
 */
public class Billiard
{
    private BagInterface<Integer> table;

    /**
     * constructor creates this.table object as ResizableArrayBag
     */
    public Billiard()
    {
        this.table = new ResizableArrayBag<>();
    }

    /**
     * prompts the user for the first numbered ball and adds it to this.table
     */
    public void addFirstElement()
    {
        final int SMALLEST_BALL = 1;
        final int LARGEST_BALL = 6;
        Scanner keyboard = new Scanner(System.in);
        int start;
        do
        {
            System.out.println("What is the first numbered ball to start with? (must be between " + SMALLEST_BALL
                    + " and " + LARGEST_BALL + " inclusive)");
            start = keyboard.nextInt();
        } while (!(start >= SMALLEST_BALL && start <= LARGEST_BALL));

        System.out.println("The first ball is: \"" + start + "\"");
        this.table.add(start);
    }

    /**
     * Removes balls from this.table until all are gone.
     */
    public void removeBallsFromTable()
    {
        System.out.println("\n*** Removing balls from the table ***\n");
        //TODO Project1
        /*
        Create Random object
        Repeat for as long as there are balls on the table:
        remove random ball (call remove() method) and display its value
        if the ball number is 1 just print a message as shown in the sample run
        otherwise (the ball number is greater than 1)
        put the “ball number” of randomly generated balls within range of [1 ... ball number – 1] on the table
        print appropriate message as shown in the sample run
        display the content of the bag
        */

        Random random = new Random();

        //run until table is empty
        while(!this.table.isEmpty())
        {
            //remove ball
            int ball = this.table.remove();

            //display removed ball value
            System.out.println("---> Removed \"" + ball + "\"");

            //check for value of 1
            if(ball == 1)
            {
                System.out.println("Removed ball has number " + ball + " no new balls will be added - " + this.table.getCurrentSize() + " balls remaining:");
                displayBag();
            }
            else
            {
                //add ball value number of balls to table with random values between 1 and (ball - 1)
                for(int i = 0; i < ball; i++)
                {
                    this.table.add(random.nextInt(ball - 1) + 1);
                }
                System.out.println("After adding " + ball + " balls, we have " + this.table.getCurrentSize() + " balls on the table:");
                displayBag();
            }
        }


        System.out.println("\nThe table is empty!!!");
    } // end removeBallsFromTable

    /**
     * Displays the content of this.table
     */
    private void displayBag()
    {
        Object[] bagArray = this.table.toArray();
        System.out.println(Arrays.toString(bagArray));
        System.out.println();
    } // end displayBag

    public static void main(String args[])
    {
        Billiard billiard = new Billiard();
        billiard.addFirstElement();

        long startTime = Calendar.getInstance().getTime().getTime(); // get current time in miliseconds

        billiard.removeBallsFromTable();

        long stopTime = Calendar.getInstance().getTime().getTime();

        System.out.println("\nThe time required was " + (stopTime - startTime) + " milliseconds");
    } // end main
} // end Billiard