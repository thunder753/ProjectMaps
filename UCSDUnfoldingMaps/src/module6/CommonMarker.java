package module6;

import Customs.Helper;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** Implements a common marker for cities and earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public abstract class CommonMarker extends SimplePointMarker {

	// Records whether this marker has been clicked (most recently)
	protected boolean clicked = false;
	
	public CommonMarker(Location location) {
		super(location);
	}
	
	public CommonMarker(Location location, java.util.HashMap<java.lang.String,java.lang.Object> properties) {
		super(location, properties);
	}
	
	// Getter method for clicked field
	public boolean getClicked() {
		return clicked;
	}
	
	// Setter method for clicked field
	public void setClicked(boolean state) {
		clicked = state;
	}
	
	// Common piece of drawing method for markers; 
	// Note that you should implement this by making calls 
	// drawMarker and showTitle, which are abstract methods 
	// implemented in subclasses
	public void draw(PGraphics pg, float x, float y) {
		// For starter code just drawMaker(...)
		pg.pushStyle();
		if (!hidden) {
			drawMarker(pg, x, y);
			if (selected) 
			{
				showTitle(pg, x, y);
			}
		}
		pg.popStyle();
	}
	public abstract void drawMarker(PGraphics pg, float x, float y);
	public abstract void showTitle(PGraphics pg, float x, float y);
	
	public void titleHelper (PGraphics pg, float x, float y, String info)
	{
		pg.pushStyle();
		pg.stroke(0);
		pg.fill(Helper.white);
		pg.rect(x, y, 300, 20);
		
		pg.fill(0);
		pg.textAlign(PConstants.LEFT, PConstants.CENTER);
		pg.text(info, x + 10, y+5);
		
		pg.pushStyle();
	}
	
}