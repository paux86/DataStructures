/**
 * @author Matt Brierley
 * @version 3/14/2019
 */
public class CircularArrayQueue<T> implements QueueInterface<T>
{
    private T[] queue; // Circular array of queue entries and one unused location
    private int frontIndex; // Index of front entry
    private int backIndex;  // Index of back entry
    private boolean initialized = false;
    private static final int DEFAULT_CAPACITY = 3;
    private static final int MAX_CAPACITY = 10000;

    public CircularArrayQueue()
    {
        // The cast is safe because the new array contains null entries
        @SuppressWarnings("unchecked")
        T[] tempQueue = (T[]) new Object[DEFAULT_CAPACITY + 1];
        this.queue = tempQueue;
        this.frontIndex = 0;
        this.backIndex = DEFAULT_CAPACITY;
        this.initialized = true;
    } // end constructor

    public CircularArrayQueue(T[] initialContent)
    {
        // TODO Project 2A - done
        T[] tempQueue = (T[]) new Object[initialContent.length + 1];
        this.queue = tempQueue;
        this.frontIndex = 0;
        this.backIndex = initialContent.length;
        this.initialized = true;

        for(T current : initialContent)
        {
            enqueue(current);
        }
    } // end constructor

    public void enqueue(T newEntry)
    {
        //System.out.println("enqueue(" + newEntry + ")");               // ***TESTING
        // TODO Project 2A - done
        checkInitialization();
        ensureCapacity();

        this.backIndex = (this.backIndex + 1) % this.queue.length;
        this.queue[this.backIndex] = newEntry;
        //System.out.println("queue[" + backIndex + "] = " + newEntry);  // ***TESTING
    } // end enqueue

    public T getFront() throws EmptyQueueException
    {
        // TODO Project 2A - done
        checkInitialization();

        if(isEmpty())
        {
            throw new EmptyQueueException("Queue is empty");
        }
        else
        {
            return this.queue[this.frontIndex];
        }
    } // end getFront

    public T dequeue() throws EmptyQueueException
    {
        // TODO Project 2A - done
        checkInitialization();

        if(isEmpty())
        {
            throw new EmptyQueueException("Queue is empty");
        }
        else
        {
            T temp = this.queue[this.frontIndex];
            this.queue[this.frontIndex] = null;
            this.frontIndex = (this.frontIndex + 1) % this.queue.length;

            return temp;
        }
    } // end dequeue

    public boolean isEmpty()
    {
        // TODO Project 2A - done
        return (this.frontIndex == (this.backIndex + 1) % this.queue.length);
    } // end isEmpty

    public void clear()
    {
        // null out only the used slots
        // TODO Project 2A - done
        for(int i = this.frontIndex; i <= this.backIndex; i++)
        {
            this.queue[i] = null;
        }

        this.frontIndex = 0;
        this.backIndex = 0;
    } // end clear


    // Throws an exception if this object is not initialized.
    private void checkInitialization()
    {
        if (!this.initialized)
            throw new SecurityException("CircularArrayQueue object is not initialized properly.");
    } // end checkInitialization

    // Throws an exception if the client requests a capacity that is too large.
    private void checkCapacity(int capacity)
    {
        if (capacity > MAX_CAPACITY)
            throw new IllegalStateException("Attempt to create a queue " +
                    "whose capacity exceeds " +
                    "allowed maximum.");
    } // end checkCapacity

    // Doubles the size of the array queue if it is full.
    // Precondition: checkInitialization has been called.
    private void ensureCapacity()
    {
        // TODO Project 2A - done
        //if array is full

        if(this.frontIndex == (this.backIndex + 2) % this.queue.length)
        {
            int newCapacity = this.queue.length * 2;
            checkCapacity(newCapacity);

            T[] tempQueue = (T[]) new Object[newCapacity];
            System.arraycopy(this.queue,0,tempQueue,0,this.queue.length);
            this.queue = tempQueue;
        }
    } // end ensureCapacity
}
