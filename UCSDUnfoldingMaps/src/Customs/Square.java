package Customs;
import processing.core.PGraphics;

public class Square extends Icon 
{
	public Square(Integer strokeColor, Integer fillColor, float length)
    {
        super(strokeColor,fillColor, length);
        this.Name = "Square";
    }
	
    public Square()
	{
		this(0,Helper.blue,5.0f);
	}

    public void draw(PGraphics pg, float x, float y) 
    {
    	pg.pushStyle();
    	float x_c = x - size/2;
    	float y_c = y - size/2;
        super.setupColors(pg);
        pg.rect(x_c, y_c, size, size);
        pg.popStyle();
    }
}