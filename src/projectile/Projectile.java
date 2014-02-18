package projectile;

import game.Universe;
import item.Item;
import item.Planet;
import toolkit.Point;
import toolkit.Vector;

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
		
		for(int a = 0; a < universe.size(); a++)
		{
			Item target = universe.get(a);
			if(target instanceof Planet)
			{
				Point[] points = getLineCircleIntersection(position, velocity, target.position, target.radius);
				if(points != null)
				{
					universe.remove(this);
					universe.remove(target);
				}
			}
		}
		
		position = position.addVector(velocity);
	}
	
	@Override
	public abstract void paint();
	
	@Override
	public abstract void updateUponDeath();
	
	@Override
	public void handleProximityToItem(Item item, double distanceSquared)
	{
		
	}
	
	/**
	 * Calculates a line and circle intersection, and returns the two or less points
	 * of collision between them. The two points represent the places where the line
	 * intersects with the outline of the circle.
	 * @param A The point that the line segment starts at.
	 * @param B A vector leading from the first point to the second point on the line segment.
	 * @param C A point that represents the center of the circle.
	 * @param r The radius of the circle.
	 * @return Two points, where the line intersects the circle first and last.
	 */
	public Point[] getLineCircleIntersection(Point A, Vector B, Point C, double r)
	{
		double a = B.x * B.x + B.y * B.y;
		double b = 2 * (A.x * B.x + A.y * B.y - B.x * C.x - B.y * C.y);
		double c = A.x * A.x - 2 * A.x * C.x + C.x * C.x + A.y * A.y - 2 * A.y * C.y + C.y * C.y - r * r;
		
		double s = Math.sqrt(b * b - 4 * a * c);
		double d1 = (-b + s) / (2 * a);
		double d2 = (-b - s) / (2 * a);
		if(s != Double.NaN && d1 >= 0 && d1 <= 1 && d2 >= 0 && d2 <= 1)
		{
			Point[] points = {A.addVector(B.multiplyBy(d1)), A.addVector(B.multiplyBy(d2))};
			return points;
		}
		if(d1 < 0 && d2 > 0 || d1 > 0 && d2 < 0)
		{
			Point[] points = {A.addVector(B.multiplyBy(d1)), A.addVector(B.multiplyBy(d2))};
			return points;
		}
		return null;
	}
}
