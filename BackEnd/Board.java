package BackEnd;
import GUI.*;


import java.util.Scanner;
/**
 * The class displays the board based on the grid properties.
 * Mainly used as a user interface for easier testing and more
 * user friendly visuals.
 *
 *@author Jackson, David, Nathanael, Ryan, and Sam
 */

public class Board
{
	private int boardSize;
	public Grid[][] grid;


	/**
	* Method sets the board to have dimensions x by x.
	* @param x An integer for the dimension of the board.
	*/
	public void chooseBoardSize(int x)
	{
		boardSize = x;

		grid = new Grid[getBoardSize() + 1][getBoardSize() + 1];
	}
    
    /**
     * The method prints out a text representation the enemy's board
     */
	public void enemyBoard()	//Will change to match if it's been hit eventually by using variables inbetween text
	{
		//print x-axis
		System.out.print("____");
		for (int i = 0; i < getBoardSize(); i++)
		{
			System.out.print("_____");
		}
		System.out.println("");

		System.out.print("|__|");
		for (int i = 0; i < getBoardSize(); i++)
		{
			System.out.print("_");
			System.out.print(i + 1);	//Top row for x-axis

			System.out.print((i+1 > 9 ? "_|":"__|"));
		}
		System.out.println("");

		for (int i = 0; i < getBoardSize(); i++)
		{
			//print lines with number for y-axis
			System.out.print("|" + (i + 1) + (i+1 > 9 ? "": " "));	//Left column for y-axis
			for (int j = 0; j < getBoardSize(); j++)
			{
				System.out.print("|  " + (grid[j][i].getBeenHit() ? grid[j][i].getHasShip() ? "x ":"o ":" " + " "));
			}
			System.out.println("| ");

			//only print lines
			System.out.print("|__");
			for (int j = 0; j < getBoardSize(); j++)
			{
				System.out.print("|____");
			}
			System.out.println("|");
		}

	}

    /** 
     * The method prints a text representation of the player's board
     */
	public void playerBoard()	//Will change to match if it's been hit eventually by using variables inbetween text
	{
     	//print x-axis
		System.out.print("____");
		for (int i = 0; i < getBoardSize(); i++)
		{
			System.out.print("_____");
		}
		System.out.println("");

		System.out.print("|__|");
		for (int i = 0; i < getBoardSize(); i++)
		{
			System.out.print("_");
			System.out.print(i + 1);	//Top row for x-axis

			System.out.print((i+1 > 9 ? "_|":"__|"));
		}
		System.out.println("");

		for (int i = 0; i < getBoardSize(); i++)
		{
			//print lines with number for y-axis
			System.out.print("|" + (i + 1) + (i+1 > 9 ? "": " "));	//Left column for y-axis
			for (int j = 0; j < getBoardSize(); j++)
			{
				System.out.print("|  " + ((grid[j][i].getBeenHit() ? (grid[j][i].getHasShip() ? "$":"o"): (grid[j][i].getHasShip() ? "S" : " "))) + " ");
			}
			System.out.println("| ");

			//only print lines
			System.out.print("|__");
			for (int j = 0; j < getBoardSize(); j++)
			{
				System.out.print("|____");
			}
			System.out.println("|");
		}
	}
	
	/**
	 * The method creates an array that represents the grid
	 */ 
	public void boardPopulate()		//Board population
	{
		for(int i = 0; i < getBoardSize(); i++)
		{
			for(int j = 0; j < getBoardSize(); j++)
			{
				grid[i][j] = new Grid();
			}
		}
		for(int i = 0; i < getBoardSize(); i++)
		{
			for(int j = 0; j < getBoardSize(); j++)
			{
				grid[i][j].setXCoord(i);
				grid[i][j].setYCoord(j);
			}

		}
	}
	/**
	* Getter method for boardSize.
	* @return boardSize Returns board size.
	*/   
	public int getBoardSize()
	{
		return this.boardSize;
	}

}