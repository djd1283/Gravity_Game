package item;

import game.Universe;
import toolkit.Create;
import toolkit.Point;
import toolkit.Vector;

/**
 * Used to create an aparent boundary to the Universe. Paints a black,
 * circular universe of radius 'radius'. When objects intersect the boundary,
 * the velocity of the object is reversed, effectively keeping them from leaving.
 * The passage of Items from one RadialBoundary to another may be included in
 * a future update.
 * @author David
 */
public class RadialBoundary extends Item
{
	public RadialBoundary(Universe universe)
	{
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update()
	{
		updateUponAllCollisions();
	}
	@Override
	public void paint()
	{
		float[] c1 = {0, 0, 0, 1};
		float[] c2 = {0, 0, 0, 1};
		Create.createCircle(new Point(0, 0), radius, c1, c2, 100);		
	}
	@Override
	public void updateUponDeath()
	{
		// TODO Auto-generated method stub
		
	}
	public Item copy()
	{
		RadialBoundary radialBoundary = new RadialBoundary(universe);
		radialBoundary.position = position.copy();
		radialBoundary.velocity = velocity.copy();
		radialBoundary.universe = universe;
		radialBoundary.radius = radius;
		radialBoundary.influenceOfGravity = influenceOfGravity;
		return radialBoundary;
	}

	private void updateUponAllCollisions()
	{
		for(int a = 0; a < universe.getBodyListSize(); a++)
		{
			updateUponCollision(universe.getBody(a));
		}
		for(int a = 0; a < universe.getShipListSize(); a++)
		{
			updateUponCollision(universe.getShip(a));
		}
		for(int a = 0; a < universe.getProjListSize(); a++)
		{
			updateUponCollision(universe.getProj(a));
		}
	}
	
	private void updateUponCollision(Item item)
	{
		double distanceX = item.position.x - position.x;
		double distanceY = item.position.y - position.y;
		double distanceSquared = distanceX * distanceX + distanceY * distanceY;
		if(distanceSquared > radius * radius)
		{
			//vector from center of radial boundary to item
			Vector centerToItem = position.vectorTo(item.position);
			//vector representing the magnitude of the items velocity away from center of radial boundary
			Vector velocityFromCenter = item.velocity.project(centerToItem);
			//vector needed to slow down
			Vector decelerationVector = velocityFromCenter.inverse().multiplyBy(2);
			item.velocity = item.velocity.addVector(decelerationVector);
		}
	}
}
