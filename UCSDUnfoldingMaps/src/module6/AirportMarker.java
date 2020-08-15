package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;
import Customs.*;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	public static final Icon icon = new AirplaneIco();
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		setId(city.getId());
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		icon.draw(pg,x,y);
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) 
	{
		String info = getCode()+ " | " + getCountry();
		super.titleHelper(pg, x, y, info);
	}
	
	private String getCode()
	{
		return this.getStringProperty("code");
	}
	
	private String getCountry()
	{
		return this.getStringProperty("country");
	}
	
	public int getID()
	{
		return Integer.parseInt(getId());
	}
	
}
