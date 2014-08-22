package summer;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.GoogleMapControl;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.InfoWindowClosedListener;
import com.vaadin.tapio.googlemaps.client.events.MapClickListener;
import com.vaadin.tapio.googlemaps.client.events.MapMoveListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerDragListener;
import com.vaadin.tapio.googlemaps.client.layers.GoogleMapKmlLayer;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapInfoWindow;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;









import javax.servlet.annotation.WebServlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("serial")
public class InitUI extends UI {
	
	
	public void basic(HorizontalLayout layout) {
        CheckBox checkbox = new CheckBox("Box with a Check");
        
        checkbox.addValueChangeListener(new Property.ValueChangeListener() {
            private static final long serialVersionUID = -6857112166321059475L;

            public void valueChange(ValueChangeEvent event) {
                boolean value = (Boolean) event.getProperty().getValue();

                Notification.show("Check: " + value);
            }
        });
        checkbox.setImmediate(true);
        // END-EXAMPLE: component.checkbox.basic

        layout.addComponent(checkbox);
	}
	

	public static final String transAPI = "Hpa9MWTsv4IlNTT88b5o";
    public int count=0;
	private GoogleMap googleMap;
    private GoogleMapMarker homeMarker = new GoogleMapMarker("DRAGGABLE: Kakolan vankila", new LatLon(49.150478, -122.911677), true, null);
    public  GoogleMapMarker[] tempMarker = new GoogleMapMarker[1000];
    private GoogleMapInfoWindow kakolaInfoWindow = new GoogleMapInfoWindow(
            "Kakola used to be a provincial prison.", homeMarker);
    private final String apiKey = "";

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = InitUI.class, widgetset = "summer.AppWidgetSet")
    public static class Servlet extends VaadinServlet {}
    @Override	
    protected void init(VaadinRequest request) {

                
    	File f = new File("VAADIN/312nbfile.csv");
    	File q = new File("VAADIN/312sbfile.csv");


    	
    	
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);

        final ArrayList<Stops> stopList = new ArrayList<Stops>();
    	final List<GoogleMapMarker> kappa = new ArrayList<GoogleMapMarker>();
    	List<Double> lati = new ArrayList<Double>();
    	List<Double> longi = new ArrayList<Double>();
       
        
        String thisLine = new String();
        FileReader fr = null;
		try {
			fr = new FileReader(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedReader reader = new BufferedReader(fr);
        
        try {
			while ((thisLine = reader.readLine()) != null) {
			    
			    
			    String[] eternity= thisLine.split(",");
			    /*
			     * 0=stop id
			     * 1=lat
			     * 2=long
			     * >2=times for stops
			     */
			    
			    //DEBUG
			    /*
			    for (int j=0; j<eternity.length;j++){
			    	System.out.println(eternity[j].toString());
			    }
			    */
			    
			    Stops newStop = new Stops(Integer.parseInt(eternity[0]), Double.parseDouble(eternity[1]),Double.parseDouble(eternity[2]));

			    
			    for (int i=3;i<eternity.length;i++)
			    {
			    	/*if you've formatted like 4:33AM or 11:01PM
				    //SimpleDateFormat df = new SimpleDateFormat("hh:mmaa");
				    final Date date = df.parse(eternity[i]);
				    System.out.println("time = " + df.format(date));
			         System.out.println((date.toString()).substring(11,16).replace(":",""));
			         */
			    	newStop.addTime(Integer.parseInt(eternity[i].replace(":","")));	
			    	
			    	
			    }
			    
			    
			    stopList.add(newStop);
			   
			 }
		} catch (NumberFormatException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
        
        try {
			reader.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
        
       HorizontalLayout layout = new HorizontalLayout();
        layout.setHeight("50px");
        content.addComponent(layout);
          basic(layout);
        

        


	        

        
        
        
        googleMap = new GoogleMap(new LatLon(49.198524, -122.921171), 12, apiKey);
        googleMap.setSizeFull();
        homeMarker.setAnimationEnabled(true);
        googleMap.addMarker(homeMarker);
        googleMap.addMarker("DRAGGABLE: Paavo Nurmi Stadion", new LatLon(60.442423, 22.26044), true, "VAADIN/1377279006_stadium.png");
        googleMap.addMarker("NOT DRAGGABLE: Iso-Heikkilä", new LatLon(60.450403, 22.230399), false, "VAADIN/bus.png");
        googleMap.setMinZoom(3);
        googleMap.setMaxZoom(20);



        
        kakolaInfoWindow.setWidth("400px");
        kakolaInfoWindow.setHeight("500px");

        content.addComponent(googleMap);
        content.setExpandRatio(googleMap, 1.0f);

        Panel console = new Panel();
        console.setHeight("100px");
        final CssLayout consoleLayout = new CssLayout();
        console.setContent(consoleLayout);
        content.addComponent(console);

        HorizontalLayout buttonLayoutRow1 = new HorizontalLayout();
        buttonLayoutRow1.setHeight("26px");
        content.addComponent(buttonLayoutRow1);

        HorizontalLayout buttonLayoutRow2 = new HorizontalLayout();
        buttonLayoutRow2.setHeight("26px");
        content.addComponent(buttonLayoutRow2);
        
        OpenInfoWindowOnMarkerClickListener infoWindowOpener = new OpenInfoWindowOnMarkerClickListener(
                googleMap, homeMarker, kakolaInfoWindow);
        googleMap.addMarkerClickListener(infoWindowOpener);

        googleMap.addMarkerClickListener(new MarkerClickListener() {
            @Override
            public void markerClicked(GoogleMapMarker clickedMarker) {
                Label consoleEntry = new Label("Marker \""
                        + clickedMarker.getCaption() + "\" at ("
                        + clickedMarker.getPosition().getLat() + ", "
                        + clickedMarker.getPosition().getLon() + ") clicked.");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        googleMap.addMapMoveListener(new MapMoveListener() {
            @Override
            public void mapMoved(int zoomLevel, LatLon center,
                    LatLon boundsNE, LatLon boundsSW) {
            	googleMap.setVisibleAreaBoundLimits(new LatLon(49.331939, -122.606344), new LatLon(49.013209, -123.264150));
                Label consoleEntry = new Label("Map moved to ("
                        + center.getLat() + ", " + center.getLon() + "), zoom "
                        + zoomLevel + ", boundsNE: (" + boundsNE.getLat()
                        + ", " + boundsNE.getLon() + "), boundsSW: ("
                        + boundsSW.getLat() + ", " + boundsSW.getLon() + ")");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        googleMap.addMapClickListener(new MapClickListener() {
            @Override
            public void mapClicked(LatLon position) {
                Label consoleEntry = new Label("Map click to ("
                        + position.getLat() + ", " + position.getLon() + ")");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        googleMap.addMarkerDragListener(new MarkerDragListener() {
            @Override
            public void markerDragged(GoogleMapMarker draggedMarker,
                    LatLon oldPosition) {
            	
                Label consoleEntry = new Label("Marker \""
                        + draggedMarker.getCaption() + "\" dragged from ("
                        + oldPosition.getLat() + ", " + oldPosition.getLon()
                        + ") to (" + draggedMarker.getPosition().getLat()
                        + ", " + draggedMarker.getPosition().getLon() + ")");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        googleMap.addInfoWindowClosedListener(new InfoWindowClosedListener() {

            @Override
            public void infoWindowClosed(GoogleMapInfoWindow window) {
                Label consoleEntry = new Label("InfoWindow \""
                        + window.getContent() + "\" closed");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        });

        Button moveCenterButton = new Button(
                "Move over Luonnonmaa (60.447737, 21.991668), zoom 12",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        googleMap.setCenter(new LatLon(60.447737, 21.991668));
                        googleMap.setZoom(12);
                    }
                });
        buttonLayoutRow1.addComponent(moveCenterButton);


        Button zoomToBoundsButton = new Button("Zoom to bounds",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        googleMap.fitToBounds(new LatLon(60.45685853323144,
                                22.320034754486073), new LatLon(
                                60.4482979242303, 22.27887893936156));

                    }
                });
        buttonLayoutRow1.addComponent(zoomToBoundsButton);
        Button addPolyOverlayButton = new Button("Add overlay over Luonnonmaa",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        ArrayList<LatLon> points = new ArrayList<LatLon>();
                        points.add(new LatLon(60.484715, 21.923706));
                        points.add(new LatLon(60.446636, 21.941387));
                        points.add(new LatLon(60.422496, 21.99546));
                        points.add(new LatLon(60.427326, 22.06464));
                        points.add(new LatLon(60.446467, 22.064297));

                        GoogleMapPolygon overlay = new GoogleMapPolygon(points,
                                "#ae1f1f", 0.8, "#194915", 0.5, 3);
                        googleMap.addPolygonOverlay(overlay);
                        event.getButton().setEnabled(false);
                    }
                });
        buttonLayoutRow2.addComponent(addPolyOverlayButton);

        Button addPolyLineButton = new Button("Refresh Current Bus Location",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        
                    	//;
                    	System.out.println(count+"what?");
            			while (count!=0){
							googleMap.removeMarker(tempMarker[count-1]);
							count--;
						}
                    	
            			count=0;
            			
                        URL tomat = null;
						try {
							tomat = new URL("http://api.translink.ca/rttiapi/v1/buses?apikey="+transAPI+"&routeNo=312");
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        BufferedReader in = null;
						try {
							in = new BufferedReader(new InputStreamReader(tomat.openStream()));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        String inputLine = null, inputLat = null, inputLong = null, inputTime = null;
                        boolean andGetLong=false;
                        
                        
                    try {
						while ((inputLine = in.readLine()) != null)
						{
							//if zero results will throw an error; im assuming user input is well-formated
				
							
							
							
							int latStart = inputLine.indexOf("<Latitude>");
							int latEnd = inputLine.indexOf("</Latitude>");
							
						
							int longStart =0;
							int longEnd = 0;
							
							int timeStart = inputLine.indexOf("<RecordedTime>");
							//System.out.println(count+"what?"+(inputLine.indexOf("<Latitude>",longEnd+1)));
							while ((inputLine.indexOf("<Latitude>",longEnd+1))!=-1)
							{
						    	latStart = inputLine.indexOf("<Latitude>",longEnd+1);
						    	latEnd = inputLine.indexOf("</Latitude>",longEnd+1);
						    	
						    
						    	longStart = inputLine.indexOf("<Longitude>",longEnd+1);
						    	longEnd = inputLine.indexOf("</Longitude>",longEnd+1);
						    	
						    	
						    	
						    	inputTime =inputLine.substring(timeStart+14, timeStart+22);
						    	inputLat=inputLine.substring(latStart+10, latEnd);
						    	inputLong = inputLine.substring(longStart+11, longEnd);
						    	
								tempMarker[count]=new GoogleMapMarker("Current Location!", new LatLon(Double.parseDouble(inputLat),Double.parseDouble(inputLong)),false, "VAADIN/bus.png");
								
								googleMap.addMarker(tempMarker[count]);
								count++;
								System.out.println(inputTime);
							}
						    
						    
						    
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }});
        buttonLayoutRow2.addComponent(addPolyLineButton);
        Button addPolyLineButton2 = new Button(
                "Draw line from Turku to Raisio2", new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        ArrayList<LatLon> points2 = new ArrayList<LatLon>();
                        points2.add(new LatLon(60.448118, 22.253738));
                        points2.add(new LatLon(60.486025, 22.169195));
                        GoogleMapPolyline overlay2 = new GoogleMapPolyline(
                                points2, "#d31717", 0.8, 10);
                        googleMap.addPolyline(overlay2);
                        event.getButton().setEnabled(false);
                    }
                });
        buttonLayoutRow2.addComponent(addPolyLineButton2);
        Button changeToTerrainButton = new Button("Change to terrain map",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        googleMap.setMapType(GoogleMap.MapType.Terrain);
                        event.getButton().setEnabled(false);
                    }
                });
        buttonLayoutRow2.addComponent(changeToTerrainButton);

        Button changeControls = new Button("Remove street view control",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        googleMap.removeControl(GoogleMapControl.StreetView);
                        event.getButton().setEnabled(false);
                    }
                });
        buttonLayoutRow2.addComponent(changeControls);

        Button addInfoWindowButton = new Button(
                "Add InfoWindow to Kakola marker", new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        googleMap.openInfoWindow(kakolaInfoWindow);
                    }
                });
        buttonLayoutRow2.addComponent(addInfoWindowButton);

        Button moveMarkerButton = new Button("Move kakola marker",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(ClickEvent event) {
                        homeMarker.setPosition(new LatLon(60.3, 22.242415));
                        googleMap.addMarker(homeMarker);
                    }
                });
        buttonLayoutRow2.addComponent(moveMarkerButton);

        Button addKmlLayerButton = new Button("Add 312 Layer!", new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        googleMap.addKmlLayer(new GoogleMapKmlLayer("http://nb.translink.ca/geodata/312.kmz"));
                       
                        
                        //Show stops for route
                        if (kappa.isEmpty()==true){
                        for (int i=0; i<stopList.size();i++){
                        	kappa.add(i, new GoogleMapMarker("", new LatLon(stopList.get(i).getLat(),stopList.get(i).getLong()),false, null));
                        	googleMap.addMarker(kappa.get(i));
                        }
                        }
                        // remove stops for route
                        

                      
                }});
        
        
     
        buttonLayoutRow2.addComponent(addKmlLayerButton);
        
        final GoogleMapKmlLayer cta = new GoogleMapKmlLayer("");
        
        Button addKmlLayerButton2 = new Button("Add 316", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	cta.setUrl("http://nb.translink.ca/geodata/316.kmz");
               googleMap.addKmlLayer(cta);
            }
        });
        
        
        buttonLayoutRow2.addComponent(addKmlLayerButton2);
        
        Button addKmlLayerButton3 = new Button("Remove", new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	
                googleMap.removeKmlLayer(cta);
                
                if (kappa.isEmpty()==false){
                for (int i=0; i<stopList.size();i++){
                	googleMap.removeMarker(kappa.get(i));
                	
                									}
                kappa.clear();
                }
            }
        });
        
        buttonLayoutRow2.addComponent(addKmlLayerButton3);
        
    }

        }
    

            