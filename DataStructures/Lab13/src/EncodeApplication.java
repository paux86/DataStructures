import java.io.File;
import java.util.*;


/**
 * A class that implements text encoding using Huffman compression algorithm
 *
 * @author Matt Brierley
 * @version 4/28/2019
 */
public class EncodeApplication
{
    //  contains all the lines included in the given text file
    private Message myMessage;
    //  contains the myMessage encoded with Huffman code
    private Code myCode;
    //  contains Huffman trees for each unique character in myMessage
    private HuffmanTree[] myTrees;
    //  initialized to the number of unique characters in the message
    private int numberOfTrees;
    //   allowing ASCII character set
    private final int MAX_NUMBER_OF_CHARS = 128;

    /**
     * Constructor for objects of class EncodeApplication
     */
    public EncodeApplication()
    {
        this.myMessage = new Message();
        this.myCode = new Code();
        this.myTrees = null;
        this.numberOfTrees = 0;
    }


    /**
     * count the number of times each character appears in the message.
     *
     * @return an array with the count for the number of times each character occurs.
     */
    public int[] getCounts()
    {
        // TODO Project 4 - done
        int[] result = new int[MAX_NUMBER_OF_CHARS + 1];
        this.numberOfTrees = 0;

        //build result[] from all characters in message
        while(this.myMessage.hasNext())
        {
            int index = this.myMessage.next();

            result[index] += 1;
        }

        //get all unique characters from result, and calc/print totals
        for(int i = 0; i < result.length; i++)
        {
            //for each unique character, increment numberOfTrees, and output values
            if(result[i] > 0)
            {
                this.numberOfTrees++;
                System.out.printf("---> Count of character %c is %d\n", i, result[i]);
            }

        }
        System.out.println();

        return result;
    }

    /**
     * create the initial forrest of trees.
     *
     * @param count the frequency of each character
     *              to be encoded - essentially the array returned by getCounts method.
     * @return the forest of trees for each single letter
     */
    public HuffmanTree[] createInitialTrees(int[] count)
    {
        // TODO Project 4 - done
        System.out.println("Creating " + this.numberOfTrees + " initial trees");
        HuffmanTree[] result = new HuffmanTree[this.numberOfTrees];
        int cIndex = 0;

        for(int rIndex = 0; rIndex < result.length; rIndex++)
        {
            //find next count index with non-zero data
            while(cIndex < count.length && count[cIndex] <= 0)
            {
                cIndex++;
            }

            List<Character> symbols = new ArrayList<>();
            symbols.add((char) cIndex);
            //System.out.println("cIndex: " + cIndex + " ToChar: " + (char) cIndex);
            //System.out.println("Adding symbols: " + symbols);
            result[rIndex] = new HuffmanTree(new HuffmanCode(symbols, count[cIndex]));
            cIndex++;
        }

        return result;
    }


    /**
     * Creates final Huffman Tree by combing two "smallest trees" at a time.
     * The smallest tree is the tree with the smallest frequency
     * Utilizes findSmallest and swap methods
     * Operates on the forest of trees contained in the variable myTrees.
     */
    public void createHuffmanTree()
    {
        // TODO Project 4 - done
        while (this.numberOfTrees > 1)
        {
            // swap smallest to the end
            //find smallest doesnt appear to actually check the last element, despite the description (loops while < last)
            swap(findSmallest(this.numberOfTrees), this.numberOfTrees - 1);

            System.out.println("--->Smallest tree moved to the position " + (this.numberOfTrees - 1));

            // swap second smallest to the end
            swap(findSmallest(this.numberOfTrees - 1), this.numberOfTrees - 2);

            System.out.println("--->Second smallest tree moved to the position " + (this.numberOfTrees - 2));

            /*
            System.out.println("Post swap trees:");
            for(int i = 0; i < this.numberOfTrees; i++)
            {
                this.myTrees[i].preOrderTraverse();
                System.out.println();
            }
            */

            // Construct a new combined tree and put in place of the second to last tree
            List<Character> symbols = new ArrayList<>();
            symbols.addAll((this.myTrees[this.numberOfTrees - 1].getRootData().getSymbols()));
            symbols.addAll((this.myTrees[this.numberOfTrees - 2].getRootData().getSymbols()));
            int combinedCount = this.myTrees[this.numberOfTrees - 1].getRootData().getFrequency()
                    + this.myTrees[this.numberOfTrees - 2].getRootData().getFrequency();
            HuffmanTree combined = new HuffmanTree(new HuffmanCode(symbols, combinedCount), this.myTrees[this.numberOfTrees - 1], this.myTrees[this.numberOfTrees - 2]);
            this.myTrees[this.numberOfTrees - 2] = combined;

            System.out.println("--->Combined tree created: " + symbols + " -> " + combinedCount);

            System.out.println("--->Combined tree added at position " + (this.numberOfTrees - 2));
            this.numberOfTrees--;
        }
    }

    /**
     * swaps two elements in myTrees
     *
     * @param index1
     * @param index2
     */
    private void swap(int index1, int index2)
    {
        HuffmanTree temp = this.myTrees[index1];
        this.myTrees[index1] = this.myTrees[index2];
        this.myTrees[index2] = temp;
    }

    /**
     * finds the Huffman tree with the smallest frequency
     *
     * @param last - the index of the last tree to check
     * @return - the index of the "smallest" tree in the given range
     */
    private int findSmallest(int last)
    {
        int smallestAt = 0;
        for (int i = 1; i < last; i++)
        {
            if ((this.myTrees[smallestAt].getRootData()).compareTo(this.myTrees[i].getRootData()) > 0)
            {
                smallestAt = i;
            }
        }
        return smallestAt;
    }

    /**
     * encode a single symbol using a Huffman tree and append
     * the Huffman code to myCode  object
     *
     * @param c the symbol to be encoded.
     */
    public void encodeCharacter(Character c)
    {
        // TODO Project 4 - done
        // make sure that we point to the root
        // at this point we only have one tree
        this.myTrees[0].reset();

        while(!this.myTrees[0].isSingleSymbol())
        {
            //if symbol is in left tree
            if(this.myTrees[0].checkLeft(c))
            {
                this.myCode.addCharacter('0');
                this.myTrees[0].advanceLeft();
            }
            //else if symbol is in right tree
            else
            {
                this.myCode.addCharacter('1');
                this.myTrees[0].advanceRight();
            }
        }
        this.myCode.addCharacter(' ');
    }

    /**
     * encodes myMessage using the generated Huffman Code by calling
     * the encodeCharacter method for each character in myMessage.
     */
    private void encodeMessage()
    {
        this.myMessage.reset();
        while (this.myMessage.hasNext())
        {
            //fill myCode with the code message
            encodeCharacter(this.myMessage.next());
        }
    }

    /**
     * load the words into the Message
     *
     * @param theFileName the name of the file holding the message
     */
    public boolean loadMessage(String theFileName)
    {
        Scanner input;
        boolean loaded = false;
        try
        {
            input = new Scanner(new File(theFileName));

            while (input.hasNextLine())  // read until  end of file
            {
                this.myMessage.addLine(input.nextLine());
            }
            System.out.println(this.myMessage);
            loaded = true;
        } catch (Exception e)
        {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }
        return loaded;
    }

    public void run()
    {
        if (loadMessage("message4.txt"))
        {
            this.myTrees = createInitialTrees(getCounts());
            this.numberOfTrees = this.myTrees.length;

            System.out.println("Building Huffman Tree");
            createHuffmanTree();

            //the tree is created so let's display it using preOrder
            System.out.println("\nHuffman Tree:");
            this.myTrees[0].preOrderTraverse();
            System.out.println();
            //we can encode now
            encodeMessage();
            System.out.println(this.myCode);
        }
    }


    public static void main(String[] args)
    {
        EncodeApplication encodeApp = new EncodeApplication();
        encodeApp.run();
    }
}