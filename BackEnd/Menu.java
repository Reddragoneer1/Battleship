package BackEnd;
import GUI.*;
import java.util.Scanner;
/**
 * This class is just the menu for which the user chooses
 * which operations to run.
 */


public class Menu
{
    /**
     * The method creates a menu that allows user to select the game mode to be played
     */
	public void startUp()
	{
		LoadGame loadGame = new LoadGame();
		loadGame.run();
		
//=======================================================================================================
// 		CODE IS KEPT TO ALLOW ACCESS TO TEXT BASE (README TO ACCESS TEXTBASE)
		/*Scanner input = new Scanner(System.in);
		int gameMode = 0;
		System.out.println("Please select a game mode \n 1. Single player \n 2. 2-player \n 3. 2-player GUI \n 0. Exit");
		gameMode = input.nextInt();
		if(gameMode == 1) //Single player
		{
			SinglePlayer singlePlayer = new SinglePlayer();
			singlePlayer.singlePlayerGame(1);
			singlePlayer.shipPlacementSelection();
			singlePlayer.boardLinking();
			singlePlayer.playerTurn();
		}
		else if(gameMode == 2)//Plays two player game mode
		{
			TwoPlayer twoPlayer = new TwoPlayer();
			//twoPlayer.player1.setName(JOptionPane.showInputDialog(null, "Please enter your username"));
			twoPlayer.twoPlayerSetup(gameMode);
			if(!twoPlayer.getGameLoaded())
				twoPlayer.shipPlacementSelection();
			twoPlayer.boardLinking();
			twoPlayer.playerTurn();
		}*/
	}

}
