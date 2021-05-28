import java.util.Comparator;

/**
 * @author Matt Brierley
 * @version 4/22/2019
 */

public class CarID implements Comparable<CarID>
{
    private String characterSequence;
    private long numericSequence;
    public static final int CHARACTER_SEQUENCE_LENGTH = 3;
    public static final int NUMERIC_SEQUENCE_LENGTH = 14;
    private final String DEFAULT_CHARACTER_SEQUENCE = "???";
    private final long DEFAULT_NUMERIC_SEQUENCE = 10000000000000L;


    public CarID(String cSeq, long nSeq)
    {
        //init to default, then attempt to set
        this.characterSequence = DEFAULT_CHARACTER_SEQUENCE;
        this.numericSequence = DEFAULT_NUMERIC_SEQUENCE;
        setCharacterSequence(cSeq);
        setNumericSequence(nSeq);
    }

    public void setCharacterSequence(String cSeq)
    {
        if(cSeq.length() == CHARACTER_SEQUENCE_LENGTH)
        {
            this.characterSequence = cSeq;
        }
        else
        {
            System.out.println("Invalid Character Sequence length");
        }
    }

    public void setNumericSequence(long nSeq)
    {
        if((int)(Math.log10(nSeq)+1) == NUMERIC_SEQUENCE_LENGTH)
        {
            this.numericSequence = nSeq;
        }
        else
        {
            System.out.println("Invalid Numeric Sequence length");
        }
    }

    public String getCharacterSequence() { return this.characterSequence; }

    public long getNumericSequence() { return  this.numericSequence; }

    public boolean equals(Object o)
    {
        if(o == this)
        {
            return true;
        }

        if(!(o instanceof CarID))
        {
            return false;
        }

        return (this.characterSequence.equals(((CarID) o).characterSequence) && this.numericSequence == ((CarID) o).numericSequence);
    }

    public int compareTo(CarID other)
    {
        //use String compare to for string, if same then compare numbers and return difference
        int comparison = this.characterSequence.compareTo(other.getCharacterSequence());

        if(comparison == 0)
        {
            comparison = (int)(this.numericSequence - other.getNumericSequence());
        }

        return comparison;
    }

    // TODO - done
    public int hashCode()
    {
        final int G = 31;

        return (G * (this.characterSequence.hashCode()) + (int)(this.numericSequence ^ (this.numericSequence >> 32)));
    }

    public String toString()
    {
        return ("CarID(" + this.characterSequence + " " + this.numericSequence + ")");
    }
}
