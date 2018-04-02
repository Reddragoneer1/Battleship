//import static org.junit.Assert.*;

//import org.junit.Test;

public class ShipTest 
{
	/*
	@Test
	public void test_Overlapping_Ships() 
	{
		Ship battleship = new Ship();
		Ship submarine = new Ship();
		
		battleship.setLength(3);
		battleship.setXPos(0);
		battleship.setYPos(3);

		submarine.setLength(3);
		submarine.setXPos(0);
		submarine.setYPos(0);
		
		battleship.getXPos().isEqual(submarine.getXPos());
		assertEquals("Name should be battleship, length should be 3 and coordinates should be (0,3)");
	}

	@Test
	public void test_Privacy_Leak()
	{
		Ship battleship = new Ship();

		battleship.setLength(3);
		battleship.setXPos(0);
		battleship.setYPos(3);

		Ship battleshipCopy = battleship;
		battleshipCopy.getLength = 2;
		battleshipCopy.getXPos = 3;
		battleshipCopy.getYPos = 2;

		boolean unchanged = battleship.length.equals(3);
		assertTrue("Was able to change battleship length directly via copy of object.", unchanged);

	}

	
	@Test
	public void test_setEnd_privacyLeak()
	{
		Line line1 = new Line(new Point(20,2), new Point(3,6));
		Point p1 = new Point(1,2);
		
		line1.setEnd(p1);
		
		p1.moveDown(4);
		
		Point p2 = line1.getEnd();
		System.out.println(p2);
		
		boolean unchanged = p2.equals(new Point(1,2));
		
		assertTrue("Was able to change end point of line directly via object passed to setter method.", unchanged);
	}
	*/
}