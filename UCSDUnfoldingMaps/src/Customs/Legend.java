package Customs;
import processing.core.PConstants;
import processing.core.PGraphics;
import java.util.ArrayList;

public class Legend extends OnBoard
{
	ArrayList<Label> labels = new ArrayList<Label>(); 
	float interLabeldist = 30;
	String title ="";
	int fillColor;
	
	public Legend(PGraphics pg, String name,float x, float y, float size_x, float size_y, int fillColor)
	{
		super(pg, x, y, size_x,  size_y);
		this.title = name;
		this.fillColor = fillColor;
		this.type = "Legend";
	}
	
	public Legend(PGraphics pg, String name,float x, float y, float size_x, float size_y)
	{
		this(pg, name, x, y, size_x,  size_y, Helper.color(150, 200, 200));
	}
	
	
	public void put(Label thisLabel)
	{
		labels.add(thisLabel);
	}
	
	public void draw()
	{
		drawrect();
		float x_c = x + 10;
		float y_c = y + 75;
		
		for(Label thisLabel : labels)
		{

			thisLabel.draw(thisPG,x_c,y_c);
			y_c = y_c + interLabeldist;
		}
	}
	
	
	
	private void drawrect()
	{
		thisPG.pushStyle();
		thisPG.fill(fillColor);
		thisPG.rect(x,y,size_x,size_y,1,50,1,50);
		
		thisPG.fill(0,0,0);
		thisPG.textAlign(PConstants.CENTER);
		thisPG.textSize(17);
//		thisPG.strokeWeight(2);
		
		thisPG.text(title, x + size_x/2, y+30);
		thisPG.pushStyle();
	}
}
