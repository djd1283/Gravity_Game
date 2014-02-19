package item;

import game.Universe;
import toolkit.Create;

/**
 * Extends the item class, representing actual planets in the observable universe.
 * Currently applies a gravitational force of experimental strength. Currently Planets
 * are destroyed through contact with the Projectile class.
 * @author David
 */
public class Planet extends Item
{
	private float[] c1 = new float[4];
	private float[] c2 = new float[4];
	
	public Planet(Universe universe)
	{
		super(universe);
		//pick random colors
		c1[0] = universe.randGen.nextFloat();
		c1[1] = universe.randGen.nextFloat();
		c1[2] = universe.randGen.nextFloat();
		c1[3] = universe.randGen.nextFloat();
		c2[0] = universe.randGen.nextFloat();
		c2[1] = universe.randGen.nextFloat();
		c2[2] = universe.randGen.nextFloat();
		c2[3] = universe.randGen.nextFloat();
		radius = 2;
	}

	@Override
	public void update()
	{
		position = position.addVector(velocity);
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
	public Item copy()
	{
		Planet planet = new Planet(universe);
		planet.position = position.copy();
		planet.velocity = velocity.copy();
		planet.universe = universe;
		planet.radius = radius;
		planet.influenceOfGravity = influenceOfGravity;
		return planet;
	}
	
	
}
