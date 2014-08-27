package summer;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;




import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapInfoWindow;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;


public class Bus                                                                                                                                        
{
 // Read from URL and return the content in a String
private double lat, lng;
private String time, direction, busRoute;
private GoogleMapMarker busMarker;
private GoogleMapInfoWindow busWindowInfo;

public static final String transAPI = "Hpa9MWTsv4IlNTT88b5o";
   
   public Bus (String _busRoute, double _lat, double _lng, String _direction ){
	   busRoute=_busRoute;
	   lat=_lat;
	   lng=_lng;
	   direction=_direction;
   }
   
   

   
   public void addTime(int _time)
   {
	 //  time.add(_time);
   }
   
   public double getLat() {
	   return lat;
   }
   public double getLong() {
	   return lng;
   }

   public String getBusRoute() {
	   return busRoute;
   }
   
   public GoogleMapMarker getMarker() {
	   busMarker=new GoogleMapMarker("", new LatLon(lat,lng),false, "VAADIN/bus.png");
	   return busMarker;
   }
   
   
   public GoogleMapInfoWindow getWindow() {
	   busWindowInfo = new GoogleMapInfoWindow("Last updated: ",getMarker());
	   busWindowInfo.setHeight("100px");
	   busWindowInfo.setWidth("100px");
	   return busWindowInfo;
   }
   
   
   public String toString() {
	return "Stop ID#:"+busRoute+"\n"+time.toString();
   }
   
   /*
   public double getDistance() {
	   return distance;
   }
   
   public void setDistance(double _distance) {
	   distance=_distance;
	   
   }
   */
   
    public static void main(String[] args) throws IOException, ParseException
    {
    	
       
        
        

      /*
        //using personal api key for this
        URL tomat = new URL("http://api.translink.ca/rttiapi/v1/buses?apikey="+transAPI+"&routeNo=312");
        BufferedReader in = new BufferedReader(new InputStreamReader(tomat.openStream()));
        String inputLine = null, inputLat = null, inputLong = null;
        boolean andGetLong=false;
        
        
    while ((inputLine = in.readLine()) != null)
    {
    	//if zero results will throw an error; im assuming user input is well-formated
    	int latStart = inputLine.indexOf("<Latitude>");
    	int latEnd = inputLine.indexOf("</Latitude>");
    	
    
    	int longStart = inputLine.indexOf("<Longitude>");
    	int longEnd = inputLine.indexOf("</Longitude>");
    	
    	
    	inputLat=inputLine.substring(latStart+10, latEnd);
    	inputLong = inputLine.substring(longStart+11, longEnd);
    	
    	System.out.println("Latitude n Long: "+ inputLat + ","+inputLong);
    	while ((inputLine.indexOf("<Latitude>",longEnd+1))!=-1)
    	{
        	latStart = inputLine.indexOf("<Latitude>",longEnd+1);
        	latEnd = inputLine.indexOf("</Latitude>",longEnd+1);
        	
        
        	longStart = inputLine.indexOf("<Longitude>",longEnd+1);
        	longEnd = inputLine.indexOf("</Longitude>",longEnd+1);
        	
        	
        	inputLat=inputLine.substring(latStart+10, latEnd);
        	inputLong = inputLine.substring(longStart+11, longEnd);
        	
        	System.out.println("Latitude n Long: "+ inputLat + ","+inputLong);
    	}
		
		//stop id, latitude, longitude, day (string), 
    	
*/

    	
    	
  //  	System.out.println(inputLine);
  //  }
  //  in.close();
        
        /*
        boolean foundLatLong = false;
        Scanner scanner = new Scanner(tomat);
        while (foundLatLong==false) {
        	
        }*/
        
        	/*


       
        

       */
    
        }
    //double dInputLong = Double.parseDouble(inputLong);
   // double dInputLat = Double.parseDouble(inputLat);
    
    /*
    double fiveNear[] = new double[5];
    for (int i=0; i<6; i++)
    {
    	fiveNear[i]=999999999;
    }

    double allDistance[] = new double[a.length];
    
    */
        
    /*
    for (int i=0; i<a.length;i++)
    {
    	a[i].setDistance(distance(dInputLat, dInputLong, a[i].getLat(), a[i].getLong()));
    	
    }
    selectionSort(a);
   // System.out.println(distance(dInputLat, dInputLong, a[i].getLat(), a[i].getLong()) + "Kilometers away\n");
	System.out.println("The closest five schools to "+userInput+" are:");
	System.out.println("//////////////////////////////");
    for (int i=0; i<5;i++)
    {
    	System.out.println((i+1)+".");
    	System.out.println("Name: "+a[i].getName());
    	System.out.println("Address: "+a[i].getAddress());
    	System.out.println("URL: "+a[i].getURL());
    	System.out.println("Distance: "+a[i].getDistance()+" km");

    	
    }
*/
        //main ends
      //}
 /////
   ///Distance calculation starts here; and it's kilometers
    ////
    //calculates distance of two points, or two locations, based on longitude and latitude
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        //In kilometres instead of miles or whatever
         dist = dist * 1.609344;
        return (dist);
      }


    	//converts decimal degrees to radians
      private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }


      	//This function converts radians to decimal degrees
      private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }

      //////////////////
      //Selection sort starts here
      //////////////////////////
      public static void selectionSort(Stops[] a)
  	{
  		for (int i = 0; i < a.length - 1; i++)
  		{
  			int min = i;
  			for (int j = i + 1; j < a.length; j++)
  				if (a[j].getDistance() < a[min].getDistance())
  					min = j;
  				
  			swap(a, i, min);
  			
  		
  		}
  	}
  	public static void swap (Stops[] a, int i, int j)
  	{
  		Stops temp = a[i];
  		a[i] = a[j];
  		a[j] = temp;
  	}

  	

  	
       
    }