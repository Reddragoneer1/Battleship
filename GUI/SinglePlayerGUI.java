package GUI;
import BackEnd.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
import javax.swing.JOptionPane;


/**
 * 
 * Class creates and handles all events for a single player
 * game of battleship versus an AI, and continues until 
 * either the player wins, or the AI.
 * 
 * @author Sam, David
 *
 */
public class SinglePlayerGUI extends Application
{
	SinglePlayer singlePlayer = new SinglePlayer();
	
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
	private boolean playerShipPlacementNotOver=true;
	private boolean shotTaken = false;
	private boolean playerTurn = false;
	private boolean load = false;
	private boolean gameLoaded = false;
	private int computerShipsSunk = 0;
	private int playerShipsSunk = 0;
	private int numTurns = 0;
	
	//JavaFX Init GUI Fundamentals
	Group root; 
	Scene scene;
	Canvas canvas;
	GraphicsContext gc;
	
	VBox vBox;
	Label stats;
	Label shipsSunkLabel;
	Label shipsSunkLabel2;
	
	//Creates Buttons
	Button p1b1;
	Button p1b2;
	Button p1b3;
	Button p1b4;
	Button p1b5;
	Button p1b6;
	Button p1b7;
	
	//Win scene
	Group root2;
	Scene scene2;
	Canvas canvas2;
	GraphicsContext gc2;
		
	VBox vBox2;
		
	Button quit;
	
	//Player Name
	String p1Name;
	String p2Name = "Computer";
	
	/**
	 * Launches javaFX
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		launch(args); 
	}
	
	/**
	 * Sets main framework of what is to be used for the GUI.
	 * Sets up the singlePlayer game in the background so the GUI
	 * can use it as a reference.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		createScene(primaryStage);
		if(!load)
		{			
			singlePlayer.setBoard(boardUnits);			
			singlePlayer.player.setName(p1Name);
			singlePlayer.computer.setName(p2Name);
			
			variableCellSize = (int)(boardSize/boardUnits);
		
			singlePlayer.player.setBoardLength(boardUnits-1);
			singlePlayer.computer.setBoardLength(boardUnits-1);
			
			singlePlayer.singlePlayerGame(4);
			
			drawBoard();
		}
		else if(load)
		{
			loadGUI();
			playerShipPlacementNotOver = false;
		}
				
		primaryStage.setTitle("Battleship");
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Sets both the player's scene and computer's scene, and in addition
	 * the win scene.
	 * 
	 * @param primaryStage
	 */
	public void createScene(Stage primaryStage)
	{
		//Scene 1
		root = new Group();
		scene = new Scene(root);
		canvas = new Canvas(wWidth, wHeight);
		gc = canvas.getGraphicsContext2D();
		canvas.setMouseTransparent(true);
						
		vBox = new VBox();
		stats = new Label();
		shipsSunkLabel = new Label();
		shipsSunkLabel2 = new Label();
		
		
		stats.setLayoutX(boardSize);
		root.getChildren().add(stats);
				
		shipsSunkLabel.setLayoutY((int)(margin/2));
		shipsSunkLabel2.setLayoutX(boardSize + margin);
		shipsSunkLabel2.setLayoutY((int)(margin/2));


		p1b1 = new Button("Done");
		p1b2 = new Button("Battleship");
		p1b3 = new Button("Submarine");
		p1b4 = new Button("Destroyer");
		p1b5 = new Button("Patrol Boat");
		p1b6 = new Button("Rotate");
		p1b7 = new Button("Save");
						
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
		p1b7.setLayoutX(wWidth - 50);

						
		//Sets Minimum size for consistency
		p1b1.setMinWidth(vBox.getPrefWidth());
		p1b2.setMinWidth(vBox.getPrefWidth());
		p1b3.setMinWidth(vBox.getPrefWidth());
		p1b4.setMinWidth(vBox.getPrefWidth());
		p1b5.setMinWidth(vBox.getPrefWidth());
		p1b6.setMinWidth(vBox.getPrefWidth());					
		
		root.getChildren().add(canvas);	

		if (!load)
		{
			root.getChildren().add(p1b2);
			root.getChildren().add(p1b3);
			root.getChildren().add(p1b4);
			root.getChildren().add(p1b5);
			root.getChildren().add(p1b6);
		}
			
		//Handle done button
		p1b1.setOnAction(e -> handleDoneButton(primaryStage));
			
		//Handle Ship Buttons
		p1b2.setOnAction(e -> handleShipButton(singlePlayer.player.battleship, singlePlayer.player));
		p1b3.setOnAction(e -> handleShipButton(singlePlayer.player.submarine, singlePlayer.player));
		p1b4.setOnAction(e -> handleShipButton(singlePlayer.player.destroyer, singlePlayer.player));
		p1b5.setOnAction(e -> handleShipButton(singlePlayer.player.patrolBoat, singlePlayer.player));

		//Handle rotate button
		p1b6.setOnAction(e -> handleRotateButton(singlePlayer.player));
		
		//Handle save button
		p1b7.setOnAction(e -> handleSaveButton());
		
		//Mouse event handler
		scene.setOnMouseClicked(e -> handleMouseClick(singlePlayer.player, e, primaryStage));
		
		//==========================================
		//Win Screen scene
		root2 = new Group();
		scene2 = new Scene(root2);
		canvas2 = new Canvas(wWidth, wHeight);
		gc2 = canvas2.getGraphicsContext2D();
		canvas2.setMouseTransparent(true);
		root2.getChildren().add(canvas2);

		vBox2 = new VBox();
		vBox2.setPrefWidth(75);
		
		gc2.setStroke(Color.BLACK);
				
		//Button
		quit = new Button("Quit");
				
		quit.setMinWidth(vBox2.getPrefWidth());
		quit.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		quit.setLayoutY(wHeight - 50);
		quit.setOnAction(e -> System.exit(0));
		root2.getChildren().add(quit);
	}
	
	/**
	 * Loads game data from single-player, and updates GUI
	 * to match the last saved game.
	 */
	public void loadGUI()
	{
		singlePlayer.load();
		
		//Sets board size
		boardUnits = singlePlayer.getBoard();
		//Sets cell size
		variableCellSize = (int)(boardSize/boardUnits);
		
		drawBoard();
		
		String fileName = "output.txt";
		Scanner inputStream = null;

		try
		{
			inputStream = new Scanner(new File(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening the file " + fileName);
			return;
			//System.exit(0);
		}
		
		for (int x = 0; x < boardUnits;	x++)
		{
			for(int y = 0; y < boardUnits; y++)
			{	
				int xl = x*variableCellSize;
				int yl = y*variableCellSize+margin+(boardSize-variableCellSize*boardUnits);
				
				//If player's own board has ship, and computer hasn't shot there yet
				if(singlePlayer.player.playerBoard.grid[x][y].getHasShip()&&!singlePlayer.computer.enemyBoard.grid[x][y].getBeenHit())
				{	
					gc.setFill(Color.BLACK);
					gc.fillRect(xl, yl, variableCellSize, variableCellSize);
				}
				//If computer's board for player is hit...
				if(singlePlayer.computer.enemyBoard.grid[x][y].getBeenHit())
				{				
					//and has a ship
					if (singlePlayer.computer.enemyBoard.grid[x][y].getHasShip())
					{
						gc.setFill(Color.RED);
					}
					//or has no ship
					else
					{
						gc.setFill(Color.GREY);
					}
					gc.fillRect(xl, yl, variableCellSize,variableCellSize);
				}
				
				xl = boardSize + margin + (variableCellSize * x) + (boardSize-variableCellSize*boardUnits);
				yl = (y*variableCellSize+margin+(boardSize-variableCellSize*boardUnits));
				
				//If player's enemy board has been hit
				if (singlePlayer.player.enemyBoard.grid[x][y].getBeenHit())
				{
					//And has a ship
					if (singlePlayer.player.enemyBoard.grid[x][y].getHasShip())
					{
						gc.setFill(Color.RED);
					}
					//or has no ship
					else
					{
						gc.setFill(Color.BLUE);
					}
					gc.fillRect(xl, yl, variableCellSize, variableCellSize);
				}
			}
		}
		if (singlePlayer.computer.battleship.getLife()<=0)
		{
			playerShipsSunk++;
		}	
		if (singlePlayer.computer.destroyer.getLife()<=0)
		{
			playerShipsSunk++;
		}		
		if (singlePlayer.computer.patrolBoat.getLife()<=0)
		{
			playerShipsSunk++;
		}		
		if (singlePlayer.computer.submarine.getLife()<=0)
		{
			playerShipsSunk++;
		}			
		if (singlePlayer.player.battleship.getLife()<=0)
		{
			computerShipsSunk++;
		}	
		if (singlePlayer.player.destroyer.getLife()<=0)
		{
			computerShipsSunk++;
		}		
		if (singlePlayer.player.patrolBoat.getLife()<=0)
		{
			computerShipsSunk++;
		}		
		if (singlePlayer.player.submarine.getLife()<=0)
		{
			computerShipsSunk++;
		}	
		gameLoaded = true;
	}
	
	/**
	 * Handles any ship button event that is thrown by the player.
	 * 
	 * @param ship 
	 * @param player
	 */
	public void handleShipButton(Ship ship, Player player)
	{
		System.out.println("Place " + ship.getName() + "\n");
		player.current = ship;
		//twoPlayer.player1.getStats();
		stats.setText(player.getStats());
	}
	
	/**
	 * Handles rotating current ship for the player.
	 * 
	 * @param player
	 */	
	public void handleRotateButton(Player player)
	{
		if(player.current.getName() == null)
		{
			//System.out.println("Please select a ship first.");
		}
		else
		{
			System.out.println("Rotated Ship\n");
			player.rotate();
			//twoPlayer.player1.getStats();
			stats.setText(player.getStats());
		}
	}
	
	/**
	 * Checks for a winner after each turn, and switches scene to 
	 * respective winner's win screen.
	 * 
	 * Switches game from ship placement phase to ship attaching phase
	 * on the first turn (ie. after the player has placed all ships).
	 * 
	 * @param primaryStage
	 */
	public void handleDoneButton(Stage primaryStage)
	{	
		numTurns++;
		
		if(numTurns == 1)//Only needs to be executed once
		{
			singlePlayer.shipPlacer(singlePlayer.computer.battleship);
			singlePlayer.shipPlacer(singlePlayer.computer.submarine);
			singlePlayer.shipPlacer(singlePlayer.computer.destroyer);
			singlePlayer.shipPlacer(singlePlayer.computer.patrolBoat);
			root.getChildren().remove(stats);
			playerShipPlacementNotOver = false;
			
			root.getChildren().add(shipsSunkLabel);
			root.getChildren().add(shipsSunkLabel2);
			root.getChildren().add(p1b7);		

		}
		
		root.getChildren().remove(p1b1);

		shotTaken = false;
		
		//Check for winner
		if(playerShipsSunk == 4)
		{
			System.out.println(p1Name + " won!");
			setWinScene(primaryStage, p1Name);
		}
		if(computerShipsSunk == 4)
		{
			System.out.println(p2Name + " won!");
			setWinScene(primaryStage, p2Name);
		}
		
		playerTurn = !playerTurn;
		singlePlayer.boardLinking();
	}
	
	/**
	 * Saves the game when button pressed
	 */
	public void handleSaveButton()
	{
		singlePlayer.save();
		System.out.println("Saved");
	}
	
	/**
	 * Handles clicks for placing ships, and ensures that desired
	 * placement is valid. After a valid ship placement is completed,
	 * ship is updated on the screen, and ships respective button is
	 * removed to ensure only one of each ship is placed.
	 * 
	 * Handles clicks for shooting at the enemy board, and ensures
	 * that shots are valid (ie. in bounds, not the same spot, and
	 * only one shot is taken). After valid shot is completed, shot
	 * is updated on the screen, and the player's respective done
	 * button appears to switch turns.
	 * 
	 * 
	 * @param player Player who made the click
	 * @param event Event to be handled
	 * @param primaryStage Primary Stage of the game
	 */
	public void handleMouseClick(Player player, MouseEvent event, Stage primaryStage)
	{
		//If still placing ships
		if(playerShipPlacementNotOver)
		{			
			if(player.current.getName() == null)
			{
				System.out.println("Please select a ship first.");
			}
			else
			{
				//System.out.println("X: " + (int)(event.getX()/cellSize) + ", Y: " + ((int)(event.getY()/cellSize)-2));
				
				System.out.println(singlePlayer.player.getBoardLength());
				
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

						gc.fillRect(x1,y1,w,h);
					}
					
					player.shipPlacement(player.current);
					singlePlayer.boardLinking();
					
					singlePlayer.player.setXPos(0);
					singlePlayer.player.setYPos(0);
					
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
					
					//Adds done button once all ships are placed, and removes rotate button
					if(player.shipsPlaced.size() == shipsRequired)
					{
						root.getChildren().add(p1b1);
						root.getChildren().remove(p1b6);
					}
					
					player.current = player.nullShip; //Null ship makes it so player must choose a new ship in between ship placements 
					stats.setText(player.getStats());

				}
			}
		}
		//No longer placing ships, now attacking phase
		else
		{
			if(playerTurn)
			{
				int x;
				int y;
			
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
						if (singlePlayer.player.enemyBoard.grid[x][y].getHasShip())// check if grid starts at 1 or 0
						{
							gc.setFill(Color.RED);
							gc.fillRect(xl,yl,w,w);
						
							if(singlePlayer.computer.shipChecker(x, y))
							{
								playerShipsSunk++;
								shipsSunkLabel.setText("# of ships sunk by " + p1Name + " = "+ playerShipsSunk);
							}
						}
						else //Hit empty spot
						{
							gc.setFill(Color.BLUE);
							gc.fillRect(xl,yl,w,w);
						}
						
						shotTaken = true;
						root.getChildren().add(p1b1);
						singlePlayer.boardLinking(); 
					}		
				}
			
				primaryStage.show();
			
			}
			else //AI shooting
			{
				singlePlayer.computer.shipFire();
				singlePlayer.computer.enemyBoard.grid[singlePlayer.computer.getXPos()][singlePlayer.computer.getYPos()].setBeenHit(true);
			
				int xc = singlePlayer.computer.getXPos()*variableCellSize;
				int yc = singlePlayer.computer.getYPos()*variableCellSize+margin+(boardSize-variableCellSize*boardUnits);
			
				if (singlePlayer.computer.enemyBoard.grid[singlePlayer.computer.getXPos()][singlePlayer.computer.getYPos()].getHasShip())
				{
					gc.setFill(Color.RED);
					gc.fillRect(xc,yc,variableCellSize,variableCellSize);
					
					if(singlePlayer.player.shipChecker(singlePlayer.computer.getXPos(), singlePlayer.computer.getYPos()))
					{
						computerShipsSunk++; 	
						shipsSunkLabel2.setText("# of ships sunk by Computer = "+ computerShipsSunk);
						singlePlayer.computer.resetShipFire();

					}
				}
				else//Hit empty spot
				{
					gc.setFill(Color.GRAY);
					gc.fillRect(xc,yc,variableCellSize,variableCellSize);				
				}
				
				checkForWinner(primaryStage);
				playerTurn = !playerTurn;
				primaryStage.show();

			}
		}					
	}
	
	/**
	 * Draws the board for the player's scene, and displays their
	 * name above their respective board.
	 * 
	 */
	public void drawBoard()
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
	
	/**
	 * Sets the text box to display the winning player's name
	 * and updates the scene.
	 * 
	 * @param primaryStage
	 * @param name Winning player's Name
	 */
	public void setWinScene(Stage primaryStage, String name)
	{
		gc2.strokeText(name, (int)(wWidth/2 - 20), (int)(wHeight/2));

		primaryStage.setScene(scene2); 
		primaryStage.show();
	}
	
	/**
	 * Checks for a winner.
	 * 
	 * @param primaryStage
	 */
	public void checkForWinner(Stage primaryStage)
	{
		if(playerShipsSunk == 4)
		{
			System.out.println(p1Name + " won!");
			setWinScene(primaryStage, p1Name);
		}
		if(computerShipsSunk == 4)
		{
			System.out.println(p2Name + " won!");
			setWinScene(primaryStage, p2Name);
		}
		
	}
	
	/**
	 * Setter for load
	 * @param bool
	 */
	public void setLoad(boolean bool)
	{
		load = bool;
	}
	
}
