package projectile;

import game.Universe;
import item.Item;
import toolkit.Create;

/**
 * Extends Projectile to represent a mid-ranged space weapon. Currently intended
 * for use with solar systems. Strong attraction to gravity makes it difficult
 * to aim, but with skill longer distance enemies can be damaged. In future
 * implementations, Missile will have a larger damage rating than Laser.
 * @author David
 */
public class Missile extends Projectile
{
	private float[] c1 = {0, 1, 0, 1};

	public Missile(Universe universe)
	{
		super(universe);
		speed = 5;
		deathCounter = 50;
		influenceOfGravity = 5;
		reload = 10;
		radius = .5;
		inaccuracy = 0;
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
		Missile missile = new Missile(universe);
		missile.position = position.copy();
		missile.velocity = velocity.copy();
		missile.universe = universe;
		missile.radius = radius;
		missile.influenceOfGravity = influenceOfGravity;
		missile.speed = speed;
		missile.deathCounter = deathCounter;
		return missile;
	}

	@Override
	public void updateUponCollision(Item item)
	{
		universe.remove(item);
		universe.remove(this);
	}

	@Override
	public void updateProjectile()
	{
		//find nearest planet and home in on it.
		for(int a = 0; a < universe.getBodyListSize(); a++)
		{
			Item currentBody = universe.getBody(a);
			if(!(currentBody instanceof Sun))
			{
				
			}
		}
	}

}
