package game;

import gameengine.GameEngine;
import gameengine.Plugin;
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
		boundary.radius = 200;
		universe.addArea(boundary);
		
		Sun sun1 = new Sun(universe);	
		sun1.position = new Point(0, 0);
		universe.addBody(sun1);
		
		Sun sun2 = new Sun(universe);	
		sun2.position = new Point(0, 50);
		universe.addBody(sun2);
		
		for(int a = 0; a < 10; a++)
		{
			Planet planet = new Planet(universe);
			planet.position = new Point(universe.randGen.nextDouble() * 200 - 100, universe.randGen.nextDouble() * 200 - 100);
			planet.velocity = new Vector(universe.randGen.nextDouble() * 5 - 2.5, universe.randGen.nextDouble() * 5 - 2.5);
			universe.addBody(planet);
		}
		
		Spaceship spaceship = new Spaceship(universe);
		spaceship.position = new Point(-50, 0);
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
