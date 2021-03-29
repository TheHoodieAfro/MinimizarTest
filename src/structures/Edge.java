package structures;

/** Edge of a Graph
 * @author Cristian Sanchez P. - Juan Pablo Herrera
 * @param <V> Abstract data type which represents an object from a natural problem that is going to be modeled as a vertex in a graph representation of the problem
 */
public class Edge<V> implements Comparable<Edge<V>>{
	
	/**
	 * Start vertex
	 */
	private V u;
	
	/**
	 * End Vertex
	 */
	private V v;
	
	/** Constructor of the Edge
	 * @param u Start vertex
	 * @param v End Vertex
	 */
	public Edge(V u, V v) {
		this.u = u;
		this.v = v;
	}

	/** Getter for the start vertex
	 * @return start vertex
	 */
	public V getU() {
		return u;
	}

	/** Getter for the end vertex
	 * @return end vertex
	 */
	public V getV() {
		return v;
	}

	/** Setter for the start vertex
	 * @param u Start vertex
	 */
	public void setU(V u) {
		this.u = u;
	}

	/** Setter for the end vertex
	 * @param v End vertex
	 */
	public void setV(V v) {
		this.v = v;
	}
	
	/** Know if the objects are equal
	 * @return Boolean indicating if the objects are equal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		Edge<V> x = (Edge<V>)obj;
		return x.u.equals(u) && x.v.equals(v);
	}

	/**
	 * doesn't work lol
	 */
	@Override
	public int compareTo(Edge<V> o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
