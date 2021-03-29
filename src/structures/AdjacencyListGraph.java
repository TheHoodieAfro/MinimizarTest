package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import structures.Vertex.Color;

/**
 * This class models a graph using an Adjacency list
 * @author AED Class # 003 // 2019
 * @version 2.0 (Modified version for "Informatica Teorica") - 2020
 * @param <V> Abstract data type which represents an object from a natural problem that is going to be modeled as a vertex in a graph representation of the problem
 */
public class AdjacencyListGraph<V> implements IGraph<V>{
	
	/**
	 * Vertices of the graph
	 */
	private HashMap<V,Vertex<V>> vertices;	
	
	/**
	 * adjacencyList representation
	 */
	private HashMap<V, ArrayList<Edge<V>>> adjacencyLists;
	
	/**
	 * Type of graph
	 */
	private boolean isDirected;
	
	/**
	 * Last vertex
	 */
	private Vertex<V> last;
	
	/**
	 * Basic constructor that is initialized with default values
	 */
	public AdjacencyListGraph() {
		initialize();
	}

	/**
	 * Constructor that gets the value for "isDirected" attribute.
	 * True if the graph is Directed or false if it's Indirected
	 * @param id value to set "isDirected"
	 */
	public AdjacencyListGraph(boolean id) {
		initialize();
		isDirected = id;
	}
	
	/**
	 * Initializes all the data structures for this graph.
	 * Sets "isDirected" attribute in false
	 */
	private final void initialize() {
		isDirected = false;
		adjacencyLists = new HashMap<>();
		vertices = new HashMap<>();
	}
	
	/**
	 * Adds a vertex to the graph
	 * @param v The new vertex to be added
	 * @return True if was added and false if it was already in the graph
	 */
	@Override
	public boolean addVertex(V v) {
		if(!searchVertex(v)) {
			
			vertices.put(v, new Vertex<V>(v));
			adjacencyLists.put(v, new ArrayList<>());
			
			return true;
		}
		return false;
	}

	/**
	 * checks if a vertex is within the graph
	 * @param v Vertex to be searched
	 * @return True if found or false if not
	 */
	public boolean searchVertex(V v) {
		return vertices.containsKey(v);
	}

	/**
	 * Adds an edge to the graph
	 * If the graph is directed the connection will be from U to V
	 * <pre> U and V have to exist in the graph
	 * @param u a vertex within the graph
	 * @param v a vertex within the graph
	 */
	@Override
	public void addEdge(V u, V v) {
		
		addVertex(u);
		addVertex(v);
		Edge<V> edgeU = new Edge<V>(u, v);
		
		ArrayList<Edge<V>> edgesU = adjacencyLists.get(u);
		edgesU.remove(edgeU);
		edgesU.add(edgeU);
		
		if(!isDirected) {
			Edge<V> edgeV = new Edge<>(u, v);
			ArrayList<Edge<V>> edgesV = adjacencyLists.get(v);
			edgesV.remove(edgeV);
			edgesV.add(edgeV);
		}
		
	}

	/**
	 * Nah
	 */
	@Override
	public void addEdge(V u, V v, double w) {
		//Nah
	}

	/**
	 * Removes a vertex within the graph
	 * @param v A vertex to be removed of the graph
	 * @return True if the vertex was removed or false if the vertex didn't exist
	 */
	@Override
	public boolean removeVertex(V v) {
		
		if(searchVertex(v)) {
			
			vertices.remove(v);
			vertices.forEach((V u, Vertex<V> x) -> {
				ArrayList<Edge<V>> erase = new ArrayList<>();
				
				for(Edge<V> e : adjacencyLists.get(x)) {
					if(e.getV().equals(v)) {
						erase.add(e);
					}
				}
				adjacencyLists.get(u).removeAll(erase);
			});
			
			return true;
		}
		
		return false;
	}

	/**
	 * Removes an edge within the graph
	 * <pre> U and V are within the graph
	 * @param u A vertex connected with V
	 * @param v A vertex connected with U
	 * @return 
	 */
	@Override
	public boolean removeEdge(V u, V v) {
		
		Vertex<V> U = vertices.get(u);
		Vertex<V> V = vertices.get(v);
		
		if(U != null && V != null) {
			adjacencyLists.get(u).remove(new Edge<V>(U.getData(), V.getData()));
			
			if(!isDirected) {
				adjacencyLists.get(v).remove(new Edge<V>(V.getData(), U.getData()));
			}
			
			return true;
		}
		
		return false;
	}

	/**
	 * Gives a list of adjacent vertices of V
	 * <pre> V Is within the graph
	 * @param v The vertex to be consulted its adjacent vertices
	 * @return A list with all the adjacent vertices of V
	 */
	@Override
	public List<V> vertexAdjacent(V v) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Check if U and V are connected
	 * <pre> U and V are within the graph
	 * @param u Is a vertex
	 * @param v Is a vertex
	 * @return True if U and V are connected or false if they're not
	 */
	@Override
	public boolean areConnected(V u, V v) {
		
		if(searchVertex(u) && searchVertex(v)) {
			return adjacencyLists.get(u).contains(new Edge<V>(u, v));
		}
		
		return false;
	}

	/**
	 * <pre> The graph is weighted
	 * @return A matrix with the weight of all the connections
	 */
	@Override
	public double[][] weightMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @return True if the graph is directed or false if it isn't
	 */
	@Override
	public boolean isDirected() {
		// TODO Auto-generated method stub
		return isDirected;
	}
	
	/**
	 * @return HashMap of the vertices
	 */
	public HashMap<V, Vertex<V>> getVertices(){
		return vertices;		
	}
	
	/** Get the vertices as an ArrayList
	 * @return ArrayList of the vertices
	 */
	public ArrayList<V> getVerticesArray(){
		ArrayList<V> vertex = new ArrayList<>();
		vertices.forEach((V x, Vertex<V> y) -> vertex.add(x));
		return vertex;
	}
	
	/**
	 * @return HashMap of the adjacencyLists
	 */
	public HashMap<V, ArrayList<Edge<V>>> getAdjacencyList(){		
		return adjacencyLists;		
	}
	
	/** Gets all adjacent edges for a vertex
	 * @return ArrayList of edges for a vertex
	 */
	public ArrayList<V> getAdjacent(V v) {
		ArrayList<V> adjacent = new ArrayList<>();
		if(searchVertex(v)) {
			for(Edge<V> e : adjacencyLists.get(v)) {
				adjacent.add(e.getV());
			}
		}
		return adjacent;
	}
	
	/**
	 * @return Boolean, true if the graph is empty
	 */
	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	/** Retrieves the amount of vertices in the graph
	 * @return amount of vertices in the graph
	 */
	@Override
	public int getVertexSize() {
		return vertices.size();
	}
	
	/**
	 * @return 
	 */
	public Vertex<V> getLastVertex() {
		return last;
	}
	
	/** Gives the color of a vertex
	 * @param v Vertex
	 * @return Color of the vertex
	 */
	public Color getVertexColor(V v) {
		if(searchVertex(v)) {
			return vertices.get(v).getColor();
		}
		
		return null;
	}

	/** Breath First Search
	 * @param v Start vertex
	 * @return Boolean
	 */
	@Override
	public boolean BFS(V v) {
		
		if(searchVertex(v)) {
			
			Vertex<V> vertex = vertices.get(v);
			last = vertex;
			
			vertices.forEach((V c, Vertex<V> u) -> {
				u.setColor(Color.RED);
				u.setDist(Integer.MAX_VALUE);
				u.setPrev(null);
			});
			
			vertex.setColor(Color.WHITE);
			vertex.setDist(0);
			Queue<Vertex<V>> queue = new LinkedList<>();
			queue.add(vertex);
			
			try {
				while(!queue.isEmpty()) {
					
					Vertex<V> u = queue.remove();
					ArrayList<Edge<V>> adj = adjacencyLists.get(u.getData());
					
					for(Edge<V> ale: adj) {
						
						Vertex<V> x = vertices.get(ale.getV());
						
						if(x.getColor() == Color.RED) {
							x.setColor(Color.WHITE);
							x.setDist(u.getDist()+1);
							x.setPrev(u);
							queue.add(x);
						}
					}
					
					u.setColor(Color.BLACK);
				}
				
			} catch (Exception emptyQueueException) {
				
			}
			
			return true;
		}
		
		return false;
	}	
}
