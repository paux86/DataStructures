/**
 * @author Matt Brierley
 * @version 4/13/2019
 */

import java.util.*;

public class BingoCard
{
    private HashMap<Character, TreeSet<Integer>> card;
    public final static String BINGO_KEYS = "BINGO";
    public final static int MAX_VALUES_PER_LETTER = 15;
    public final static int NUMBERS_PER_LETTER = 5;

    public BingoCard()
    {
        // TODO Project 2.2 - done
        this.card = new HashMap<>();
        Random random = new Random();

        for(int i = 0; i < BINGO_KEYS.length(); i++)
        {
            char key = BINGO_KEYS.charAt(i);
            this.card.put(key, new TreeSet<>());

            while(this.card.get(key).size() < NUMBERS_PER_LETTER)
            {
                this.card.get(key).add(random.nextInt(MAX_VALUES_PER_LETTER) + 1 + (i * MAX_VALUES_PER_LETTER));
            }
        }
    }

    public boolean hasNumber(BingoChip chip)
    {
        // TODO Project 2.2 - done
        boolean hasN = this.card.get(chip.getLetter()).contains(chip.getNumber());

        //if true, add marker (0)
        if(hasN)
        {
            this.card.get(chip.getLetter()).add(0);
        }

        return hasN;
    }

    public String toString()
    {
        // TODO Project 2.2 - done

        // utilize StringBuffer and String.format
        // utilize forEach lambda construct to process a row
        StringBuffer output = new StringBuffer();

        for(int i = 0; i < BINGO_KEYS.length(); i++)
        {
            char currentChar = BINGO_KEYS.charAt(i);
            TreeSet<Integer> val = this.card.get(currentChar);
            output.append(currentChar);
            val.forEach(item -> output.append(String.format("%3s",item)));
            output.append("\n");
        }

        /*this.card.forEach((k,v) ->
        {
            output.append(String.format("%3s",k));
            v.forEach(item -> output.append(String.format("%3s",item)));
            output.append("\n");
        });*/
        return output.toString();
    }

    public boolean equals(Object o)
    {
        // TODO Project 2.2 - done
        //if same object, return true
        if(o == this)
        {
            return true;
        }

        //if not same class, return false
        if(!(o instanceof BingoCard))
        {
            return false;
        }

        BingoCard other = (BingoCard) o;
        return this.card.equals(other.card);
    }
}