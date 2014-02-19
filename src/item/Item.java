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

	/**
	 * Applies a gravitational force of strength 'strengthOfGravity' to the item.
	 * The gravitational force is scaled inversely by the distance between this Item and the
	 * target Item, and is taken to the power of 'distanceFactorOfGravity' for relevance
	 * purposes.
	 * @param item The Item that the gravitational vector will be applied to.
	 */
	public void applyGravitationalForceToItem(Item item, double strengthOfGravity, double distanceFactorOfGravity)
	{
		//quick check to see if target item is affected by gravity
		if(item.influenceOfGravity != 0)
		{
			//gravity cannot be applied on an item by itself
			if(item != this)
			{
				// get vector from planet to sun
				Vector vectorFromPlanetToSun = item.position.vectorTo(position);
				// get magnitude representing distance from planet to sun
				double distanceFromPlanetToSun = vectorFromPlanetToSun.magnitude();
				// a = G / d^2 used to find magnitude of gravitational acceleration
				double gravitationalAcceleration = strengthOfGravity * item.influenceOfGravity / Math.pow(distanceFromPlanetToSun, distanceFactorOfGravity);
				// scale vector from planet to sun to gravitational vector, to change magnitude but not direction
				Vector gravitationalVector = vectorFromPlanetToSun.multiplyBy(gravitationalAcceleration / distanceFromPlanetToSun);
				// apply final gravitational vector to planets velocity, as acceleration = change in velocity
				item.velocity = item.velocity.addVector(gravitationalVector);
				// only 1 square root used in process
			}
		}
	}

}
