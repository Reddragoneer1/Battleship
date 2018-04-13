package BackEnd;
import GUI.*;

import java.util.Random;
/**
* The class extends Player
* Used in single player, targets ship randomly but once a ship is hit it'll
* fire more intelligently 
* @author Jackson, David, Nathanael, Ryan, and Sam
*/

public class Ai extends Player{
	Random rand = new Random();
	private int direction = 0;
	private boolean smartFire = false;
	private boolean foundTarget = false;
	private int hitShipXPos;
	private int hitShipYPos;
	private int orginHitXPos;
	private int orginHitYPos;

	
	/**
	* Method determinds if a ship has been targeted in the previous
	* round.
	*
	*/
	public void shipFire(){
		if(smartFire && foundTarget) smartTargetedShipFire();
		else if(smartFire && !foundTarget) smartShipFire();
		else randomShipFire();
	}

	
	/**
	* Displays the AI's board
	*/
	public void aiDisplay(){ // displays the AI's enemy board (the player's board)

		System.out.println("Player: " + getName());

		System.out.println("___________                             __________                       .___");
		System.out.println("\\_   _____/ ____   ____   _____ ___.__. \\______   \\ _________ _______  __| _/");
		System.out.println(" |    __)_ /    \\_/ __ \\ /     <   |  |  |    |  _//  _ \\__  \\\\_  __ \\/ __ | ");
		System.out.println(" |        \\   |  \\  ___/|  Y Y  \\___  |  |    |   (  <_> ) __ \\|  | \\/ /_/ | ");
		System.out.println("/_______  /___|  /\\___  >__|_|  / ____|  |______  /\\____(____  /__|  \\____ | ");
        System.out.println("	\\/     \\/     \\/      \\/\\/              \\/           \\/           \\/ ");

		enemyBoard.enemyBoard();
	}

	
	/**
	*	Getter for the current grid size
	*/
	public int getGridSize(){
		return(getBoardLength()+1); //change for getter
	}

	
	/**
	* Fires on the board randomly, and checks if the fire is a hit
	* 
	*/
	public void randomShipPlacement(){
		boolean changeOrientation = rand.nextBoolean();
		if(changeOrientation) rotate();
		int shipLength = current.getLength();
		if(current.getVerticalOrientation()){
			boolean isValidYPos = false;
			do{
				int randXPos = rand.nextInt(getGridSize());
				int randYPos = rand.nextInt(getGridSize());
				if((shipLength + randYPos) >= getGridSize()){
				}
				else{
					setXPos(randXPos);
					setYPos(randYPos);
					isValidYPos = true;
				}
			}while(isValidYPos == false);
		}
		else{
			boolean isValidXPos = false;
			do{
				int randXPos = rand.nextInt(getGridSize());
				int randYPos = rand.nextInt(getGridSize());
				if((shipLength + randXPos) >= getGridSize()){
				}
				else{	
					setXPos(randXPos);
					setYPos(randYPos);
					isValidXPos = true;}

			}while(isValidXPos == false);

		}
	}

	
	/**
	* Setter for the previous turn position, only stores the previous turn
	* if it is a hit
	* @param hitXPos - X position of the targeted ship
	* @param hitYPos - Y position of the targeted ship
	*/

	public void setHitPos(int hitXPos, int hitYPos){ //sets dynamtic hit
		hitShipXPos = hitXPos;
		hitShipYPos = hitYPos;
	}
	
	
	/**
	* Setter for the orginal ship hit position
	*/
	public void setOrginHitPos(int hitXPos, int hitYPos){ //sets orgin hit
		orginHitXPos = hitXPos;
		orginHitYPos = hitYPos;
	}
	
	
	/**
	* Setter for the position move
	* @param x - X position
	* @param y - Y position
	*/
	public void setMovePos(int x, int y){ //sets move hit
		setXPos(x);
		setYPos(y);
	}
	
	
	/**
	* Resets to orginal hit position, and changes back to smartFire
	*/
	public void resetHitPos(){
		setHitPos(orginHitXPos, orginHitYPos);
		direction = 0;
		foundTarget = false;
	}

	
	/**
	* Randomly shoots until ship is found
	*/
	public void randomShipFire()
	{
		boolean isValidMove = false;
		do
		{
			int randXPos = rand.nextInt(getGridSize());
			int randYPos = rand.nextInt(getGridSize());
			if(!(enemyBoard.grid[randXPos][randYPos].getBeenHit())){
				setMovePos(randXPos,randYPos);
				isValidMove = true;
		}
		if(enemyBoard.grid[randXPos][randYPos].getHasShip())
		{
			smartFire = true;
			setHitPos(randXPos,randYPos);
			setOrginHitPos(randXPos,randYPos);
		}
		
		}while(!isValidMove);
	}

	
	/**
	* Checks if the position has been hit
	* @param x - X position
	* @param y - Y position
	*/
	public boolean getBeenHitChecker(int x, int y)
	{
		if(enemyBoard.grid[x][y].getBeenHit()){
			return false;
		}
		else{
			setMovePos(x,y);
			checkTargetedShipFire(x,y);
			return true;
		}

	}
	
	
	/**
	* Checks if the hit position has a ship
	* @param x - X position
	* @param y - Y position
	*/
	public void checkTargetedShipFire(int x, int y){
		if(enemyBoard.grid[x][y].getHasShip()){					//HAS SHIP IF TRUE
			setHitPos(x,y);
			foundTarget = true;
		}
		else{
			direction++;
		}

	}

	
	/**
	* Checks if the position has been hit for TargetShipFire
	* @param x - X position
	* @param y - Y position
	*/
	public boolean getTargetedBeenHitChecker(int x, int y){
		if(enemyBoard.grid[x][y].getBeenHit()){
			foundTarget = false;
			resetHitPos();
			return false;
		}
		else{
			setMovePos(x,y);
			if((enemyBoard.grid[x][y].getHasShip())) setHitPos(x,y);
			else resetHitPos();
			return true;
		}
	}

	
	/**
	* Resets ship fire to random
	*/
	public void resetShipFire(){
		direction = 0;
		smartFire = false;
		foundTarget = false;
	}
	
	
	/**
	* Fires in the direction of the found ship
	*/
	public void smartTargetedShipFire(){
		boolean madeMove = false;
		do{
			switch(direction){
				case 0: //bottom
					if((hitShipYPos + 1) < getGridSize()){
						madeMove = getTargetedBeenHitChecker(hitShipXPos, hitShipYPos + 1);
					}
					break;
				case 1: //top
					if((hitShipYPos - 1) >= 0){
						madeMove = getTargetedBeenHitChecker(hitShipXPos, hitShipYPos - 1);
						}
					break;
				case 2: //right
					if((hitShipXPos + 1) < getGridSize()){
						madeMove = getTargetedBeenHitChecker(hitShipXPos + 1, hitShipYPos);
						}
					break;
				case 3: //left
					if ((hitShipXPos - 1) >= 0){
						madeMove = getTargetedBeenHitChecker(hitShipXPos - 1, hitShipYPos);
					}
					break;
			}
			if(!madeMove){
				madeMove = true;
				resetHitPos();
				shipFire();
				}

		}while(!madeMove);
	}
	
	
	/**
	* Finds the orientation of the targeted ship
	*/
	public void smartShipFire(){
		boolean madeMove = false;

		do{
			switch(direction){
				case 0: //bottom
					if((hitShipYPos + 1) < getGridSize()){
						madeMove = getBeenHitChecker(hitShipXPos, hitShipYPos + 1);
						}
					break;
					
				case 1: //top
					if((hitShipYPos - 1) >= 0){
						madeMove = getBeenHitChecker(hitShipXPos, hitShipYPos - 1);
					}
					break;
				case 2: //right
					if ((hitShipXPos + 1) < getGridSize()){
						madeMove = getBeenHitChecker(hitShipXPos + 1, hitShipYPos);

					}
					break;
				case 3: //left
					if ((hitShipXPos - 1) >= 0){
						madeMove = getBeenHitChecker(hitShipXPos - 1, hitShipYPos);

					}
					break;
				default:
					resetShipFire();
					randomShipFire();
					madeMove = true;
					break;
			}
			if(!madeMove) direction++;

		}while(!madeMove);

	}
}