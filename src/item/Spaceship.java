package item;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import projectile.Laser;
import projectile.LongRangeCannon;
import projectile.Missile;
import projectile.Projectile;

import toolkit.Create;
import toolkit.Point;
import toolkit.Vector;

import game.Universe;

/**
 * Extends Item to represent a player-controlled(for now) spaceship. Currently, the spaceship
 * can rotate, accelerate forwards, and fire projectiles toward a Mouse-click upon updating.
 * Unused methods exist for AI navigation, although they have not been completed.
 * @author David
 */
public class Spaceship extends Item
{
	public Projectile projectile;
	public double angle = 0;
	public double acceleration = .04;
	public boolean isHumanPlayer = true;
	public int reloadCounter = 0;
	
	public float[] color = {0, 0, 1, 1};
	
	public Spaceship(Universe universe)
	{
		super(universe);
		influenceOfGravity = .5;
		projectile = new LongRangeCannon(universe);
	}
	
	@Override
	public void update()
	{
		if(reloadCounter > 0)
		{
			reloadCounter--;
		}
		if(isHumanPlayer)
		{
			handleKeyboardAndNavigation();
		}
		else
		{
			handleAIAndNavigation();
		}
		position = position.addVector(velocity);
		
		universe.cof = position;
	}
	@Override
	public void paint()
	{
		double x1 = Math.cos(angle) * 5;
		double y1 = Math.sin(angle) * 5;
		double x2 = Math.cos(angle - 2.36) * 2.5;
		double y2 = Math.sin(angle - 2.36) * 2.5;
		double x3 = Math.cos(angle + 2.36) * 2.5;
		double y3 = Math.sin(angle + 2.36) * 2.5;
		
		Point p1 = position.addVector(new Vector(x1, y1));
		Point p2 = position.addVector(new Vector(x2, y2));
		Point p3 = position.addVector(new Vector(x3, y3));
		
		Create.createTri(p1, p2, p3, color, color, color);
	}
	@Override
	public void handleProximityToItem(Item item, double distanceSquared)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateUponDeath()
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public Item copy()
	{
		Spaceship spaceship = new Spaceship(universe);
		spaceship.position = position.copy();
		spaceship.velocity = velocity.copy();
		spaceship.universe = universe;
		spaceship.radius = radius;
		spaceship.influenceOfGravity = influenceOfGravity;
		return spaceship;
	}

	/**
	 * @deprecated
	 * Calculates movement and attack of spaceship without keyboard controls.
	 */
	private void handleAIAndNavigation()
	{
		
	}
	
	/**
	 * Used by the Spaceship to handle Keyboard and Mouse inputs.
	 */
	private void handleKeyboardAndNavigation()
	{
		double currentAcceleration = 0;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			currentAcceleration = acceleration;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			angle += .2;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			angle -= .2;
		}
		if(Mouse.isButtonDown(0))
		{
			fireProjectile();
		}
		
		double x = Math.cos(angle) * currentAcceleration;
		double y = Math.sin(angle) * currentAcceleration;
		
		//create vector representing the spaceship's acceleration of propulsion
		Vector accelerationVector = new Vector(x, y);
		//apply acceleration
		velocity = velocity.addVector(accelerationVector);
	}
	
	/**
	 * Currently unused, but functional. Corrects the parameter angle 'angle.
	 * If the angle is below 0 radians or above 2 * pi radians, this method
	 * will scale it back down into the 0 < angle < 2 * pi range.
	 * @param angle
	 * @return
	 */
	@SuppressWarnings("unused")
	private double correctAngle(double angle)
	{
		while(true)
		{
			if(angle >= 2 * Math.PI)
			{
				angle -= 2 * Math.PI;
			}
			else if(angle < 0)
			{
				angle += 2 * Math.PI;
			}
			else
			{
				break;
			}
		}
		return angle;
	}

	/**
	 * Used to fire a copy of the Projectile 'projectile' in the direction of the current Mouse coordinates,
	 * with the speed 'projectile.speed' at accuracy !'projectile.inaccuracy'. The Projectile is fired from
	 * the Spaceship's current position.
	 */
	public void fireProjectile()
	{
		//if the gun has reloaded, fire again
		if(reloadCounter <= 0)
		{
			//reset the counter until next shot fired for reload
			reloadCounter = projectile.reload;
			
			//save screen width
			double width = Display.getWidth();
			//save screen height
			double height = Display.getHeight();
			//find cursor x position by shifting and scaling absolute position
			double cursorX = (Mouse.getX() - width / 2) / (width / 2) * universe.zoom;
			//find cursor y position by shifting and scaling absolute position
			double cursorY = (Mouse.getY() - height / 2) / (width / 2) * universe.zoom;
			//construct final cursor Point instance from x and y values, convert from screen to universe coordinates
			Point cursorPoint = new Point(cursorX, cursorY).addVector(universe.cof.toVector());
			//create vector from spaceship's current position to mouse position
			Vector toCursorPoint = position.vectorTo(cursorPoint);
			
			//save angle from the spaceship to the cursor point
			double angleFromSpaceshipToCursor = toCursorPoint.angle();
			//apply noise to the angle above, as to simulate inaccuracy
			double noise = 0;//universe.randGen.nextDouble() * projectile.inaccuracy - projectile.inaccuracy / 2;
			//save the x coordinate of the final velocity of the projectile
			double velocityX = Math.cos(angleFromSpaceshipToCursor + noise) * projectile.speed;
			//save the y coordinate of the final velocity of the projectile
			double velocityY = Math.sin(angleFromSpaceshipToCursor + noise) * projectile.speed;
			
			//create new copy of currently selected projectile
			Projectile projectileFired = (Projectile) projectile.copy();
			//construct final velocity vector and save
			projectileFired.velocity = new Vector(velocityX, velocityY);
			//place projectile at spaceship's position
			projectileFired.position = position.copy();
			//apply final gravitational vector to planets velocity, as acceleration = change in velocity
			universe.add(projectileFired);
		}
	}
}
