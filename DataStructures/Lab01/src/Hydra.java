import java.util.*;

/**
 * This class determines how long it would take to remove heads from a Hydra,
 * where head n is replaced by two heads of length head.length - 1
 * @author Matt Brierley
 * @version 1/27/2019
 */

public class Hydra
{
    private BagInterface<String> hydraHeads;

    /**
     * Constructor creates this.hydraHeads object as ResizableArrayBag
     */
    public Hydra()
    {
        this.hydraHeads = new ResizableArrayBag<>();
    }

    /**
     * Prompts user for the first String and adds it to this.hydraHeads
     */
    public void addFirstElement()
    {
        Scanner keyboard = new Scanner(System.in);

        String firstString;
        System.out.println("What is the initial string?");
        firstString = keyboard.next();
        this.hydraHeads.add(firstString);

        System.out.printf("The initial string %s has a length of %d", firstString, firstString.length());
    }

    /**
     * While hydraHeads bag is not empty, removes a head (String) from the bag
     * If removed String is more than 1 character long, removes the first character of the removed
     * String and adds two copies of it to the bag
     */
    public void removeHead()
    {
        System.out.println("\n\n*** Removing heads from the Hydra ***\n");

        while(!this.hydraHeads.isEmpty())
        {
            String head = this.hydraHeads.remove();
            System.out.printf("---> Removed \"%s\"", head);

            if(head.length() == 1)
            {
                System.out.printf("\nThe removed head is of length 1, no new heads will be added - %d heads remaining\n", this.hydraHeads.getCurrentSize());
            }
            else
            {
                head = head.substring(1);
                for (int newHeads = 0; newHeads < 2; newHeads++)
                {
                    this.hydraHeads.add(head);
                }
                System.out.printf("\nAfter adding two, the Hydra has %d heads:\n", this.hydraHeads.getCurrentSize());
            }
            this.displayBag();
        }

        System.out.println("The Hydra is no more!!!");
    }

    /**
     * Displays the contents of this.hydraHeads
     */
    private void displayBag()
    {
        Object[] bagArray = this.hydraHeads.toArray();
        System.out.println(Arrays.toString(bagArray));
        System.out.println();
    }

    public static void main(String args[])
    {
        Hydra hydra = new Hydra();
        hydra.addFirstElement();

        long startTime = Calendar.getInstance().getTime().getTime(); // get current time in milliseconds

        hydra.removeHead();

        long stopTime = Calendar.getInstance().getTime().getTime();

        System.out.println("\nThe time required was " + (stopTime - startTime) + " milliseconds");
    }
}
