import java.util.*;

/**
 * A class that implements solutions to several problems using hashing
 *
 *
 * @author Matt Brierley
 * @version 4/18/2019
 */
public class SolveWithHashing
{
    private DictionaryInterface<Integer, Integer> hashedDictionary;
    private final int DEFAULT_CAPACITY = 5;

    public SolveWithHashing()
    {
        createHashedDictionary();
    }

    public void createHashedDictionary()
    {
        this.hashedDictionary = new HashedDictionary<>(DEFAULT_CAPACITY);
    }

    private void testDisplayHashTable()
    {
        System.out.println("displaying empty dictionary");
        this.hashedDictionary.displayHashTable();

        this.hashedDictionary.add(1, 1);
        this.hashedDictionary.add(7, 7);
        System.out.println("displaying dictionary after 2 entries have been added");
        this.hashedDictionary.displayHashTable();

        this.hashedDictionary.add(13, 13);
        this.hashedDictionary.add(17, 17);
        this.hashedDictionary.add(8, 8);
        System.out.println("displaying dictionary after 3 additional entries have been added");
        this.hashedDictionary.displayHashTable();

        this.hashedDictionary.remove(1);
        this.hashedDictionary.remove(17);
        System.out.println("displaying dictionary after 2 entries have been removed");
        this.hashedDictionary.displayHashTable();
    }

    public Integer getFirstRepeatedElement(int[] a)
    {
        // TODO Project1 #1 - done
        System.out.println("The content of the hash table for array: " + Arrays.toString(a));

        //add elements to hashedDictionary
        for(int i = 0; i < a.length; i++)
        {
            //if already in the dictionary, negate key value if not already negative
            if(this.hashedDictionary.getValue(a[i]) != null)
            {
                int value = this.hashedDictionary.getValue(a[i]);
                if(value > 0)
                {
                    value = value * -1;
                }
                this.hashedDictionary.add(a[i],value);
            }
            //if not in the dictionary, add it with 1 base index
            else
            {
                this.hashedDictionary.add(a[i],i+1);
            }
        }

        this.hashedDictionary.displayHashTable();

        Iterator<Integer> it = this.hashedDictionary.getValueIterator();

        Integer firstRepeated = null;
        while(it.hasNext())
        {
            Integer current = it.next();
            if(current < 0 && (firstRepeated == null || firstRepeated < current))
            {
                firstRepeated = current;
            }
        }

        return (firstRepeated == null ? null : a[firstRepeated * (-1) - 1]);
    }

    public boolean lookForPair(int[] a, int[] b, int k)
    {
        // TODO Project1 #2 - done
        //add contents of a to dictionary
        System.out.printf("toPutInHashTables = %s\n", Arrays.toString(a));
        for(int i : a)
        {
            this.hashedDictionary.add(i,i);
        }

        //while pair not found, search for pair
        //k-b[i]
        System.out.printf("toCheck = %s\n", Arrays.toString(b));

        boolean found = false;
        int index = 0;
        while(!found && index < b.length)
        {
            found = this.hashedDictionary.contains(k-b[index]);

            if(found)
            {
                System.out.printf("The pair {%d,%d} adds to %d\n",k-b[index],b[index],k);
            }
            else
            {
                index++;
            }
        }

        return found;
    }

    public static void main(String[] args)
    {
        ArrayList<int[]> toCheck = new ArrayList<>();
        toCheck.add(new int[]{9, 3, 5, 1, 2, 2, 5, 3});
        toCheck.add(new int[]{6, 6, 3, 2, 1, 2, 2, 3});
        toCheck.add(new int[]{2, 1, 6, 2, 3, 2, 3, 6});
        toCheck.add(new int[]{3, 2, 1, 2, 2, 3, 6, 6});
        toCheck.add(new int[]{9, 3, 4, 4, 4, 1, 2, 2, 5, 3});
        toCheck.add(new int[]{3, 3, 3, 3, 3, 3, 3});
        toCheck.add(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        toCheck.add(new int[]{8, 1, 2, 3, 4, 5, 6, 7});

        SolveWithHashing solver = new SolveWithHashing();

        System.out.println("\n\t*** Testing displayHashTable ***");
        solver.testDisplayHashTable();

        System.out.println("\n\t*** Find The First Element With Duplicate ***");
        Integer firstDuplicate;
        for (int[] array : toCheck)
        {
            solver.createHashedDictionary();
            firstDuplicate = solver.getFirstRepeatedElement(array);
            if (firstDuplicate != null)
                System.out.println("--> the first element that is repeated is: " + firstDuplicate);
            else
                System.out.println("--> duplicates not found");
            System.out.println();
        }

        System.out.println("\n\t*** Check If There Exists A Pair Of Elements That Add Up To k ***");
        boolean found;
        for (int k = 2; k < 10; k++)
        {
            System.out.println("k = " + k);
            for (int i = 1; i < toCheck.size(); i++)
            {
                solver.createHashedDictionary();
                found = solver.lookForPair(toCheck.get(i - 1), toCheck.get(i), k);
                System.out.println("--> pair that add up to " + k + (found ? "" : " NOT") + " found.");
            }
            System.out.println();
        }
        System.out.println("\nBye!");
    }  // end main
}
