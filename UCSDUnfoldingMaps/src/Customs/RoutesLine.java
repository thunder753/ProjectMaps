package Customs;

import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;

public class RoutesLine extends SimpleLinesMarker {
	
	public RoutesLine(List<Location> arg0, HashMap<String, Object> arg1) 
	{
		super(arg0, arg1);
		this.hidden = false;
	}
	
	public RoutesLine(List<Location> arg0) 
	{
		this(arg0, null);
	}

	
//	@Override
//	public void draw(PGraphics pg, float x, float y)
//	{
//		pg.pushStyle();
//		if (!hidden) 
//		{
//			super.draw(pg, x, y);
//		}
//		pg.popStyle();
//	}
	
	public int getSource()
	{
		return Integer.parseInt(this.getStringProperty("source"));
	}
	
	public int getDestination()
	{
		return Integer.parseInt(this.getStringProperty("destination"));
	}

}
