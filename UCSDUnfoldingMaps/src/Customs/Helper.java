package Customs;
/**
 * @author Divas Subedi
 *
 */
public class Helper 
{

	// ADD constants for colors
	public static final int red = Helper.color(255,50,50);
	public static final int yellow = Helper.color(255,255,0);
	public static final int orange = Helper.color(255,150,0);
	public static final int green = Helper.color(50,255,50);
	public static final int blue = Helper.color(50,150,250);
	public static final int purple = Helper.color(255,0,255);
	public static final int black = Helper.color(0,0,0);
	public static final int white = Helper.color(255,255,255);
		

	public static int color(int  a, int b, int c)
	{
		int color = 16777216;
		color = color - c;
		color = color - 256*b;
		color = color - 256*a*256;
		return -color;
	}
}
