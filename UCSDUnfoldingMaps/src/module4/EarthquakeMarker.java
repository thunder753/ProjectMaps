package module4;

import Customs.Helper;
import Customs.Icon;
import Customs.Xmark;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import processing.core.PGraphics;

/** Implements a visual marker for earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
public abstract class EarthquakeMarker extends SimplePointMarker
{
	
	// Did the earthquake occur on land?  This will be set by the subclasses.
	protected boolean isOnLand;

	// SimplePointMarker has a field "radius" which is inherited
	// by Earthquake marker:
	// protected float radius;
	//
	// You will want to set this in the constructor, either
	// using the thresholds below, or a continuous function
	// based on magnitude. 
  
	
	
	/** Greater than or equal to this threshold is a moderate earthquake */
	public static final float THRESHOLD_MODERATE = 5;
	/** Greater than or equal to this threshold is a light earthquake */
	public static final float THRESHOLD_LIGHT = 4;

	/** Greater than or equal to this threshold is an intermediate depth */
	public static final float THRESHOLD_INTERMEDIATE = 70;
	/** Greater than or equal to this threshold is a deep depth */
	public static final float THRESHOLD_DEEP = 300;

	// ADD constants for colors
	public static final int red = Helper.color(255,50,50);
	public static final int yellow = Helper.color(255,255,0);
	public static final int orange = Helper.color(255,150,0);
	
	public static final int green = Helper.color(50,255,50);
	public static final int blue = Helper.color(50,50,255);
	public static final int purple = Helper.color(255,0,255);
	public static final int black = Helper.color(0,0,0);
	public static final int white = Helper.color(255,255,255);
	
	
	
	public Icon icon;
	public static final Icon xMark = new Xmark(10);
	// constructor
	public EarthquakeMarker (PointFeature feature) 
	{
		super(feature.getLocation());
		// Add a radius property and then set the properties
		java.util.HashMap<String, Object> properties = feature.getProperties();
		float magnitude = Float.parseFloat(properties.get("magnitude").toString());
		properties.put("radius", 2*magnitude );
		setProperties(properties);
		this.radius = 2.0f*getMagnitude();
	}
	

	// calls abstract method drawEarthquake and then checks age and draws X if needed
	abstract public void draw(PGraphics pg, float x, float y); 

	// determine color of marker from depth, and set pg's fill color 
	// using the pg.fill method.
	// We suggest: Deep = red, intermediate = blue, shallow = yellow
	// But this is up to you, of course.
	// You might find the getters below helpful.
	
	int determineFill()
	{
		float depth = getDepth();
		if(depth > THRESHOLD_DEEP) {
			color = red;
		}
		else if(depth > THRESHOLD_INTERMEDIATE) {
			color = yellow;
		}
		else {
			color = green;
		}
		return color;
	}
	
	void ifRecent(PGraphics pg, float x, float y)
	{
		if(getAge().equals("Past Day"))
		{
			xMark.draw(pg, x, y);
		}
	}
	
	
	
	/*
	 * getters for earthquake properties
	 */
	
	public float getMagnitude() {
		return Float.parseFloat(getProperty("magnitude").toString());
	}
	
	public float getDepth() {
		return Float.parseFloat(getProperty("depth").toString());	
	}
	
	public String getTitle() {
		return (String) getProperty("title");	
		
	}
	
	public float getRadius() {
		return Float.parseFloat(getProperty("radius").toString());
	}
	
	public String getAge() {
		return getProperty("age").toString();
	}
	
	public boolean isOnLand()
	{
		return isOnLand;
	}
	
	
}
