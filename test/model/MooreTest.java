package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import structures.Vertex;

class MooreTest {
	
	private Moore<Character, Boolean, Boolean> moore;
	
	private void setupStageMoore() {
		
		moore = new Moore<>('A', false);
		moore.addState('B', false);
		moore.addState('C', false);
		moore.addState('D', false);
		moore.addState('E', false);
		moore.addState('F', false);
		moore.addState('G', false);
		moore.addState('H', false);
		moore.addState('I', true);
		moore.addState('J', false);
		moore.addState('K', true);
		
		moore.connect('A', 'B', false);
		moore.connect('A', 'A', true);
		moore.connect('B', 'C', false);
		moore.connect('B', 'D', true);
		moore.connect('C', 'E', false);
		moore.connect('C', 'C', true);
		moore.connect('D', 'F', false);
		moore.connect('D', 'B', true);
		moore.connect('E', 'G', false);
		moore.connect('E', 'E', true);
		moore.connect('F', 'H', false);
		moore.connect('F', 'F', true);
		moore.connect('G', 'I', false);
		moore.connect('G', 'G', true);
		moore.connect('H', 'J', false);
		moore.connect('H', 'H', true);
		moore.connect('I', 'A', false);
		moore.connect('I', 'K', true);
		moore.connect('J', 'K', false);
		moore.connect('J', 'J', true);
		moore.connect('K', 'A', false);
		moore.connect('K', 'K', true);
		
	}
	
	private void setupStageAddVertices() {
		
		moore = new Moore<>('A', false);
		
		for(int i=0; i<8; i++) {
			moore.addState((char)(i+1), (Math.random()*10) % 2 == 0);
		}
		
	}

	@Test
	void insertSearchTest() {
		
		setupStageAddVertices();
		
		assertFalse(moore.isEmpty(), "Moore machine should not be empty");
		
	}
	
	@Test
	void minimizeTest() {
		
		setupStageMoore();
		moore = moore.minimize();
		assertEquals(6, moore.getVertexSize(), "The Moore machine didn`t minimize correctly");
		
		char q0 = moore.getV0();
		moore.BFS(q0);
		ArrayList<Character> Q = moore.getVerticesArray();
		for(char q : Q) {
			assertSame(moore.getVertexColor(q), Vertex.Color.BLACK, "Inaccessible state for q0");
		}
		
	}

}
