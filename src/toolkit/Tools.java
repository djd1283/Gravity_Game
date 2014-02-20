package toolkit;

public class Tools
{
	/**
	 * Corrects the parameter angle 'angle.
	 * If the angle is below 0 radians or above 2 * pi radians, this method
	 * will scale it back down into the 0 < angle < 2 * pi range.
	 * @param angle
	 * @return
	 */
	public static double correctAngle(double angle)
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
}
