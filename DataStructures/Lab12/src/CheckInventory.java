import java.util.*;
import java.io.*;

/**
 * @author atb
 * @version 4/22/2019
 * @modifiedBy Matt Brierley
 */
public class CheckInventory
{
    // TODO Project 2 Part1 - implement CardID class - done
    // TODO Project 2 Part1 - implement CheckInventory class - done
    private DictionaryInterface<CarID, Integer> hashDictionary;
    private final int DEFAULT_CAPACITY = 5;
    private Random random;

    public CheckInventory()
    {
        createHashedDictionary();
        this.random = new Random(101);
    }

    public void createHashedDictionary()
    {
        this.hashDictionary = new HashedDictionary<>(DEFAULT_CAPACITY);
    }

    public boolean compareInventory(String file1, String file2)
    {
        //process file1
        try
        {
            Scanner file = new Scanner (new File (file1));

            //while not at the end of the file
            while (file.hasNext())
            {
                // read a line
                try
                {
                    CarID currentID = new CarID(file.next(),file.nextLong());
                    //if key already exists, increment its value, else add it
                    if(this.hashDictionary.contains(currentID))
                    {
                        this.hashDictionary.add(currentID, this.hashDictionary.getValue(currentID) + 1);
                    }
                    else
                    {
                        this.hashDictionary.add(currentID, 1);
                    }
                }
                catch ( NumberFormatException nfe )
                {
                    System.out.println( "Error in input file ignoring" );
                }
            }

            file.close( );
            System.out.printf("The content of the hash table after file %s was processed:\n", file1);
            this.hashDictionary.displayHashTable();
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println("Unable to find " + file1);
        }


        //process file2
        try
        {
            //process file2
            Scanner file = new Scanner (new File (file2));

            //while not at the end of the file
            while (file.hasNext())
            {
                // read a line
                try
                {
                    //if ID is already in the hashTable, decrement its value, or remove is value is now 0
                    CarID currentID = new CarID(file.next(),file.nextLong());
                    if(this.hashDictionary.contains(currentID))
                    {
                        if(this.hashDictionary.getValue(currentID) - 1 <= 0)
                        {
                            this.hashDictionary.remove(currentID);
                        }
                        else
                        {
                            this.hashDictionary.add(currentID, this.hashDictionary.getValue(currentID) - 1);
                        }
                    }
                    //else add element that isn't already in the table?
                    else
                    {
                        //this.hashDictionary.add(currentID, 1);
                    }
                }
                catch ( NumberFormatException nfe )
                {
                    System.out.println("Error in input file - NFE");
                }
                catch ( NoSuchElementException nsee )
                {
                    //System.out.println("Error in input file - NSEE");
                }
            }

            file.close( );
            System.out.printf("The content of the hash table after file %s was processed:\n", file2);
            this.hashDictionary.displayHashTable();
        }
        catch (FileNotFoundException fnfe)
        {
            System.out.println("Unable to find " + file2);
        }


        return this.hashDictionary.isEmpty();
    }

    public TreeSet<CarID> generateContentAndSaveToRandomFile(int numEntries, String fileName)
    {
        final int ASCII_Z = 90;
        final int ASCII_A = 65;
        TreeSet<CarID> ids = new TreeSet<>();

        try
        {
            PrintWriter file = new PrintWriter(new File(fileName));

            while(ids.size() < numEntries)
            {
                String cSeq = "";
                long nSeq = 0;

                for(int i = 0; i < CarID.CHARACTER_SEQUENCE_LENGTH; i++)
                {
                    cSeq += (char)(random.nextInt(ASCII_Z - ASCII_A) + ASCII_A);
                }

                //magic numbers for testing
                nSeq = 10000000000000L + (long)(random.nextFloat() * 100000000000000L);

                CarID newID = new CarID(cSeq,nSeq);
                if(ids.add(newID))
                {
                    file.write(newID.getCharacterSequence() + " " + newID.getNumericSequence());
                    file.println();
                }

                //System.out.println("Adding " + newID.toString());
            }
            file.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Failed to open file");
        }

        return ids;
    }

    public void saveSortedContentToSortedFile(TreeSet<CarID> idTreeSet, String fileName)
    {
        try
        {
            PrintWriter file = new PrintWriter(new File(fileName));

            for(CarID id : idTreeSet)
            {
                file.write(id.getCharacterSequence() + " " + id.getNumericSequence());
                file.println();
            }

            file.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Failed to open file");
        }
    }

    public void createCorruptedFile(TreeSet<CarID> idTreeSet, String fileName)
    {
        try
        {
            PrintWriter file = new PrintWriter(new File(fileName));

            for(CarID id : idTreeSet)
            {
                if(random.nextInt(10) %  2 == 0)
                {
                    file.write(id.getCharacterSequence());
                }

                if(random.nextInt(10) %  2 == 0)
                {
                    file.write(" " + id.getNumericSequence());
                }

                file.println();
            }

            file.close();
        }
        catch (IOException ioe)
        {
            System.out.println("Failed to open file");
        }
    }

    // uncomment main when the class CardID and
    // the skeleton for this class are in place


    public static void main(String[] args)
    {
        String receivedFile = "randomFile.txt";
        String sentFile = "sortedFile.txt";
        String corruptedFile = "corruptedFile.txt";
        CheckInventory checker = new CheckInventory();

        try
        {
            System.out.println("How many CarIDs to generate?");
            Scanner keyboard = new Scanner(System.in);
            int amount = keyboard.nextInt();
            TreeSet<CarID> sortedSet = checker.generateContentAndSaveToRandomFile(amount, receivedFile);
            checker.saveSortedContentToSortedFile(sortedSet, sentFile);
            checker.createCorruptedFile(sortedSet, corruptedFile);
            System.out.println("\n*** Checking if \"" + sentFile + "\" and \"" + receivedFile + "\" have the same elements ***");
            boolean same = checker.compareInventory(receivedFile, sentFile);
            System.out.println("--> the elements in files \"" + receivedFile
                    + "\" and \"" + sentFile
                    + " are " + (same ? "" : "NOT ") + "the same");


            System.out.println("\n*** Checking if \"" + sentFile + "\" and \"" + corruptedFile + "\" have the same elements ***");
            checker.createHashedDictionary();
            same = checker.compareInventory(sentFile, corruptedFile);
            System.out.println("--> the elements in files \"" + corruptedFile
                    + "\" and \"" + sentFile
                    + " are " + (same ? "" : "NOT ") + "the same");

            //Can't compile without this commented out, probably something I'm missing?
        /*} catch (IOException ioe)
        {
            System.out.println("There was an error in reading or opening the file: ");
            System.out.println(ioe.getMessage());
        */} catch (InputMismatchException ime)
        {
            System.out.println(ime.getMessage());
        }
        System.out.println("\nBye!");
    }  // end main
}
