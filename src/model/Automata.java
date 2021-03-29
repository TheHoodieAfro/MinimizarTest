package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import structures.AdjacencyListGraph;

/**
 * @author Cristian Sanchez P. - Juan Pablo Herrera
 * This class models an automata
 * @param <V> Abstract data type that represents a state of the automata
 * @param <S> Abstract data type that represents a stimulus of the automata
 * @param <R> Abstract data type that represents a response of the automata
 */
public abstract class Automata<V, S, R> extends AdjacencyListGraph<V>{
	
	/**
	 * Initial state
	 */
	private final V v0;
	
	/**
	 * Stimulus set 
	 */
	private final HashSet<S> stimulus;
	
	/**
	 * Response set 
	 */
	private final HashSet<R> response;
	
	/**
	 * Transition function
	 */
	private final HashMap<V, HashMap<S, V>> F;
	
	/** Constructor for the Automata class
	 * Makes the graph directed
	 * @param v0 initial state
	 */
	public Automata(V v0) {
		super(true);
		this.v0 = v0;
		stimulus = new HashSet<>();
		response = new HashSet<>();
		F = new HashMap<>();
		addVertex(v0);
	}
	
	/** getter for v0
	 * @return initial state
	 */
	public V getV0() {
		return v0;
	}

	/** getter for stimulus set
	 * @return stimulus set
	 */
	public HashSet<S> getStimulus() {
		return stimulus;
	}

	/** getter for response set
	 * @return response set
	 */
	public HashSet<R> getResponse() {
		return response;
	}

	/** getter for transition function
	 * @return transition function
	 */
	public HashMap<V, HashMap<S, V>> getF() {
		return F;
	}

	/** Connects two states through a stimulus
	 * @param u Start state
	 * @param v End state
	 * @param s Stimulus
	 * @return Boolean indicating is the states where connected
	 */
	public boolean connect(V u, V v, S s) {
		if(!F.containsKey(u)) {
			F.put(u, new HashMap<>());
		}
		if(!F.get(u).containsKey(s) && searchVertex(u) && searchVertex(v) && s != null) {
			super.addEdge(u, v);
			F.get(u).put(s, v);
			stimulus.add(s);
			return true;
		}
		return false;
	}
	
	/** Use of the transition function to get the obtained state from a state given a stimulus
	 * @param u State
	 * @param s Stimulus
	 * @return State obtained state from a state given a stimulus
	 */
	public V transition(V u, S s) {
		if(F.containsKey(u)) {
			return F.get(u).get(s);
		}
		return null;
	}
	
	/** Abstract method to minimize a machine
	 * @return Minimized machine
	 */
	public abstract Automata<V, S, R> minimize();
	
	/** Partition method shared between Mealy and Moore machines
	 * @param partitions
	 * @return Partitioned sets of states
	 */
	public ArrayList<ArrayList<V>> partition2_3(ArrayList<ArrayList<V>> partitions) {
		
		ArrayList<ArrayList<V>> prev;
		boolean end = false;
		
		while (!end) {
			prev = partitions;
			
			for (S s : getStimulus()) {
				HashMap<Integer, HashMap<Integer, ArrayList<V>>> original = new HashMap<>();
				int count = 0;
				
				for (int i = 0; i < partitions.size(); i++) {
					HashMap<Integer, ArrayList<V>> result = refine(partitions, i, s);
					original.put(i, result);
					count += result.size();
				}
				
				if (count > partitions.size()) {
					partitions = new ArrayList<>();
					
					for (int i = 0; i < original.size(); i++) {
						partitions.addAll(original.get(i).values());
					}
					
					break;
				}
			}
			end = prev.size() == partitions.size();
		}
		return partitions;
	}
	
	/** Refining method to partition state sets
	 * @param partitions
	 * @param set specified
	 * @param s stimulus
	 * @return Refined set
	 */
	public HashMap<Integer, ArrayList<V>> refine(ArrayList<ArrayList<V>> partitions, int set, S s) {
		
		ArrayList<V> partition = partitions.get(set);
		HashMap<Integer, ArrayList<V>> refinedPartition = new HashMap<>();
		
		for (V u : partition) {
			V v = transition(u, s);
			boolean exists = false;
			int j;
			
			for (j = 0; j < partitions.size() && !exists; j++) {
				ArrayList<V> actualPartition = partitions.get(j);
				exists = actualPartition.contains(v);
			}
			
			if (!refinedPartition.containsKey(j)) {
				refinedPartition.put(j, new ArrayList<>());
			}
			
			refinedPartition.get(j).add(u);
		}
		return refinedPartition;
	}
	
}
