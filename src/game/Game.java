package game;

import gameengine.GameEngine;
import gameengine.Plugin;
import item.Item;
import item.Planet;
import item.RadialBoundary;
import item.Spaceship;
import item.Sun;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import toolkit.Point;
import toolkit.Vector;

/**
 * Used to store and operate on all components of the game. Contains the target Universe instance
 * used to contain all game objects, as well as other loop and setup related variables/methods.
 * @author David
 */
public class Game implements Plugin
{
	public Universe universe;
	public double heightWidthRatio;
	public boolean startGame = false;
	
	public Game()
	{
		try
		{
			//start new game engine to initialize LWJGL and give graphics to this class
			new GameEngine(this, 30);
		} 
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		//start game
		new Game();
	}

	@Override
	public void setupPlugin()
	{
		heightWidthRatio = (double)Display.getHeight() / (double)Display.getWidth();
		
		//create universe
		universe = new Universe();
		
		RadialBoundary boundary = new RadialBoundary(universe);
		boundary.radius = 4000;
		universe.addArea(boundary);
		
		for(int a = 0; a < 60; a++)
		{
			Sun sun = new Sun(universe);
			
			double sx = universe.randGen.nextDouble() * 4000 - 2000;
			double sy = universe.randGen.nextDouble() * 4000 - 2000;
			
			sun.position = new Point(sx, sy);
			sun.radius = 10;
			
			int minDistance = 200;
			boolean canPlaceHere = true;
			
			for(int c = 0; c < universe.getBodyListSize(); c++)
			{
				Item item = universe.getBody(c);
				if(item instanceof Sun)
				{
					Vector toSun = sun.position.vectorTo(item.position);
					
					double distance = toSun.magnitude();
					
					if(distance < minDistance)
					{
						canPlaceHere = false;
						break;
					}
				}
			}
			
			if(canPlaceHere)
			{
				universe.addBody(sun);
				//loop through 10 planets to be created
				for(int b = 0; b < 10; b++)
				{
					//create planet
					Planet planet = new Planet(universe);
					//set the planet's radius between 0 and 4
					planet.radius = 1 + universe.randGen.nextDouble() * 5;
					//generate the planet's location to a random location in the universe
					double angle = universe.randGen.nextDouble() * 2 * Math.PI;
					double x = sx + (universe.randGen.nextDouble() * 50 + 50) * Math.cos(angle);
					double y = sy + (universe.randGen.nextDouble() * 50 + 50) * Math.sin(angle);
					//save the generated location
					planet.position = new Point(x, y);
					//set the planets velocity to that needed to orbit the sun
					planet.velocity = planet.getVelocityToOrbitItem(sun);
					//add the planet to the universe
					universe.addBody(planet);
				}
			}
			else
			{
				a--;
			}
		}
		Spaceship spaceship = new Spaceship(universe);
		spaceship.position = new Point(-100, 0);
		universe.addShip(spaceship);
	}	
	@Override
	public boolean runPlugin()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_0))
		{
			startGame = true;
		}
		if(startGame)
		{	
			updateGame();
			setupView();
			paintGame();
		}
		return false;
	}

	/**
	 * Used to update all components. Currently updates the saved universe.
	 */
	public void updateGame()
	{
		universe.update();
	}
	/**
	 * Used to paint all components. Current updates the saved universe.
	 */
	public void paintGame()
	{
		universe.paint();
	}
	/**
	 * Used to initialize the coordinate system and other paint parameters for OpenGL.
	 */
	public void setupView()
	{
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//setup up coordinate system, translated by center of focus, scaled height to width of 200
		GL11.glOrtho(-universe.zoom + universe.centerOfFocus.x, universe.zoom + universe.centerOfFocus.x, -universe.zoom * heightWidthRatio + universe.centerOfFocus.y, universe.zoom * heightWidthRatio + universe.centerOfFocus.y, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

}
