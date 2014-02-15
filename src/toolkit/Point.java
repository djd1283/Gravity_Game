package toolkit;

/**
 * Represents a single coordinate pair in any context.
 * Modular methods are included for coordinate pair processing.
 * @author David
 */
public class Point
{
	public double x, y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Applies a Vector to this point. A vector in this
	 * context is simply a change in position, or a change in
	 * this coordinate pair. This method will not change this
	 * point, but will return the result of the operation.
	 * @param vector the Vector to be added to this Point
	 * @return the resultant Point after the Vector is applied.
	 */
	public Point addVector(Vector vector)
	{
		return new Point(x + vector.x, y + vector.y);
	}
	
	/**
	 * Returns a Vector that conceptually "goes" from this Point to
	 * the target Point 'point'
	 * change this Point or the parameter Point.
	 * @param point The Point that this Point and the resultant Vector will add to.
	 * @return A vector that goes from this Point to the parameter Point.
	 */
	public Vector vectorTo(Point point)
	{
		return new Vector(point.x - x, point.y - y);
	}
	
	/**
	 * Converts the x and y coordinates of this Point to the x and y
	 * coordinates of a Vector. Used mostly for conceptual purposes.
	 * @return
	 */
	public Vector toVector()
	{
		return new Vector(x, y);
	}
	
	/**
	 * Copies this reference.
	 * @return a pass-by-value copy of this Point.
	 */
	public Point copy()
	{
		return new Point(x, y);
	}
}
