package Customs;
import processing.core.PGraphics;

public class Circle extends Icon{
    public Circle (Integer strokeColor, Integer fillColor, float radius)
    {
        super(strokeColor,fillColor, radius);
        this.Name = "Circle";
    }
	
    public Circle()
	{
		this(0,Helper.blue,5.0f);
	}
	
    public void draw(PGraphics pg, float x, float y)
    {
    	pg.pushStyle();
        super.setupColors(pg);
        pg.ellipse(x, y, size+2, size+2);
        pg.popStyle();
    }
}