package BackEnd;
import GUI.*;


import java.util.ArrayList;

/**
 * Class manages and performs functions corresponding to
 * the player object. Such as moving and manipulating their
 * selector, and board respectively.
 * @author Jackson, David, Nathanael, Ryan, and Sam
 */

public class Player
{
	private String name;
	public Board playerBoard = new Board();	//Creates board for player from board class
	public Board enemyBoard = new Board();		//Creates enemy board for player from board class
	private int xPos = 0;
	private int yPos = 0;

	private int boardLength = 7; //Since array starts at 0

	public Ship current = new Ship();
	public Ship battleship = new Ship();
	public Ship submarine = new Ship();
	public Ship destroyer = new Ship();
	public Ship patrolBoat = new Ship();

	public ArrayList<Ship> shipsPlaced = new ArrayList<Ship>();

	public void setBoardLength(int board)
	{
		boardLength = board;
	}

	public int getBoardLength()
	{
		return boardLength;
	}

	/**
	* Getter method for player name.
	* @return name Returns player name.
	*/
	public String getName()
	{
		return name;
	}

	/**
	* Setter method for player name.
	* @param name Name to be set.
	*/
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	* Getter method for player board.
	* @return playerBoard Returns player's board.
	*/
	public Board getPlayerBoard()
	{
		return playerBoard;
	}

	/**
	* Setter method for player board.
	* @param playerBoard Player's board set.
	*/
	public void setPlayerBoard(Board playerBoard)
	{
		this.playerBoard = playerBoard;
	}

	/**
	* Getter method for enemy board.
	* @return enemyBoard Returns enemy board.
	*/
	public Board getEnemyBoard()
	{
		return enemyBoard;
	}
	
	/**
	* Setter method for enemy board.
	* @param enemyBoard Enemy's board set.
	*/
	public void setEnemyBoard(Board enemyBoard)
	{
		this.enemyBoard = enemyBoard;
	}

	/**
	* Getter method for player's selected xPos.
	* @return xPos Returns player's selected xPos
	*/
	public int getXPos()
	{
		return xPos;
	}

	/**
	* Setter method for player's selected xPos.
	* @param xPos xPos to be set.
	*/
	public void setXPos(int xPos)
	{
		this.xPos = xPos;
	}

	/**
	* Getter method for player's selected yPos.
	* @return yPos Returns player's selected yPos
	*/
	public int getYPos()
	{
		return yPos;
	}

	/**
	* Setter method for player's selected yPos.
	* @param yPos yPos to be set.
	*/
	public void setYPos(int yPos)
	{
		this.yPos = yPos;
	}

	public Ship getCurrent()
	{
		return current;
	}


	public void setCurrent(Ship current)
	{
		this.current = current;
	}


	public Ship getBattleship()
	{
		return battleship;
	}


	public void setBattleship(Ship battleship)
	{
		this.battleship = battleship;
	}


	public Ship getSubmarine()
	{
		return submarine;
	}


	public void setSubmarine(Ship submarine)
	{
		this.submarine = submarine;
	}


	public Ship getDestroyer()
	{
		return destroyer;
	}


	public void setDestroyer(Ship destroyer)
	{
		this.destroyer = destroyer;
	}


	public Ship getPatrolBoat()
	{
		return patrolBoat;
	}


	public void setPatrolBoat(Ship patrolBoat)
	{
		this.patrolBoat = patrolBoat;
	}

	
    /**
     * The method prints a greeting for the enemy and player
     */
	public void display()
	{	
		System.out.println("Player: " + name);

		System.out.println("___________                             __________                       .___");
		System.out.println("\\_   _____/ ____   ____   _____ ___.__. \\______   \\ _________ _______  __| _/");
		System.out.println(" |    __)_ /    \\_/ __ \\ /     <   |  |  |    |  _//  _ \\__  \\\\_  __ \\/ __ | ");
		System.out.println(" |        \\   |  \\  ___/|  Y Y  \\___  |  |    |   (  <_> ) __ \\|  | \\/ /_/ | ");
		System.out.println("/_______  /___|  /\\___  >__|_|  / ____|  |______  /\\____(____  /__|  \\____ | ");
        System.out.println("	\\/     \\/     \\/      \\/\\/              \\/           \\/           \\/ ");
		System.out.println("Enemy Board");

		enemyBoard.enemyBoard();

		System.out.println("__________.__                              __________                       .___");
		System.out.println("\\______   \\  | _____  ___.__. ___________  \\______   \\ _________ _______  __| _/");
		System.out.println(" |     ___/  | \\__  \\<   |  |/ __ \\_  __ \\  |    |  _//  _ \\__  \\\\_  __ \\/ __ | ");
		System.out.println(" |    |   |  |__/ __ \\\\___  \\  ___/|  | \\/  |    |   (  <_> ) __ \\|  | \\/ /_/ | ");
		System.out.println(" |____|   |____(____  / ____|\\___  >__|     |______  /\\____(____  /__|  \\____ | ");
        System.out.println("                    \\/\\/         \\/                \\/           \\/           \\/ ");
		System.out.println("Player Board");

		playerBoard.playerBoard();
	}
	
	/**
	* The method checks and moves ship placer up if possible.
	*/
	public void shipPlacementMarkerMoveUp()
	{
		if(yPos > 0)
			yPos--;
		else //Tells user move not possible
			System.out.println("Sorry, you can't move up");
	}
	
	/**
	* The method checks and moves ship placer down if possible.
	*/
	public void shipPlacementMarkerMoveDown()
	{
		if(current.getVerticalOrientation() == true)//if vertical...
		{
			if(yPos + current.getLength() <= boardLength)//checks if added length will remain in bounds
				yPos++;

			else //Tells user move not possible
				System.out.println("Sorry, you can't move down");
		}

		else //otherwise horizontal...
		{
			if(yPos < boardLength)
			{
				yPos++;
			}

			else //Tells user move not possible
				System.out.println("Sorry, you can't move down");
		}
	}
	
	/**
	* The method checks and moves ship placer left if possible.
	*/
	public void shipPlacementMarkerMoveLeft()
	{
		if(xPos > 0)
			xPos--;
		else //Tells user move not possible
			System.out.println("Sorry, you can't move left");
	}
	
	/**
	* The method checks and moves ship placer right if possible.
	*/
	public void shipPlacementMarkerMoveRight()
	{
		if(current.getVerticalOrientation() == false)//if horizontal...
		{	
			if((xPos+current.getLength()) <= boardLength) //checks if added length will remain in bounds
			{
				xPos++;
			}
			else //Tells user move not possible
				System.out.println("Sorry, you can't move right");
		}
		else //otherwise vertical
		{
			if(xPos < boardLength)
			{
				xPos++;
			}

			else //Tells user move not possible
				System.out.println("Sorry, you can't move right");
		}
	}
	
    /**
     * The method moves the location of the player shooter up one grid
     */
	public void moveUp()
	{
		if(yPos > 0)
		{
			yPos--;
		}

		else //Tells user move not possible
			System.out.println("Sorry, you can't move up");
    }
	
	/**
	 * The method moves the location of the player shooter down one grid
	 */
	public void moveDown()
	{	
		if(yPos < boardLength)
			yPos++;
		else //Tells user move not possible
			System.out.println("Sorry, you can't move down");
	}
	
	/**
	 * The method moves the location of the player shooter left one grid
	 */
	public void moveLeft()
	{
		if(xPos > 0)
		{
			xPos--;
		}

		else //Tells user move not possible
			System.out.println("Sorry, you can't move left");
	}
	
	/**
	 * The method moves the location of the player shooter right one grid
	 */
	public void moveRight()
	{
		if(xPos < boardLength)
			xPos++;
		else //Tells user move not possible
			System.out.println("Sorry, you can't move right");
	}
	
	/**
	 * The method allows the player to place a ship and updates it inside the grid
	 */
	public void shipPlacement(Ship s)
	{
		s.setXPos(xPos); //sets x,y cords
		s.setYPos(yPos);	//TODO: This may be redundant so someone might want to change this
		s.setXPositions(xPos);
		s.setYPositions(yPos);

		shipsPlaced.add(s);

		for(int i = 0; i < shipsPlaced.size(); i++)
		{
			System.out.println(shipsPlaced.get(i).getName());
		}

		if(s.getVerticalOrientation())//checks if placement is valid for vertical
		{
			for(int j = 0; j < s.getLength(); j++)//loops through to update grid for the length of the ship
			{
				playerBoard.grid[xPos][yPos+j].setHasShip(true);
			 	playerBoard.grid[xPos][yPos+j].setShipName(s.getName());
			 }
		}
		else if(!s.getVerticalOrientation())//checks if placement is valid for horizontal
		{
			for(int i = 0; i < s.getLength(); i++)//loops through to update grid for the length of the ship
			{
				playerBoard.grid[xPos+i][yPos].setHasShip(true);
				playerBoard.grid[xPos+i][yPos].setShipName(s.getName());
			}
		}
	}
	
	public void printArrayList() 
	{
		for(int i = 0; i < shipsPlaced.size(); i++)
		{
			System.out.print("Ship: " + shipsPlaced.get(i).getName() + ", X: ");
			for(int j = 0; j < shipsPlaced.get(i).getXPositions().length; j++)
			{
				System.out.print(shipsPlaced.get(i).getXPositions()[j] + ", ");
			}
			System.out.print("Y: ");
			for(int k = 0; k < shipsPlaced.get(i).getYPositions().length; k++)
			{
				System.out.print(shipsPlaced.get(i).getYPositions()[k] + ", ");
			}		
			System.out.print("\n");
		}
		System.out.println("");
	}
	
	/**
	 * The method flips the orientation of the ship. Also, depending
	 * on where the ship is on the grid, it will move the ship accordingly
	 * to ensure the rotation does not move the ship out of the grid.
	 */
	public void rotate()
	{
		current.reverseOrientation(); //Rotates
		System.out.println(xPos);
		if((xPos + current.getLength()) > boardLength)//if new pos is out of grid, move inside
		{
			xPos -= current.getLength();
		}

		if(xPos < 0)
		{
			xPos += 1;
		}

		if((yPos + current.getLength()) > boardLength)//if new pos is out of grid, move inside
		{
			yPos -= current.getLength();
		}
		
		if(yPos < 0)
		{
			yPos += 1;
		}
	}
	
	//TODO: Needs Java Documentation
	public boolean shipChecker(int x, int y)
	{
		for(int i = 0; i < shipsPlaced.size(); i++)
		{
			if(shipsPlaced.get(i).xChecker(x) && shipsPlaced.get(i).yChecker(y))
			{
				shipsPlaced.get(i).setLife(shipsPlaced.get(i).getLife()-1);
				if(shipsPlaced.get(i).getLife() <= 0)
				{
					System.out.println("You have sunk my " + shipsPlaced.get(i).getName());
					return true;
				}
			}
		}
		return false;
	}

	public void beenHitChecker(int x, int y)
	{
		if(enemyBoard.grid[x][y].getBeenHit())
			System.out.println("Sorry, that spot has already been hit");
		else
			enemyBoard.grid[x][y].setBeenHit(true);
	}
	public boolean validShipPlacement(Ship s)
	{
		s.setXPos(xPos); 	//sets x,y cords
		s.setYPos(yPos);	//TODO: This may be redundant so someone might want to change this
		s.setXPositions(xPos);
		s.setYPositions(yPos);

		if(s.getVerticalOrientation())	//checks if placement is for vertical
		{
			for(int j = 0; j < current.getLength(); j++)	//loops through to update grid for the length of the ship
			{
				if(xPos <= boardLength && yPos >= 0 && yPos+j <= boardLength )	//if ship is in grid
				{
					if(playerBoard.grid[xPos][yPos+j].getHasShip())
					{
						System.out.println("Sorry, that is not a valid ship placement");
						return false;
					}
				}
				else	//Ship out of bounds
				{
					System.out.println("Ship placement is out of bounds.");
					return false;
				}
			}
		}
		else if(!s.getVerticalOrientation())	//checks if placement is for horizontal
		{
			for(int i = 0; i < current.getLength(); i++)	//loops through to update grid for the length of the ship
			{
				if(yPos <= boardLength && yPos >= 0 && xPos+i <= boardLength)
				{
					if(playerBoard.grid[xPos+i][yPos].getHasShip())
					{
						System.out.println("Sorry, that is not a valid ship placement");
						return false;
					}
				}
				else
				{
					System.out.println("Ship placement is out of bounds.");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * The method prints the stats of the players current selection,
	 * ship that is in the process of being placed, the corresponding
	 * size, ship orientation, and every spot currently occupied by the
	 * ship being placed.
	 */
	public String getStats()
	{
		String statsString = "";
		//Stats printed in nice layout for vertical
		System.out.println("Ship: " + current.getName());
		System.out.println("Length: " + current.getLength());
		statsString += "Ship: " + current.getName();
		statsString += "\nLength: "  + current.getLength();
		if (current.getVerticalOrientation() == true){
			System.out.println("Orientation: Vertical");
			System.out.print("X: " + (xPos + 1));
			System.out.println();
			System.out.print("Y: ");
			statsString += "\nOrientation: Vertical";
			for(int i = 0; i < current.getLength(); i++){
				System.out.print((yPos+(i+1)) + ", ");
			}
			System.out.println();

		}
		else{
			//Stats printed in nice layout for horizontal
			System.out.println("Orientation: Horizontal");
			System.out.print("X: " );
			statsString += "\nOrientation: Horizontal";
			for(int i = 0; i < current.getLength(); i++){
				System.out.print((xPos+(i+1)) + ", ");
			}
			System.out.println();
			System.out.println("Y: " + (yPos + 1));
		}
		
		return statsString;
	}
	public boolean hitChecker(int x, int y)
	{
		
		return !enemyBoard.grid[x][y].getBeenHit();
	}

}
