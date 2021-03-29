package structures;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class AdjacencyListGraphTest {
	
	private AdjacencyListGraph<Integer> graph;
	private ArrayList<Edge<Integer>> edges;
	
	private void setupStageDirected() {
		graph = new AdjacencyListGraph<>(true);
		edges = new ArrayList<>();
	}
	
	private void setupStageVertices() {
		for(int i=0; i<5; i++) {
			graph.addVertex(i+1);
		}
		edges = new ArrayList<>();
	}

	@Test
	void insertSearchVertexTest() {
		
		setupStageDirected();
		assertTrue(graph.isEmpty(), "Graph should be empty");
		
		int c = 0;
		
		for(int i=0; i<50; i++) {
			
			int x = (int)(Math.random()*100);
			
			if(graph.addVertex(x)) {
				c++;
			}
			
			assertTrue(graph.searchVertex(x), "The vertex x"+c+" was not found");
			assertTrue(graph.getVertexSize() == c, "The amount of vertices dont add up");
			assertTrue(graph.getAdjacent(x).isEmpty(), "The vertex x"+c+" should not have edges");
			assertFalse(graph.isEmpty(), "The graph shoould not be empty");
			
		}
		
	}
	
	@Test
	void addEdgeTest() {
		
		setupStageDirected();
		assertTrue(graph.isEmpty(), "Graph should be empty");
		setupStageVertices();
		
		int u = 1;
		int v = 2;
		edges.add(new Edge<Integer>(u, v));
		graph.addEdge(u, v);
		
		u = 2;
		v = 1;
		edges.add(new Edge<Integer>(u, v));
		graph.addEdge(u, v);
		
		u = 2;
		v = 3;
		edges.add(new Edge<Integer>(u, v));
		graph.addEdge(u, v);
		
		u = 3;
		v = 4;
		edges.add(new Edge<Integer>(u, v));
		graph.addEdge(u, v);
		
		u = 4;
		v = 2;
		edges.add(new Edge<Integer>(u, v));
		graph.addEdge(u, v);
		
		for(Edge<Integer> x : edges) {
			assertTrue(graph.areConnected(x.getU(), x.getV()), "An edge was not added");
		}
		
	}
	
	@Test
	void removeEdgeTest() {
		
		addEdgeTest();
		
		graph.removeEdge(4, 2);
		assertFalse(graph.areConnected(4, 2), "The edge should be removed");
		
		graph.removeEdge(3, 4);
		assertFalse(graph.areConnected(3, 4), "The edge should be removed");
		
	}

}
