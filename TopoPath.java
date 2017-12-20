// Jonathon Sauers
// jo046326
// COP 3503, Fall 2017
// TopoPath.java

import java.util.*;
import java.io.*;

public class TopoPath
{
  // Determines if a given graph has a topological path.
  public static boolean hasTopoPath(String filename) throws IOException
  {
    boolean [][] matrix;

    Scanner in = new Scanner(new File(filename));
    int N = in.nextInt();

    // Sets the dimensions of the 2D matrix.
    matrix = new boolean[N][N];

    // Fills in the adjacency matrix for the given graph.
    for(int i = 0; i < N; i++)
    {
      int temp = in.nextInt();

			for(int j = 0; j < temp; j++)
      {
        int value = in.nextInt();
				matrix[i][value - 1] = true;
      }
    }

    in.close();

  	int [] incoming = new int[matrix.length];
  	int cnt = 0, previousNode = 0;

  	// Counts the number of incoming edges incident to each vertex.
  	for (int i = 0; i < matrix.length; i++)
  		for (int j = 0; j < matrix.length; j++)
  			incoming[j] += (matrix[i][j] ? 1 : 0);

  	Queue<Integer> q = new ArrayDeque<Integer>();

  	// Any vertex with zero incoming edges is added to the queue.
  	for (int i = 0; i < matrix.length; i++)
  		if (incoming[i] == 0)
  			q.add(i);

  	while (!q.isEmpty())
  	{
  		// Pull a vertex out of the queue and add it to the topological sort.
  		int node = q.remove();

      // Checks if the previous node and the new node are opposite, which
      // is a fail state. (i.e. there is no valid path that is also a valid topo sort).
  		if(cnt > 0 && !matrix[previousNode][node])
  			return false;

  		previousNode = node;

      // Counts the vertices.
  		++cnt;

  		// All vertices we can reach via an edge from the current vertex should
  		// have their incoming edge counts decremented. If one of these hits
  		// zero, add it to the queue, as it's ready to be included in our
  		// topological sort.
  		for (int i = 0; i < matrix.length; i++)
  			if (matrix[node][i] && --incoming[i] == 0)
  				q.add(i);
  	}

  	// If we pass out of the loop without including each vertex in our
  	// topological sort, we must have a cycle in the graph.
  	if (cnt != matrix.length)
  		return false;

  	return true;
  }

  // How difficult I found this assignment.
  public static double difficultyRating()
  {
    return 2.0;
  }

  // How many hours I spent on this assignment.
  public static double hoursSpent()
  {
    return 4.0;
  }
}
