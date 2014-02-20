package projectile;

import game.Universe;
import item.Item;
import item.Sun;
import toolkit.Create;
import toolkit.Tools;
import toolkit.Vector;

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
		deathCounter = 100;
		influenceOfGravity = 1;
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
		Item targetItem = null;
		//variable used to find item closest to this missile
		double minDistance = Double.MAX_VALUE;
		//loop through all celestial bodies
		for(int a = 0; a < universe.getBodyListSize(); a++)
		{
			//save current celestial body
			Item currentBody = universe.getBody(a);
			//continue only if the item is not a sun
			if(!(currentBody instanceof Sun))
			{
				//create a vector from this item to the celestial body
				Vector toItem = position.vectorTo(currentBody.position);
				//use the created vector to find the distance between them
				double distance = toItem.magnitude();
				//if the distance found is less than the last found closest distance
				if(distance < minDistance)
				{
					//set the new closest distance to the one found above
					minDistance = distance;
					//set the new closest celestial body to the current one
					targetItem  = currentBody;
				}
			}
		}
		//if a target celestial body is found
		if(targetItem != null)
		{
			//create a vector from this item to the celestial body
			Vector toItem = position.vectorTo(targetItem.position);
			//find the desired angle to the item, where the missile has to be facing to hit it
			double angleToItem = toItem.angle();
			//find the current angle the missile is facing
			double currentAngle = velocity.angle();
			//find the difference between the desired angle and the current angle
			double difference = angleToItem - currentAngle;
			//if the desired angle is greater than the current angle
			if(difference > 0) 
			{
				//add small change in angle
				currentAngle += .01;
			}
			//if the current angle is greater than the desired angle
			else if(difference < 0)
			{
				//subtract small change in angle
				currentAngle -= .01;
			}
			//find old velocity magnitude
			double magnitude = velocity.magnitude();
			//find new velocity x
			double x = magnitude * Math.cos(currentAngle);
			//find new velocity y
			double y = magnitude * Math.sin(currentAngle);
			//apply changes
			velocity = new Vector(x, y);
		}
	}

}
