import java.util.*;

/**
 * @author Matt Brierley
 * @version 5/3/2019
 */

public class UndirectedGraph<T> extends DirectedGraph<T> implements ConnectedGraphInterface<T>, java.io.Serializable
{
    public UndirectedGraph()
    {
        super();
    }

    public boolean addEdge(T begin, T end, double edgeWeight)
    {
        return (super.addEdge(begin,end,edgeWeight) && super.addEdge(end,begin,edgeWeight));
    }

    public boolean addEdge(T begin, T end)
    {
        return (super.addEdge(begin,end) && super.addEdge(end,begin));
    }

    public int getNumberOfEdges()
    {
        return super.getNumberOfEdges() / 2;
    }

    public Stack<T> getTopologicalOrder()
    {
        throw new UnsupportedOperationException("Topological sort illegal in an undirected graph");
    }

    public boolean isConnected(T origin)
    {
        //graph is connected if BFS reaches all vertices
        //if BFS reaches all vertices, then the size of the Queue returned by getBFS should be equal to the number of vertices
        return super.getBreadthFirstTraversal(origin).size() == super.getNumberOfVertices();
    }
}
