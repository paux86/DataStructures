/**
 * @author Matt Brierley
 * @version 4/13/2019
 */

import java.util.*;

public class BingoGame
{
    public final static int NUMBER_OF_CHIPS = 75;
    private int numberOfPlayers;
    private ArrayList<BingoChip> bingoDrum;
    private Player[] players;

    public BingoGame(int numOfPlayers)
    {
        // TODO Project 2.4 - done
        setNumOfPlayers(numOfPlayers);
        createBingoDrum();
        createPlayers();
    }

    private void createBingoDrum()
    {
        // TODO Project 2.4 - done
        this.bingoDrum = new ArrayList<>();

        for(int i = 1; i <= NUMBER_OF_CHIPS; i++)
        {
            this.bingoDrum.add(new BingoChip(i));
        }
    }

    private void createPlayers()
    {
        // TODO Project 2.4 - done
        this.players = new Player[this.numberOfPlayers];

        for(int i = 0; i < this.numberOfPlayers; i++)
        {
            System.out.printf("---> Creating bingo card for Player %d\n", i+1);
            this.players[i] = new Player();
        }
    }

    private void setNumOfPlayers(int numOfPlayers)
    {
        // TODO Project 2.4 - done
        this.numberOfPlayers = numOfPlayers;
    }

    public int getNumberOfPulledChips()
    {
        // TODO Project 2.4 - done
        return NUMBER_OF_CHIPS - this.bingoDrum.size();
    }

    public BingoChip pullChip()
    {
        // TODO Project 2.4 - done
        Random random = new Random();
        return this.bingoDrum.remove(random.nextInt(this.bingoDrum.size()));
    }

    public void play()
    {
        // TODO Project 2.4 - done
        boolean winner = false;

        //loop until there is a winner
        while(!winner && !this.bingoDrum.isEmpty())
        {
            BingoChip pulledChip = pullChip();
            System.out.println("---> Calling: " + pulledChip);

            for(int i = 0; i < this.numberOfPlayers; i++)
            {
                System.out.printf("Player %d's card:\n", i+1);
                this.players[i].checkCard(pulledChip);
                this.players[i].printCard();

                if(this.players[i].isWinner())
                {
                    winner = true;
                }
            }
        }

        //report final results
        for(int i = 0; i < this.numberOfPlayers; i++)
        {
            if(this.players[i].isWinner())
            {
                System.out.printf("!!! Player %d says BINGO !!!\n", i+1);
            }
        }
        System.out.println(getNumberOfPulledChips() + " chips were pulled");
    }
}