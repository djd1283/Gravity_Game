package game;

import item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;

import toolkit.Create;
import toolkit.Point;
import toolkit.Vector;


/**
 * Holds and maintains all Items for processing and storage. Contains the center of focus for
 * universe-to-screen mapping 'centerOfFocus', and a zoom variable 'zoom' used to scale all game objects around that point. 
 * Contains methods for maintaining objects stored within its main item Lists. Also contains the
 * only permitted Random generator, 'randGen'.
 * @author David
 */
public class Universe
{
	public Point centerOfFocus;
	public Random randGen;
	public double zoom;
	
	private List<Item> areaList = new ArrayList<Item>();
	private List<Item> bodyList = new ArrayList<Item>();
	private List<Item> shipList = new ArrayList<Item>();
	private List<Item> projList = new ArrayList<Item>();
	
	public Universe()
	{
		centerOfFocus = new Point(0, 0);
		zoom = 200;
		randGen = new Random();
	}
	
	
	/**
	 * Currently used to update all the Items in the Universe with a single loop.
	 */
	public void update()
	{
		for(int a = 0; a < areaList.size(); a++)
		{
			areaList.get(a).update();
		}
		for(int a = 0; a < bodyList.size(); a++)
		{
			bodyList.get(a).update();
		}
		for(int a = 0; a < shipList.size(); a++)
		{
			shipList.get(a).update();
		}
		for(int a = 0; a < projList.size(); a++)
		{
			projList.get(a).update();
		}
	}
	
	/**
	 * Used to paint the entire universe. Currently paints all the items in the Universe with
	 * a single loop.
	 */
	public void paint()
	{
		paintBackground();
		
		for(int a = 0; a < areaList.size(); a++)
		{
			areaList.get(a).paint();
		}
		for(int a = 0; a < bodyList.size(); a++)
		{
			bodyList.get(a).paint();
		}
		for(int a = 0; a < shipList.size(); a++)
		{
			shipList.get(a).paint();
		}
		for(int a = 0; a < projList.size(); a++)
		{
			projList.get(a).paint();
		}
	}
	
	/**
	 * @param item Area to be added to the universe.
	 */
	public void addArea(Item item)
	{
		areaList.add(item);
	}
	
	/**
	 * @param item Celestial body to be added to the universe.
	 */
	public void addBody(Item item)
	{
		bodyList.add(item);
	}
	
	/**
	 * @param item Spaceship to be added to the universe.
	 */
	public void addShip(Item item)
	{
		shipList.add(item);
	}
	
	/**
	 * @param item Projectile to be added to the universe.
	 */
	public void addProj(Item item)
	{
		projList.add(item);
	}
	
	/**
	 * @param item Area to be removed from the universe.
	 */
	public void removeArea(Item item)
	{
		areaList.remove(item);
	}
	
	/**
	 * @param item Celestial body to be removed from the universe.
	 */
	public void removeBody(Item item)
	{
		bodyList.remove(item);
	}
	
	/**
	 * @param item Spaceship to be removed from the universe.
	 */
	public void removeShip(Item item)
	{
		shipList.remove(item);
	}
	
	/**
	 * @param item Projectile to be removed from the universe.
	 */
	public void removeProj(Item item)
	{
		projList.remove(item);
	}
	
	/**
	 * Removes the parameter item from all lists.
	 * @param item Item to be removed.
	 */
	public void remove(Item item)
	{
		areaList.remove(item);
		bodyList.remove(item);
		shipList.remove(item);
		projList.remove(item);
	}
	
	/**
	 * @param index Identifier representing the returned area.
	 * @return An area within the universe.
	 */
	public Item getArea(int index)
	{
		return areaList.get(index);
	}
	
	/**
	 * @param index Identifier representing the returned celestial body.
	 * @return A celestial body within the universe.
	 */
	public Item getBody(int index)
	{
		return bodyList.get(index);
	}
	
	/**
	 * @param index Identifier representing the returned spaceship.
	 * @return A spaceship within the universe.
	 */
	public Item getShip(int index)
	{
		return shipList.get(index);
	}
	
	/**
	 * @param index Identifier representing the returned projectile.
	 * @return A projectile within the universe.
	 */
	public Item getProj(int index)
	{
		return projList.get(index);
	}

	/**
	 * @return The number of areas in the list.
	 */
	public int getAreaListSize()
	{
		return areaList.size();
	}
	
	/**
	 * @return The number of celestial bodies in the list.
	 */
	public int getBodyListSize()
	{
		return bodyList.size();
	}
	
	/**
	 * @return The number of spaceships in the list.
	 */
	public int getShipListSize()
	{
		return shipList.size();
	}
	
	/**
	 * @return The number of projectiles in the list.
	 */
	public int getProjListSize()
	{
		return projList.size();
	}
	
	/**
	 * Used to paint the background of the universe. Currently paints a white background covering
	 * the entire screen, using 'centerOfFocus' and 'zoom' to create the vertices. A RadialBoundary is then
	 * added to the universe to project a large black circle, covering most of the screen depending
	 * on position.
	 */
	public void paintBackground()
	{
		double ratio = (double)Display.getHeight() / (double)Display.getWidth();
		Vector shift = centerOfFocus.toVector();
		float[] c1 = {1, 1, 1, 1};
		Point p1 = new Point(-zoom, -zoom * ratio).addVector(shift);
		Point p2 = new Point(zoom, -zoom * ratio).addVector(shift);
		Point p3 = new Point(zoom, zoom * ratio).addVector(shift);
		Point p4 = new Point(-zoom, zoom * ratio).addVector(shift);
		Create.createRect(p1, p2, p3, p4, c1, c1, c1, c1);
	}

}
