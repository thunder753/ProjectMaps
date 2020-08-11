package Customs;
import processing.core.PGraphics;


public class Triangle extends Icon
{
	public Triangle(Integer strokeColor, Integer fillColor, float size)
    {
        super(strokeColor,fillColor,size);
        this.Name = "Triangle";
    }
	
	public Triangle()
	{
		this(0,Helper.blue, 5.0f);
	}
    
    public void draw(PGraphics pg, float x, float y)
    {
    	pg.pushStyle();
		super.setupColors(pg);
		float[][] cordinate = getCordinates(x,y,size);
		pg.triangle(cordinate[0][0], cordinate[0][1], cordinate[1][0], cordinate[1][1],cordinate[2][0], cordinate[2][1]);
		pg.popStyle();
    }

    private float[][] getCordinates(float x, float y, float s)
	{
		float[][] ans = new float[3][2];
		ans[0][0] = x;
		ans[0][1] = y - s;
		ans[1][0] = x - (float)(s*Math.sqrt(3.0)/2);
		ans[1][1] = y + (float)(s*0.5);
		ans[2][0] = x + (float)(s*Math.sqrt(3.0)/2);
		ans[2][1] = y + (float)(s*0.5);
		return ans;
	}
}