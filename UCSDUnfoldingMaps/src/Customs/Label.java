package Customs;
import processing.core.PConstants;
import processing.core.PGraphics;

public class Label 
{
	String text = "";
	public Icon thisIcon;
	
	public Label(Icon newIcon, String text)
	{
		thisIcon = newIcon;
		this.text = text;
	}
	
	public Label()
	{
		this(new Xmark(), "A Label");
	}
	
	
	void draw(PGraphics pg, float x, float y)
	{
		pg.pushStyle();
		float location_x = x + 5; 
		
		if(!(this.thisIcon == null))
		{
			thisIcon.draw(pg, location_x, y);
			location_x += 40;
		}
		
		if(!(this.text == null))
		{
			pg.fill(0, 0, 0);
			pg.stroke(0,0,0);
			
			pg.textAlign(PConstants.LEFT, PConstants.CENTER);
			pg.textSize(13);
			
			pg.text(text, location_x, y);
		}

		pg.popStyle();
	}
}
