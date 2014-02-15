package projectile;

import item.Item;
import item.Planet;
import game.Universe;

/**
 * Used to represent a weapon that can be used by intelligent Items
 * within the Universe, such as Spaceship. Contains variables and
 * weapons for handling projectile functionality(damage, explosions,
 * other effects). 
 * 
 * <p>'speed' indicates the projectiles initial velocity.
 * 'deathCounter' represents the number of ticks this Projectile will
 * stay active for. 'inaccuracy' represents noise on the initial angle
 * this Projectile is fired from. 'reload' represents the number of ticks
 * between shots.
 * @author David
 */
public abstract class Projectile extends Item
{
	public double speed;
	public double deathCounter;
	public double inaccuracy;
	public int reload;
	
	public Projectile(Universe universe)
	{
		super(universe);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update()
	{
		deathCounter--;
		if(deathCounter <= 0)
		{
			universe.remove(this);
		}
		
		position = position.addVector(velocity);
		
		detectProximityToAllItems();
	}
	
	@Override
	public abstract void paint();
	
	@Override
	public abstract void updateUponDeath();
	
	@Override
	public void handleProximityToItem(Item item, double distanceSquared)
	{
		if(item instanceof Planet)
		{
			if(distanceSquared < Math.pow(radius + item.radius, 2))
			{
				universe.remove(item);
			}
		}
	}
	
	
}
