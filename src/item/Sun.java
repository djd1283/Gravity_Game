package item;

import game.Universe;
import toolkit.Create;

/**
 * Extends Item to represent a sun in the observable universe. A Sun instance is currently
 * unaffected by gravitational force, although it applies a strong gravitational pull on 
 * nearby objects.
 * @author David
 */
public class Sun extends GravitationalItem
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
		strengthOfGravity = .1;
		distanceFactorOfGravity = 1;
	}

	@Override
	public void paint()
	{
		Create.createCircle(position, radius, c1, c1);
	}
	
	@Override
	public void updateUponDeath()
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateUponCollision(Item item)
	{
		universe.remove(item);
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

	@Override
	public void updateGravitationalItem()
	{
		// TODO Auto-generated method stub
		
	}

	
}
