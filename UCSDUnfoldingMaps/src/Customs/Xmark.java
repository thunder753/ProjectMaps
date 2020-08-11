package Customs;
import processing.core.PGraphics;

public class Xmark extends Icon
{
	public Xmark(float size)
	{
		super(0, 0, (float)(size/Math.sqrt(2)));
	}
	
	public Xmark()
	{
		this(5.0f);
		this.Name = "X";
	}
	
	public void draw(PGraphics pg, float x, float y)
	{
		pg.pushStyle();
		super.setupColors(pg);
		pg.line(x - size, y - size, x+size, y+size);
		pg.line(x - size, y + size, x+size, y-size);
		pg.popStyle();
	}
}
