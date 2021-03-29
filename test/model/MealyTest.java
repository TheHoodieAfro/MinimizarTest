package model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import structures.Vertex;

class MealyTest {
	
	private Mealy<Character, Boolean, Character> mealy;
	
	private void setupStageMealy() {
		
		mealy = new Mealy<>('A');
		mealy.addState('B');
		mealy.addState('C');
		mealy.addState('D');
		mealy.addState('E');
		mealy.addState('F');
		mealy.addState('G');
		mealy.addState('H');
		
		mealy.connect('A', 'A', false, 'a');
		mealy.connect('A', 'B', true, 'b');
		mealy.connect('B', 'B', false, 'a');
		mealy.connect('B', 'C', true, 'b');
		mealy.connect('C', 'C', false, 'a');
		mealy.connect('C', 'D', true, 'b');
		mealy.connect('D', 'D', false, 'a');
		mealy.connect('D', 'E', true, 'c');
		mealy.connect('E', 'E', false, 'a');
		mealy.connect('E', 'F', true, 'b');
		mealy.connect('F', 'F', false, 'a');
		mealy.connect('F', 'G', true, 'b');
		mealy.connect('G', 'G', false, 'a');
		mealy.connect('G', 'H', true, 'b');
		mealy.connect('H', 'H', false, 'a');
		mealy.connect('H', 'A', true, 'c');
		
	}
	
	private void setupStage() {
		mealy = new Mealy<>('A');
		mealy = mealy.minimize();
	}

	@Test
	void insertSearchTest() {
		
		setupStage();
		
		assertFalse(mealy.isEmpty(), "Mealy machine should not be empty");
		
	}
	
	@Test
	void minimizeTest() {
		
		setupStageMealy();
		mealy = mealy.minimize();
		assertEquals(4, mealy.getVertexSize(), "The Mealy machine didn`t minimize correctly");
		
		char q0 = mealy.getV0();
		mealy.BFS(q0);
		ArrayList<Character> Q = mealy.getVerticesArray();
		for(char q : Q) {
			assertSame(mealy.getVertexColor(q), Vertex.Color.BLACK, "Inaccessible state for q0");
		}
		
	}

}
