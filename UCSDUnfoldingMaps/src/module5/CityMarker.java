package module5;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
//import processing.core.PConstants;
import processing.core.PGraphics;
import Customs.*;

/** Implements a visual marker for cities on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */

public class CityMarker extends CommonMarker  {
	
	public static int TRI_SIZE = 6;  // The size of the triangle marker
	public static Icon icon = new Triangle(0, Helper.blue, TRI_SIZE);
	
	public CityMarker(Location location) {
		super(location);
	}
	
	
	public CityMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		// Cities have properties: "name" (city name), "country" (country name)
		// and "population" (population, in millions)
	}

	
	/** Show the title of the city if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y)
	{
		String info = "City: " + getCity() + "| Country: "+ getCountry()+ "| Population: "+ getPopulation() ; 
		super.titleHelper(pg, x, y, info);
	}
	
	
	
	/* Local getters for some city properties.  
	 */
	public String getCity()
	{
		return getStringProperty("name");
	}
	
	public String getCountry()
	{
		return getStringProperty("country");
	}
	
	public float getPopulation()
	{
		return Float.parseFloat(getStringProperty("population"));
	}


	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		// IMPLEMENT: drawing triangle for each city
				icon.draw(pg, x, y);
		
	}
}
