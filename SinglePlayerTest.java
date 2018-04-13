import static org.junit.Assert.*;
import java.util.ArrayList;

import BackEnd.*;
import GUI.*;

import org.junit.Test;
import java.io.*;

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class SinglePlayerTest 
{
	@Test
	public void test_Ship_Checker()
	{
		SinglePlayer singlePlayer = new SinglePlayer();
		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();
		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		singlePlayer.player.battleship.setLife(0);

		assertEquals("Ship should be sunk.", true, singlePlayer.player.shipChecker(0,0));
	}

	@Test
	public void test_Rotate()
	{
		SinglePlayer singlePlayer = new SinglePlayer();
		singlePlayer.player.setCurrent(singlePlayer.player.battleship);
		singlePlayer.player.rotate();

		assertEquals("Ship should be horizontal.", false, singlePlayer.player.battleship.getVerticalOrientation());
	}

	@Test
	public void test_Overlapping_Ships() 
	{
		SinglePlayer singlePlayer = new SinglePlayer();
		
		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);		
		singlePlayer.player.shipPlacement(singlePlayer.player.battleship);

		singlePlayer.player.battleship.setName("Submarine");
		singlePlayer.player.battleship.setLength(3);
		singlePlayer.player.battleship.setLife(3);
		singlePlayer.player.shipPlacement(singlePlayer.player.submarine);

		assertEquals("Ship name should be Battleship.", "Battleship", singlePlayer.player.playerBoard.grid[0][0].getShipName());
	}

	@Test
	public void test_Valid_Ship_Placement_Upper_Border_Vertical()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.current = singlePlayer.player.battleship;

		singlePlayer.player.shipPlacementMarkerMoveUp();
		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}

		assertEquals("Ship can't move beyond the upper boundary of the grid", 0, singlePlayer.player.battleship.getYPos());
	}

	@Test
	public void test_Valid_Ship_Placement_Upper_Border_Horizontal()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.battleship.reverseOrientation();
		singlePlayer.player.current = singlePlayer.player.battleship;

		singlePlayer.player.shipPlacementMarkerMoveUp();
		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}

		assertEquals("Ship can't move beyond the upper boundary of the grid", 0, singlePlayer.player.battleship.getYPos());
	}

	@Test
	public void test_Valid_Ship_Placement_Left_Border_Vertical()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);		
		singlePlayer.player.current = singlePlayer.player.battleship;

		singlePlayer.player.shipPlacementMarkerMoveLeft();
		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}

		assertEquals("Ship can't move beyond the left-most boundary of the grid", 0, singlePlayer.player.battleship.getXPos());	
	}

	@Test
	public void test_Valid_Ship_Placement_Left_Border_Horizontal()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);		
		singlePlayer.player.battleship.reverseOrientation();
		singlePlayer.player.current = singlePlayer.player.battleship;

		singlePlayer.player.shipPlacementMarkerMoveLeft();
		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}

		assertEquals("Ship can't move beyond the left-most boundary of the grid", 0, singlePlayer.player.battleship.getXPos());	
	}

	@Test
	public void test_Valid_Ship_Placement_Lower_Border_Vertical()
	{

		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.current = singlePlayer.player.battleship;

		try
		{
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();


			if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
			{
				singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
			}					
		}

		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Caught the ArrayIndexOutofBoundsException");
		}

		catch (NullPointerException e)
		{
			System.out.println("Caught the NullPointerException");
		}

		assertEquals("Ship can't move beyond the lower boundary of the grid", 4, singlePlayer.player.battleship.getYPos());
	}

	@Test
	public void test_Valid_Ship_Placement_Lower_Border_Horizontal()
	{

		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.battleship.reverseOrientation();
		singlePlayer.player.current = singlePlayer.player.battleship;

		try
		{
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();
			singlePlayer.player.shipPlacementMarkerMoveDown();


			if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
			{
				singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
			}					
		}

		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Caught the ArrayIndexOutofBoundsException");
		}

		catch (NullPointerException e)
		{
			System.out.println("Caught the NullPointerException");
		}

		assertEquals("Ship can't move beyond the lower boundary of the grid", 7, singlePlayer.player.battleship.getYPos());
	}

	@Test
	public void test_Valid_Ship_Placement_Right_Border_Vertical()
	{

		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.current = singlePlayer.player.battleship;

		try
		{
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();



			if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
			{
				singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
			}					
		}

		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Caught the ArrayIndexOutofBoundsException");
		}

		catch (NullPointerException e)
		{
			System.out.println("Caught the NullPointerException");
		}

		assertEquals("Ship can't move beyond the right-most boundary of the grid", 7, singlePlayer.player.battleship.getXPos());
	}

	@Test
	public void test_Valid_Ship_Placement_Right_Border_Horizontal()
	{

		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.battleship.reverseOrientation();
		singlePlayer.player.current = singlePlayer.player.battleship;

		try
		{
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();
			singlePlayer.player.shipPlacementMarkerMoveRight();


			if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
			{
				singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
			}					
		}

		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Caught the ArrayIndexOutofBoundsException");
		}

		catch (NullPointerException e)
		{
			System.out.println("Caught the NullPointerException");
		}

		assertEquals("Ship can't move beyond the right-most boundary of the grid", 4, singlePlayer.player.battleship.getXPos());
	}

	@Test
	public void test_Valid_Ship_Placement_Within_Bounds_Vertical()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.current = singlePlayer.player.battleship;

		singlePlayer.player.shipPlacementMarkerMoveRight();
		singlePlayer.player.shipPlacementMarkerMoveDown();

		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}

		assertEquals("Ship should have been moved one space right and one space down", true, singlePlayer.player.battleship.getXPos() == 1 && singlePlayer.player.battleship.getYPos() == 1);
	}

	@Test
	public void test_Valid_Ship_Placement_Within_Bounds_Horizontal()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.battleship.reverseOrientation();
		singlePlayer.player.current = singlePlayer.player.battleship;

		singlePlayer.player.shipPlacementMarkerMoveRight();
		singlePlayer.player.shipPlacementMarkerMoveDown();

		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}

		assertEquals("Ship should have been moved one space right and one space down", true, singlePlayer.player.battleship.getXPos() == 1 && singlePlayer.player.battleship.getYPos() == 1);
	}


	@Test
	public void test_Save_And_Load_File()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.setBoard(7);
		singlePlayer.setBoardSize();

		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.computer.playerBoard.boardPopulate();
		singlePlayer.computer.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.battleship.reverseOrientation();


		singlePlayer.computer.battleship.setName("Battleship");
		singlePlayer.computer.battleship.setLength(4);
		singlePlayer.computer.battleship.setLife(4);

		singlePlayer.player.current = singlePlayer.player.battleship;
		singlePlayer.computer.current = singlePlayer.computer.battleship;

		singlePlayer.player.shipPlacementMarkerMoveRight();
		singlePlayer.computer.shipPlacementMarkerMoveDown();

		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}

		if(singlePlayer.computer.validShipPlacement(singlePlayer.computer.battleship))
		{
			singlePlayer.computer.shipPlacement(singlePlayer.computer.battleship);
		}

		singlePlayer.save();

		String fileName = "output.txt";
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

		singlePlayer.setBoard(inputStream.nextInt());
		inputStream.nextLine();

		singlePlayer.choosingBoardSize(singlePlayer.getBoard());
		singlePlayer.setBoardSize();
		singlePlayer.player.playerBoard.boardPopulate();	//populates boards
		singlePlayer.player.enemyBoard.boardPopulate();
		singlePlayer.computer.playerBoard.boardPopulate();
		singlePlayer.computer.enemyBoard.boardPopulate();

		for(int i = 0; i < singlePlayer.player.getBoardLength(); i++)
		{
			for(int j = 0; j < singlePlayer.player.getBoardLength(); j++)
			{
				singlePlayer.player.playerBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				singlePlayer.player.playerBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());
			}
			inputStream.nextLine();

		}

		for(int i = 0; i < singlePlayer.player.getBoardLength(); i++)
		{
			for(int j = 0; j < singlePlayer.player.getBoardLength(); j++)
			{
				singlePlayer.player.enemyBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				singlePlayer.player.enemyBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());			
			}
			inputStream.nextLine();
		}


		for(int i = 0; i < singlePlayer.computer.getBoardLength(); i++)
		{
			for(int j = 0; j < singlePlayer.computer.getBoardLength(); j++)
			{
				singlePlayer.computer.playerBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				singlePlayer.computer.playerBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());
			}
		}

		for(int i = 0; i < singlePlayer.computer.getBoardLength(); i++)
		{
			for(int j = 0; j < singlePlayer.computer.getBoardLength(); j++)
			{
				singlePlayer.computer.enemyBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				singlePlayer.computer.enemyBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());			
			}
		}

		assertEquals("Board Length should be 7.", 7, singlePlayer.getBoard());

		assertEquals("Ship should be taking up x-position 1", true, singlePlayer.player.playerBoard.grid[1][0].getHasShip());
		assertEquals("Ship should be taking up x-position 2", true, singlePlayer.player.playerBoard.grid[2][0].getHasShip());
		assertEquals("Ship should be taking up x-position 3", true, singlePlayer.player.playerBoard.grid[3][0].getHasShip());
		assertEquals("Ship should be taking up x-position 4", true, singlePlayer.player.playerBoard.grid[4][0].getHasShip());

		assertEquals("Ship should bve taking up y-position 1", true, singlePlayer.computer.playerBoard.grid[0][1].getHasShip());
		assertEquals("Ship should bve taking up y-position 2", true, singlePlayer.computer.playerBoard.grid[0][2].getHasShip());
		assertEquals("Ship should bve taking up y-position 3", true, singlePlayer.computer.playerBoard.grid[0][3].getHasShip());
		assertEquals("Ship should bve taking up y-position 4", true, singlePlayer.computer.playerBoard.grid[0][4].getHasShip());
	}

	@Test
	public void test_Overwrite_Save_File()
	{
		SinglePlayer singlePlayer1 = new SinglePlayer();

		singlePlayer1.choosingBoardSize(7);
		singlePlayer1.setBoard(7);
		singlePlayer1.setBoardSize();

		singlePlayer1.player.playerBoard.boardPopulate();
		singlePlayer1.player.enemyBoard.boardPopulate();

		singlePlayer1.computer.playerBoard.boardPopulate();
		singlePlayer1.computer.enemyBoard.boardPopulate();

		singlePlayer1.player.battleship.setName("Battleship");
		singlePlayer1.player.battleship.setLength(4);
		singlePlayer1.player.battleship.setLife(4);
		singlePlayer1.player.battleship.reverseOrientation();

		singlePlayer1.computer.battleship.setName("Battleship");
		singlePlayer1.computer.battleship.setLength(4);
		singlePlayer1.computer.battleship.setLife(4);

		singlePlayer1.player.current = singlePlayer1.player.battleship;
		singlePlayer1.computer.current = singlePlayer1.computer.battleship;

		singlePlayer1.player.shipPlacementMarkerMoveRight();
		singlePlayer1.computer.shipPlacementMarkerMoveDown();

		if(singlePlayer1.player.validShipPlacement(singlePlayer1.player.battleship))
		{
			singlePlayer1.player.shipPlacement(singlePlayer1.player.battleship);
		}

		if(singlePlayer1.computer.validShipPlacement(singlePlayer1.computer.battleship))
		{
			singlePlayer1.computer.shipPlacement(singlePlayer1.computer.battleship);
		}

		singlePlayer1.boardLinking();
		singlePlayer1.save();

		SinglePlayer singlePlayer2 = new SinglePlayer();

		singlePlayer2.choosingBoardSize(8);
		singlePlayer2.setBoard(8);
		singlePlayer2.setBoardSize();

		singlePlayer2.player.playerBoard.boardPopulate();
		singlePlayer2.player.enemyBoard.boardPopulate();

		singlePlayer2.computer.playerBoard.boardPopulate();
		singlePlayer2.computer.enemyBoard.boardPopulate();

		singlePlayer2.player.battleship.setName("Battleship");
		singlePlayer2.player.battleship.setLength(4);
		singlePlayer2.player.battleship.setLife(4);

		singlePlayer2.computer.battleship.setName("Battleship");
		singlePlayer2.computer.battleship.setLength(4);
		singlePlayer2.computer.battleship.setLife(4);
		singlePlayer2.computer.battleship.reverseOrientation();

		singlePlayer2.player.current = singlePlayer2.player.battleship;
		singlePlayer2.computer.current = singlePlayer2.computer.battleship;

		singlePlayer2.player.shipPlacementMarkerMoveRight();
		singlePlayer2.computer.shipPlacementMarkerMoveDown();		

		if(singlePlayer2.player.validShipPlacement(singlePlayer2.player.battleship))
		{
			singlePlayer2.player.shipPlacement(singlePlayer2.player.battleship);
		}

		if(singlePlayer2.computer.validShipPlacement(singlePlayer2.computer.battleship))
		{
			singlePlayer2.computer.shipPlacement(singlePlayer2.computer.battleship);
		}

		singlePlayer2.save();

		String fileName = "output.txt";
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

		singlePlayer2.setBoard(inputStream.nextInt());
		inputStream.nextLine();

		singlePlayer2.choosingBoardSize(singlePlayer2.getBoard());
		singlePlayer2.setBoardSize();
		singlePlayer2.player.playerBoard.boardPopulate();	//populates boards
		singlePlayer2.player.enemyBoard.boardPopulate();
		singlePlayer2.computer.playerBoard.boardPopulate();
		singlePlayer2.computer.enemyBoard.boardPopulate();

		for(int i = 0; i < singlePlayer2.player.getBoardLength(); i++)
		{
			for(int j = 0; j < singlePlayer2.player.getBoardLength(); j++)
			{
				singlePlayer2.player.playerBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				singlePlayer2.player.playerBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());
			}
			inputStream.nextLine();

		}

		for(int i = 0; i < singlePlayer2.player.getBoardLength(); i++)
		{
			for(int j = 0; j < singlePlayer2.player.getBoardLength(); j++)
			{
				singlePlayer2.player.enemyBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				singlePlayer2.player.enemyBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());			
			}
			inputStream.nextLine();
		}


		for(int i = 0; i < singlePlayer2.computer.getBoardLength(); i++)
		{
			for(int j = 0; j < singlePlayer2.computer.getBoardLength(); j++)
			{
				singlePlayer2.computer.playerBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				singlePlayer2.computer.playerBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());
			}
		}

		for(int i = 0; i < singlePlayer2.computer.getBoardLength(); i++)
		{
			for(int j = 0; j < singlePlayer2.computer.getBoardLength(); j++)
			{
				singlePlayer2.computer.enemyBoard.grid[i][j].setHasShip(inputStream.nextBoolean());
				singlePlayer2.computer.enemyBoard.grid[i][j].setBeenHit(inputStream.nextBoolean());			
			}
		}
		
		assertEquals("Board Length should be 8.", 8, singlePlayer2.getBoard());
		
		assertEquals("Ship should be taking up y-position 1", true, singlePlayer2.player.playerBoard.grid[1][0].getHasShip());
		assertEquals("Ship should be taking up y-position 2", true, singlePlayer2.player.playerBoard.grid[1][1].getHasShip());
		assertEquals("Ship should be taking up y-position 3", true, singlePlayer2.player.playerBoard.grid[1][2].getHasShip());
		assertEquals("Ship should be taking up y-position 4", true, singlePlayer2.player.playerBoard.grid[1][3].getHasShip());

		assertEquals("Ship should be taking up x-position 1", true, singlePlayer2.computer.playerBoard.grid[0][1].getHasShip());
		assertEquals("Ship should be taking up x-position 2", true, singlePlayer2.computer.playerBoard.grid[1][1].getHasShip());
		assertEquals("Ship should be taking up x-position 3", true, singlePlayer2.computer.playerBoard.grid[2][1].getHasShip());
		assertEquals("Ship should be taking up x-position 4", true, singlePlayer2.computer.playerBoard.grid[3][1].getHasShip());


	}

}