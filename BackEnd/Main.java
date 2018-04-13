package BackEnd;
import GUI.*;

import java.util.Scanner;

/**
 * The class is the main program to be run in order to run the entirety
 * of the remainder of the Battleship game. It starts up the menu program
 * which runs the other modes.
 */

public class Main
{
	public static void main(String [] args)
	{
		Menu menu = new Menu();
		menu.startUp();
	}
	
}