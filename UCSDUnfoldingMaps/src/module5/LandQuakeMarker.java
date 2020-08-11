package module5;

import Customs.Circle;
import de.fhpotsdam.unfolding.data.PointFeature;

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
	
	private void setup_icon()
	{
		int fillColor = super.determineFill();
		icon = new Circle(0, fillColor, radius*1.5f);
	}

	

	// Get the country the earthquake is in
	public String getCountry() {
		return (String) getProperty("country");
	}

		
}