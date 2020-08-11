package Customs;

import processing.core.PConstants;
import processing.core.PGraphics;

public class Button extends OnBoard {
	
	int fillColor;
	public Label label = new Label(new Circle(), "OK");

	public Button(PGraphics pg, String text, float x, float y, float size_x, float size_y, int fillColor)
	{
		super(pg, x, y, size_x,  size_y);
		this.fillColor = fillColor;
		this.type = "Button";
		this.label.text = text;
	}
	
	public Button(PGraphics pg, String text,float x, float y, float size_x, float size_y)
	{
		this(pg, text, x, y, size_x,  size_y, Helper.color(150, 200, 200));
	}
	
	public Button(PGraphics pg, String text,float x, float y)
	{
		this(pg, text ,x, y, 15,  15, Helper.color(150, 200, 200));
	}
	
	public Button(PGraphics pg,float x, float y)
	{
		this(pg, "OK" ,x, y, 20,  20, Helper.color(150, 200, 200));
	}

	@Override
	public void draw() 
	{
		thisPG.pushStyle();
		thisPG.fill(fillColor);
		thisPG.rect(x,y,size_x,size_y,5,5,5,5);
		
		thisPG.fill(0,0,0);
		thisPG.textAlign(PConstants.CENTER, PConstants.CENTER);
		thisPG.textSize(10);
		
		thisPG.text(label.text, x + size_x/2, y + size_y/2);
		thisPG.pushStyle();

	}

}
