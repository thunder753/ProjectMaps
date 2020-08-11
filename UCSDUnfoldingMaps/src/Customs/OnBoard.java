package Customs;

import processing.core.PGraphics;

public abstract class OnBoard
{
	PGraphics thisPG;
	float x, y, size_x, size_y;
	String type;
	public OnBoard(PGraphics pg, float x, float y, float size_x, float size_y)
	{
		this.thisPG = pg;
		this.x = x;
		this.y = y;
		this.size_x = size_x;
		this.size_y = size_y;
	}
	
	public abstract void draw();
	
}
