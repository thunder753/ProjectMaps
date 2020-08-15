package Customs;

import processing.core.PConstants;
import processing.core.PGraphics;

public class Button extends OnBoard {
	
	int fillColor;
	private Label label = new Label(new Circle(), "OK");
	private boolean state = false;
	public int id = 0;

	public Button(PGraphics pg, String text, int id, float x, float y, float size_x, float size_y, int fillColor)
	{
		super(pg, x, y, size_x,  size_y);
		this.fillColor = fillColor;
		this.type = "Button";
		this.label.text = text;
		this.id = id;
	}
	
	public Button(PGraphics pg, String text, int id, float x, float y, float size_x, float size_y)
	{
		this(pg, text, id,x, y,size_x,  size_y, Helper.color(150, 200, 200));
	}
	
	public Button(PGraphics pg, String text, int id, float x, float y)
	{
		this(pg, text ,id,x, y, 100,  30);;
	}
	
	public Button(PGraphics pg, float x, float y)
	{
		this(pg, "OK" ,0, x , y);
	}
	
	public Button(PGraphics pg, int id,float x, float y)
	{
		this(pg, "OK", id, x, y);
	}

	@Override
	public void draw() 
	{
		thisPG.pushStyle();
		state2color();
		thisPG.fill(fillColor);
		thisPG.rect(x,y,size_x,size_y,4,4,4,4);
		
		thisPG.fill(0,0,0);
		thisPG.textAlign(PConstants.CENTER, PConstants.CENTER);
		thisPG.textSize(12);
		
		thisPG.text(label.text, x + size_x/2, y + size_y/2);
		thisPG.pushStyle();

	}
	
	private void state2color()
	{
		if(state)
		{
			fillColor = Helper.color(200,150,150);
		}
		else
		{
			fillColor = Helper.color(150,200,200);
		}
	}
	
	public boolean getState()
	{
		return state;
	}
	
	public void click()
	{
		state = !state;
	}
	
	public boolean isInside(float x_c, float y_c)
	{
		return ((x_c > x) && (x_c < x + size_x) && (y_c > y) && (y_c < y + size_y)); 
	}

}
