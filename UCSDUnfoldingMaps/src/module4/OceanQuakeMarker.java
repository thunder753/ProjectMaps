package module4;

import Customs.Square;
import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
		setup_icon();
	}
	

	public void draw(PGraphics pg, float x, float y) 
	{
		
		icon.draw(pg, x, y);
		super.ifRecent(pg, x, y);
				
	}
	
	private void setup_icon()
	{
		int fillColor = super.determineFill();
		icon = new Square(0, fillColor, radius*2);
	}
	


	

}
