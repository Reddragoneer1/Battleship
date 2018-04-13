package BackEnd;
import GUI.*;

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
/**
 * The class is the basis for the one player game mode.
 * It sets the player user name, allows the player to place
 * their ships within the board, and goes through the turn
 * based firing actions after the player is done initializing
 * their board. The player will play against an AI.
 * 
 * @author Jackson, David, Nathanael, Ryan, and Sam
 */
public class SinglePlayer
{
	public Scanner input = new Scanner(System.in);
	private boolean winner = false;
	public Player player = new Player();
	public Ai computer = new Ai();
	private int board;
	private boolean playerturn = true;
	private boolean playerDoneTurn = false;
	private boolean gameLoaded = false;

	/**
	 * Setter for board
	 * @param b
	 */
	public void setBoard(int b)
	{
		this.board = b;
	}
	
	/**
	 * Getter for board
	 * @return board 
	 */
	public int getBoard()
	{
		return board;
	}
	
/**
* Getter for board size
*/
	public void setBoardSize()
	{
		player.setBoardLength(board-1);
		computer.setBoardLength(board-1);	
	}
/**
* Function for changing board size
* @param x - Size that the grid will be changed too
*/
	public void choosingBoardSize(int x)
	{
		player.playerBoard.chooseBoardSize(x);
		player.enemyBoard.chooseBoardSize(x);
		computer.playerBoard.chooseBoardSize(x);
		computer.enemyBoard.chooseBoardSize(x);
	}
/**
* Gets the previous saved game
*/
	public boolean getGameLoaded()
	{
		return gameLoaded;
	}
/**
* Saves the current loaded game
*/
	public void save()
	{
		String fileName = "singleplayer.txt";

		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(fileName);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}
		outputStream.println(board);
		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				outputStream.print(player.playerBoard.grid[i][j].getHasShip() + " " + player.playerBoard.grid[i][j].getBeenHit() + " ");
			}
			outputStream.println();
		}
		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				outputStream.print(player.enemyBoard.grid[i][j].getHasShip() + " " + player.enemyBoard.grid[i][j].getBeenHit() + " ");
			}
			outputStream.println();
		}

		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				outputStream.print(computer.playerBoard.grid[i][j].getHasShip() + " " + computer.playerBoard.grid[i][j].getBeenHit() + " ");
			}
			outputStream.println();
		}
		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				outputStream.print(computer.enemyBoard.grid[i][j].getHasShip() + " " + computer.enemyBoard.grid[i][j].getBeenHit() + " ");
			}
			outputStream.println();
		}
			
		outputStream.println(playerturn);
		
		outputStream.println(player.shipsPlaced.size());
		outputStream.println(computer.shipsPlaced.size());
		
		for(int i = 0; i < player.shipsPlaced.size(); i++)
		{
			outputStream.print(player.shipsPlaced.get(i).getName() + " " + player.shipsPlaced.get(i).getXPos() + " " + player.shipsPlaced.get(i).getYPos() + " " + player.shipsPlaced.get(i).getLife()+" " + player.shipsPlaced.get(i).getVerticalOrientation()+" ");
		}
		outputStream.println();
		for(int i = 0; i < computer.shipsPlaced.size(); i++)
		{
			outputStream.print(computer.shipsPlaced.get(i).getName() + " " + computer.shipsPlaced.get(i).getXPos() + " " + computer.shipsPlaced.get(i).getYPos() + " " + computer.shipsPlaced.get(i).getLife()+" " + computer.shipsPlaced.get(i).getVerticalOrientation()+" ");
		}
			
		outputStream.println();
		outputStream.println(player.getName());
		outputStream.println(computer.getName());
		System.out.println("Got here");

		outputStream.close();
	}
/**
* reads the previous played game from text file
*/
	public void load()
	{
		String fileName = "singleplayer.txt";
		Scanner inputStream = null;

		try
		{
			inputStream = new Scanner(new File(fileName));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}
		board = inputStream.nextInt();
		inputStream.nextLine();

		choosingBoardSize(board);
		setBoardSize();
		player.playerBoard.boardPopulate();	//populates boards
		player.enemyBoard.boardPopulate();
		computer.playerBoard.boardPopulate();
		computer.enemyBoard.boardPopulate();

		//Creates all ships for both players
		player.battleship.setName("Battleship");
		player.battleship.setLength(4);
		player.battleship.setLife(4);

		player.submarine.setName("Submarine");
		player.submarine.setLength(3);
		player.submarine.setLife(3);

		player.destroyer.setName("Destroyer");
		player.destroyer.setLength(3);		
		player.destroyer.setLife(3);

		player.patrolBoat.setName("Patrol Boat");
		player.patrolBoat.setLength(2);
		player.patrolBoat.setLife(2);

		computer.battleship.setName("Battleship");
		computer.battleship.setLength(4);
		computer.battleship.setLife(4);

		computer.submarine.setName("Submarine");
		computer.submarine.setLength(3);
		computer.submarine.setLife(3);

		computer.destroyer.setName("Destroyer");
		computer.destroyer.setLength(3);
		computer.destroyer.setLife(3);

		computer.patrolBoat.setName("Patrol Boat");
		computer.patrolBoat.setLength(2);
		computer.patrolBoat.setLife(2);

		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				player.playerBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				player.playerBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());
			}
			inputStream.nextLine();

		}
		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				player.enemyBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				player.enemyBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());			
			}
			inputStream.nextLine();
		}

		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				computer.playerBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				computer.playerBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());
			}
		}
		for(int i = 0; i < board; i++)
		{
			for(int j = 0; j < board; j++)
			{
				computer.enemyBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				computer.enemyBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());			
			}
		}
				
		playerturn = inputStream.nextBoolean();
		
		int playerShipSize = inputStream.nextInt();
		int computerShipSize = inputStream.nextInt();
		System.out.println(playerShipSize+","+computerShipSize);
		
		for(int i = 0; i < playerShipSize; i++)
		{
			String s = inputStream.next();
			if (s.equals("Battleship"))
			{
				player.battleship.setName(s);
				player.battleship.setXPos(inputStream.nextInt());
				player.battleship.setYPos(inputStream.nextInt());
				player.battleship.setLife(inputStream.nextInt());
				player.battleship.setVerticalOrientation(inputStream.nextBoolean());

				player.battleship.setYPositions(player.battleship.getYPos());
				player.battleship.setXPositions(player.battleship.getXPos());
				
				player.shipsPlaced.add(player.battleship);
			}
			if (s.equals("Destroyer"))
			{
				player.destroyer.setName(s);
				player.destroyer.setXPos(inputStream.nextInt());
				player.destroyer.setYPos(inputStream.nextInt());
				player.destroyer.setLife(inputStream.nextInt());
				player.destroyer.setVerticalOrientation(inputStream.nextBoolean());
				
				player.destroyer.setYPositions(player.destroyer.getYPos());
				player.destroyer.setXPositions(player.destroyer.getXPos());
				
				player.shipsPlaced.add(player.destroyer);
			}
			if (s.equals("Submarine"))
			{
				player.submarine.setName(s);
				player.submarine.setXPos(inputStream.nextInt());
				player.submarine.setYPos(inputStream.nextInt());
				player.submarine.setLife(inputStream.nextInt());
				player.submarine.setVerticalOrientation(inputStream.nextBoolean());

				player.submarine.setYPositions(player.submarine.getYPos());
				player.submarine.setXPositions(player.submarine.getXPos());
				
				player.shipsPlaced.add(player.submarine);
			}
			if (s.equals("PatrolBoat"))
			{
				player.patrolBoat.setName(s);
				player.patrolBoat.setXPos(inputStream.nextInt());
				player.patrolBoat.setYPos(inputStream.nextInt());
				player.patrolBoat.setLife(inputStream.nextInt());
				player.patrolBoat.setVerticalOrientation(inputStream.nextBoolean());
				
				player.patrolBoat.setYPositions(player.patrolBoat.getYPos());
				player.patrolBoat.setXPositions(player.patrolBoat.getXPos());

				player.shipsPlaced.add(player.patrolBoat);
			}
			
			/*player.shipsPlaced.get(i).setName(inputStream.next());
			player.shipsPlaced.get(i).setXPos(inputStream.nextInt());
			player.shipsPlaced.get(i).setYPos(inputStream.nextInt());
			player.shipsPlaced.get(i).setLife(inputStream.nextInt());*/
			
		}
		for(int i = 0; i < computerShipSize; i++)
		{
			String s = inputStream.next();
			if (s.equals("Battleship"))
			{
				computer.battleship.setName(s);
				computer.battleship.setXPos(inputStream.nextInt());
				computer.battleship.setYPos(inputStream.nextInt());
				computer.battleship.setLife(inputStream.nextInt());
				computer.battleship.setVerticalOrientation(inputStream.nextBoolean());

				computer.battleship.setYPositions(computer.battleship.getYPos());
				computer.battleship.setXPositions(computer.battleship.getXPos());
				
				computer.shipsPlaced.add(computer.battleship);
			}
			if (s.equals("Destroyer"))
			{
				computer.destroyer.setName(s);
				computer.destroyer.setXPos(inputStream.nextInt());
				computer.destroyer.setYPos(inputStream.nextInt());
				computer.destroyer.setLife(inputStream.nextInt());
				computer.destroyer.setVerticalOrientation(inputStream.nextBoolean());

				computer.destroyer.setYPositions(computer.destroyer.getYPos());
				computer.destroyer.setXPositions(computer.destroyer.getXPos());
				
				computer.shipsPlaced.add(computer.destroyer);
			}
			if (s.equals("Submarine"))
			{
				computer.submarine.setName(s);
				computer.submarine.setXPos(inputStream.nextInt());
				computer.submarine.setYPos(inputStream.nextInt());
				computer.submarine.setLife(inputStream.nextInt());
				computer.submarine.setVerticalOrientation(inputStream.nextBoolean());

				computer.submarine.setYPositions(computer.submarine.getYPos());
				computer.submarine.setXPositions(computer.submarine.getXPos());
				
				computer.shipsPlaced.add(computer.submarine);
			}
			if (s.equals("PatrolBoat"))
			{
				computer.patrolBoat.setName(s);
				computer.patrolBoat.setXPos(inputStream.nextInt());
				computer.patrolBoat.setYPos(inputStream.nextInt());
				computer.patrolBoat.setLife(inputStream.nextInt());
				computer.patrolBoat.setVerticalOrientation(inputStream.nextBoolean());

				computer.patrolBoat.setYPositions(computer.patrolBoat.getYPos());
				computer.patrolBoat.setXPositions(computer.patrolBoat.getXPos());
				
				computer.shipsPlaced.add(computer.patrolBoat);
			}
			/*computer.shipsPlaced.get(i).setName(inputStream.next());
			computer.shipsPlaced.get(i).setXPos(inputStream.nextInt());
			computer.shipsPlaced.get(i).setYPos(inputStream.nextInt());
			computer.shipsPlaced.get(i).setLife(inputStream.nextInt());*/
		}
		inputStream.nextLine();
		inputStream.nextLine();
		player.setName(inputStream.nextLine());
		computer.setName(inputStream.nextLine());	
		
		gameLoaded = true;
	}
/**
* Initializes the single player game, places ships ect.
* @param gameMode - Ensures that the set game mode is == 1
*/
	public void singlePlayerGame(int gameMode)
	{
		if(gameMode == 1)
		{
			System.out.println("Would you like to load a game? y/n");
			String stringLoadGame = input.next();
			char loadGame = stringLoadGame.charAt(0);
			if(loadGame == 'y')
			{
				load();
			}
			else
			{
				System.out.println("Please input your username: ");
				player.setName(input.next());
				computer.setName("Computer");
	
				System.out.println("What size would you like the board?");
				board = input.nextInt();
			}
		}
		
		choosingBoardSize(board);
		setBoardSize();
	
		player.playerBoard.boardPopulate();	//populates boards
		player.enemyBoard.boardPopulate();
		computer.playerBoard.boardPopulate();	//populates boards
		computer.enemyBoard.boardPopulate();
	
		//PLAYER SHIPS
		player.battleship.setName("Battleship");
		player.battleship.setLength(4);
		player.battleship.setLife(4);
	
		player.submarine.setName("Submarine");
		player.submarine.setLength(3);
		player.submarine.setLife(3);
	
		player.destroyer.setName("Destroyer");
		player.destroyer.setLength(3);		
		player.destroyer.setLife(3);
	
		player.patrolBoat.setName("Patrol Boat");
		player.patrolBoat.setLength(2);
		player.patrolBoat.setLife(2);
				
		//COMPUTER SHIPS
		computer.battleship.setName("Battleship");
		computer.battleship.setLength(4);
		computer.battleship.setLife(4);
	
		computer.submarine.setName("Submarine");
		computer.submarine.setLength(3);
		computer.submarine.setLife(3);
	
		computer.destroyer.setName("Destroyer");
		computer.destroyer.setLength(3);		
		computer.destroyer.setLife(3);
	
		computer.patrolBoat.setName("Patrol Boat");
		computer.patrolBoat.setLength(2);
		computer.patrolBoat.setLife(2);
	}

/**
* Selection screen for player to place ships
* also places AI's ships
*/	
	public void shipPlacementSelection(){

	boolean playerShipPlacementNotOver = true;

	do //loops through player 1's turn to place boats
		{
			player.display(); //display boards
			char playerShipMove = '1';
			System.out.println("Please select a ship to place \n1. Battleship \n2. Submarine \n3. Destroyer \n4. Patrol Boat \n0. Done");
			int playerShipSelector = input.nextInt(); //ship selection
			if(playerShipSelector == 1)
				player.current = player.battleship;
			if(playerShipSelector == 2)
				player.current = player.submarine;
			if(playerShipSelector == 3)
				player.current = player.destroyer;
			if(playerShipSelector == 4)
				player.current = player.patrolBoat;
			if(playerShipSelector == 0) //Finish placing boats
			{
				playerShipPlacementNotOver = false;
				break;
			}
			do //Loops through ship placement
			{	
				System.out.println("WASD to move, R to rotate and E to place");
				player.getStats(); //prints ship stats
				String playerShipMoveString = input.next();
				playerShipMove = playerShipMoveString.charAt(0);
				if(playerShipMove == 'w')
					player.shipPlacementMarkerMoveUp();
				if(playerShipMove == 'a')
					player.shipPlacementMarkerMoveLeft();
				if(playerShipMove == 's')
					player.shipPlacementMarkerMoveDown();
				if(playerShipMove == 'd')
					player.shipPlacementMarkerMoveRight();
				if(playerShipMove == 'e') //places ship
				{
					if(player.validShipPlacement(player.current))
					{
						player.shipPlacement(player.current);
						player.setXPos(0);
						player.setYPos(0);
						playerShipMove = '0';
					}
				}
				if(playerShipMove == 'i')
					save();
				if(playerShipMove == 'r') //rotates ship
					player.rotate();
			}while(playerShipMove != '0');//while player not done placing ship
			System.out.println(player.current.getName());
		}while(playerShipPlacementNotOver);//while player not done turn

		//GOES THROUGH EACH SHIP FOR AI
		shipPlacer(computer.battleship);
		shipPlacer(computer.submarine);
		shipPlacer(computer.destroyer);
		shipPlacer(computer.patrolBoat);

	}

/**
* Error checks and places ships (makes sures there is no overlapping)
*/
	public void shipPlacer(Ship s){
		boolean isValidShipPlacement;
		do{
			isValidShipPlacement = true;
			computer.randomShipPlacement();
			computer.current = s;
			if(computer.validShipPlacement(computer.current)) 
				computer.shipPlacement(computer.current);
			else 
				isValidShipPlacement = false;
		}while(!isValidShipPlacement);
	}

/**
* Links the player board with the ememy board
* takes information from the computer's board and fills in the enemy board
*/

	public void boardLinking()
	{
		for(int i = 0; i < board; i++)//iterates through all spaces in board and links the two players boards
		{
			for(int j = 0; j < board; j++)
			{
				player.enemyBoard.grid[i][j].setHasShip(computer.playerBoard.grid[i][j].getHasShip());
				player.enemyBoard.grid[i][j].setShipName(computer.playerBoard.grid[i][j].getShipName());
				computer.enemyBoard.grid[i][j].setHasShip(player.playerBoard.grid[i][j].getHasShip());
				computer.enemyBoard.grid[i][j].setShipName(player.playerBoard.grid[i][j].getShipName());
			}
		}
		System.out.println("TESTING: Board has been linked");
	}
/**
* Rotates turns between player and computer 
*/
	public void playerTurn()
	{
		int playerShipsSunk = 0;
		int computerShipsSunk = 0;
		do
		{
			System.out.println("Press c to clear screen");
			String switchTurn = input.next();
			char switchTurnChar = switchTurn.charAt(0);
			if(switchTurnChar == 'c')
			{
				if(playerturn)	//Turn switcher
					playerturn = false;
				else if(!playerturn)
					playerturn = true;
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); //Clear Screen
			}
			if(playerturn) //Player turn changers
			{
				do //player 1 turn
				{
					//Prints helpful stats
					System.out.println("Selector is on: " + player.getXPos() + " " + player.getYPos());
					System.out.println("Press 0 to end your turn, wasd to move and f to fire");
					player.display();
					String movePointerString = input.next();
					char movePointer = movePointerString.charAt(0);
					if(movePointer == 'w')
						player.moveUp();
					if(movePointer == 'a')
						player.moveLeft();
					if(movePointer == 's')
						player.moveDown();
					if(movePointer == 'd')
						player.moveRight();
					if(movePointer == '0')
						playerDoneTurn = true;
					if(movePointer == 'f')//fire weapon
					{
						if(!player.enemyBoard.grid[player.getXPos()][player.getYPos()].getBeenHit())
						{
							player.enemyBoard.grid[player.getXPos()][player.getYPos()].setBeenHit(true);
							player.display();

							if(computer.shipChecker(player.getXPos(), player.getYPos()))
								playerShipsSunk++;
							playerDoneTurn = true;
							break;
						}
						else
							System.out.println("You've already hit that spot");
					}
				}while(!playerDoneTurn);//while player 1 is not done their turn
				playerDoneTurn = false;
			}
			else
			{
				do //computer turn (for more specific comments, see player one's code above).
				{
					computer.shipFire();
					computer.enemyBoard.grid[computer.getXPos()][computer.getYPos()].setBeenHit(true);
					computer.aiDisplay(); //displays the AI's Enemy board

					if(player.shipChecker(player.getXPos(), player.getYPos()))
						computerShipsSunk++;
					playerDoneTurn = true;		
				}while(!playerDoneTurn);	//while player 2 is not done their turn
				playerDoneTurn = false;
			}
			if(playerShipsSunk == 4 || computerShipsSunk == 4)
				winner = true;
		}while(winner == false);	//while there is no winner
		System.out.println((playerShipsSunk == 4 ? player.getName() : computer.getName()) + " has won");//prints winner
	}
}


