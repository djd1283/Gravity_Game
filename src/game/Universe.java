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
 * universe-to-screen mapping 'cof', and a zoom variable 'zoom' used to scale all game objects around that point. 
 * Contains methods for maintaining objects stored within its main item Lists. Also contains the
 * only permitted Random generator, 'randGen'.
 * @author David
 */
public class Universe
{
	public Point cof;
	public Random randGen;
	public double zoom;
	
	private List<Item> itemList = new ArrayList<Item>();
	
	public Universe()
	{
		cof = new Point(0, 0);
		zoom = 200;
		randGen = new Random();
	}
	
	
	/**
	 * Currently used to update all the Items in the Universe with a single loop.
	 */
	public void update()
	{
		for(int a = 0; a < itemList.size(); a++)
		{
			itemList.get(a).update();
		}
	}

	/**
	 * Used to add an Item to this Universe, to be included in updates and paint requests.
	 * @param item Item to be added
	 */
	public void add(Item item)
	{
		itemList.add(item);
	}
	
	/**
	 * Used to remove an Item from this Universe. The item will no longer be updated and will
	 * no longer receive paint requests.
	 * @param item Item to be removed
	 */
	public void remove(Item item)
	{
		item.updateUponDeath();
		itemList.remove(item);
	}
	
	/**
	 * Used to paint the entire universe. Currently paints all the items in the Universe with
	 * a single loop.
	 */
	public void paint()
	{
		paintBackground();
		for(int a = 0; a < itemList.size(); a++)
		{
			itemList.get(a).paint();
		}
	}

	/**
	 * Used to paint the background of the universe. Currently paints a white background covering
	 * the entire screen, using 'cof' and 'zoom' to create the vertices. A RadialBoundary is then
	 * added to the universe to project a large black circle, covering most of the screen depending
	 * on position.
	 */
	public void paintBackground()
	{
		double ratio = (double)Display.getHeight() / (double)Display.getWidth();
		Vector shift = cof.toVector();
		float[] c1 = {1, 1, 1, 1};
		Point p1 = new Point(-zoom, -zoom * ratio).addVector(shift);
		Point p2 = new Point(zoom, -zoom * ratio).addVector(shift);
		Point p3 = new Point(zoom, zoom * ratio).addVector(shift);
		Point p4 = new Point(-zoom, zoom * ratio).addVector(shift);
		Create.createRect(p1, p2, p3, p4, c1, c1, c1, c1);
	}

	/**
	 * Used to retrieve an item from this universe's main storage list.
	 * @param index The identifier for the Item to be retrieved in this universe's main List of Items.
	 * @return The Item indicated by 'index', after being retrieved from the main List.
	 */
	public Item get(int index)
	{
		return itemList.get(index);
	}

	/**
	 * Used for looping and referencing purposes.
	 * @return The number of Items contained by this universe.
	 */
	public int size()
	{
		return itemList.size();
	}

}
