package model;

import java.util.ArrayList;
import java.util.HashMap;
import structures.Vertex;

/**
 * @author Cristian Sanchez P. - Juan Pablo Herrera
 * This class models a Mealy machine
 * @param <V> Abstract data type that represents a state of the machine
 * @param <S> Abstract data type that represents a stimulus of the machine
 * @param <R> Abstract data type that represents a response of the machine
 */
public class Mealy<V, S, R> extends Automata<V, S, R> {
	
	/**
	 * HashMap of the response for each state and its stimulus
	 */
	private final HashMap<V, HashMap<S, R>> response;

	/** Constructor for the Moore class
	 * @param v0 Initial state
	 */
	public Mealy(V v0) {
		super(v0);
		response = new HashMap<>();
	}
	
	/** Adds a state v to the machine
	 * @param v new state to add
	 * @return Boolean indicating is the state was added
	 */
	public boolean addState(V v) {
		if(v != null) {
			return addVertex(v);
		}
		return false;
	}
	
	/** Gives the response for a specified state and stimulus
	 * @param v specified state
	 * @param s specified stimulus
	 * @return response of the state and stimulus
	 */
	public R response(V v, S s) {
		if(response.containsKey(v)) {
			return response.get(v).get(s);
		}
		return null;
	}
	
	/** Connects two states through a stimulus and gives a response
	 * @param u Start state
	 * @param v End state
	 * @param s Stimulus
	 * @param r Response
	 * @return Boolean indicating if the states where connected
	 */
	public boolean connect(V u, V v, S s, R r) {
		
		boolean connect = super.connect(u, v, s);
		
		if(connect) {
			if(!response.containsKey(u)) {
				response.put(u, new HashMap<>());
			}
			response.get(u).put(s, r);
			getResponse().add(r);
		}
		
		return connect;
	}
	
	/** Partitions the states of the machine
	 * @return ArrayList of ArrayLists that represent the different partitions
	 */
	private ArrayList<ArrayList<V>> partition() {
		
		ArrayList<ArrayList<V>> partitions = new ArrayList<>();
		HashMap<ArrayList<R>, Integer> responsesI = new HashMap<>();
		ArrayList<V> states = getVerticesArray();
        
		int i = 0;
        
		for (V v : states) {
			if(getVertexColor(v) == Vertex.Color.RED) {
				continue;
			}
            
			ArrayList<R> responsesV = new ArrayList<>();
            
			for(S s : getStimulus()) {
				responsesV.add(response(v, s));
			}
            
			if (!responsesI.containsKey(responsesV)) {
				responsesI.put(responsesV, i);
				partitions.add(new ArrayList<>());
				i++;
			}
            
			partitions.get(responsesI.get(responsesV)).add(v);
		}
        
		return  super.partition2_3(partitions);
	}

	/** Minimizes the Mealy machine using the partitioning method
	 * @return Minimized version of the Mealy machine
	 */
	@Override
	public Mealy<V, S, R> minimize() {
		
		BFS(getV0());
		
		ArrayList<ArrayList<V>> partitions = partition();
		V state = partitions.get(0).get(0);
		Mealy<V, S, R> mini = new Mealy<>(state);
		
		for (int i = 1; i < partitions.size(); i++) {
			state = partitions.get(i).get(0);
			mini.addState(state);
		}
		
		for (V v : mini.getVerticesArray()) {
			for (S s : getStimulus()) {
				
				V u = transition(v, s);
				R r = response(v, s);
				boolean connected = false;
				
				for (int i = 0; i < partitions.size() && !connected; i++) {
					
					ArrayList<V> minis = partitions.get(i);
					
					if (minis.contains(u)) {
						mini.connect(v, minis.get(0), s, r);
						connected = true;
					}
				}
			}
		}
		
		return mini;
	}

}
