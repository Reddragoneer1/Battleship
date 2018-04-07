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
	final int margin = 110; //Not cell units, instead flat pixel number
	final int boardSize = 400; //pixels
	final int wWidth = boardSize * 2 + margin;
	final int wHeight = boardSize + margin;
		
	//Adjusted board variables
	int boardUnits = 8; //Set to 8 as default
	private int variableCellSize;
	
	//Variables
	private boolean gameOver = false;
	private boolean player1Turn = true;
	private boolean playerShipPlacementNotOver = true;
	private boolean shotTaken = false;
	private int player1ShipsSunk;
	private int player2ShipsSunk;
	private int numTurns;
	
	//Player Names
	String p1Name;
	String p2Name;
	
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
	
	//Win scene
	Group root3;
	Scene scene3;
	Canvas canvas3;
	GraphicsContext gc3;
	
	VBox vBox3;
	
	Button quit;
	
	public static void main(String [] args)
	{
		launch(args);
	}
	
	//Main for GUI
	@Override
	public void start(Stage primaryStage) throws Exception
	{	
		//Init two-player
		twoPlayer = new TwoPlayer();
		
		twoPlayer.setBoard(boardUnits);
		twoPlayer.player1.setName(p1Name);
		twoPlayer.player2.setName(p2Name);

		twoPlayer.player1.setBoardLength(boardUnits-1);
		twoPlayer.player2.setBoardLength(boardUnits-1);
		
		twoPlayer.twoPlayerSetup(3);
		
		//Creates cell size
		variableCellSize = (int)(boardSize/boardUnits);
		
		primaryStage.setTitle("Battleship");
		createScenes(primaryStage);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	//Creates the scenes
	public void createScenes(Stage primaryStage) 
	{
		//Player 1 Scene
		root = new Group();
		scene = new Scene(root);
		canvas = new Canvas(wWidth, wHeight);
		gc = canvas.getGraphicsContext2D();
		canvas.setMouseTransparent(true);
				
		vBox = new VBox();
		stats = new Label();
		shipsSunkLabel = new Label(); //Not added yet
		
		stats.setLayoutX(boardSize);
		root.getChildren().add(stats);
		
		shipsSunkLabel.setLayoutX(boardSize);
		shipsSunkLabel.setLayoutY((int)(margin/4));


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
		gc.strokeText(p1Name, 0, 10);
		gc.strokeText(p2Name, boardSize + margin, 10);
				
		//Sets Positions
		p1b1.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b1.setLayoutY(wHeight - 50);
		p1b2.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b2.setLayoutY(wHeight - 2*50);
		p1b3.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b3.setLayoutY(wHeight - 3*50);
		p1b4.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b4.setLayoutY(wHeight - 4*50);
		p1b5.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b5.setLayoutY(wHeight - 5*50);
		p1b6.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p1b6.setLayoutY(wHeight - 6*50);
				
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
		//Player 2 Scene
		root2 = new Group();
		scene2 = new Scene(root2);
		canvas2 = new Canvas(wWidth, wHeight);
		gc2 = canvas2.getGraphicsContext2D();
		canvas2.setMouseTransparent(true);

		vBox2 = new VBox();
		stats2 = new Label();
		shipsSunkLabel2 = new Label();//Not added yet
		
		stats2.setLayoutX(boardSize);
		root2.getChildren().add(stats2);
		
		shipsSunkLabel2.setLayoutX(boardSize);
		shipsSunkLabel2.setLayoutY((int)(margin/4));


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
		gc2.strokeText(p2Name, 0, 10);
		gc2.strokeText(p1Name, boardSize + margin, 10);
				
		//Sets Positions
		p2b1.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b1.setLayoutY(wHeight - 50);
		p2b2.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b2.setLayoutY(wHeight - 2*50);
		p2b3.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b3.setLayoutY(wHeight - 3*50);
		p2b4.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b4.setLayoutY(wHeight - 4*50);
		p2b5.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b5.setLayoutY(wHeight - 5*50);
		p2b6.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		p2b6.setLayoutY(wHeight - 6*50);
				
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
		//Player 1 event handles
		
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
		//Player 2 event handles
		
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
		
		//=============================================
		//Win Screen scene
		root3 = new Group();
		scene3 = new Scene(root3);
		canvas3 = new Canvas(wWidth, wHeight);
		gc3 = canvas3.getGraphicsContext2D();
		canvas3.setMouseTransparent(true);
		root3.getChildren().add(canvas3);

		
		vBox3 = new VBox();
		vBox3.setPrefWidth(75);

		
		gc3.setStroke(Color.BLACK);
		
		//Button
		quit = new Button("Quit");
		
		quit.setMinWidth(vBox3.getPrefWidth());
		quit.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		quit.setLayoutY(wHeight - 50);
		
		root3.getChildren().add(quit);

		//=============================================
		//Win Screen event handles
		quit.setOnAction(e -> System.exit(0));
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
		
		if(player1ShipsSunk == 4)
		{
			System.out.println(p1Name + " won!");
			setWinScene(primaryStage, p1Name);
			gameOver = true;
		}
		if(player2ShipsSunk == 4)
		{
			System.out.println(p2Name + " won!");
			setWinScene(primaryStage, p2Name);
			gameOver = true;
		}
		
		//Both players finished placing ships, stops placing ships mouse clicking
		if(numTurns == 2) 
		{
			root.getChildren().remove(stats);
			root2.getChildren().remove(stats2);
			
			playerShipPlacementNotOver = false;
			
			root.getChildren().add(shipsSunkLabel);
			root2.getChildren().add(shipsSunkLabel2);

		}
		
		//Swaps player's screens
		if(player1Turn && !gameOver)
		{
			root.getChildren().remove(p1b1);
			
			primaryStage.setScene(scene2); 
			primaryStage.show();
		}
		else if(!player1Turn && !gameOver)
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
				//System.out.println("X: " + event.getX() + ", Y: " + event.getY());
				
				player.setXPos((int)(event.getX()/variableCellSize));
				if(event.getY() <= margin + (boardSize-variableCellSize*boardUnits))//If click is above board
				{
					player.setYPos(-1);//Just an invalid pos
				}
				else 
				{
					player.setYPos((int)((event.getY()-margin-(boardSize-variableCellSize*boardUnits))/variableCellSize));
				}
				
				boolean validPlacement = player.validShipPlacement(player.current);

				if(validPlacement)
				{
					//Updates GUI on click
					if(player.current.getVerticalOrientation())//Ship vertical
					{
						int[] y = player.current.getYPositions();
						int x = player.current.getXPos();
						
						int x1 = variableCellSize*x;
						int y1 = variableCellSize*y[0] + margin + (boardSize-variableCellSize*boardUnits);
						int w = variableCellSize;
						int h = variableCellSize*player.current.getLength();
						//System.out.println("x1="+ x1 + " y1=" + y1 + " w=" + w + " h=" + h);
									
						gc.fillRect(x1,y1,w,h);
					}
					else //Ship horizontal
					{
						int[] x = player.current.getXPositions();
						int y = player.current.getYPos();
						
						int x1 = variableCellSize*x[0];
						int y1 = variableCellSize*y + margin + (boardSize-variableCellSize*boardUnits);
						int w = variableCellSize*player.current.getLength();
						int h = variableCellSize;
						//System.out.println("x1="+ x1 + " y1=" + y1 + " w=" + w + " h=" + h);

						gc.fillRect(x1,y1,w,h);
					}
					
					player.shipPlacement(player.current);
					twoPlayer.boardLinking();
					
					if(player.getName() == p1Name)
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
					
					//System.out.println("\n\n\n" + player.shipsPlaced.size() + "\n\n\n");
					
					//Adds done button once all ships are placed, and removes rotate button
					if(player.shipsPlaced.size() == shipsRequired)
					{
						if(player.getName() == p1Name)
						{
							root.getChildren().add(p1b1);
							root.getChildren().remove(p1b6);
						}
						else if (player.getName() == p2Name)
						{
							root.getChildren().add(p2b1);
							root.getChildren().remove(p2b6);
						}
						
					}
					
					player.current = player.nullShip; //Null ship makes it so player must choose a new ship in between ship placements 
					stats.setText(player.getStats());
				}
			}
		}
		//No longer placing ships, now attacking phase
		else
		{
			int y;
			int x;

			x = boardUnits-1 - (int)((wWidth - event.getX())/variableCellSize ); //boardUnits-1 to convert to index in array
			if(event.getY() <= margin + (boardSize-variableCellSize*boardUnits))//If click is above board
			{
				y = -1; //Just an invalid position so no action taken
			}
			else 
			{
				y = (int)((event.getY()-margin-(boardSize-variableCellSize*boardUnits))/variableCellSize);
			}
			
			System.out.println("x: "+ x + " y: " + y);
			
			int xl = wWidth - (boardUnits-x)*variableCellSize;
			int yl = (y*variableCellSize)+margin+(boardSize-variableCellSize*boardUnits);
			
			int w = variableCellSize;
			
			if((x>=0 && y>=0 ) && (x<=boardUnits-1 && y<=boardUnits-1))
			{
				if(player.enemyBoard.grid[x][y].getBeenHit())
				{
					System.out.println("Already shot that spot.");
				}
				else if (!shotTaken)
				{
					player.enemyBoard.grid[x][y].setBeenHit(true);
					if (player.enemyBoard.grid[x][y].getHasShip())// has ship
					{
						gc.setFill(Color.RED);
						gc.fillRect(xl,yl,w,w);
						
						//If player is player1
						if(player == twoPlayer.player1)
						{
							if(twoPlayer.player2.shipChecker(x, y))
							{
								player1ShipsSunk++;
								shipsSunkLabel.setText("# of ships sunk = "+ player1ShipsSunk);
							}
						}
						//If player is player2
						else
						{
							if(twoPlayer.player1.shipChecker(x, y))
							{
								player2ShipsSunk++;
								shipsSunkLabel2.setText("# of ships sunk = "+ player2ShipsSunk);
							}
						}
					}
					else //Hit empty space
					{
						System.out.println("no ship found");
						gc.setFill(Color.BLUE);
						gc.fillRect(xl,yl,w,w);
					}
					
					shotTaken = true;
					root.getChildren().add(doneButton);
					twoPlayer.boardLinking(); 
				}
			}
	
			primaryStage.show();
		}
	}
	
	//Draws the main frame of the screen
	public void drawBoard(GraphicsContext gc)
	{
		/*
		//Used only for testing purposes, borders max board size and used to compensate for remainder
		gc.strokeLine(0, margin, wWidth, margin);
		gc.strokeLine(boardSize, 0, boardSize , wHeight);
		gc.strokeLine(boardSize + margin, 0, boardSize + margin, wHeight);
		*/

		//Draws Grids, and creates number intervals
		for(int i = 0; i < boardUnits+1; i++)
		{
			//Left grid
			gc.strokeLine(0, wHeight-(i*variableCellSize), (boardUnits*variableCellSize), wHeight-(i*variableCellSize));
			gc.strokeLine(i*variableCellSize, wHeight, i*variableCellSize, wHeight-(boardUnits*variableCellSize));
			
			//Right grid
			gc.strokeLine(wWidth, wHeight-(i*variableCellSize), wWidth-(boardUnits*variableCellSize), wHeight-(i*variableCellSize));
			gc.strokeLine(wWidth-(i*variableCellSize), wHeight, wWidth-(i*variableCellSize), wHeight-(boardUnits*variableCellSize));	
			
			//Because Intervals from 1-n, not n+1 
			if(i > 0)
			{
				String interval = Integer.toString(i);
				
				//Horizontal intervals
				gc.strokeText(interval, i*variableCellSize-(int)(variableCellSize/2)-5, wHeight - (boardUnits*variableCellSize) - 2);
				gc.strokeText(interval, wWidth-(boardUnits*variableCellSize) + i*variableCellSize-(int)(variableCellSize/2)-5, wHeight - (boardUnits*variableCellSize) - 2);
				
				//Vertical intervals
				if(i > 9)
				{
					gc.strokeText(interval, wWidth-(boardUnits*variableCellSize)-15, margin+i*variableCellSize-(int)(variableCellSize/2)+(boardSize-boardUnits*variableCellSize) + 5);

				}
				else 
				{
					gc.strokeText(interval, wWidth-(boardUnits*variableCellSize)-10, margin+i*variableCellSize-(int)(variableCellSize/2)+(boardSize-boardUnits*variableCellSize) + 5);

				}
				gc.strokeText(interval, (boardUnits*variableCellSize)+2, margin+i*variableCellSize-(int)(variableCellSize/2)+(boardSize-boardUnits*variableCellSize) + 5);
			}	
		}
	}
	
	//Sets Win scene
	public void setWinScene(Stage primaryStage, String name)
	{
		gc3.strokeText(name, (int)(wWidth/2 - 20), (int)(wHeight/2));

		primaryStage.setScene(scene3); 
		primaryStage.show();
	}
	
	//Runs GUI without use of an argument when called
	public void run()
	{
		launch();
	}
	
}
