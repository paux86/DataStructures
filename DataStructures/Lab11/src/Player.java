/**
 * @author Matt Brierley
 * @version 4/13/2019
 */
import java.util.TreeSet;

public class Player
{
    private TreeSet<Character> bingoChars;
    private BingoCard bingoCard;

    public Player()
    {
        // TODO 2.3 - done
        this.bingoCard = new BingoCard();
        printCard();
        this.bingoChars = new TreeSet<>();
    }

    public boolean isWinner()
    {
    	// TODO 2.3 - done
        //System.out.println("bingoChars.size = " + bingoChars.size());
        return bingoChars.size() == BingoCard.BINGO_KEYS.length();
    }

    public void checkCard(BingoChip chip)
    {
        // TODO 2.3 - done
        if(this.bingoCard.hasNumber(chip))
        {
            this.bingoChars.add(chip.getLetter());
        }
    }

    public void printCard()
    {
        // TODO 2.3 - done
        System.out.println(this.bingoCard.toString());
    }

}
