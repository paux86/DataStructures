import java.util.*;

/**
 * A class that checks connectivity in a directed unweighted graph
 * The graph is represented by adjacency matrix
 *
 * @author atb
 * @version 4/30/2019
 */
public class StrongWeakDisjoint
{
    private int[][] adjMatrixDirected;
    private int[][] adjMatrixUndirected;
    private int[][] reachabilityMatrix;
    private int numberOfNodes;

    public StrongWeakDisjoint(int numberOfNodes, int[][] graph)
    {
        this.numberOfNodes = numberOfNodes;
        this.adjMatrixDirected = new int[numberOfNodes][numberOfNodes];
        this.reachabilityMatrix = new int[this.numberOfNodes][this.numberOfNodes];

        for (int row = 0; row < this.numberOfNodes; row++)
            for (int col = 0; col < this.numberOfNodes; col++)
            {
                this.adjMatrixDirected[row][col] = graph[row][col];
                this.reachabilityMatrix[row][col] = this.adjMatrixDirected[row][col];
            }
        buildReachabilityMatrix();
    }

    private void buildReachabilityMatrix()
    {
        //TODO Lab14b #2.1 - done
        //check each row
        for(int i = 0; i < this.numberOfNodes; i++)
        {
            //if something is changed to 1, make sure newly included row is also checked
            boolean changed;
            do
            {
                changed = false;

                //check each column
                for(int j = 0; j < this.numberOfNodes; j++)
                {
                    if(this.reachabilityMatrix[i][j] == 1)
                    {
                        for(int k = 0; k < this.numberOfNodes; k++)
                        {
                            if(k != i && this.reachabilityMatrix[j][k] == 1)
                            {
                                if(this.reachabilityMatrix[i][k] == 0)
                                {
                                    this.reachabilityMatrix[i][k] = 1;
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }while(changed);
        }
    }

    public boolean isGraphStronglyConnected()
    {
        //TODO Lab14b #2.2 - done
        boolean stronglyConnected = true;
        for(int row = 0; stronglyConnected && row < this.numberOfNodes; row++)
        {
            for(int col = 0; stronglyConnected && col < this.numberOfNodes; col++)
            {
                if(row != col && this.reachabilityMatrix[row][col] != 1)
                {
                    stronglyConnected = false;
                }
            }
        }
        return stronglyConnected;
    }

    private boolean isUndirectedGraphConnected()
    {
        //TODO Lab14b #2.4 - done
        // utilizes breath-first-traversal algorithm - the same implementation as in Lab14b #1

        //each row is a vertex, and their values are the [col] vertices they connect to
        Queue<Integer> vertexQueue = new LinkedList<>();
        //array of rows that have been visited
        boolean[] visited = new boolean[this.numberOfNodes];
        int firstVertex = 0;
        vertexQueue.offer(firstVertex);
        visited[firstVertex] = true;

        while(!vertexQueue.isEmpty())
        {
            int curVertex = vertexQueue.poll();

            for(int col = 0; col < this.numberOfNodes; col++)
            {
                if(this.adjMatrixUndirected[curVertex][col] != 0 && !visited[col])
                {
                    visited[col] = true;
                    vertexQueue.offer(col);
                }
            }
        }

        boolean connected = true;
        for(int i = 1; connected && i < visited.length; i++)
        {
            if(!visited[i])
            {
                connected = false;
            }
        }

        return connected;
    }

    public boolean isGraphWeaklyConnected()
    {
        this.adjMatrixUndirected = new int[this.numberOfNodes][this.numberOfNodes];
        // TODO Lab14b #2.3 - done
        //  builds adjMatrixUndirected
        for(int row = 0; row < this.numberOfNodes; row++)
        {
            for(int col = 0; col < this.numberOfNodes; col++)
            {
                if(this.adjMatrixDirected[row][col] == 1)
                {
                    this.adjMatrixUndirected[row][col] = 1;
                    this.adjMatrixUndirected[col][row] = 1;
                }
            }
        }
        return isUndirectedGraphConnected();
    }

    public void displayGraph()
    {
        System.out.println("\n***** GRAPH TO CHECK *****");
        displayMatrix(this.adjMatrixDirected);
    }

    public void displayReachabilityMatrix()
    {
        System.out.println("\n***** REACHABILITY MATRIX *****");
        displayMatrix(this.reachabilityMatrix);
    }


    private void displayMatrix(int[][] matrix)
    {
        System.out.print("     ");
        for (int c = 0; c < this.numberOfNodes; c++)
        {
            System.out.printf("[%1$2d]", c);
        }
        System.out.println();
        for (int r = 0; r < this.numberOfNodes; r++)
        {
            System.out.printf("[%1$2d]", r);
            for (int c = 0; c < this.numberOfNodes; c++)
            {
                System.out.printf("%1$4d", matrix[r][c]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        System.out.println("\n*** Checking graphs' connectivity ***");
        int[][] matrix = new int[5][5]; // setting the matrix to the matrix shown in the project description
        matrix[0][1] = 1;
        matrix[0][3] = 1;
        matrix[1][2] = 1;
        matrix[1][3] = 1;
        matrix[3][4] = 1;
        matrix[4][0] = 1;
        matrix[4][3] = 1;
        StrongWeakDisjoint graph = new StrongWeakDisjoint(5, matrix);
        graph.displayGraph();
        graph.displayReachabilityMatrix();

        if (graph.isGraphStronglyConnected())
            System.out.println("-->The graph is strongly connected.\n\n");
        else if (graph.isGraphWeaklyConnected())
            System.out.println("-->The graph is weakly connected.\n\n");
        else
            System.out.println("-->The graph is disjoint.\n\n");

        matrix = new int[5][5];
        matrix[0][1] = 1;
        matrix[0][3] = 1;
        matrix[1][2] = 1;
        matrix[1][3] = 1;
        matrix[2][1] = 1;
        matrix[3][4] = 1;
        matrix[4][0] = 1;
        matrix[4][3] = 1;

        graph = new StrongWeakDisjoint(5, matrix);
        graph.displayGraph();
        graph.displayReachabilityMatrix();
        if (graph.isGraphStronglyConnected())
            System.out.println("-->The graph is strongly connected.\n\n");
        else if (graph.isGraphWeaklyConnected())
            System.out.println("-->The graph is weakly connected.\n\n");
        else
            System.out.println("-->The graph is disjoint.\n\n");


        matrix = new int[5][5];
        matrix[0][1] = 1;
        matrix[0][3] = 1;
        matrix[1][3] = 1;
        matrix[3][4] = 1;
        matrix[4][0] = 1;
        matrix[4][3] = 1;

        graph = new StrongWeakDisjoint(5, matrix);
        graph.displayGraph();
        graph.displayReachabilityMatrix();
        if (graph.isGraphStronglyConnected())
            System.out.println("-->The graph is strongly connected.\n\n");
        else if (graph.isGraphWeaklyConnected())
            System.out.println("-->The graph is weakly connected.\n\n");
        else
            System.out.println("-->The graph is disjoint.\n\n");
    } // end main
}
