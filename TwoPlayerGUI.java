import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.text.Text;

public class TwoPlayerGUI extends Application
{
	//TwoPlayer class
	TwoPlayer twoPlayer;
	
	//CONSTANTS
	final int shipsRequired = 4;
	final int margin = 2; //Want margin to always be 100 for better aspect ratio
	
	final int betterMargin = 100; //Not cell units, instead flat pixel number
	
	//Static boardsize values
	final int cellSize = 50; //with and height of cell
	final int boardSize = 8; //8x8 Grid
	final int wWidth = cellSize * 2*(boardSize + 1);
	final int wHeight = cellSize * (boardSize + 2);
	
	private int variableBoardSize;
	private int variableCellSize;
	int variableWinWidth;
	int variableWinHeight;
	
	//Variables
	private boolean player1Turn = true;
	private boolean playerShipPlacementNotOver = true;
	private boolean shotTaken = false;
	private int player1ShipsSunk;
	private int player2ShipsSunk;
	private int numTurns;
	
	//Scene 1
	Group root;
	Scene scene;
	Canvas canvas;
	GraphicsContext gc;
	
	VBox vBox;
	Label stats;
	Label shipsSunkLabel;
	
	Button p1b1;
	Button p1b2;
	Button p1b3;
	Button p1b4;
	Button p1b5;
	Button p1b6;
	
	//Scene 2
	Group root2;
	Scene scene2;
	Canvas canvas2;
	GraphicsContext gc2;
	
	VBox vBox2;
	Label stats2;
	Label shipsSunkLabel2;

	Button p2b1;
	Button p2b2;
	Button p2b3;
	Button p2b4;
	Button p2b5;
	Button p2b6;
	
	public static void main(String [] args)
	{
		launch(args);
	}
	
	//Main for GUI
	@Override
	public void start(Stage primaryStage) throws Exception
	{	
		primaryStage.setTitle("Battleship");
		createScenes(primaryStage);
		
		//Init two-player
		twoPlayer = new TwoPlayer();
		twoPlayer.twoPlayerSetup(3);
		
		playGame(primaryStage);
	}
	
	//Creates the scenes
	public void createScenes(Stage primaryStage) 
	{
		//Scene 1
		root = new Group();
		scene = new Scene(root);
		canvas = new Canvas(wWidth, wHeight);
		gc = canvas.getGraphicsContext2D();
		canvas.setMouseTransparent(true);
				
		vBox = new VBox();
		stats = new Label();
		shipsSunkLabel = new Label(); //Not added yet
		
		stats.setLayoutX(cellSize * boardSize);
		root.getChildren().add(stats);

		p1b1 = new Button("Done");
		p1b2 = new Button("Battleship");
		p1b3 = new Button("Submarine");
		p1b4 = new Button("Destroyer");
		p1b5 = new Button("Patrol Boat");
		p1b6 = new Button("Rotate");
				
		//Sets minimum button width
		vBox.setPrefWidth(75);
				
		//Draws Player Text
		gc.setStroke(Color.BLACK);
		gc.strokeText("Player Board", 0, (int)(cellSize/5));
		gc.strokeText("Enemy Board", cellSize * (boardSize + 2), (int)(cellSize/5));
				
		//Sets Positions
		p1b1.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b1.setLayoutY(wHeight - cellSize);
		p1b2.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b2.setLayoutY(wHeight - 2*cellSize);
		p1b3.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b3.setLayoutY(wHeight - 3*cellSize);
		p1b4.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b4.setLayoutY(wHeight - 4*cellSize);
		p1b5.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b5.setLayoutY(wHeight - 5*cellSize);
		p1b6.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b6.setLayoutY(wHeight - 6*cellSize);
				
		//Sets Minimum size for consistency
		p1b1.setMinWidth(vBox.getPrefWidth());
		p1b2.setMinWidth(vBox.getPrefWidth());
		p1b3.setMinWidth(vBox.getPrefWidth());
		p1b4.setMinWidth(vBox.getPrefWidth());
		p1b5.setMinWidth(vBox.getPrefWidth());
		p1b6.setMinWidth(vBox.getPrefWidth());
				
		//Draws Boards and grid-lines
		drawBoard(gc);
		root.getChildren().add(canvas);	

		//Adds Buttons
		root.getChildren().add(p1b2);
		root.getChildren().add(p1b3);
		root.getChildren().add(p1b4);
		root.getChildren().add(p1b5);
		root.getChildren().add(p1b6);
				
		//=====================================
		//Scene 2
		root2 = new Group();
		scene2 = new Scene(root2);
		canvas2 = new Canvas(wWidth, wHeight);
		gc2 = canvas2.getGraphicsContext2D();
		canvas2.setMouseTransparent(true);

		vBox2 = new VBox();
		stats2 = new Label();
		shipsSunkLabel2 = new Label();//Not added yet
		
		stats2.setLayoutX(cellSize * boardSize);
		root2.getChildren().add(stats2);
			
		p2b1 = new Button("Done");
		p2b2 = new Button("Battleship");
		p2b3 = new Button("Submarine");
		p2b4 = new Button("Destroyer");
		p2b5 = new Button("Patrol Boat");
		p2b6 = new Button("Rotate");
				
		//Sets minimum button width
		vBox2.setPrefWidth(75);
				
		//Draws Player Text
		gc2.setStroke(Color.BLACK);
		gc2.strokeText("Player Board", 0, (int)(cellSize/5));
		gc2.strokeText("Enemy Board", cellSize * (boardSize + 2), (int)(cellSize/5));
				
		//Sets Positions
		p2b1.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b1.setLayoutY(wHeight - cellSize);
		p2b2.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b2.setLayoutY(wHeight - 2*cellSize);
		p2b3.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b3.setLayoutY(wHeight - 3*cellSize);
		p2b4.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b4.setLayoutY(wHeight - 4*cellSize);
		p2b5.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b5.setLayoutY(wHeight - 5*cellSize);
		p2b6.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b6.setLayoutY(wHeight - 6*cellSize);
				
		//Sets Minimum size for consistency
		p2b1.setMinWidth(vBox.getPrefWidth());
		p2b2.setMinWidth(vBox.getPrefWidth());
		p2b3.setMinWidth(vBox.getPrefWidth());
		p2b4.setMinWidth(vBox.getPrefWidth());
		p2b5.setMinWidth(vBox.getPrefWidth());
		p2b6.setMinWidth(vBox.getPrefWidth());
				
		//Draws Boards and grid-lines
		drawBoard(gc2);
		root2.getChildren().add(canvas2);
		
		//Adds Buttons
		root2.getChildren().add(p2b2);
		root2.getChildren().add(p2b3);
		root2.getChildren().add(p2b4);
		root2.getChildren().add(p2b5);
		root2.getChildren().add(p2b6);
		
		//=======================================
		//Scene 1 event handles
		
		//Handle done button
		p1b1.setOnAction(e -> handleDoneButton(primaryStage));
						
		//Handle Ship Buttons
		p1b2.setOnAction(e -> handleShipButton(twoPlayer.player1.battleship, twoPlayer.player1, stats));
		p1b3.setOnAction(e -> handleShipButton(twoPlayer.player1.submarine, twoPlayer.player1, stats));
		p1b4.setOnAction(e -> handleShipButton(twoPlayer.player1.destroyer, twoPlayer.player1, stats));
		p1b5.setOnAction(e -> handleShipButton(twoPlayer.player1.patrolBoat, twoPlayer.player1, stats));
		
		//Handle rotate button
		p1b6.setOnAction(e -> handleRotateButton(twoPlayer.player1, stats));
		
		//Mouse event handler
		scene.setOnMouseClicked(e -> handleMouseClick(twoPlayer.player1, e, gc, root, stats, p1b1, primaryStage));
		
		//========================================
		//Scene 2 event handles
		
		//Handle done button
		p2b1.setOnAction(e -> handleDoneButton(primaryStage));
		
		//Handle Ship Buttons
		p2b2.setOnAction(e -> handleShipButton(twoPlayer.player2.battleship, twoPlayer.player2, stats2));
		p2b3.setOnAction(e -> handleShipButton(twoPlayer.player2.submarine, twoPlayer.player2, stats2));
		p2b4.setOnAction(e -> handleShipButton(twoPlayer.player2.destroyer, twoPlayer.player2, stats2));
		p2b5.setOnAction(e -> handleShipButton(twoPlayer.player2.patrolBoat, twoPlayer.player2, stats2));
		
		//Handle rotate button
		p2b6.setOnAction(e -> handleRotateButton(twoPlayer.player2, stats2));
		
		//Mouse event handler
		scene2.setOnMouseClicked(e -> handleMouseClick(twoPlayer.player2, e, gc2, root2, stats2, p2b1, primaryStage));
		
	}
	
	//Handle ship buttons
	public void handleShipButton(Ship ship, Player player, Label stats)
	{
		System.out.println("Place " + ship.getName() + "\n");
		player.current = ship;
		//twoPlayer.player1.getStats();
		stats.setText(player.getStats());
	}
	
	//Handle rotate button
	public void handleRotateButton(Player player, Label stats)
	{
		if(player.current.getName() == null)
		{
			System.out.println("Please select a ship first.");
		}
		else
		{
			System.out.println("Rotated Ship\n");
			player.rotate();
			//twoPlayer.player1.getStats();
			stats.setText(player.getStats());
		}
	}
	
	//Handle done button
	public void handleDoneButton(Stage primaryStage)
	{
		//Counts turns
		numTurns++;
		
		//Reset shotTaken
		shotTaken = false;
		
		//Both players finished placing ships, stops placing ships mouse clicking
		if(numTurns == 2) 
		{
			root.getChildren().remove(stats);
			root2.getChildren().remove(stats2);
			playerShipPlacementNotOver = false;
		}
		
		//Swaps player's screens
		if(player1Turn)
		{
			root.getChildren().remove(p1b1);
			
			primaryStage.setScene(scene2); 
			primaryStage.show();
		}
		else 
		{
			root2.getChildren().remove(p2b1);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
		//Swaps player turns
		player1Turn = !player1Turn;
	}
	
	//Handle mouse clicks
	public void handleMouseClick(Player player, MouseEvent event, GraphicsContext gc, Group root, Label stats, Button doneButton, Stage primaryStage)
	{
		//If still placing ships
		if(playerShipPlacementNotOver) 
		{
			//System.out.println("Mouse X: " + event.getX() + ", Mouse Y: " + event.getY());
			if(player.current.getName() == null)
			{
				System.out.println("Please select a ship first.");
			}
			else
			{
				//System.out.println("X: " + (int)(event.getX()/cellSize) + ", Y: " + ((int)(event.getY()/cellSize)-2));
				player.setXPos((int)(event.getX()/cellSize));
				player.setYPos((int)(event.getY()/cellSize)-2);

				boolean validPlacement = player.validShipPlacement(player.current);

				if(validPlacement)
				{
					//Updates GUI on click
					if(player.current.getVerticalOrientation())//Ship vertical
					{
						int[] y = player.current.getYPositions();
						int x = player.current.getXPos();
						
						int x1 = cellSize*x;
						int y1 = cellSize*y[0] + cellSize*2;
						int w = cellSize;
						int h = cellSize*player.current.getLength();
						//System.out.println("x1="+ x1 + " y1=" + y1 + " w=" + w + " h=" + h);
									
						gc.fillRect(x1,y1,w,h);
					}
					else //Ship horizontal
					{
						int[] x = player.current.getXPositions();
						int y = player.current.getYPos();
						
						int x1 = cellSize*x[0];
						int y1 = cellSize*y + cellSize*2;
						int w = cellSize*player.current.getLength();
						int h = cellSize;
						//System.out.println("x1="+ x1 + " y1=" + y1 + " w=" + w + " h=" + h);

						gc.fillRect(x1,y1,w,h);
					}
					
					player.shipPlacement(player.current);
					
					if(player.getName() == "Player-1")
					{
						if(player.current.getName() == "Battleship")
						{
							root.getChildren().remove(p1b2);
						}
						else if(player.current.getName() == "Submarine")
						{
							root.getChildren().remove(p1b3);
						}
						else if(player.current.getName() == "Destroyer")
						{
							root.getChildren().remove(p1b4);
						}
						else
						{
							root.getChildren().remove(p1b5);
						}	
					}
					else 
					{
						if(player.current.getName() == "Battleship")
						{
							root.getChildren().remove(p2b2);
						}
						else if(player.current.getName() == "Submarine")
						{
							root.getChildren().remove(p2b3);
						}
						else if(player.current.getName() == "Destroyer")
						{
							root.getChildren().remove(p2b4);
						}
						else
						{
							root.getChildren().remove(p2b5);
						}	
					}
					
					System.out.println("\n\n\n" + player.shipsPlaced.size() + "\n\n\n");
					
					//Adds done button once all ships are placed, and removes rotate button
					if(player.shipsPlaced.size() == shipsRequired)
					{
						if(player.getName() == "Player-1")
						{
							root.getChildren().add(p1b1);
							root.getChildren().remove(p1b6);
						}
						else
						{
							root.getChildren().add(p2b1);
							root.getChildren().remove(p2b6);
						}
						
					}
					
					stats.setText(player.getStats());
					player.current.setName(null); //Forces player to select a new ship to place

				}
			}
		}
		//No longer placing ships, now attacking
		else
		{
			//System.out.println("it worked");
			int x = (int)(((event.getX()-(wWidth/2)-cellSize))/cellSize);
			int y = ((int)((event.getY())/cellSize))-2;
			System.out.println("x: "+x +" y: " + y);
			int xl = (wWidth/2) + cellSize + (cellSize * x);
			int yl = (y*cellSize+(2*cellSize));
			int w = (cellSize);
			int h = (cellSize);
			
			if ((x>=0 && y>=0 ) && (x<=7 && y<=7) && shotTaken == false)
			{
				player.enemyBoard.grid[x][y].setBeenHit(true);
				if (player.enemyBoard.grid[x][y].getHasShip())// check if grid starts at 1 or 0
				{
					System.out.println("hit ship");
					gc.setFill(Color.RED);
					gc.fillRect(xl,yl,w,h);
					
					//If player is player1
					if(player == twoPlayer.player1) 
					{
						if(twoPlayer.player2.shipChecker(x, y))
						{
							player1ShipsSunk++;
							shipsSunkLabel.setText("Number of computer ships sunk = "+ player1ShipsSunk);
						}
						System.out.println("ship is sunk");
					}
					//If player is player2
					else
					{
						if(twoPlayer.player1.shipChecker(x, y))
						{
							player2ShipsSunk++;
							shipsSunkLabel.setText("Number of computer ships sunk = "+ player2ShipsSunk);
						}
						System.out.println("ship is sunk");
					}
				}
				else //Hit empty space
				{
					System.out.println("no ship found");
					gc.setFill(Color.BLUE);
					gc.fillRect(xl,yl,w,h);
				}
				
				shotTaken = true;
				root.getChildren().add(doneButton);
				twoPlayer.boardLinking();
			}	
			
			primaryStage.show();
		}
	}
	
	//Draws the main frame of the screen
	public void drawBoard(GraphicsContext gc)
	{
		//Draws Grids, and creates number intervals
		for(int i = 0; i < boardSize+1; i++)
		{
			//Left grid
			gc.strokeLine(0, wHeight-(i*cellSize), (cellSize*boardSize), wHeight-(i*cellSize));
			gc.strokeLine(i*cellSize, wHeight, i*cellSize, wHeight-(boardSize*cellSize));
			
			//Right grid
			gc.strokeLine(wWidth, wHeight-(i*cellSize), wWidth-(cellSize*boardSize), wHeight-(i*cellSize));
			gc.strokeLine(wWidth-(i*cellSize), wHeight, wWidth-(i*cellSize), wHeight-(cellSize*boardSize));	
			
			//Because Intervals from 1-8, not 9
			if(i > 0)
			{
				String interval = Integer.toString(i);
				
				//Horizontal intervals
				gc.strokeText(interval, i*cellSize-(int)(cellSize/2),cellSize*2 - margin);
				gc.strokeText(interval, wWidth-cellSize*boardSize + i*cellSize-(int)(cellSize/2), cellSize*2 - margin);
				
				//Vertical intervals
				gc.strokeText(interval, boardSize*cellSize+margin, 2*cellSize+i*cellSize-(int)(cellSize/2));
				gc.strokeText(interval, wWidth-boardSize*cellSize-4*margin, 2*cellSize+i*cellSize-(int)(cellSize/2));
			}	
		}
	}
	
	//Runs through player turns
	public void playGame(Stage primaryStage)
	{
		if(player1Turn)
		{		
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else
		{		
			primaryStage.setScene(scene2);
			primaryStage.show();
		}
		
	}
	
	//Runs GUI without use of an argument when called
	public void run()
	{
		launch();
	}
	
}
