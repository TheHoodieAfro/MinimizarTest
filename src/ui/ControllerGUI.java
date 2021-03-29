package ui;

import java.util.ArrayList;

/**
 * Los imports de la libreria externa para mostrar los grafos de manera visual
 */
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Mealy;
import model.Moore;

public class ControllerGUI {
	
	  @FXML
	    private BorderPane BorderPane1;

	    @FXML
	    private TextArea StateTxtA;

	    @FXML
	    private TextArea StimulusTxtA;

	    @FXML
	    private ComboBox<String> MachineCB;

	    

	    @FXML
	    private Button MinimizeBt;

	    @FXML
	    private Button GrafoBt;
	    
	    @FXML
	    private ScrollPane ScrollPane1;
	    
	    public static int columns;
	    
	    public static int rows;
	    
	   public static GridPane InputGrid;
	   @FXML
	   public static GridPane OutputGrid;
	   
	   public static TextField [][] TransformationMatrix;
	   @FXML
	    private ScrollPane ScrollPane2;
	   
	   public  Moore<Character, Character, Character> GlobalMooreMachine;
	   
	   
	   public  Mealy<Character, Character, Character> GlobalMealyMachine;

	    
	    
	    /**
	     * Inicializa los objetos y metodos necesarios para el controlador
	     */
	    @FXML
	    public void initialize() {
	        MachineCB.getItems().addAll("MOORE", "MEALY");
	       
	    }

	    /**
	     * Genera la matriz de entrada para que el usuario ingrese los datos del automata
	     * Dependiendo de la opcion del Moore o MEaley del combobox
	     */
	    @FXML
	    public void generate() {
	    	//Se recolectan los datos de los TextAreas y se colocan de manera legible para operaciones
	    	//en el GridPane
	    	
	    String [] StimulusArray = StimulusTxtA.getText().split(",");
		    columns = StimulusArray.length;
	      String [] StateArray =  StateTxtA.getText().split(",");
	        rows = StateArray.length;
	  
	        if (rows <= 26) {
	        	  InputGrid = new GridPane();
	            
	            ScrollPane1.setContent(InputGrid);

	            try {
	         TransformationMatrix = new TextField[columns + 1][rows];
	          
	          //Se Colocan los indices superiores en el GridPane de Entrada

	          for (int i = 1; i < columns + 1; i++) {
	              TextField HeaderTextField = new TextField(StimulusArray[i - 1]);
	              HeaderTextField.setEditable(false);
	              HeaderTextField.setPrefWidth(45);
	              InputGrid.add(HeaderTextField, i, 0);
	          }

	          
	          
	          //Se colocan todas las Textfields en el GridPane dependiendo del tamano de las entradas
	               for (int i = 1; i < rows + 1; i++) {
	               TextField CoordinateTextField = new TextField(StateArray[i-1]);
	                CoordinateTextField.setEditable(false);
	                CoordinateTextField.setPrefWidth(45);
	                    
	                  InputGrid.add(CoordinateTextField, 0, i);
	                  
	                  
	                           for (int j = 1; j < columns + 1; j++) {
	                      TextField CoordinateTextField1= new TextField(StateArray[i-1]);
	                      CoordinateTextField1.setPrefWidth(45);
	                      CoordinateTextField1.setText("");;
	                      
	                               TransformationMatrix[j - 1][i - 1] = CoordinateTextField1;
	                                 InputGrid.add(CoordinateTextField1, j, i);
	                    }
	                           
	                           
	                           
	                           //If the Automata is a Moore automata then an extra column with textfields
	                           //for the output states are added
	                           
	                    if (MachineCB.getValue().equals("MOORE")) {
	                        TextField ExtraTextField = new TextField("");
	                        ExtraTextField.setPrefWidth(45);
	                        ExtraTextField.setStyle(ExtraTextField.getStyle() + "\n-fx-background-color:  rgb(43,120,10)");
	                       
	                        TransformationMatrix[columns][i - 1] = ExtraTextField;
	                        InputGrid.add(ExtraTextField, columns + 1, i);
	                    }
	                }
	            } catch (NegativeArraySizeException | IllegalArgumentException e) {
	                Alert a = new Alert(Alert.AlertType.ERROR);
	                a.setContentText(e.getMessage());
	                a.show();
	            }
	        } else {
	            Alert a = new Alert(Alert.AlertType.ERROR);
	            a.setContentText("Although the model lets you manage many states, the GUI only supports a maximum of 26 states for visual reasons");
	            a.show();
	        }
	        
	        MinimizeBt.setVisible(true);
	    }
	    //Executes the minimizing algorithim for a meleay or moore machine depending on the combobox option
	    /**
	     * Selecciona el metodo para procesar un automata de mealey o Moore dependiendo de la opcion que escoga
	     * el usuario en el combo box
	     */
	    @FXML
	    public void SelectMachine() {
	    	if(MachineCB.getValue().equals("MEALY")){
	    	ProcessMealeyMachine();


	    	}else {
	    		ProcessMooreMachine();
	    		
	    	}
	    	GrafoBt.setVisible(true);
	    }
	    
	    
	    /**
	     * Obtiene todos los datos de entrada de la matriz de entrada y los procesa con el metodo de minimizar de maquinas de Mealey
	     * luego de la minimizacion coloca el automata resultante en la matriz de salida.
	     */
	    @FXML
	    public void ProcessMealeyMachine() {
	   
	        Mealy<Character, Character, Character> mealyMachine = new Mealy<>('A');
	        //Se crea otra ves la matriz para la minimizacion
	    	
	        String[][] TransformationMatrix = PrepareTransformationMatrixMealey();
	        String [] StimulusArray = StimulusTxtA.getText().split(",");
	        String [] StateArray =  StateTxtA.getText().split(",");
	       
       //Se agregan los estados al automata de mealey
	        
	        for (int j = 0; j < TransformationMatrix[0].length; j++) {
	            char tempState = StateArray[j].charAt(0);
	            mealyMachine.addState(tempState);
	        }
	        
	        //Se empiezan a las conexiones entre los estados y los estimulos para el automata
	        
	        for (int i = 0; i < TransformationMatrix.length; i++) {
	            for (int j = 0; j < TransformationMatrix[0].length; j++) {
	            	char tempState = StateArray[j].charAt(0);
	                String[] cell = TransformationMatrix[i][j].split(",");
	            
	                mealyMachine.connect(tempState, cell[0].charAt(0), StimulusArray[i].charAt(0), cell[1].charAt(0));
	            }
	        }
//Se minimiza el automata recolecatado por con el metodo minimieze de Mealey
	        mealyMachine = mealyMachine.minimize();
	       
	        Alert a = new Alert(Alert.AlertType.INFORMATION);
	        int minStates = mealyMachine.getVertexSize();
	        a.setContentText("El automata de Mealy.\n El automata equivalente tiene " + minStates + " estados");
	        a.show();
	        
	       
	        OutputGrid = new GridPane();
	        ScrollPane2.setContent(OutputGrid);

	        //Se coloca el automata en el outputGrid 
	        GlobalMealyMachine = mealyMachine;
	        ArrayList<Character> minStatesList = mealyMachine.getVerticesArray();
	     
	        for (int i = 1; i < minStates + 1; i++) {
	            char current = StateRow(minStatesList, i);
	            TextField tempTextField;
	            for (int j = 1; j < columns + 1; j++) {
	                char stimulus = StimulusArray[j - 1].charAt(0);
	                String cell = "" + mealyMachine.transition(current, stimulus);
	               
	                cell += "," + mealyMachine.response(current, stimulus);
	               
	                tempTextField = new TextField(cell);
	                tempTextField.setEditable(false);
	                tempTextField.setPrefWidth(45);
	                OutputGrid.add(tempTextField, j, i);
	            }
	        }
	    }
	    
	    
	  
	    /**
	     * Obtiene todos los datos de entrada de la matriz de entrada y los procesa con el metodo de minimizar de maquinas de Moore
	     *luego de la minimizacion coloca el automata resultante en la matriz de salida.
	     * 
	     */
	    public void ProcessMooreMachine() {
	  
	    	//Se crea otra ves la matriz para la minimizacion
	  
	        String[][] TransformationMatrix = PrepareTransformationMatrixMoore();
	        String [] StimulusArray = StimulusTxtA.getText().split(",");
	       
	       
	        Moore<Character, Character, Character> mooreMachine = new Moore<>('A', TransformationMatrix[TransformationMatrix.length - 1][0].charAt(0));
	        
	        //Se agregan los estados al automata de moore
	        for (int j = 0; j < TransformationMatrix[0].length; j++) {
	            char tempState = (char) (j + 65);
	            mooreMachine.addState(tempState, TransformationMatrix[TransformationMatrix.length - 1][j].charAt(0));
	        }
	        
	      //Se empiezan a las conexiones entre los estados y los estimulos para el automata
	        for (int i = 0; i < TransformationMatrix.length - 1; i++) {
	            for (int j = 0; j < TransformationMatrix[0].length; j++) {
	                char tempState = (char) (j + 65);
	                mooreMachine.connect(tempState, TransformationMatrix[i][j].charAt(0), StimulusArray[i].charAt(0));
	            }
	        }
	        //Se llama al metodod de minimizar de la clase Moore
 
	        mooreMachine = mooreMachine.minimize();
	        GlobalMooreMachine = mooreMachine;
	        Alert a = new Alert(Alert.AlertType.INFORMATION);
	        int minStates = mooreMachine.getVertexSize();
	        a.setContentText("El Automata de Moore fue minimizado .\n El automata equivalente contiene " + minStates + " estados");
	        a.show();

	        
	        OutputGrid = new GridPane();
	        ScrollPane2.setContent(OutputGrid);
	        
	      //Se coloca el automata en el outputGrid 
	        ArrayList<Character> minStatesList = mooreMachine.getVerticesArray();
	        for (int i = 1; i < minStates + 1; i++) {
	            char current = StateRow(minStatesList, i);
	            TextField tempTextfield;
	            tempTextfield = new TextField("" + mooreMachine.response(current));
	         
	            tempTextfield.setEditable(false);
	            tempTextfield.setPrefWidth(45);
	            OutputGrid.add(tempTextfield, columns + 1, i);
	            
	            
	            
	            
	            
	            for (int j = 1; j < columns + 1; j++) {
	            	tempTextfield = new TextField("" + mooreMachine.transition(current, StimulusArray[j - 1].charAt(0)));
	            	tempTextfield.setEditable(false);
	            	tempTextfield.setPrefWidth(45);
	              
	                OutputGrid.add(tempTextfield, j, i);
	            }
	        }
	    }
	    
	    
	    
	    
	    /**
	     * Prepara el estado representativo de la fila, para luego llenar el resto de la matriz con los estados de transicion adecuados
	     * @param minStatesList Lista de los estados despues de la minimizacion
	     * @param i el indice del estado dentro del arreglo de estados
	     * @return un caracter que represente el estado correspondiente a la fila dentro de la matriz.
	     */
	    public char StateRow(ArrayList<Character> minStatesList, int i) {
	        char current = minStatesList.get(i - 1);
	        TextField temporaryTextField = new TextField(current + "");
	        temporaryTextField.setEditable(false);
	        temporaryTextField.setPrefWidth(45);
	       
	        OutputGrid.add(temporaryTextField, 0, i);
	        return current;
	    }
	    
	    /**
	     * Crea una copia de la matriz de entrada para que sea manejada para ser operada por el metodo de Process Mealey Machine
	     * @return Una copia de la matriz de entradas para Mealey
	     */
	    public static String[][] PrepareTransformationMatrixMealey(){
	    	String[][] matrix = new String[columns][rows];
	        for (int i = 0; i < matrix.length; i++) {
	            for (int j = 0; j < matrix[0].length; j++) {
	                matrix[i][j] = TransformationMatrix[i][j].getText();
	            }
	        }
	    	
	    	return matrix;
	    }
	    /**
	     * Crea una copia de la matriz de entradas para ser operada por el metodo de Process Moore Machine
	     * @return Una copia de la matriz de entradas para Moore
	     */
	    public static String[][] PrepareTransformationMatrixMoore(){
	    	String[][] matrix = new String[columns+1][rows];
	        for (int i = 0; i < matrix.length; i++) {
	            for (int j = 0; j < matrix[0].length; j++) {
	                matrix[i][j] = TransformationMatrix[i][j].getText();
	            }
	        }
	        return matrix;
	    }
	    
	    /**
	     * Muestra el grafo minimizado de forma visual utilizando una libreria externa: GraphStream
	     */
	    
	    public void showGraph() {
	    	
	    	Graph graph = new SingleGraph("Tutorial 1");
			
			System.setProperty("org.graphstream.ui", "javafx");
			graph.setStrict(false);
	  
	        
			ArrayList<Character> minStatesList = GlobalMealyMachine.getVerticesArray();
			String [] StimulusArray = StimulusTxtA.getText().split(",");
			
			
			for(int i = 0; i < minStatesList.size(); i++) {
				graph.addNode(minStatesList.get(i).toString());
				
			}
			
			for (int i = 1; i < minStatesList.size(); i++) {
	            char current = StateRow(minStatesList, i);
	            
	            for (int j = 1; j < columns + 1; j++) {
	                char stimulus = StimulusArray[j - 1].charAt(0);
	                String EdgeName = "" + StimulusArray[j - 1].charAt(0);
	               String origin = current+"";
	                EdgeName += "," + GlobalMealyMachine.response(current, stimulus);
	               String Destiny = ""+GlobalMealyMachine.transition(current, stimulus);
	               graph.addEdge(EdgeName,  origin , Destiny);
	            
	            }
	        }
			
		
			graph.edges().forEach(e -> {
		e.setAttribute("ui.label", e.getId());
			});

	     
	          graph.setAttribute("ui.stylesheet", "node { text-size: 30;}");
	          
			for (Node node : graph) {
		        node.setAttribute("ui.label", node.getId());
		        node.setAttribute("ui.style", "fill-color: rgb(0,100,255);");
		      //  node.setAttribute("ui.style", "text-size: 30;");
		      
		    }
			
		
			graph.display();
			
			
	    }

	    

}
