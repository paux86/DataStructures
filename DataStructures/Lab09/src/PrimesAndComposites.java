import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * PrimesAndComposites is a program that will compute prime numbers using the sieve of Eratosthenes algorithm.
 *
 * @author Matt Brierley
 * @version 3/30/2019
 */

public class PrimesAndComposites
{
    private ArrayList<Integer> composites;
    private ArrayList<Integer> primes;

    public PrimesAndComposites(ArrayList<Integer> candidates)
    {
        this.composites = new ArrayList<>();
        this.primes = new ArrayList<>();
        setPrimesAndComposites(candidates);
    }

    public void setPrimesAndComposites(ArrayList<Integer> candidates)
    {
        // TODO Project 3 - done
        Integer foundPrime;
        Integer maybeComposite;
        Iterator<Integer> candidatesIter = candidates.iterator();


        // fills the primes and composites lists
        // when the method is finished the candidates list is empty
        // scans the candidates list with an iterator and removes elements from the candidates list with
        // an iterator's remove method

        while(candidatesIter.hasNext())
        {
            foundPrime = candidatesIter.next();
            this.primes.add(foundPrime);
            candidatesIter.remove();

            System.out.println("==> Found the prime " + foundPrime);

            while(candidatesIter.hasNext())
            {
                maybeComposite = candidatesIter.next();

                if(maybeComposite % foundPrime == 0)
                {
                    this.composites.add(maybeComposite);
                    candidatesIter.remove();

                    System.out.println("===> Found the composite " + maybeComposite);
                }
            }
            candidatesIter = candidates.iterator();
        }
    }

    public void display()
    {
        System.out.println("The primes list is ");
        Iterator<Integer> iter = this.primes.iterator();
        while (iter.hasNext())
        {
            System.out.print(iter.next() + " ");
        }
        System.out.println();

        System.out.println("The composites list is ");
        iter = this.composites.iterator();
        while (iter.hasNext())
        {
            System.out.print(iter.next() + " ");
        }
        System.out.println();
    }

    public static void main(String args[])
    {
        ArrayList<Integer> candidates;

        Scanner keyboard = new Scanner(System.in);
        final int DEFAULT_MAX = 10;
        int max;
        System.out.println("Enter the maximum value to test for primes"
                + "\nIt should be an integer value greater than or equal to 2.");
        try
        {
            max = keyboard.nextInt();
            if (max < 2)
            {
                System.out.println(max + " is smaller than 2. Will use " + DEFAULT_MAX + " as the default value.");
                max = DEFAULT_MAX;
            }
        } catch (NumberFormatException e)
        {
            System.out.println("Could not convert input to an integer.");
            System.out.println(e.getMessage());
            System.out.println("Will use " + DEFAULT_MAX + " as the default value.");
            max = DEFAULT_MAX;
        } catch (Exception e)
        {
            System.out.println("There was an error with System.in");
            System.out.println(e.getMessage());
            System.out.println("Will use " + DEFAULT_MAX + " as the default value.");
            max = DEFAULT_MAX;
        }

        System.out.println("\n ====> Constructing list of candidates up to " + max);

        candidates = new ArrayList<>();
        for (int i = 2; i <= max; i++)
            candidates.add(new Integer(i));

        System.out.println("The candidates list is " + candidates);

        PrimesAndComposites pac = new PrimesAndComposites(candidates);
        pac.display();
    }
}