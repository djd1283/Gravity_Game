package game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import toolkit.Create;
import toolkit.Point;
import toolkit.Vector;

import gameengine.GameEngine;
import gameengine.Plugin;

public class CircleTest implements Plugin
{
	public double heightWidthRatio;
	
	public CircleTest() throws LWJGLException
	{
		new GameEngine(this, 30);
	}
	
	public static void main(String[] args) throws LWJGLException
	{
		new CircleTest();
	}

	@Override
	public void setupPlugin()
	{
		heightWidthRatio = (double)Display.getHeight() / (double)Display.getWidth();
	}

	@Override
	public boolean runPlugin()
	{
		setupView();
		Point origin = new Point(0, 0);
		Point lineSegStart = new Point(20, 0);
		Vector toLineSegEnd = new Vector(-40, 5);
		Point[] points = getLineCircleIntersection(lineSegStart, toLineSegEnd, origin, 10);
		float[] c1 = {0, 0, 1, 1};
		float[] c2 = {1, 0, 0, 1};
		float[] c3 = {0, 1, 0, 1};
		Create.createCircle(origin, 10, c1, c1);
		Create.createLine(lineSegStart, lineSegStart.addVector(toLineSegEnd), c3);
		Create.createLine(points[0], points[1], c2);
		System.out.println("Point 1: " + points[0].x + ", " + points[0].y);
		System.out.println("Point 2: " + points[1].x + ", " + points[1].y);
		return false;
	}
	
	public void setupView()
	{
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		//setup up coordinate system, translated by center of focus, scaled height to width of 200
		GL11.glOrtho(-100, 100, -100 * heightWidthRatio, 100 * heightWidthRatio, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}
	
	/**
	 * Calculates a line and circle intersection, and returns the two or less points
	 * of collision between them. The two points represent the places where the line
	 * intersects with the outline of the circle.
	 * @param A The point that the line segment starts at.
	 * @param B A vector leading from the first point to the second point on the line segment.
	 * @param C A point that represents the center of the circle.
	 * @return Two points, where the line intersects the circle first and last.
	 */
	public Point[] getLineCircleIntersection(Point A, Vector B, Point C, double radius)
	{
		double a = B.x * B.x + B.y * B.y;
		double b = 2 * (A.x * B.x + A.y * B.y - B.x * C.x - B.y * C.y);
		double c = A.x * A.x + C.x * C.x + A.y * A.y + C.y * C.y - radius * radius;
		
		double s = Math.sqrt(b * b - 4 * a * c);
		double d1 = (-b + s) / (2 * a);
		double d2 = (-b - s) / (2 * a);
		if(s != Double.NaN && d1 >= 0 && d1 <= 1 && d2 >= 0 && d2 <= 1)
		{
			Point[] points = {A.addVector(B.multiplyBy(d1)), A.addVector(B.multiplyBy(d2))};
			System.out.println("d1:" + d1);
			System.out.println("d2:" + d2);
			return points;
		}
		return null;
	}

}
