import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SketchPane extends BorderPane {
	//Declare all instance variables listed in UML diagram
		private ArrayList<Shape> shapeList; 
		private ArrayList<Shape> tempList; 
		private Button undoButton; 
		private Button eraseButton; 
		private Label fillColorLabel; 
		private Label strokeColorLabel; 
		private Label strokeWidthLabel;
		private ComboBox<String> fillColorCombo; 
		private ComboBox<String> strokeWidthCombo;
		private ComboBox<String> strokeColorCombo; 
		private RadioButton radioButtonLine; 
		private RadioButton radioButtonRectangle; 
		private RadioButton radioButtonCircle; 
		private RadioButton radioButtonBrush; 
		private RadioButton radioButtonEraser; 
		private RadioButton radioButtonBackground; 
		private Pane sketchCanvas;
		private Color[] colors; 
		private String[] strokeWidth; 
		private String[] colorLabels; 
		private Color currentStrokeColor; 
		private Color currentFillColor;
		private Color eraserColor; 
		private int currentStrokeWidth; 
		private Line line; 
		private Circle circle; 
		private Rectangle rectangle; 
		private double x1;
		private double y1; 
		
		
		
		 
		
		
		
		
			//SketchPane constructor
			public SketchPane() {
				// Colors, labels, and stroke widths that are available to the user
				colors = new Color[] {Color.BLACK, Color.GREY, Color.YELLOW, Color.GOLD, Color.ORANGE, Color.DARKRED, Color.PURPLE, Color.HOTPINK, Color.TEAL, Color.DEEPSKYBLUE, Color.LIME} ;
		        colorLabels = new String[] {"black", "grey", "yellow", "gold", "orange", "dark red", "purple", "hot pink", "teal", "deep sky blue", "lime"};
		        fillColorLabel = new Label("Fill Color:");
		        strokeColorLabel = new Label("Border Color:");
		        strokeWidthLabel = new Label("Stroke Width:");
		        strokeWidth = new String[] {"1", "3", "5", "7", "9", "11", "13", "15", "20", "25", "30", "35", "40"};  
		        //instantiate 2 arrayList, 2 buttons, and bind the buttons to their handlers
		        shapeList = new ArrayList<>();					//ArrayList for shapes to be added to
		        tempList = new ArrayList<>(); 					//arrayList for temporary hold
		        undoButton = new Button("Undo");					
		        eraseButton = new Button("Erase");
		        undoButton.setOnAction(new ButtonHandler());	
		        eraseButton.setOnAction(new ButtonHandler());
		        //Fill Color Combo Box 
		        fillColorCombo = new ComboBox<String>(); 			
		        fillColorCombo.getItems().addAll(colorLabels);			//add color labels list to combobox
		        fillColorCombo.getSelectionModel().selectFirst();   	//selects black color which is first
		        fillColorCombo.setOnAction(new ColorHandler());			//Bind fillColor combobox to its event handler
		        //Stroke Color Combobox
		        strokeColorCombo = new ComboBox<String>(); 			
		        strokeColorCombo.getItems().addAll(colorLabels);		//add color labels list to combobox
		        strokeColorCombo.getSelectionModel().selectFirst();		//selects black color which is first
		        strokeColorCombo.setOnAction(new ColorHandler());		//bind strokeColor combobox to its event handler
		        //Stroke Width Combobox
		        strokeWidthCombo = new ComboBox<String>();
		        strokeWidthCombo.getItems().addAll(strokeWidth);		//add strokeWith list to comboBox
		        strokeWidthCombo.getSelectionModel().selectFirst();  	//selects 1 as default
		        strokeWidthCombo.setOnAction(new WidthHandler());		//bind strokeWidth comboBox to its eventhandler
		        //Radio Button toggle group
		        ToggleGroup tg = new ToggleGroup();						//instantiate toggle group tg
		        radioButtonLine = new RadioButton("Line"); 				//radiobutton line
		    	radioButtonRectangle = new RadioButton("Rectangle");	//radiobutton rectangle
		    	radioButtonCircle = new RadioButton("Circle");			//radiobutton circle
		    	radioButtonBrush = new RadioButton("Paint Brush");
		    	radioButtonEraser = new RadioButton("Eraser");
		    	radioButtonBackground = new RadioButton("Background");
		    	//Set up toggle group
		    	radioButtonLine.setToggleGroup(tg);
		    	radioButtonRectangle.setToggleGroup(tg);
		    	radioButtonCircle.setToggleGroup(tg); 
		    	radioButtonBrush.setToggleGroup(tg);
		    	radioButtonEraser.setToggleGroup(tg);
		    	radioButtonBackground.setToggleGroup(tg); 
		    	radioButtonBrush.setSelected(true); 						//radioButton line is set as default  
		    	//instantiate sketchCanvas pane and set background color to white 
		    	sketchCanvas = new Pane(); 
		    	sketchCanvas.setStyle("-fx-background-color: white;");
		    	eraserColor = Color.WHITE; 
		    	//instantiate top HBox with 20 size and minimum (20,40)
		    	HBox topBox = new HBox(20);
		    	topBox.setMinSize(20, 40);
		    	topBox.setAlignment(Pos.CENTER);						//Position items in HBox center
		    	topBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,null,null)));		//HBox background is lightgrey
		    	//add 3 labels and 3 comboBox to topHbox
		    	topBox.getChildren().addAll(fillColorLabel, fillColorCombo, strokeWidthLabel, strokeWidthCombo, strokeColorLabel, strokeColorCombo);
		    	//Instantiate bottom HBox with size 20 and minimum(20,40)
		    	HBox bottomBox = new HBox(20);
		    	bottomBox.setMinSize(50, 50);
		    	bottomBox.setAlignment(Pos.CENTER);						//Position items in Hbox center
		    	bottomBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY,null,null)));		//HBox background is lightgrey
		    	//add 3 radio buttons and 2 buttons to bottom HBox
		    	bottomBox.getChildren().addAll(radioButtonBrush, radioButtonEraser, radioButtonLine, radioButtonRectangle, radioButtonCircle, radioButtonBackground, undoButton, eraseButton); 
		    	//Add sketchCanvas pane, top HBox and bottom HBox to boderPane
		    	this.setCenter(sketchCanvas);
		    	this.setTop(topBox);
		    	this.setBottom(bottomBox);
		    	//bind sketchCanvas to its mouse handler
		    	sketchCanvas.setOnMousePressed(new MouseHandler());
		    	sketchCanvas.setOnMouseDragged(new MouseHandler());
		    	sketchCanvas.setOnMouseReleased(new MouseHandler());
		    	//default numbers and color 
		    	x1 = 0.0;
		    	y1 = 0.0;
		    	currentFillColor = Color.BLACK; 
		    	currentStrokeColor = Color.BLACK;
		    	currentStrokeWidth = 1;  
		    }
			
			//sketchCanvas MouseHandler to draw shapes 
			private class MouseHandler implements EventHandler<MouseEvent> {
				@Override
				public void handle(MouseEvent event) {
					
					//conditional for when rectangle is chosen
					if (radioButtonRectangle.isSelected()) {
						/*Mouse is pressed - create a rectangle with x and y coordinates of mouse pressed
						add rectangle to shapeList and add it to sketchCanvas pane with default fill 
						white and stoke color black*/
						if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
							x1 = event.getX();												
							y1 = event.getY();
							rectangle = new Rectangle();
							rectangle.setX(x1);
							rectangle.setY(y1);
							shapeList.add(rectangle);
							rectangle.setFill(Color.WHITE);
							rectangle.setStroke(Color.BLACK);	
							sketchCanvas.getChildren().add(rectangle);
						}
						//Mouse is dragged - x and y coordinates follows mouse until released  
						else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
							rectangle.setWidth(Math.abs(event.getX() - x1));
							rectangle.setHeight(Math.abs(event.getY() - y1));

						}
						//Mouse is released - rectangle is created based with color from variables currentFillColor
						//and currentStrokeColor with width from currentStrokeWidth
						else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
							rectangle.setFill(currentFillColor);
							rectangle.setStroke(currentStrokeColor);
							rectangle.setStrokeWidth(currentStrokeWidth);
						}
					}
					
					//conditional when circle is chosen
					if (radioButtonCircle.isSelected()) {
						/*Mouse is pressed - create a circle with x and y coordinates of mouse pressed
						add circle to shapeList and add it to sketchCanvas pane with default fill 
						white and stoke color black*/
						if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
							x1 = event.getX();
							y1 = event.getY();
							circle = new Circle();
							circle.setCenterX(x1);
							circle.setCenterY(y1);
							shapeList.add(circle);
							circle.setFill(Color.WHITE);
							circle.setStroke(Color.BLACK);	
							sketchCanvas.getChildren().add(circle);
						}
						//Mouse is dragged - x and y coordinates follows mouse until released 
						//call getDistance() method 
						else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
							circle.setRadius(getDistance(x1, y1, event.getX(), event.getY()));
							

						}
						//Mouse is released - circle is created based with color from variables currentFillColor
						//and currentStrokeColor with width from currentStrokeWidth
						else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
							circle.setFill(currentFillColor);
							circle.setStroke(currentStrokeColor);
							circle.setStrokeWidth(currentStrokeWidth);
						}
					}
					
					//conditional for when line is chosen 
					if (radioButtonLine.isSelected()) {
						/*Mouse is pressed - create a line with x and y coordinates of mouse pressed
						add line to shapeList and add it to sketchCanvas pane with default fill 
						white and stoke color black*/
						if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
							x1 = event.getX();
							y1 = event.getY();
							line = new Line();
							line.setStartX(x1);
							line.setStartY(y1);
							line.setEndX(x1);
							line.setEndY(y1);
							shapeList.add(line);
							line.setFill(Color.WHITE);
							line.setStroke(Color.BLACK);	
							sketchCanvas.getChildren().add(line);
						}
						//Mouse is dragged - x and y coordinates follows mouse until released
						else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
							line.setEndX(Math.abs(event.getX()));
							line.setEndY(Math.abs(event.getY()));

						}
						//Mouse is released - circle is created based with color from variables currentFillColor
						//and currentStrokeColor with width from currentStrokeWidth
						else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
							line.setFill(currentFillColor);
							line.setStroke(currentFillColor);
							line.setStrokeWidth(currentStrokeWidth);
						}
					}
					
					if (radioButtonBrush.isSelected()) {
						//Mouse is pressed - paint brush will be able to draw in canvas
						if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
							x1 = event.getX();
							y1 = event.getY();
							circle = new Circle();
							circle.setCenterX(x1);
							circle.setCenterY(y1);
							shapeList.add(circle);
							circle.setFill(Color.WHITE);
							circle.setStroke(Color.BLACK);	
							sketchCanvas.getChildren().add(circle);
						}
						//Mouse is dragged - user will be painting the canvas
						else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
							circle = new Circle();
							circle.setCenterX(event.getX());
							circle.setCenterY(event.getY());
							shapeList.add(circle);
							circle.setFill(currentFillColor);
							circle.setStroke(currentFillColor);
							circle.setStrokeWidth(currentStrokeWidth);	
							sketchCanvas.getChildren().add(circle);
							
						}
						//Mouse is released - painting canvas will cease
						else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
							circle.setFill(currentFillColor);
							circle.setStroke(currentFillColor);
							circle.setStrokeWidth(currentStrokeWidth);
						}
					}
					
					if (radioButtonEraser.isSelected()) {
						//Mouse is pressed - will allow the user to erase stuff
						if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
							x1 = event.getX();
							y1 = event.getY();
							circle = new Circle();
							circle.setCenterX(x1);
							circle.setCenterY(y1);
							shapeList.add(circle);
							circle.setFill(Color.WHITE);
							circle.setStroke(Color.WHITE);	
							sketchCanvas.getChildren().add(circle);
						}
						//Mouse is dragged - user will erase where the mouse is dragged
						else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
							circle = new Circle();
							circle.setCenterX(event.getX());
							circle.setCenterY(event.getY());
							shapeList.add(circle);
							circle.setFill(eraserColor);
							circle.setStroke(eraserColor);
							circle.setStrokeWidth(currentStrokeWidth);	
							sketchCanvas.getChildren().add(circle);
						}
						else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
							circle.setFill(eraserColor);
							circle.setStroke(eraserColor);
							circle.setStrokeWidth(currentStrokeWidth);
						}
					}
					
					
					if (radioButtonBackground.isSelected()) {
						//if mouse is pressed paint the whole canvas to color set for background
						if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
							x1 = event.getX();												
							y1 = event.getY();
							//currentlyFillColor is changed depending on the option chosen from fillColorComboBox
							int indexNum = fillColorCombo.getSelectionModel().getSelectedIndex(); 
							currentFillColor = colors[indexNum]; 
							//currentStrokeColor is changed depending on the option chosen from strokeColorComboBox 
							int indexNum2 = strokeColorCombo.getSelectionModel().getSelectedIndex();
							currentStrokeColor = colors[indexNum2]; 
							//background color is set up 
							rectangle = new Rectangle(0, 0, 800, 800);
							shapeList.add(rectangle);
							rectangle.setFill(currentFillColor);
							rectangle.setStroke(currentFillColor);	
							sketchCanvas.getChildren().add(rectangle);
							//Erase color is set to background color
					    	eraserColor = currentFillColor; 
						}
					}
				}
			}
			
			//Button handler for undoButton and eraseButton to remove or add shapes to sketchCanvas pane 
			private class ButtonHandler implements EventHandler<ActionEvent> {
				@Override
				public void handle(ActionEvent event) {
					//conditional if undoButton is press and arrayList is not empty
					if(event.getSource() == undoButton && shapeList.size() != 0) {
						//remove last item from shapeList and sketchCanvas pane
						int indexNum = shapeList.size() - 1; 
						sketchCanvas.getChildren().remove(indexNum); 
						shapeList.remove(indexNum);	
						
					}
					//conditional if undoButton is pressed and arrayList is empty
					else if(event.getSource() == undoButton && shapeList.size() == 0) {
						//add all items from tempList to shapeList 
						for (int i = 0; i <tempList.size(); ++i) {
							shapeList.add(tempList.get(i));
						}
						tempList.clear(); 		//erase everything from tempList
						//add everything from shapeList to sketchCanvas pane
						for (int j = 0; j < shapeList.size(); ++j){
							sketchCanvas.getChildren().add(shapeList.get(j)); 
						}
					}
					//conditional if eraseButton is pressed and shapeList is not empty
					if(event.getSource() == eraseButton && shapeList.size() != 0) {
						tempList.clear();					  //erase everything from tempList
						//copy everything from shapeList to tempList
						for (int i = 0; i < shapeList.size(); ++i) {
							tempList.add(shapeList.get(i));
						}
						shapeList.clear();					  //erase everything from shapeList
						sketchCanvas.getChildren().clear();   //erase everything on sketchCanvas pane
						eraserColor = Color.WHITE;
					}				
				}
			}
			
			//ColorHandler for fillColor combobox and currentStroke color comboBox to change colors 
			private class ColorHandler implements EventHandler<ActionEvent> {
				@Override
				public void handle(ActionEvent event) {
					//currentlyFillColor is changed depending on the option chosen from fillColorComboBox
					int indexNum = fillColorCombo.getSelectionModel().getSelectedIndex(); 
					currentFillColor = colors[indexNum]; 
					//currentStrokeColor is changed depending on the option chosen from strokeColorComboBox 
					int indexNum2 = strokeColorCombo.getSelectionModel().getSelectedIndex();
					currentStrokeColor = colors[indexNum2]; 
				}
			}
			
			//WidthHandler for currentStroke width to change its size
			private class WidthHandler implements EventHandler<ActionEvent> {
				@Override
				public void handle(ActionEvent event){
					//curentStrokeWidth is changed depending on the which option is chosen from strokeWidthCombo
					int width = Integer.parseInt(strokeWidthCombo.getValue());
					currentStrokeWidth = width; 
				}
			}
				
			// Get the Euclidean distance between (x1,y1) and (x2,y2)
		    private double getDistance(double x1, double y1, double x2, double y2)  {
		        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		    }

}
