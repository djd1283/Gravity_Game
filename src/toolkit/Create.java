package toolkit;

import java.util.Random;

import org.lwjgl.opengl.GL11;

/**
 * Used to paint basic shapes given basic parameters. Will only
 * work while LWJGL is active.
 * @author David
 */
public class Create
{
	/**
	 * Paints a circle to the screen.
	 * @param p1 the center of the circle.
	 * @param radius the radius of the circle.
	 * @param c1 a 4 float array representing the rgba color for the center of the circle.
	 * @param c2 a 4 float array representing the rgba color for the outer edge of the circle.
	 * @param precision representing the number of triangles that will make up the circle.
	 */
	public static void createCircle(Point p1, double radius, float[] c1, float[] c2, int precision)
	{
		double angle = 2 * Math.PI / (double)precision;
		Point p2 = p1.addVector(new Vector(radius, 0));
		for(int a = 0; a < precision; a++)
		{
			Vector toThirdPoint = new Vector(radius * Math.cos(angle * (a + 1)), radius * Math.sin(angle * (a + 1)));
			Point p3 = p1.addVector(toThirdPoint);
			
			GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glColor4f(c1[0], c1[1], c1[2], c1[3]);
			GL11.glVertex2d(p1.x, p1.y);
			GL11.glColor4f(c2[0], c2[1], c2[2], c2[3]);
			GL11.glVertex2d(p2.x, p2.y);
			GL11.glVertex2d(p3.x, p3.y);
			GL11.glEnd();
			p2 = p3;
		}
	}
	
	/**
	 * Paints a circle to the screen with a precision of 50.
	 * @param p1 the center of the circle.
	 * @param radius the radius of the circle.
	 * @param c1 a 4 float array representing the rgba color for the center of the circle.
	 * @param c2 a 4 float array representing the rgba color for the outer edge of the circle.
	 */
	public static void createCircle(Point p1, double radius, float[] c1, float[] c2)
	{
		createCircle(p1, radius, c1, c2, 50);
	}
	
	/**
	 * @param p1 the first vertex of the rectangle.
	 * @param p2 the second vertex of the rectangle.
	 * @param p3 the third vertex of the rectangle.
	 * @param p4 the fourth vertex of the rectangle.
	 * @param c1 the color of first vertex of the rectangle.
	 * @param c2 the color of second vertex of the rectangle.
	 * @param c3 the color of third vertex of the rectangle.
	 * @param c4 the color of fourth vertex of the rectangle.
	 */
	public static void createRect(Point p1, Point p2, Point p3, Point p4, float[] c1, float[] c2, float[] c3, float[] c4)
	{
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor4d(c1[0], c1[1], c1[2], c1[3]);
		GL11.glVertex2d(p1.x, p1.y);
		GL11.glColor4d(c2[0], c2[1], c2[2], c2[3]);		
		GL11.glVertex2d(p2.x, p2.y);
		GL11.glColor4d(c3[0], c3[1], c3[2], c3[3]);
		GL11.glVertex2d(p3.x, p3.y);
		GL11.glColor4d(c4[0], c4[1], c4[2], c4[3]);
		GL11.glVertex2d(p4.x, p4.y);
		GL11.glEnd();
	}
	
	/**
	 * @param p1 the first vertex of the triangle.
	 * @param p2 the second vertex of the triangle.
	 * @param p3 the third vertex of the triangle.
	 * @param c1 the color of the first vertex of the triangle.
	 * @param c2 the color of the second vertex of the triangle.
	 * @param c3 the color of the third vertex of the triangle.
	 */
	public static void createTri(Point p1,  Point p2, Point p3, float[] c1, float[] c2, float[] c3)
	{
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glColor3f(c1[0], c1[1], c1[2]);
		GL11.glVertex2d(p1.x, p1.y);
		GL11.glColor3f(c2[0], c2[1], c2[2]);		
		GL11.glVertex2d(p2.x, p2.y);
		GL11.glColor3f(c3[0], c3[1], c3[2]);
		GL11.glVertex2d(p3.x, p3.y);
		GL11.glEnd();
	}

	/**
	 * @param p1 The starting vertex of the line.
	 * @param p2 The ending vertex of the line.
	 * @param c1 The color of the line.
	 */
	public static void createLine(Point p1, Point p2, float[] c1)
	{
		GL11.glBegin(GL11.GL_LINES);
		GL11.glColor3f(c1[0], c1[1], c1[2]);
		GL11.glVertex2d(p1.x, p1.y);
		GL11.glVertex2d(p2.x, p2.y);
		GL11.glEnd();
	}
}
