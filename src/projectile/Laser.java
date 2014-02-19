package projectile;

import game.Universe;
import item.Item;
import toolkit.Create;

/**
 * Extends Projectile to represent a short-ranged space weapon. It
 * is meant to be rapid fire with a high 'inaccuracy' rating. This
 * weapon is mostly meant for beginners and close-range encounters.
 * @author David
 */
public class Laser extends Projectile
{
	private float[] c1 = {1, 1, 1, 1};

	public Laser(Universe universe)
	{
		super(universe);
		radius = .25;
		speed = 1;
		deathCounter = 100;
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
		Laser laser = new Laser(universe);
		laser.position = position.copy();
		laser.velocity = velocity.copy();
		laser.universe = universe;
		laser.radius = radius;
		laser.influenceOfGravity = influenceOfGravity;
		laser.speed = speed;
		laser.deathCounter = deathCounter;
		return laser;
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
		// TODO Auto-generated method stub
		
	}
	
	
}
