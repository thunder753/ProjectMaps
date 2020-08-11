package module5;

import Customs.Square;
import de.fhpotsdam.unfolding.data.PointFeature;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Divas Subedi
 *
 */
public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
		setup_icon();
	}
	
	private void setup_icon()
	{
		int fillColor = super.determineFill();
		icon = new Square(0, fillColor, radius*1.5f);
	}
	

	

}
