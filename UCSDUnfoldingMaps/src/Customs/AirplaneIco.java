package Customs;

import processing.core.PGraphics;
import processing.core.PImage; 
import processing.core.PApplet;

public class AirplaneIco extends Icon {

	PImage thisPI;
	public AirplaneIco(float size) 
	{
		super(null, 0, size);
		PApplet pa = new PApplet();
		thisPI = pa.loadImage("plane.png");
		thisPI.resize(0, Math.round(size));
		this.Name = "Airport";
	}
	public AirplaneIco()
	{
		this(10.0f);
	}

	@Override
	public void draw(PGraphics pg, float x, float y) 
	{
		super.setupColors(pg);
		pg.image(thisPI, x, y);
	}

}
