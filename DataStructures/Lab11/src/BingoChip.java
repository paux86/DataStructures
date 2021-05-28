/**
 * @author Matt Brierley
 * @version 4/13/2019
 */

public class BingoChip
{
    private char letter;
    private int number;

    public BingoChip(int number)
    {
        setNumber(number);
        setLetter();
        //System.out.println(getNumber() + ":" + getLetter());
    }

    private void setNumber(int number)
    {
       // TODO Project 2.1 - done
        this.number = number;
    }

    private void setLetter()
    {
        // TODO Project 2.1 - done
        this.letter = BingoCard.BINGO_KEYS.charAt((this.number-1) / BingoCard.MAX_VALUES_PER_LETTER);
    }

    public int getNumber()
    {
        // TODO Project 2.1 - done
        return this.number;
    }

    public char getLetter()
    {
        // TODO Project 2.1 - done
        return letter;
    }

    public String toString()
    {
        // TODO Project 2.1 - done
        return this.letter + " " + this.number;
    }
}