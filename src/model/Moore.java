package model;

import java.util.ArrayList;
import java.util.HashMap;
import structures.Vertex;

/**
 * @author Cristian Sanchez P. - Juan Pablo Herrera
 * This class models a Moore machine
 * @param <V> Abstract data type that represents a state of the machine
 * @param <S> Abstract data type that represents a stimulus of the machine
 * @param <R> Abstract data type that represents a response of the machine
 */
public class Moore<V, S, R> extends Automata<V, S, R> {
	
	/**
	 * HashMap of the response for each state
	 */
	private final HashMap<V, R> response;

	/** Constructor for the Moore class
	 * @param v0 Initial state
	 * @param r response of the initial state
	 */
	public Moore(V v0, R r) {
		super(v0);
		response = new HashMap<>();
		addState(v0, r);
	}
	
	/** Adds a state v to the machine
	 * @param v new state to add
	 * @param r response of the new state
	 * @return Boolean indicating is the state was added
	 */
	public boolean addState(V v, R r) {
		if(v != null && !response.containsKey(v)) {
			response.put(v, r);
			addVertex(v);
			getResponse().add(r);
			return true;
		}
		return false;
	}
	
	/** Gives the response for a specified state
	 * @param v specified state
	 * @return response of the state
	 */
	public R response(V v) {
		return response.get(v);
	}
	
	/** Partitions the states of the machine
	 * @return ArrayList of ArrayLists that represent the different partitions
	 */
	private ArrayList<ArrayList<V>> partition() {
		
		ArrayList<ArrayList<V>> partitions = new ArrayList<>();
		HashMap<R, Integer> responsesI = new HashMap<>();
		ArrayList<V> states = getVerticesArray();
        
		int i = 0;
        
		for (V v : states) {
			if(getVertexColor(v) == Vertex.Color.RED) {
				continue;
			}
			
			R r = response(v);
            
			if (!responsesI.containsKey(r)) {
				responsesI.put(r, i);
				partitions.add(new ArrayList<>());
				i++;
			}
            
			partitions.get(responsesI.get(r)).add(v);
		}
        
		return  super.partition2_3(partitions);
	}

	/** Minimizes the Moore machine using the partitioning method
	 * @return Minimized version of the Moore machine
	 */
	@Override
	public Moore<V, S, R> minimize() {
		
		BFS(getV0());
		
		ArrayList<ArrayList<V>> partitions = partition();
		V state = partitions.get(0).get(0);
		Moore<V, S, R> mini = new Moore<>(state, response(state));
		
		for (int i = 1; i < partitions.size(); i++) {
			state = partitions.get(i).get(0);
			mini.addState(state, response(state));
		}
		
		for (V v : mini.getVerticesArray()) {
			for (S s : getStimulus()) {
				
				V u = transition(v, s);
				boolean connected = false;
				
				for (int i = 0; i < partitions.size() && !connected; i++) {
					
					ArrayList<V> minis = partitions.get(i);
					
					if (minis.contains(u)) {
						mini.connect(v, minis.get(0), s);
						connected = true;
					}
				}
			}
		}
		
		return mini;
		
	}

}
