import java.text.DecimalFormat;
import java.util.*;

/**
 * A class that implements math operations utilizing a stack.
 *
 * @author Matt Brierley
 * @version 02/16/2019
 */
public class FunWithStack
{
    public void decimalToBinary()
    {
        System.out.println("DECIMAL TO BINARY CONVERTER");
        // TODO PROJECT #1 - done
        Scanner keyboard = new Scanner(System.in);
        Stack<Integer> stack = new Stack<>();
        try
        {
            do
            {
                System.out.println("\nPlease enter a positive integer, or type \"stop\"");
                int decimalNumber = keyboard.nextInt();

                System.out.print(decimalNumber + " in binary is --> ");

                // YOUR CODE GOES HERE
                while(decimalNumber != 0)
                {
                    stack.push(decimalNumber%2);
                    decimalNumber = decimalNumber/2;
                }

                while(!stack.isEmpty())
                {
                    System.out.print(stack.pop());
                }

                System.out.println();
            } while (true);
        }
        catch (InputMismatchException ime)
        {
            System.out.println("Done with conversion.\n");
        }
    }

    public void ancientMultiplier()
    {
        // TODO PROJECT #1 - done
        // http://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication
        Stack<Integer> op1 = new Stack<>();
        Stack<Integer> op2 = new Stack<>();
        Scanner keyboard = new Scanner(System.in);

        try
        {
            do
            {
                //get operands from user
                System.out.println("Please enter operand1, or type \"stop\"");
                int operand1 = keyboard.nextInt();
                System.out.println("Please enter operand2");
                int operand2 = keyboard.nextInt();

                //determine and print smaller/larger value
                int temp = (operand1 > operand2 ? operand1 : operand2);
                operand1 = (operand1 < operand2 ? operand1 : operand2);
                operand2 = temp;
                System.out.printf("The smaller operand is: %d; and the larger operand is: %d\n", operand1, operand2);

                //build and print mapping table
                System.out.print("--> Creating the mapping table:\n");
                for(int powOfTwo = 1; powOfTwo <= (operand1); powOfTwo*=2)
                {
                    /*
                    op1.push(powOfTwo);
                    op2.push(powOfTwo * operand2);
                    System.out.printf("%d ->> %d\n", op1.peek(), op2.peek());
                    */
                    System.out.printf("%d ->> %d\n", op1.push(powOfTwo), op2.push(powOfTwo * operand2));
                }

                //calculate and print result; currently changes the value of operand1, but it is not used after this point
                //attempts to subtract remaining highest power of two (op1) from remaining value of operand1 and add
                //corresponding op2 value to result; else just pops both op1 and op2
                System.out.printf("\n--> Calculating the result\n%d * %d is: ", operand2, operand1);
                int result = 0;
                while(operand1 > 0)
                {
                    if(op1.peek() <= operand1)
                    {
                        operand1 -= op1.pop();

                        System.out.print(op2.peek() + (operand1 > 0 ? " + " : " "));
                        result += op2.pop();
                    }
                    else
                    {
                        op1.pop();
                        op2.pop();
                    }
                }
                System.out.printf("= %d\n\n", result);
            } while(true);
        }
        catch (InputMismatchException ime)
        {
            System.out.print("Done multiplying\n");
        }
    }

    public ArrayList<Integer> noAdjacentDuplicates(int... input)
    {
        // TODO PROJECT #1 - done
        ArrayList<Integer> result = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();

        System.out.println("input = " + Arrays.toString(input));

        boolean duplicate = false;
        int index = 0;

        //iterate through input array
        while(index < input.length)
        {
            //if stack is not empty, compare index to top of stack
            if(!stack.isEmpty() && stack.peek() == input[index])
            {
                //if duplicate, set duplicate flag to true and move to next index
                duplicate = true;
                index++;
            }
            else
            {
                //check bool, if true (but no longer true) pop top of stack and do not increment index
                if(duplicate)
                {
                    stack.pop();
                }
                //if not duplicate, push to stack and increment index
                else
                {
                    stack.push(input[index]);
                    index++;
                }
                duplicate = false;
            }
        }

        //final pop of last element if bool is still true after exiting loop
        if(duplicate)
        {
            stack.pop();
        }

        //pop stack to arraylist
        while(!stack.isEmpty())
        {
            result.add(0,stack.pop());
        }

        return result;
    }


    public static void main(String[] args)
    {
        FunWithStack funWithStack = new FunWithStack();
        System.out.println("*** DECIMAL TO BINARY CONVERTER ***");
        funWithStack.decimalToBinary();
        System.out.println("*** ANCIENT MULTIPLIER ***");
        funWithStack.ancientMultiplier();

        System.out.println("*** ELIMINATING ADJACENT DUPLICATES ***");

        System.out.println("--> testcase #1");
        ArrayList<Integer> result = funWithStack.noAdjacentDuplicates(1, 5, 6, 8, 8, 8, 0, 1, 1, 0, 6, 5);
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        if (result.equals(expected))
            System.out.println("result = " + result + " CORRECT");
        else
        {
            System.out.println("INCORRECT, expected: " + expected);
            System.out.println("got: " + result);
        }

        System.out.println("\n---> testcase #2");
        result = funWithStack.noAdjacentDuplicates(1, 9, 6, 8, 8, 8, 0, 1, 1, 0, 6, 5);
        expected.clear();
        expected.add(1);
        expected.add(9);
        expected.add(5);
        if (result.equals(expected))
            System.out.println("result = " + result + " CORRECT");
        else
        {
            System.out.println("INCORRECT, expected: " + expected);
            System.out.println("got: " + result);
        }

        System.out.println("\n---> testcase #3");
        result = funWithStack.noAdjacentDuplicates(1, 1, 6, 8, 8, 8, 0, 1, 1, 0, 6, 5);
        expected.clear();
        expected.add(5);
        if (result.equals(expected))
            System.out.println("result = " + result + " CORRECT");
        else
        {
            System.out.println("INCORRECT, expected: " + expected);
            System.out.println("got: " + result);
        }

        System.out.println("\n---> testcase #4");
        result = funWithStack.noAdjacentDuplicates(1, 1, 1, 5, 6, 8, 8, 8, 0, 1, 1, 0, 6, 5);
        expected.clear();
        if (result.equals(expected))
            System.out.println("result = " + result + " CORRECT");
        else
        {
            System.out.println("INCORRECT, expected: " + expected);
            System.out.println("got: " + result);
        }

        System.out.println("Done!");
    }
}