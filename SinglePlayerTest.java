import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Test;
import java.io.*;

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
	public void test_Valid_Ship_Placement_Upper_Border()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);		
		singlePlayer.player.shipPlacementMarkerMoveUp();
		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}



		assertEquals("Ship can't move beyond the upper boundary of the grid", 0, singlePlayer.player.battleship.getYPos());

	}

	@Test
	public void test_Valid_Ship_Placement_Left_Border()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);		
		singlePlayer.player.shipPlacementMarkerMoveLeft();
		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}

		assertEquals("Ship can't move beyond the left-most boundary of the grid", 0, singlePlayer.player.battleship.getXPos());	
	}

	@Test
	public void test_Valid_Ship_Placement_Lower_Border()
	{

		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);

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
			singlePlayer.player.shipPlacementMarkerMoveDown();


			if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
			{
				singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
			}					
		}

		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.print("Caught the ArrayIndexOutofBoundsException");
		}

		assertEquals("Ship can't move beyond the lower boundary of the grid", 7, singlePlayer.player.battleship.getYPos());

	}

	// @Test
	// public void test_Valid_Ship_Placement_Right_Border()
	// {

	// 	SinglePlayer singlePlayer = new SinglePlayer();

	// 	singlePlayer.choosingBoardSize(7);
	// 	singlePlayer.player.playerBoard.boardPopulate();
	// 	singlePlayer.player.enemyBoard.boardPopulate();

	// 	singlePlayer.player.battleship.setName("Battleship");
	// 	singlePlayer.player.battleship.setLength(4);
	// 	singlePlayer.player.battleship.setLife(4);
	// 	singlePlayer.player.battleship.reverseOrientation();

	// 	try
	// 	{
	// 		singlePlayer.player.shipPlacementMarkerMoveRight();
	// 		singlePlayer.player.shipPlacementMarkerMoveRight();
	// 		singlePlayer.player.shipPlacementMarkerMoveRight();
	// 		singlePlayer.player.shipPlacementMarkerMoveRight();

	// 		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
	// 		{
	// 			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
	// 		}					
	// 	}

	// 	catch (NullPointerException e)
	// 	{
	// 		System.out.print("Caught the NullPointerException");
	// 	}

	// 	assertEquals("Ship can't move beyond the right-most boundary of the grid", 7, singlePlayer.player.battleship.getXPos() + singlePlayer.player.battleship.getLength());

	// }
	@Test
	public void test_Valid_Ship_Placement_Within_Bounds()
	{
		SinglePlayer singlePlayer = new SinglePlayer();

		singlePlayer.choosingBoardSize(7);
		singlePlayer.player.playerBoard.boardPopulate();
		singlePlayer.player.enemyBoard.boardPopulate();

		singlePlayer.player.battleship.setName("Battleship");
		singlePlayer.player.battleship.setLength(4);
		singlePlayer.player.battleship.setLife(4);
		singlePlayer.player.shipPlacementMarkerMoveRight();
		singlePlayer.player.shipPlacementMarkerMoveDown();		
		if(singlePlayer.player.validShipPlacement(singlePlayer.player.battleship))
		{
			singlePlayer.player.shipPlacement(singlePlayer.player.battleship);
		}

		assertEquals("Ship should have been moved one space right and one space down", true, singlePlayer.player.battleship.getXPos() == 1 && singlePlayer.player.battleship.getYPos() == 1);
	}


}
//passes in ship in grid sees if hit or miss
//assert that ships can't overlap
//check save file works the same
//boundaries of where a ship can be placed