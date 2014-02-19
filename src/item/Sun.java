package item;

import game.Universe;
import toolkit.Create;

/**
 * Extends Item to represent a sun in the observable universe. A Sun instance is currently
 * unaffected by gravitational force, although it applies a strong gravitational pull on 
 * nearby objects.
 * @author David
 */
public class Sun extends Item
{
	private float[] c1 = new float[4];

	public Sun(Universe universe)
	{
		super(universe);
		c1[0] = 1;
		c1[1] = 1;
		c1[2] = 0;
		c1[3] = 1;
		radius = 5;
		influenceOfGravity = 0;
	}

	@Override
	public void paint()
	{
		Create.createCircle(position, radius, c1, c1);
	}
	@Override
	public void update()
	{
		position = position.addVector(velocity);
		applyGravitationalForceToAllItems();
		updateUponAllCollisions();
	}
	
	private void updateUponAllCollisions()
	{
		for(int a = 0; a < universe.getBodyListSize(); a++)
		{
			Item currentBody = universe.getBody(a);
			if(!(currentBody instanceof Sun))
			{
				updateUponCollision(currentBody);
			}
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
	
	private void applyGravitationalForceToAllItems()
	{
		for (int a = 0; a < universe.getBodyListSize(); a++)
		{
			Item currentBody = universe.getBody(a);
			if(!(currentBody instanceof Sun))
			{
				applyGravitationalForceToItem(currentBody, .1, .5);
			}
		}
		for (int a = 0; a < universe.getShipListSize(); a++)
		{
			applyGravitationalForceToItem(universe.getShip(a), .1, .5);
		}
		for (int a = 0; a < universe.getProjListSize(); a++)
		{
			applyGravitationalForceToItem(universe.getProj(a), .1, .5);
		}
	}
	
	public void updateUponCollision(Item item)
	{
		double distanceX = item.position.x - position.x;
		double distanceY = item.position.y - position.y;
		if(distanceX < radius + item.radius && distanceY < radius + item.radius)
		{
			double distanceSquared = distanceX * distanceX + distanceY * distanceY;
			if(distanceSquared < Math.pow(radius + item.radius, 2) && !(item instanceof RadialBoundary))
			{
				universe.remove(item);
			}
		}
	}
	
	@Override
	public void updateUponDeath()
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public Item copy()
	{
		Sun sun = new Sun(universe);
		sun.position = position.copy();
		sun.velocity = velocity.copy();
		sun.universe = universe;
		sun.radius = radius;
		sun.influenceOfGravity = influenceOfGravity;
		return sun;
	}
	
}
