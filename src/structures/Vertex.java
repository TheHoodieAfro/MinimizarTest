package structures;

/** Edge of a Graph
 * @author Cristian Sanchez P. - Juan Pablo Herrera
 * @param <V> Abstract data type which represents an object from a natural problem that is going to be modeled as a vertex in a graph representation of the problem
 */
public class Vertex<V> implements Comparable<Vertex<V>>{
	
	/**
	 * Enum that group the states of a vertex
	 */
	public static enum Color{RED, WHITE, BLACK}
	
	/**
	 * Data to be saved as a vertex
	 */
	private V data;
	
	/**
	 * State of the vertex
	 */
	private Color color;
	
	/**
	 * Previous vertex
	 */
	private Vertex<V> prev;
	
	/**
	 * Distance between previous vertex and this one
	 */
	private int dist;
	
	/** Constructor for the Vertex
	 * Sets color as red
	 * Sets previous vertex as null
	 * @param data
	 */
	public Vertex(V data) {
		this.data = data;
		color = Color.RED;
		setPrev(null);
	}

	/**
	 * @return the data
	 */
	public V getData() {
		return data;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the prev
	 */
	public Vertex<V> getPrev() {
		return prev;
	}

	/**
	 * @return the dist
	 */
	public int getDist() {
		return dist;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(V data) {
		this.data = data;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @param prev the prev to set
	 */
	public void setPrev(Vertex<V> prev) {
		this.prev = prev;
	}

	/**
	 * @param dist the dist to set
	 */
	public void setDist(int dist) {
		this.dist = dist;
	}

	/** Compares two objects, this one and another from parameter
	 * @param o Object
	 * @return Comparison between this object and o
	 */
	@Override
	public int compareTo(Vertex<V> o) {
		return this.dist-o.dist;
	}
	
	/** Know if the objects are equal
	 * @return Boolean indicating if the objects are equal
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		Vertex<V> x = (Vertex<V>)obj;
		return x.data.equals(data);
	}
	
	

}
