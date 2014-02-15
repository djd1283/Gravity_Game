package toolkit;

/**
 * This class is used to represent a vector, or a change in position(which is represented
 * by the Point class). Vectors can be used to represent common directional velocities,
 * accelerations, forces, and momenta.
 * @author David
 */
public class Vector
{
	public double x, y;
	
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Calculates the magnitude of this vector. The magnitude can also
	 * be thought of as the hypotenuse, where x and y are the two
	 * sides of a right-triangle.
	 * @return the magnitude of this Vector.
	 */
	public double magnitude()
	{
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Calculates the angle of this vector. Simply returns the
	 * result of the function Math.atan2() with y and x as parameters.
	 * @return The angle of this vector.
	 */
	public double angle()
	{
		return Math.atan2(y, x);
	}
	
	/**
	 * Multiplies both the x and y coordinates of this vector
	 * by the scalar 'd' specified. This vector is not changed
	 * during the execution of this method.
	 * @param d The scalar to multiply by this vector.
	 * @return The result of this operation.
	 */
	public Vector multiplyBy(double d)
	{
		return new Vector(d * x, d * y);
	}
	
	/**
	 * Adds two Vectors together, this Vector and Vector
	 * 'vector'. Simply put, the x coordinates of both and
	 * the y coordinates of both are added together separately.
	 * @param vector The vector to add to this vector.
	 * @return the result of the operation.
	 */
	public Vector addVector(Vector vector)
	{
		return new Vector(x + vector.x, y + vector.y);
	}
	
	/**
	 * Returns the classical definition of the dot product
	 * for this Vector.
	 * @param vector The second vector involved in the dot product operation.
	 * @return The resultant dot product scalar.
	 */
	public double dotProduct(Vector vector)
	{
		return x * vector.x + y * vector.y;
	}
	
	/**
	 * Projects this vector onto Vector vector.
	 * @return A projection vector.
	 */
	public Vector project(Vector vector)
	{
		double magnitude = vector.magnitude();
		double d1 = this.dotProduct(vector) / magnitude / magnitude;
		return vector.multiplyBy(d1);
	}
	
	/**
	 * Converts this vector to a point using its x and y coordinates as parameters.
	 * For conceptual purposes only. Does not change this vector, but returns the result
	 * @return the Point form of this Vector.
	 */
	public Point toPoint()
	{
		return new Point(x, y);
	}
	
	/**
	 * Inverts the x and y coordinates of this Vector, and returns the result. This
	 * vector is not changed in the process.
	 * @return a Vector in the opposite direction of this Vector.
	 */
	public Vector inverse()
	{
		return new Vector(-x, -y);
	}
	
	/**
	 * Copies this Vector and returns a pass-by-value replica.
	 * @return The copied Vector.
	 */
	public Vector copy()
	{
		return new Vector(x, y);
	}
	
}
