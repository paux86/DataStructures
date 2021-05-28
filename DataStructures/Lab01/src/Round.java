/**
 * A client class that implements a piggy bank functionality.
 *
 * @author Matt Brierley
 * @version 1/29/2019
 */
public class Round
{
    private PiggyBank myPiggyBank;

    /**
     * Round constructor creates PiggyBank object and displays the content of the piggy bank.
     * The Round object is created in the main.
     */
    public Round(int numberOfMonies, int capacity)
    {

        //TODO Project3 - done
        this.myPiggyBank = new PiggyBank(numberOfMonies, capacity);
        System.out.println("\n>> The contents of your piggy bank <<\n" + this.myPiggyBank.toString());
    }

    /**
     * addTwoMonies method creates one Coin object and one Bill object and attempts to add them to the piggy bank.
     * Since the piggy bank may be full, the addTwoMonies method must handle PiggyBankFullException
     */
    public void addTwoMonies()
    {
        //TODO Project3 - done
        try
        {
            System.out.println("\n--> Adding additional monies:");
            Money newCoin = new Coin();
            Money newBill = new Bill();

            this.myPiggyBank.add(newCoin);
            this.myPiggyBank.add(newBill);
        }
        catch(PiggyBankFullException pbfe)
        {
            System.out.println(pbfe);
        }
    }


    /**
     * run method calls addTwoMonies method; shakes the piggy bank; prints its content; asks the piggy bank
     * to emptyAndCountHeads and returns the number of heads.
     */
    public int run()
    {
        //TODO Project3
        addTwoMonies();
        this.myPiggyBank.shake();
        System.out.println(this.myPiggyBank.toString());

        return this.myPiggyBank.emptyAndCountHeads();
    }

 }