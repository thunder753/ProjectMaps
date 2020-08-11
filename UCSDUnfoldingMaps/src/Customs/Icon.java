package Customs;
import processing.core.PGraphics;

public abstract class Icon 
{
    Integer strokeColor = null;
    Integer fillColor = null;
    String Name = "";
    float size;
    
    public abstract void draw(PGraphics pg, float x, float y);

    public Icon(Integer strokeColor, Integer fillColor, float size)
    {
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
		this.size = size;
    }

    void setupColors(PGraphics pg)
    {
        if (strokeColor == null)
		{
			pg.noStroke();
		}else
		{
			pg.stroke(strokeColor);
        }
        pg.strokeWeight(1.5f);
        pg.fill(fillColor);
    }
}