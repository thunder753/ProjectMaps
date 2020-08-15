package module6;

import java.util.*;
import Customs.*;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;
import processing.core.PImage;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {
	
	// We will use member variables, instead of local variables, to store the data
	// that the setup and draw methods will need to access (as well as other methods)
	// You will use many of these variables, but the only one you should need to add
	// code to modify is countryQuakes, where you will store the number of earthquakes
	// per country.
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	private static final boolean offline = false;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// The map
	private UnfoldingMap map;
	
	// The legend
	Legend thisLG;
	
	// Markers for each city
	private List<Marker> cityMarkers;
	// Markers for each earthquake
	private List<Marker> quakeMarkers;
	// A List of country markers
	private List<Marker> countryMarkers;
	// A list of Airport Markers
	private List<Marker> airportMarkers;
	// A list of Airport Markers
	private List<Marker> routeList;	
	
	HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
	
	// Button
	private List<Button> buttonList;
	
	// NEW IN MODULE 5
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	
	
	public void setup() {		
		
//		earthquakesURL = "quiz2.atom";
		
		// Initializing canvas
		size(1600, 900, OPENGL);
				
		//	Setup Maps
		setup_map(250, 50, 1000, 600);
		setup_button();
		setup_legend();
		
		//	Reading in earthquake data and geometric properties
		loadData();
	 		
	    // Add markers to map
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
	    map.addMarkers(airportMarkers);
	    map.addMarkers(routeList);
	    
	    textFont(createFont("TrebuchetMS",15));
	} 
	
	public void draw() 
	{
		background(0);
		map.draw();
		thisLG.draw();
		for (Button thisButton : buttonList)
		{
			thisButton.draw();
		}
	}
	
	private void setup_map(float x, float y, float size_x, float size_y)
	{
		/*
		 * Initialize maps
		 */
		if (offline) {
		    map = new UnfoldingMap(this, x, y, size_x, size_y, new MBTilesMapProvider(mbTilesString));
//		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
			map = new UnfoldingMap(this, x, y, size_x, size_y, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
		    //earthquakesURL = "2.5_week.atom";
		}
		MapUtils.createDefaultEventDispatcher(this, map);
	}
	private void setup_button()
	{
		buttonList = new ArrayList<Button>();
		buttonList.add(new Button(this.g, "Reveal Routes" ,0, 50, 500));
		buttonList.add(new Button(this.g, "Hide All", 1,50, 550));
		buttonList.add(new Button(this.g, "Filter City",2, 50, 600));
		buttonList.add(new Button(this.g, "Filter Quakes",3, 50, 650));
		buttonList.add(new Button(this.g, "Filter Airports",4, 50, 700));
	}
	private void setup_legend()
	{
		thisLG= new Legend(this.g,"Earthquake Key" , 25, 50, 200, 400, color(255, 250, 240));
		
		thisLG.put(new Label(CityMarker.icon, "City"));
		
		thisLG.put(new Label( new Circle(0, color(255, 255, 255),15),"Land Quake" ));
		thisLG.put(new Label( new Square(0, color(255, 255, 255),15),"Ocean Quake" ));
		
		thisLG.put(new Label( null,"Size ~ Magnitude" ));
		
		thisLG.put(new Label( new Circle(0, Helper.red,10),"Deep " ));
		thisLG.put(new Label(new Circle(0, Helper.yellow,10),  "Intermediate"));
		thisLG.put(new Label(new Circle(0,Helper.green, 10), "Shallow"));
		
		thisLG.put(new Label( null ,null ));
		thisLG.put(new Label( new AirplaneIco() ,"Airport" ));
		
		thisLG.put(new Label(new Xmark(10), "Recent"));
		
	}

	private void loadData()
	{
		 //	STEP 1: load country features and markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		///////////////////////////////////////////
		
		
		//	STEP 2: read in city data
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities) 
		{
		  cityMarkers.add(new CityMarker(city));
		}
		///////////////////////////////////////////
	    
		
		//	STEP 3: read in earthquake RSS feed
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    for(PointFeature feature : earthquakes) 
	    {
		  //check if LandQuake
		  if(isLand(feature)) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
		  }
		  // OceanQuakes
		  else {
		    quakeMarkers.add(new OceanQuakeMarker(feature));
		  }
	    }
	    ///////////////////////////////////////////
	    
	    
	    //	STEP 4: load the features of airport.
	    List<PointFeature> airportFeats = ParseFeed.parseAirports(this, "airports.dat");
	    airportMarkers = new ArrayList<Marker>();
	    Random myRandom = new Random(); // to get random airports
	    for(int i = 0; i < 200; i++)
	    {   	
	    	PointFeature port = airportFeats.get(myRandom.nextInt(airportFeats.size()));
	    	airportMarkers.add(new AirportMarker(port));
	    	airports.put(Integer.parseInt(port.getId()), port.getLocation());
	    }
	    ///////////////////////////////////////////	
	    
	    
	    
		//	STEP 5: parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		
		for(ShapeFeature route : routes) 
		{
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) 
			{
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
				routeList.add(new RoutesLine(route.getLocations(), route.getProperties()));
			}
		}
		System.out.println("Debug Point");
		///////////////////////////////////////////	
	}
	
	/** Event handler that gets called automatically when the 
	 * mouse moves.
	 */
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover();
	}
	
	/* If there is a marker under the cursor, and lastSelected is null 
	 * set the lastSelected to be the first marker found under the cursor
	 * Make sure you do not select two markers.
	 */
	private void selectMarkerIfHover()
	{	
		for(Marker m : map.getMarkers())
		{
			if(m instanceof CommonMarker) {
				CommonMarker cm = (CommonMarker)m;
				if(cm.isInside(map, mouseX, mouseY))
				{
					lastSelected = cm;
					lastSelected.setSelected(true);
					break;
				}
			}
		}
	}
	
	/** The event handler for mouse clicks
	 * It will display an earthquake and its threat circle of cities
	 * Or if a city is clicked, it will display all the earthquakes 
	 * where the city is in the threat circle
	 */
	@Override
	public void mouseClicked()
	{
		// Clear the previously clicked marker
		if (lastClicked != null) 
		{
			lastClicked.setClicked(false);
			lastClicked = null;
		}
		
		// Set the currently selected as currently clicked
		lastClicked = lastSelected;

		// If clicked on non-hidden marker
		if (!(lastClicked == null || lastSelected.isHidden()))
		{
			lastClicked.setClicked(true);
			showOnlyRelvent();
		} else 
		{
			unhideMarkers();
			checkIfButton();
		}
		
	}
	
	private void checkIfButton()
	{
		float clickX = mouseX;
		float clickY = mouseY;
		for(Button thisButton : buttonList) {
			if(thisButton.isInside(clickX, clickY))
			{
				thisButton.click();
				if(thisButton.getState()) action_button(thisButton);
				else unhideMarkers();
				break;
			}
		}
		

		
	}
	
	// loop over and un-hide all markers
	private void unhideMarkers() 
	{
		hideAllMarkers();
		for(Marker marker : map.getMarkers()) 
		{
			if(marker instanceof CommonMarker) marker.setHidden(false);
		}
	}
	
	private void revealAllRoutes()
	{
		for(Marker thisM : routeList)
		{
			thisM.setHidden(false);
		}
	}
	
	private void hideAllMarkers()
	{
		for(Marker marker : map.getMarkers()) 
		{
			marker.setHidden(true);
		}
	}

	private void action_button(Button thisButton)
	{
		switch (thisButton.id)
		{
			case 0: 
				unhideMarkers();
				revealAllRoutes();
				break;
			case 1:
				hideAllMarkers();
				break;
			case 2:
				filterCity();
				break;
			case 3:
				filterQuakes();
				break;
			case 4:
				filterPorts();
				break;
			default:
				System.out.println("Default");
		}

	}
	
	private void filterCity()
	{
		hideAllMarkers();
		for(Marker thisMarker : cityMarkers)
		{
			thisMarker.setHidden(false);
		}
	}
	
	private void filterQuakes()
	{
		hideAllMarkers();
		for(Marker thisMarker : quakeMarkers)
		{
			thisMarker.setHidden(false);
		}
	}
	
	private void filterPorts()
	{
		hideAllMarkers();
		for(Marker thisMarker : airportMarkers)
		{
			thisMarker.setHidden(false);
		}
	}
	private void showOnlyRelvent()
	{
		hideAllMarkers();
		lastClicked.setHidden(false);
		
		if(lastClicked instanceof EarthquakeMarker)
		{		
			
			for(Marker marker : cityMarkers) 
			{
				// if the city 'marker' is within the selected earthquake's impact radius
				if(isNearDanger(marker, (EarthquakeMarker)lastClicked)) marker.setHidden(false);
			}
			
			for(Marker marker : airportMarkers) 
			{
				// if the city 'marker' is within the selected earthquake's impact radius
				if(isNearDanger(marker, (EarthquakeMarker)lastClicked)) marker.setHidden(false);
			}
			
		}
		
		else 
		{
			for(Marker marker : quakeMarkers) 
			{
				// if this earthquake 'marker' is within its impact radius of selected marker
				if (isNearDanger(lastClicked, (EarthquakeMarker) marker)) marker.setHidden(false);
			}
		}
		
		if(lastClicked instanceof AirportMarker)
		{
			showRoutes();
		}
		
	}
	
	// Finds if the selected marker is near the threat radius of the given marker
	private boolean isNearDanger(Marker thisMarker, EarthquakeMarker eqMarker)
	{
		return (thisMarker.getDistanceTo(eqMarker.getLocation()) < eqMarker.threatCircle());
	}
	
	private void showRoutes()
	{
		AirportMarker thisAM = (AirportMarker) lastClicked;
		ArrayList<Integer> otherEnd = new ArrayList<Integer>();
		
		for(Marker thisMarker : routeList)
		{
			RoutesLine thisRL = (RoutesLine) thisMarker;
			if(thisRL.getDestination() == thisAM.getID())
			{
				thisRL.setHidden(false);
				otherEnd.add(thisRL.getSource());
			}
			else if(thisRL.getSource() == thisAM.getID())
			{
				thisRL.setHidden(false);
				otherEnd.add(thisRL.getDestination());
			}
		}
		
		for(Marker thisMarker : airportMarkers)
		{
			AirportMarker thisPort = (AirportMarker) thisMarker;
			if(otherEnd.contains(thisPort.getID()))
			{
				thisPort.setHidden(false);
			}
		}
	}
	
	/* Checks whether this quake occurred on land.  If it did, it sets the 
	 * "country" property of its PointFeature to the country where it occurred
	 * and returns true.  Notice that the helper method isInCountry will
	 * set this "country" property already.  Otherwise it returns false.
	 */	
	private boolean isLand(PointFeature earthquake) {
		
		// IMPLEMENT THIS: loop over all countries to check if location is in any of them
		// If it is, add 1 to the entry in countryQuakes corresponding to this country.
		for (Marker country : countryMarkers) {
			if (isInCountry(earthquake, country)) {
				return true;
			}
		}
		
		// not inside any country
		return false;
	}
	
	// prints countries with number of earthquakes
	private void printQuakes() {
		int totalWaterQuakes = quakeMarkers.size();
		for (Marker country : countryMarkers) {
			String countryName = country.getStringProperty("name");
			int numQuakes = 0;
			for (Marker marker : quakeMarkers)
			{
				EarthquakeMarker eqMarker = (EarthquakeMarker)marker;
				if (eqMarker.isOnLand()) {
					if (countryName.equals(eqMarker.getStringProperty("country"))) {
						numQuakes++;
					}
				}
			}
			if (numQuakes > 0) {
				totalWaterQuakes -= numQuakes;
				System.out.println(countryName + ": " + numQuakes);
			}
		}
		System.out.println("OCEAN QUAKES: " + totalWaterQuakes);
	}
	
	
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

	private void sortAndPrint(int numToPrint) 
	{
		if (numToPrint > quakeMarkers.size()) numToPrint = quakeMarkers.size();
		
		Object[] arrayQuakes =  quakeMarkers.toArray();
		
		for(int i = 0; i < arrayQuakes.length - 1 ; i++)
		{
			int max = i;
			
			for(int j = i + 1; j < arrayQuakes.length; j++) 
			{
				if (((EarthquakeMarker)(arrayQuakes[j])).compareTo(((EarthquakeMarker)(arrayQuakes[max]))) > 0) max = j; 
			}
			swap(arrayQuakes, max, i);
			if(i > numToPrint) break;
		}
		
		for(int i = 0; i < numToPrint; i++)
		{
			System.out.println(arrayQuakes[i]);
		}
		
		
	}
	
	private void swap(Object[] arrayMarkers, int max, int indx)
	{
		Marker temp = (Marker) arrayMarkers[indx];
		arrayMarkers[indx] = arrayMarkers[max];
		arrayMarkers[max] = temp;
	}
	
}
