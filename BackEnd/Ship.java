/**
 * 
 * The class constructs the Ship object, with associated properties.
 * Namely, the ship length, orientation, coordinates, and ship name.
 *
 *  @author Jackson, David, Nathanael, Ryan, and Sam
 */
package BackEnd;
import GUI.*;


public class Ship
{
	private String name;
	private int length;
	private boolean verticalOrientation = true;
	private int xPos;
	private int yPos;
	private int life;
	private int[] xPositions;
	private int[] yPositions;

	
	/**
	 * A default constructor with no set values.
	 */
	public Ship()
	{

	}
	
	
	/**
	 * A copy constructor that makes a copy of a ship's info.
	 * @param s The ship object to be copied.
	 */    
	public Ship(Ship s)
	{
		name = s.getName();
		xPos = s.getXPos();
		yPos = s.getXPos();
		life = s.getLife();
	}

	
	/**
	* Getter method for ship life.
	* @return this.life An integer value representing the health of the ship.
	*/
	public int getLife()
	{
		return this.life;
	}
	
	
	/**
	* Setter method for ship life.
	* @param newLife An integer value of health.
	*/
	public void setLife(int newLife)
	{
		life = newLife;
	}
	
	
	/**
	* Getter method for ship name.
	* @return this.name Ship's name.
	*/
	public String getName()
	{
		return this.name;
	}
	
	
	/**
	* Setter method for ship name.
	* @param shipName The ship's name.
	*/
	public void setName(String shipName)
	{
		this.name = shipName;
	}

	
	/**
	* Getter method for ship length.
	* @return this.length Ship's length.
	*/
	public int getLength()
	{
		return this.length;
	}
	
	
	/**
	* Setter method for ship length.
	* @param shipLength An integer for ship's length.
	*/
	public void setLength(int shipLength)
	{
		this.length = shipLength;
	}
	
	
	/**
	* Method rotates ship orientation.
	*/
	public void reverseOrientation()
	{
		verticalOrientation = !verticalOrientation;
	}
	
	
	/**
	* Method gets current orientation (vertical or horizontal).
	* @return verticalOrientation Returns true if the ship is vertically oriented.
	*/
	public boolean getVerticalOrientation()
	{
		return verticalOrientation;
	}   
	
	
	/**
	 * Sets orientation of ship
	 * @param ort - true is vertical
	 */
	public void setVerticalOrientation(boolean ort)
	{
		verticalOrientation = ort;
	}
	
	
	/**
	* Setter method for the X position of a ship.
	* @param x An integer for X position.
	*/
	public void setXPos(int x)
	{
		xPos = x;
	}
	
	
	/**
	* Getter method for a particular X position of a ship.
	* @return xPos Returns X position.
	*/
	public int getXPos()
	{
		return xPos;
	}
	
	
	/**
	* Setter method for the Y position of a ship.
	* @param y An integer for Y position.
	*/
	public void setYPos(int y)
	{
		yPos = y;
	}

	
	/**
	* Getter method for the Y position of a ship.
	* @return yPos Returns integer Y position.
	*/
	public int getYPos()
	{
		return yPos;
	}

	
	/**
	* Getter method for the X positions of a ship.
	* @return xPositions Returns X positions.
	*/    
	public int[] getXPositions()
	{
		return xPositions;
	}

	
	/**
	* Getter method for yPositions.
	* @return yPositions Returns Y positions.
	*/     
	public int[] getYPositions()
	{
		return yPositions;
	}

	
	/**
	* Setter method for xPositions.
	* @param x An integer for X position.
	*/    
	public void setXPositions(int x)
	{
		if(verticalOrientation)
		{
			xPositions = new int[1];
			xPositions[0] = x;
		}
		else
		{
			xPositions = new int[length];
			for(int i = 0; i < xPositions.length; i++)
			{
				xPositions[i] = x + i;
			}
		}
	}

	
	/**
	* Setter method for yPositions.
	* @param y An integer for Y position.
	*/        
	public void setYPositions(int y)
	{
		if(!verticalOrientation)
		{
			yPositions = new int[1];
			yPositions[0] = y;
		}
		else
		{
			yPositions = new int[length];
			for(int i = 0; i < yPositions.length; i++)
			{
				yPositions[i] = y + i;
			}
		}
	}

	
	/**
	* Method checks if any of the X positions of the ship match the X position provided.
	* @param x An integer for X position.
	* @return boolean Returns true if the X position provided is occupied.
	*/    
	public boolean xChecker(int x)
	{
		for(int i = 0; i < xPositions.length; i++)
		{
			if(xPositions[i] == x)
			{
				return true;
			}
				
		}
		return false;
	}

	
	/**
	* Method checks if any of the Y positions of the ship match the Y position provided.
	* @param y An integer for Y position.
	* @return boolean Returns true if the Y position provided is occupied.
	*/    	
	public boolean yChecker(int y)
	{
		for(int i = 0; i < yPositions.length; i++)
		{
			if(yPositions[i] == y)
			{
				return true;
			}
				
		}
		return false;
	}
}