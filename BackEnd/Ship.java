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

    //TODO: Needs Java Documentation
    public Ship()
    {

    }
    
    //TODO: Needs Java Documentation
    public Ship(Ship s)
    {
        name = s.getName();
        xPos = s.getXPos();
        yPos = s.getXPos();
        life = s.getLife();
    }

    /**
    * Getter method for ship life.
    * @return this.life An integer value representing the health of the ship
    */
    public int getLife()
    {
        return this.life;
    }
    
    /**
    * Setter method for ship life.
    * @param newLife An integer value of health
    */
    public void setLife(int newLife)
    {
        life = newLife;
    }
    
    /**
    * Getter method for ship name.
    * @return this.name Ships name.
    */
    public String getName()
    {
        return this.name;
    }
    
    /**
    * Setter method for ship name.
    * @param shipName The ships name
    */
    public void setName(String shipName)
    {
        this.name = shipName;
    }

    /**
    * Getter method for ship length.
    * @return this.length Ships length.
    */
    public int getLength()
    {
        return this.length;
    }
    
    /**
    * Setter method for ship length.
    * @param shipLength The ships length
    */
    public void setLength(int shipLength)
    {
        this.length = shipLength;
    }
    
    /**
    * Method rotates ship orientation
    */
    public void reverseOrientation()
    {
        verticalOrientation = !verticalOrientation;
    }
    
    /**
    * Method gets current orientation (vertical or horizontal)
    * @return verticalOrientation
    */
    public boolean getVerticalOrientation()
    {
        return verticalOrientation;
    }   
    
    /**
    * Setter method for xPos.
    * @param x X position
    */
    public void setXPos(int x)
    {
        xPos = x;
    }
    
    /**
    * Getter method for xPos.
    * @return xPos Returns X position.
    */
    public int getXPos()
    {
        return xPos;
    }
    
    /**
    * Setter method for yPos.
    * @param y Y position
    */
    public void setYPos(int y)
    {
        yPos = y;
    }

    /**
    * Getter method for yPos.
    * @return yPos Returns Y position.
    */
    public int getYPos()
    {
        return yPos;
    }

    //TODO: Needs Java Documentation
    public int[] getXPositions()
    {
        return xPositions;
    }

    //TODO: Needs Java Documentation
    public int[] getYPositions()
    {
        return yPositions;
    }

    //TODO: Needs Java Documentation
    public void setXPositions(int x)
    {
        if(verticalOrientation)
        {
            xPositions = new int[1];
            xPositions[0] = x;
            // xPositions = {x};
        }
        else
        {
            xPositions = new int[length];
            for(int i = 0; i < xPositions.length; i++)
            {
                xPositions[i] = x+i;
            }
        }
    }

    //TODO: Needs Java Documentation
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
                yPositions[i] = y+i;
            }
        }
    }

    //TODO: Needs Java Documentation
    public boolean xChecker(int x)
    {
        for(int i = 0; i < xPositions.length; i++)
        {
            if(xPositions[i] == x)
                return true;
        }
        return false;
    }

    //TODO: Needs Java Documentation
    public boolean yChecker(int y)
    {
        for(int i = 0; i < yPositions.length; i++)
        {
            if(yPositions[i] == y)
                return true;
        }
        return false;
    }
}