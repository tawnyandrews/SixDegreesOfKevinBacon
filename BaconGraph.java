import java.util.Queue;
import java.util.LinkedList;

public class BaconGraph {
	//this class checks for neighbors between actors & movies
	class Neighbor {
		// initialize variables
		int vertexNum;
		Neighbor next;

		//Neighbor constructor
		public Neighbor(int num, Neighbor neighbor) {
			vertexNum = num;
			next = neighbor;
		}
	}
	class Vertex {
		// initialize variables
		String actor; //this is the name of the actor
		Neighbor connectedNeighbors; //this is a list of the connected neighbors
		Boolean visited; //this will check if the actor has been visited & the boolean will act like a flag

		// constructor
		public Vertex(String actorVertex, Neighbor neighbors) {
			actor = actorVertex; // actor is the Vertex in graph
			connectedNeighbors = neighbors;
			visited = false;
		}
	}

	Vertex[] adjList;
	private int numActors;

	// this initializes the adjacency list
	public BaconGraph(int x) {
		adjList = new Vertex[x];
		numActors = 0;
	}

	// this returns the vertex of the actor based on the given actor's vertex
	public int getVertex(Vertex actor) {
		for (int i = 0; i < numActors; i++) {
			if (adjList[i].equals(actor))
				return i;
		}
		return -1;
	}

	// retrieves the vertex with actor string
	public int getVertex(String actor) {
		for (int i = 0; i < numActors; i++) {
			if (adjList[i].actor.equals(actor))
				return i;
		}
		return -1;

	}

	// this makes a connection between 2 actors on the list based on which movies they've starred in
	public void createEdge(String actor1, String actor2, String movie) {
		int i;
		int j;
		i = getVertex(actor1);
		j = getVertex(actor2);
		if(i == -1) {
			adjList[numActors] = new Vertex(actor1, null);
			i = numActors;
			numActors++;
		}
		if(j == -1) {
			adjList[numActors] = new Vertex(actor2, null);
			j = numActors;
			numActors++;
		}
		adjList[i].adjList = new Neighbor(j, adjList[i].adjList);
		adjList[j].adjList = new Neighbor(i, adjList[j].adjList);
	}

	// breadth first search
	public int BFS(String actor1, String actor2) {
		// this declares a queue that links the vertices together in the Linked List
		Queue<Vertex> vertexQueue = new LinkedList<Vertex>();
		int verticeCount = 0;
		verticeCount = getVertex(actor1);
		if(verticeCount != -1)
			vertexQueue.add(adjList[verticeCount]);
		else
			return -1;
		if(getVertex(actor2) == -1)
			return -1;

		while(!vertexQueue.isEmpty()){
			Vertex act1 = vertexQueue.poll();
			verticeCount = getVertex(act1);
			adjList[verticeCount].visited = true;

			for(Neighbor neighbor = act1.adjList; neighbor!=null; neighbor=neighbor.next) {

				if(!adjList[neighbor.vertexNum].visited){
					vertexQueue.add(adjList[neighbor.vertexNum]);
					adjList[neighbor.vertexNum].visited = true;
				}
				if(adjList[neighbor.vertexNum].actor.equals(actor2))
					return neighbor.vertexNum;
			}
		}
		return -1;
	}

}
