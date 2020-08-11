package module4;

import Customs.Circle;
import de.fhpotsdam.unfolding.data.PointFeature;
import processing.core.PGraphics;
import processing.core.PGraphics;

/** Implements a visual marker for land earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public class LandQuakeMarker extends EarthquakeMarker {
	
	
	
	public LandQuakeMarker(PointFeature quake) {
		
		// calling EarthquakeMarker constructor
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = true;
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
		icon = new Circle(0, fillColor, radius+2);
	}
	

	// Get the country the earthquake is in
	public String getCountry() {
		return (String) getProperty("country");
	}



		
}