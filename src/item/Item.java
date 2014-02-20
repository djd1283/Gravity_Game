package item;

import game.Universe;
import toolkit.Point;
import toolkit.Vector;

/**
 * An abstract class used to represent any ambiguous object in the universe. Contains a reference
 * to the main Universe, as well as its own 'position' and 'velocity'. A 'radius' variable is provided
 * for easy collision handling, and the Item's response to gravity 'influenceOfGravity' is given for
 * applications of gravitational acceleration on this Item. Numerous abstract methods are provided
 * for updating and painting calls, and optional methods for gravity and proximity handling 
 * exist for modularity.
 * @author David
 */
public abstract class Item
{
	public Universe universe;
	public Point position;
	public Vector velocity;
	
	public double radius;
	public double influenceOfGravity;
	
	public Item(Universe universe)
	{
		this.universe = universe;
		this.position = new Point(0, 0);
		this.velocity = new Vector(0, 0);
		influenceOfGravity = 1;
	}
	
	public abstract void update();
	public abstract void paint();
	public abstract void updateUponDeath();
	public abstract Item copy();

	public Vector getVelocityToOrbitItem(GravitationalItem item)
	{
		//get a vector from this position to the item's position
		Vector toGravitationalItem = position.vectorTo(item.position);
		//find the distance between this item and that item
		double distanceToGravitationalItem = toGravitationalItem.magnitude();
		//find the acceleration from this item to the gravitational item
		double acceleration = item.strengthOfGravity * influenceOfGravity / Math.pow(distanceToGravitationalItem, item.distanceFactorOfGravity);
		//find the orbit velocity with previous values
		double orbitVelocity = Math.sqrt(acceleration * distanceToGravitationalItem);
		//find the angle to the item
		double angleToGravitationalItem = toGravitationalItem.angle();
		//find the angle perpendicular to the item
		double angleOfOrbitVelocity = angleToGravitationalItem + Math.PI / 2;
		//get x and y coordinates of orbit velocity
		double x = orbitVelocity * Math.cos(angleOfOrbitVelocity);
		double y = orbitVelocity * Math.sin(angleOfOrbitVelocity);
		//create and return the orbital velocity vector
		return new Vector(x, y);
		//1 sqrt, 3 trigs used
	}
}
