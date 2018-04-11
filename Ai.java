import java.util.Random;

public class Ai extends Player{
	Random rand = new Random();
	private int direction = 0;
	private boolean smartFire = false;
	private boolean foundTarget = false;
	private int hitShipXPos;
	private int hitShipYPos;
	private int orginHitXPos;
	private int orginHitYPos;

	public void shipFire(){
		if(smartFire && foundTarget) smartTargetedShipFire();
		else if(smartFire && !foundTarget) smartShipFire();
		else randomShipFire();

	}


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

	public int getGridSize(){
		return(getBoardLength()+1); //change for getter
	}

	public void randomShipPlacement(){
		boolean changeOrientation = rand.nextBoolean();
		System.out.println(changeOrientation);
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

	public void setHitPos(int hitXPos, int hitYPos){ //sets dynamtic hit
		hitShipXPos = hitXPos;
		hitShipYPos = hitYPos;
	}
	public void setOrginHitPos(int hitXPos, int hitYPos){ //sets orgin hit
		orginHitXPos = hitXPos;
		orginHitYPos = hitYPos;
	}
	public void setMovePos(int x, int y){ //sets move hit
		setXPos(x);
		setYPos(y);
	}
	public void resetHitPos(){
		setHitPos(orginHitXPos, orginHitYPos);
		direction = 0;
		foundTarget = false;
	}

	public void randomShipFire(){
		boolean isValidMove = false;
			do{
			int randXPos = rand.nextInt(getGridSize());
			int randYPos = rand.nextInt(getGridSize());
			if(!(enemyBoard.grid[randXPos][randYPos].getBeenHit())){
				setMovePos(randXPos,randYPos);
				isValidMove = true;
			}
			if(enemyBoard.grid[randXPos][randYPos].getHasShip()){
				smartFire = true;
				setHitPos(randXPos,randYPos);
				setOrginHitPos(randXPos,randYPos);
			}
		
		}while(!isValidMove);
	}

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

	public void checkTargetedShipFire(int x, int y){
		if(enemyBoard.grid[x][y].getHasShip()){					//HAS SHIP IF TRUE
			setHitPos(x,y);
			foundTarget = true;
		}
		else{
			direction++;
		}

	}

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

	public void resetShipFire(){
		direction = 0;
		smartFire = false;
		foundTarget = false;
	}

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