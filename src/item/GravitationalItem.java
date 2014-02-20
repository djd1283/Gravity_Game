package item;

import toolkit.Vector;
import game.Universe;

public abstract class GravitationalItem extends Item
{
	public double strengthOfGravity;
	public double distanceFactorOfGravity;

	public GravitationalItem(Universe universe)
	{
		super(universe);
	}
	
	public abstract void updateUponCollision(Item item);
	public abstract void updateGravitationalItem();
	
	@Override
	public void update()
	{
		applyGravitationalForceToAllItems();
		handleAllCollisions();
		updateGravitationalItem();
		position = position.addVector(velocity);
	}
	
	/**
	 * Loops through all items, detects a collision between this item and
	 * the current item in the loop, and updates upon collision.
	 */
	private void handleAllCollisions()
	{
		for(int a = 0; a < universe.getBodyListSize(); a++)
		{
			Item currentBody = universe.getBody(a);
			if(!(currentBody instanceof Sun))
			{
				handleCollision(currentBody);
			}
		}
		for(int a = 0; a < universe.getShipListSize(); a++)
		{
			handleCollision(universe.getShip(a));
		}
		for(int a = 0; a < universe.getProjListSize(); a++)
		{
			handleCollision(universe.getProj(a));
		}
	}
	
	/**
	 * Detects a collision between this item and the target item and calls an abstract method to
	 * handle the collision.
	 * @param item t
	 */
	public void handleCollision(Item item)
	{
		double distanceX = item.position.x - position.x;
		double distanceY = item.position.y - position.y;
		if(distanceX < radius + item.radius && distanceY < radius + item.radius)
		{
			double distanceSquared = distanceX * distanceX + distanceY * distanceY;
			if(distanceSquared < Math.pow(radius + item.radius, 2) && !(item instanceof RadialBoundary))
			{
				updateUponCollision(item);
			}
		}
	}
	
	/**
	 * @param item The item the gravitational force is acting on.
	 * @param strengthOfGravity the scalar strength of gravity.
	 * @param distanceFactorOfGravity the inverse expontential effect of distance on gravity.
	 * @return a vector representing the gravitational acceleration to be applied to the item.
	 */
	public Vector gravitationalForce(Item item, double strengthOfGravity, double distanceFactorOfGravity)
	{
		//quick check to see if target item is affected by gravity
		if(item.influenceOfGravity != 0)
		{
			//gravity cannot be applied on an item by itself
			if(item != this)
			{
				// get vector from item to sun
				Vector vectorFromItemToSun = item.position.vectorTo(position);
				// get magnitude representing distance from item to sun
				double distanceFromItemToSun = vectorFromItemToSun.magnitude();
				// a = G / d^2 used to find magnitude of gravitational acceleration
				double gravitationalAcceleration = strengthOfGravity * item.influenceOfGravity / Math.pow(distanceFromItemToSun, distanceFactorOfGravity);
				// scale vector from item to sun to gravitational vector, to change magnitude but not direction
				Vector gravitationalVector = vectorFromItemToSun.multiplyBy(gravitationalAcceleration / distanceFromItemToSun);
				// apply final gravitational vector to item's velocity, as acceleration = change in velocity
				return gravitationalVector;
				// only 1 square root used in process
			}
		}
		return new Vector(0, 0);
	}
	
	/**
	 * Loop through all viable items and apply gravitational acceleration to each one. Will not apply this vector
	 * to areas.
	 */
	private void applyGravitationalForceToAllItems()
	{
		//loop through all celestial bodies
		for (int a = 0; a < universe.getBodyListSize(); a++)
		{
			//save current celestial body
			Item item = universe.getBody(a);
			//get the gravitational acceleration to be applied to the current item
			Vector acceleration = gravitationalForce(item, strengthOfGravity, distanceFactorOfGravity);
			//apply the gravitational acceleration
			item.velocity = item.velocity.addVector(acceleration);
		}
		//loop through all spaceships
		for (int a = 0; a < universe.getShipListSize(); a++)
		{
			//save current celestial body
			Item item = universe.getShip(a);
			//get the gravitational acceleration to be applied to the current item
			Vector acceleration = gravitationalForce(item, strengthOfGravity, distanceFactorOfGravity);
			//apply the gravitational acceleration
			item.velocity = item.velocity.addVector(acceleration);
		}
		//loop through all projectiles
		for (int a = 0; a < universe.getProjListSize(); a++)
		{
			//save current celestial body
			Item item = universe.getProj(a);
			//get the gravitational acceleration to be applied to the current item
			Vector acceleration = gravitationalForce(item, strengthOfGravity, distanceFactorOfGravity);
			//apply the gravitational acceleration
			item.velocity = item.velocity.addVector(acceleration);
		}
	}

}
