package Lab06;

import java.util.*;

/**
 * This class creates all permutations of the given string
 *
 * @author Matt Brierley
 * @version 3/2/2019
 */
public class UniqueAnagrams
{
    private ArrayList<String> anagrams;

    public UniqueAnagrams()
    {
        this.anagrams = new ArrayList<>();
    }

    public void permutations(String word)
    {
        permutations("", word);
        System.out.println("Possible anagrams = " + this.anagrams);

        TreeSet<String> toTest = new TreeSet(this.anagrams);
        System.out.println("Expected unique and sorted anagrams = " + toTest);
        System.out.println();

        sort(); // duplicates will be removed during the sorting process
    }

    private void permutations(String prefix, String suffix)
    {
        int numOfChars = suffix.length();
        if (numOfChars == 1)
        {
            //System.out.println(prefix + suffix);
            this.anagrams.add(prefix + suffix);
        }
        else
        {
            //TODO Project2 - done
            for(int i = 0; i < numOfChars; i++)
            {
                //recursive call passing each char appended to prefix and suffix with same char removed
                //System.out.println(prefix + suffix.charAt(i) + " " + suffix.substring(0,i) + suffix.substring(i+1,numOfChars));
                permutations(prefix + suffix.charAt(i), suffix.substring(0,i) + suffix.substring(i+1,numOfChars));
            }
        }
    }

    private void sort()
    {
        //TODO Project2 - done

        // calls getIndexOfSmallestAndRemoveDuplicates(index, this.anagrams.size());
        // calls swap(index, indexOfNextSmallest);

        for (int index = 0; index < this.anagrams.size() - 1; index++)
        {
            int indexOfNextSmallest = getIndexOfSmallestAndRemoveDuplicates(index, this.anagrams.size() - 1);
            swap(index, indexOfNextSmallest);
        }
    }

    private int getIndexOfSmallestAndRemoveDuplicates(int first, int last)
    {
        //TODO Project2 - done

        String min = this.anagrams.get(last);
        int indexOfMin = last;

        //loop last to first to avoid indexoutofbounds and concurrentmodification exceptions
        for(int index1 = last; index1 >= first; index1--)
        {
            for (int index2 = index1 - 1; index2 >= first; index2--)
            {
                //System.out.println("Comparing " + index1 + " with " + index2);

                //remove duplicates and shift indexes
                if (this.anagrams.get(index2).compareTo(this.anagrams.get(index1)) == 0) {
                    //System.out.println("Removing " + index2);
                    this.anagrams.remove(index2);
                    index1--;
                    indexOfMin--;
                }
            }

            //after removals, check for new min, only needs to be done once per outer loop
            if (this.anagrams.get(index1).compareTo(min) < 0)
            {
                min = this.anagrams.get(index1);
                indexOfMin = index1;
            }
        }
        return indexOfMin;
    }

    private void swap(int i, int j)
    {
        //TODO Project2 - done
        //get and set, dont use add and remove

        if (i != j)
        {
            String temp = this.anagrams.get(i);
            this.anagrams.set(i, this.anagrams.get(j));
            this.anagrams.set(j, temp);
        }
    }

    private void display()
    {
        System.out.println("Computed unique and sorted anagrams = " + this.anagrams);
    }

    public static void main(String[] args)
    {
        UniqueAnagrams uniqueAnagrams = new UniqueAnagrams();
        Scanner keyboard = new Scanner(System.in);

        System.out.println("Please enter a word");
        String word = keyboard.next();

        uniqueAnagrams.permutations(word);
        uniqueAnagrams.display();
    }
}
