package module4;

import java.util.*;
import java.util.List;

import Customs.*;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;


/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {
	
	// We will use member variables, instead of local variables, to store the data
	// that the setUp and draw methods will need to access (as well as other methods)
	// You will use many of these variables, but the only one you should need to add
	// code to modify is countryQuakes, where you will store the number of earthquakes
	// per country.
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	private static final boolean offline = true;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	


	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// The map
	private UnfoldingMap map;
	
	// Markers for each city
	private List<Marker> cityMarkers;
	// Markers for each earthquake
	private List<Marker> quakeMarkers;

	// A List of country markers
	private List<Marker> countryMarkers;
	
	// Key
	private Legend thisLG;
	public void setup() {		
		// Initializing canvas and map tiles
		size(1600, 900, OPENGL);
		
		setup_map(250, 50, 1000, 600);
		setup_legend();
		

//		earthquakesURL = "test1.atom";
//		earthquakesURL = "test2.atom";
//		
//		// WHEN TAKING THIS QUIZ: Uncomment the next line
		earthquakesURL = "quiz1.atom";
//		
		
		
		loadCountriesCities();
		loadEarthquakeData();
	    printQuakes();
//		
////	    // Add markers to map
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
		
	    int a = 255, b = 150, c = 150;
	    
		System.out.println(color(a,b,c));
		System.out.println(Helper.color(a, b, c));
	}  // End setup
	
	public void draw() {
		background(0);
		map.draw();
		textFont(createFont("TrebuchetMS", 17));
		strokeWeight(5);
		thisLG.draw();
		
	}
	
	// Sets up map
	private void setup_map(float x, float y, float size_x, float size_y)
	{
		if (offline) {
		    map = new UnfoldingMap(this, x, y, size_x, size_y, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
			map = new UnfoldingMap(this, x, y, size_x, size_y, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
		    //earthquakesURL = "2.5_week.atom";
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		
	}
	
	private void setup_legend()
	{
		thisLG= new Legend(this.g,"Earthquake Key" , 25, 50, 200, 400, color(255, 250, 240));
		
		thisLG.put(new Label(CityMarker.icon, "City"));
		
		thisLG.put(new Label( new Circle(0, color(255, 255, 255),10),"Land Quake" ));
		thisLG.put(new Label( new Square(0, color(255, 255, 255),10),"Ocean Quake" ));
		
		thisLG.put(new Label( null,"Size ~ Magnitude" ));
		
		thisLG.put(new Label( new Circle(0, EarthquakeMarker.red,10),"Shallow " ));
		thisLG.put(new Label(new Circle(0, EarthquakeMarker.yellow,10),  "Intermediate"));
		thisLG.put(new Label(new Circle(0,EarthquakeMarker.green, 10), "Deep"));
		
		thisLG.put(new Label( null ,null ));
		
		thisLG.put(new Label(new Xmark(5), "Recent"));
		
	}
	
	private void loadCountriesCities()
	{
		/*
		 * 	Loads data from storage to List
		*/
		
	    //     STEP 1: load country features and markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		
		
		//     STEP 2: read in city data
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) 
		{
		  cityMarkers.add(new CityMarker(city));
		}
	    
	}
	
	private void loadEarthquakeData()
	{
//	     STEP 3: read in earthquake RSS feed
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    
	    for(PointFeature feature : earthquakes) {
		  //check if LandQuake
		  if(isLand(feature)) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
		  }
		  // OceanQuakes
		  else {
		    quakeMarkers.add(new OceanQuakeMarker(feature));
		  }
	    }
	}

	
	
	// Checks whether this quake occurred on land.  If it did, it sets the 
	// "country" property of its PointFeature to the country where it occurred
	// and returns true.  Notice that the helper method isInCountry will
	// set this "country" property already.  Otherwise it returns false.
	private boolean isLand(PointFeature earthquake) {
		
		for (Marker m : countryMarkers) 
		{

			if (isInCountry(earthquake,m))
			{
				return true;
			}
		}
		
		// not inside any country
		return false;
	}
	
	/* prints countries with number of earthquakes as
	 * Country1: numQuakes1
	 * Country2: numQuakes2
	 * ...
	 * OCEAN QUAKES: numOceanQuakes
	 * */
	void printQuakes() 
	{
		HashMap<String, Integer> counts = new HashMap<String, Integer> ();
		counts.put("OCEAN QUAKES", 0);
		
		for(Marker m : quakeMarkers)
		{
			if (m instanceof LandQuakeMarker)
			{
				String country = (String) m.getProperty("country");
				if(counts.containsKey(country)) {
					counts.put(country, counts.get(country)+1);
				}else {
					counts.put(country,1);
				}
			}
			else
			{
				counts.put("OCEAN QUAKES", counts.get("OCEAN QUAKES") + 1);
			}

        }
		
		System.out.println(counts);
		
	}
	
	
	
	
	// helper method to test whether a given earthquake is in a given country
	// This will also add the country property to the properties of the earthquake 
	// feature if it's in one of the countries.
	// You should not have to modify this code
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// getting location of feature
		Location checkLoc = earthquake.getLocation();

		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if(country.getClass() == MultiMarker.class) {
				
			// looping over markers making up MultiMarker
			for(Marker marker : ((MultiMarker)country).getMarkers()) {
					
				// checking if inside
				if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));
						
					// return if is inside one
					return true;
				}
			}
		}
			
		// check if inside country represented by SimplePolygonMarker
		else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));
			
			return true;
		}
		return false;
	}

}
