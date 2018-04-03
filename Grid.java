/**
 * This class manipulates properties to do with the grid.
 * Such as updating tiles with ships, hits, and marking
 * missed shots. Also ensures ship placements are legal
 * and in the bounds of the grid.
 *
 * @author Jackson, David, Nathanael, Ryan, and Sam
 */
public class Grid
{
	private int xCoord;
	private int yCoord;
	private boolean hasShip;
	private boolean beenHit;
	private boolean canHaveShip = true;
	private String shipName = " ";

    /**
     * This method prints out the info inside a grid
     */
	public void display()
	{
		System.out.print("X-coordinate: " + xCoord);
		System.out.print(", Y-coordinate: " + yCoord);
		System.out.print(", Has a ship: " + hasShip);
		System.out.print(", Been hit: " + beenHit + "\n");
	}
	
	/**
	 * The method returns the x coordinate of the grid
	 * @return Returns integer x coordinate
	 */
	public int getXCoord()
	{
		return xCoord;
	}

	/**
	 * The method sets the x coordinate of the grid
	 * @param xCoord An integer for x coordinate
	 */
	public void setXCoord(int xCoord)
	{
		this.xCoord = xCoord;
	}
	
	/**
	 * The method returns the y coordinate of the grid
	 * @return Returns integer y coordinate
	 */
	public int getYCoord()
	{
		return yCoord;
	}

	/**
	 * The method sets the y coordinate of the grid
	 * @param yCoord An integer for y coordinate
	 */
	public void setYCoord(int yCoord)
	{
		this.yCoord = yCoord;
	}

	/**
	* Getter method for grid, checks if space has a ship.
	* @return hasShip Returns a boolean of if the space has a ship or not.
	*/
	public boolean getHasShip()
	{
		return hasShip;
	}

	/**
	* Setter method for grid, sets if space has a ship.
	* @param hasShip A boolean value the determines if the space has a ship.
	*/
	public void setHasShip(boolean hasShip)
	{
		this.hasShip = hasShip;
	}

	/**
	* Getter method for grid, checks if space has been hit.
	* @return beenHit Returns a boolean of if the space has been hit or not.
	*/
	public boolean getBeenHit()
	{
		return beenHit;
	}

	/**
	* Setter method for grid, sets if space has beenHit.
	* @param beenHit A boolean value the determines if the space has beenHit.
	*/
	public void setBeenHit(boolean beenHit)
	{
		this.beenHit = beenHit;
	}

	/**
	* Getter method for grid, gets if space canHaveShip.
	* @return canHaveShip Returns a boolean of if a space canHaveShip.
	*/
	public boolean getCanHaveShip()
	{
		return canHaveShip;
	}

	/**
	* Setter method for grid, sets if space canHaveShip.
	* @param canHaveShip A boolean value the determines if a space canHaveShip
	*/
	public void setCanHaveShip(boolean canHaveShip)
	{
		this.canHaveShip = canHaveShip;
	}

	/**
	* Setter method for grid, sets the name of ship in a space.
	* @param name A name for a ship
	*/
	public void setShipName(String name)
	{
		shipName = name;
	}
	
	/**
	* Getter method for grid, gets the name of a ship from a space.
	* @return shipName Returns the ship's name from a space.
	*/
	public String getShipName()
	{
		return shipName;
	}

}