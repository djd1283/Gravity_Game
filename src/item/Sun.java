package item;

import game.Universe;

import java.util.List;

import toolkit.Create;
import toolkit.Point;
import toolkit.Vector;

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

		for (int a = 0; a < universe.size(); a++)
		{
			applyGravitationalForceToItem(universe.get(a), .1, .5);
		}
		
		detectProximityToAllItems();
	}
	@Override
	public void handleProximityToItem(Item item, double distanceSquared)
	{
		if(distanceSquared < Math.pow(radius + item.radius, 2) && !(item instanceof RadialBoundary))
		{
			universe.remove(item);
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
