package projectile;

import game.Universe;
import item.Item;
import toolkit.Create;

public class LongRangeCannon extends Projectile
{
	private float[] c1 = {1, 0, 0, 1};

	public LongRangeCannon(Universe universe)
	{
		super(universe);
		this.deathCounter = 100;
		this.inaccuracy = 0;
		this.radius = .8;
		this.speed = 8;
		this.reload = 20;
		this.influenceOfGravity = 8;
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
		LongRangeCannon longRangeCannon = new LongRangeCannon(universe);
		longRangeCannon.position = position.copy();
		longRangeCannon.velocity = velocity.copy();
		longRangeCannon.universe = universe;
		longRangeCannon.radius = radius;
		longRangeCannon.influenceOfGravity = influenceOfGravity;
		longRangeCannon.speed = speed;
		longRangeCannon.deathCounter = deathCounter;
		return longRangeCannon;
	}

}