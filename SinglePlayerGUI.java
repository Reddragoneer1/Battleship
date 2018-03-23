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

import java.util.Scanner;

public class SinglePlayerGUI extends Application
{
	SinglePlayer singlePlayer = new SinglePlayer();
		
	private boolean playerShipPlacementNotOver=true;
	private boolean playerFiring = false;
	private boolean gameOver = false;
	private int computerShipsSunk = 0;
	private int playerShipsSunk = 0;
	private String playerName = singlePlayer.player.getName();
	
	//CONSTANTS
	final int cellSize = 50; //with and height of cell
	final int boardSize = 8; //8x8 Grid
	final int wWidth = cellSize * 2*(boardSize + 1);
	final int wHeight = cellSize * (boardSize + 2);
	final int margin = 2;
	final int shipsRequired = 4;

	//JavaFX Init GUI Fundamentals
	Group root; 
	Scene scene;
	Canvas canvas;
	GraphicsContext gc;
	
	VBox vBox;
	Label stats = new Label();
	Label shipsSunkLabel = new Label();
	Label shipsSunkLabel2 = new Label();
	Label playerNameLabel = new Label(playerName);
	
	//Creates Buttons
	Button b1 = new Button("Done");
	Button b2 = new Button("Battleship");
	Button b3 = new Button("Submarine");
	Button b4 = new Button("Destroyer");
	Button b5 = new Button("Patrol Boat");
	Button b6 = new Button("Rotate");
	
	public static void main(String[] args)
	{
		launch(args); 
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		root = new Group();
		scene = new Scene(root);
		canvas = new Canvas(wWidth, wHeight);
		gc = canvas.getGraphicsContext2D();
		vBox = new VBox();
		
		singlePlayer.singlePlayerGame(4);
		canvas.setMouseTransparent(true);
		vBox.setPrefWidth(75);
		
		primaryStage.setTitle("Battleship");
		primaryStage.setScene(scene);

		gameBoardSetup(primaryStage);
		primaryStage.show();
	}
	
	public void winnerIsDetermined(Stage primaryStage)
	{
		if (gameOver == true)
		{
			Scanner input = new Scanner(System.in);
			String line = input.nextLine();
			primaryStage.close();
		}
	}
	
	public void gameBoardSetup(Stage primaryStage)
	{
		gc.setStroke(Color.BLACK);
		gc.strokeText("Player Board", 0, (int)(cellSize/5));
		gc.strokeText("Enemy Board", cellSize * (boardSize + 2), (int)(cellSize/5));
		root.getChildren().add(canvas);
		drawBoard();
		
		stats.setText(singlePlayer.player.getStats());
		stats.setLayoutX(cellSize * boardSize);
		shipsSunkLabel.setText("Number of computer ships sunk = " + computerShipsSunk);
		shipsSunkLabel.setLayoutY(cellSize);
		shipsSunkLabel2.setText("Number of player ships sunk = " + playerShipsSunk);
		shipsSunkLabel2.setLayoutY(cellSize);
		shipsSunkLabel.setLayoutX(10*cellSize);
		playerNameLabel.setLayoutY(25);
		
		
		root.getChildren().add(stats);
		root.getChildren().add(shipsSunkLabel);
		root.getChildren().add(shipsSunkLabel2);
		root.getChildren().add(playerNameLabel);
			
		b1.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		b1.setLayoutY(wHeight - cellSize);
		b2.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		b2.setLayoutY(wHeight - 2*cellSize);
		b3.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		b3.setLayoutY(wHeight - 3*cellSize);
		b4.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		b4.setLayoutY(wHeight - 4*cellSize);
		b5.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		b5.setLayoutY(wHeight - 5*cellSize);
		b6.setLayoutX((int)(wWidth/2) - (int)(vBox.getPrefWidth()/2));
		b6.setLayoutY(wHeight - 6*cellSize);
			
		//Sets Minimum size for consistency
		b1.setMinWidth(vBox.getPrefWidth());
		b2.setMinWidth(vBox.getPrefWidth());
		b3.setMinWidth(vBox.getPrefWidth());
		b4.setMinWidth(vBox.getPrefWidth());
		b5.setMinWidth(vBox.getPrefWidth());
		b6.setMinWidth(vBox.getPrefWidth());
			
		//Adds Buttons
		root.getChildren().add(b2);
		root.getChildren().add(b3);
		root.getChildren().add(b4);
		root.getChildren().add(b5);
		root.getChildren().add(b6);

		//Handle done button
		b1.setOnAction(e -> handleDoneButton());
			
		//Handle Ship Buttons
		b2.setOnAction(e -> handleShipButton(singlePlayer.player.battleship, singlePlayer.player));
		b3.setOnAction(e -> handleShipButton(singlePlayer.player.submarine, singlePlayer.player));
		b4.setOnAction(e -> handleShipButton(singlePlayer.player.destroyer, singlePlayer.player));
		b5.setOnAction(e -> handleShipButton(singlePlayer.player.patrolBoat, singlePlayer.player));

		//Handle rotate button
		b6.setOnAction(e -> handleRotateButton(singlePlayer.player));

		//Mouse event handler
		scene.setOnMouseClicked(e -> handleMouseClick(singlePlayer.player, e, primaryStage));
	}

	public void handleShipButton(Ship ship, Player player)
	{
		System.out.println("Place " + ship.getName() + "\n");
		player.current = ship;
		//twoPlayer.player1.getStats();
		stats.setText(player.getStats());
	}
	
	//Handle rotate button
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
	
	//Handle done button
	public void handleDoneButton()
	{
		playerShipPlacementNotOver = false;
		
		root.getChildren().remove(b6);
		root.getChildren().remove(b1);
		root.getChildren().remove(stats);
		
		singlePlayer.shipPlacer(singlePlayer.computer.battleship);
		singlePlayer.shipPlacer(singlePlayer.computer.submarine);
		singlePlayer.shipPlacer(singlePlayer.computer.destroyer);
		singlePlayer.shipPlacer(singlePlayer.computer.patrolBoat);
		
		singlePlayer.boardLinking();
	}
	
	//Handle mouse clicks
	public void handleMouseClick(Player player, MouseEvent event, Stage primaryStage)
	{
		if (playerShipPlacementNotOver==true)
		{			
			if(player.current.getName() == null)
			{
				//System.out.println("Please select a ship first.");
			}
			else
			{
				//System.out.println("X: " + (int)(event.getX()/cellSize) + ", Y: " + ((int)(event.getY()/cellSize)-2));
				player.setXPos((int)(event.getX()/cellSize));
				player.setYPos((int)(event.getY()/cellSize)-2);

				boolean validPlacement = player.validShipPlacement(player.current);
				if(validPlacement)
				{
					if(player.current.getVerticalOrientation())//Ship vertical
					{
						int[] y = player.current.getYPositions();
						int x = player.current.getXPos();
						
						int x1 = cellSize*x;
						int y1 = cellSize*y[0] + cellSize*2;
						int w = cellSize;
						int h = cellSize*player.current.getLength();
									
						gc.fillRect(x1,y1,w,h);
						singlePlayer.player.shipPlacement(player.current);
						singlePlayer.player.setXPos(0);
						singlePlayer.player.setYPos(0);
					}
					else //Ship horizontal
					{
						int[] x = player.current.getXPositions();
						int y = player.current.getYPos();
						
						int x1 = cellSize*x[0];
						int y1 = cellSize*y + cellSize*2;
						int w = cellSize*player.current.getLength();
						int h = cellSize;

						gc.fillRect(x1,y1,w,h);
						singlePlayer.player.shipPlacement(player.current);
						singlePlayer.player.setXPos(0);
						singlePlayer.player.setYPos(0);
					}
					
					if(player.current.getName() == "Battleship")
					{
						root.getChildren().remove(b2);
					}
					else if(player.current.getName() == "Submarine")
					{
						root.getChildren().remove(b3);
					}
					else if(player.current.getName() == "Destroyer")
					{
						root.getChildren().remove(b4);
					}
					else
					{
						root.getChildren().remove(b5);
					}	
					
					if(player.shipsPlaced.size() == shipsRequired)
					{
						root.getChildren().add(b1);
						root.getChildren().remove(b6);
					}
					
					stats.setText(player.getStats());
					//player.current.setName(null); //Forces player to select a new ship to place 	//NATHANAEL CHANGED

				}
			}
		}
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
			if (x >= 0 && y >= 0 && x <= 7 && y <= 7)
			{
				if(!player.enemyBoard.grid[x][y].getBeenHit())
				{
					player.enemyBoard.grid[x][y].setBeenHit(true);
					if (singlePlayer.player.enemyBoard.grid[x][y].getHasShip())// check if grid starts at 1 or 0
					{
						System.out.println("hit ship");
						gc.setFill(Color.RED);
						gc.fillRect(xl,yl,w,h);
						
						if(singlePlayer.computer.shipChecker(x, y)) {
							computerShipsSunk++;	
							shipsSunkLabel.setText("Number of computer ships sunk = " + computerShipsSunk);
							if (playerShipsSunk == 4 || computerShipsSunk == 4)
							{
								gameOver = true;
								System.out.println("GAME OVER: PLAYER WINS");
							}
							winnerIsDetermined(primaryStage);
						}

						
					}
					else 
					{
						gc.setFill(Color.BLUE);
						gc.fillRect(xl,yl,w,h);
					}
				}
				else
				{
					System.out.println("You've already hit this spot");
					//Label saying already hit this spot
				}
				//AI shooting
				singlePlayer.computer.shipFire();
				singlePlayer.computer.enemyBoard.grid[singlePlayer.computer.getXPos()][singlePlayer.computer.getYPos()].setBeenHit(true);
				int xc = singlePlayer.computer.getXPos()*cellSize;
				int yc = singlePlayer.computer.getYPos()*cellSize+(2*cellSize);
				gc.setFill(Color.RED);
				if (singlePlayer.computer.enemyBoard.grid[singlePlayer.computer.getXPos()][singlePlayer.computer.getYPos()].getHasShip())
				{
					gc.setFill(Color.RED);
					gc.fillRect(xc,yc,cellSize,cellSize);
					if(singlePlayer.player.shipChecker(singlePlayer.computer.getXPos(), singlePlayer.computer.getYPos()))
					{
							playerShipsSunk++;	
							shipsSunkLabel2.setText("Number of player ships sunk = "+ playerShipsSunk);
							if (playerShipsSunk == 4 || computerShipsSunk == 4)
							{
								gameOver = true;
								System.out.println("GAME OVER: CPU WINS");
							}
							winnerIsDetermined(primaryStage);

					}
				}
				else
				{
					gc.setFill(Color.GRAY);
					gc.fillRect(xc,yc,cellSize,cellSize);				
				}
			}				
		}
	}
	
	//Draws the main frame of the screen
	public void drawBoard()
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
}
