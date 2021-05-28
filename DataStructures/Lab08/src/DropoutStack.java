import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * A class of stacks whose entries are stored in a deque.
 * @author Matt Brierley
 * @version 3/12/2019
 */
public class DropoutStack<T>
{
    // TODO Project 1 - done
    // IMPLEMENT ALL THE METHODS DEFINED IN THE UML DIAGRAM
    //       push method should utilize removeLast()
    // UNCOMMENT THE display and main METHODS WHEN READY FOR TESTING

    private ArrayDeque<T> stack;
    private final int capacity;
    private static final int DEFAULT_CAPACITY = 20;

    public DropoutStack()
    {
        this(DEFAULT_CAPACITY);
    }

    public DropoutStack(int cap)
    {
        this.capacity = cap;
        stack = new ArrayDeque<>(this.capacity);
    }

    void push(T newEntry)
    {
        if(this.stack.size() == capacity)
        {
            this.stack.removeLast();
        }
        this.stack.push(newEntry);
    }

    public T peek()
    {
        return this.stack.peek();
    }

    public T pop()
    {
        return this.stack.isEmpty() ? null : this.stack.pop();
    }

    public boolean isEmpty()
    {
        return this.stack.isEmpty();
    }

    public void clear()
    {
        this.stack.clear();
    }


    /**
     * METHOD display implemented for debugging purposes
     */
    public void display()
    {
        if (isEmpty())
            System.out.println("The stack is empty");
        else
            System.out.println(Arrays.toString(this.stack.toArray()));
    }

    public static void main(String args[])
    {
        System.out.println("**************  TESTING DROPOUT STACK  **************\n");
        DropoutStack<Integer> dropoutStack = new DropoutStack<Integer>();

        System.out.println("---->  Adding 20 items to empty stack of capacity of 20");
        for (int i = 0; i < 20; i++)
        {
            dropoutStack.push(i);
        }
        System.out.println("---->  The content of the stack is:");
        dropoutStack.display();
        System.out.println("----> The top of the stack is: " + dropoutStack.peek());
        System.out.println("\n----> Adding 5 more items to full stack");
        for (int i = 20; i < 25; i++)
        {
            System.out.println("push " + i);
            dropoutStack.push(i);
        }

        System.out.println("---->  The content of the stack is:");
        dropoutStack.display();
        System.out.println("----> The top of the stack is: " + dropoutStack.peek());

        System.out.println("\n---->  Removing all elements from the stack:");
        while (!dropoutStack.isEmpty())
        {
            System.out.println("----> pop " + dropoutStack.pop());
        }

        dropoutStack.display();
        System.out.println("----> The top of the stack is: " + dropoutStack.peek());

        System.out.println("\n----> Trying to pop from the empty stack");
        System.out.println("----> Got back " + dropoutStack.pop());

        System.out.println("\n----> Trying to peek at the top of the empty stack");
        System.out.println("----> Got back " + dropoutStack.peek());

        System.out.println("\n----> Adding 22 items to empty stack of capacity of 20");
        for (int i = 0; i < 22; i++)
        {
            dropoutStack.push(i);
        }
        System.out.println("---->  The content of the stack is:");
        dropoutStack.display();

        System.out.println("\n---->  Clearing the stack with the clear method");
        dropoutStack.clear();
        System.out.println("---->  The content of the stack is:");
        dropoutStack.display();
    }
}