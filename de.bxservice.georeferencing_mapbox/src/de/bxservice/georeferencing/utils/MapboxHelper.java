/**********************************************************************
* This file is part of iDempiere ERP Open Source                      *
* http://www.idempiere.org                                            *
*                                                                     *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Diego Ruiz - BX Service GmbH								      *
**********************************************************************/
package de.bxservice.georeferencing.utils;

import java.util.List;
import java.util.logging.Level;

import org.compiere.model.MCountry;
import org.compiere.model.MLocation;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.compiere.util.Util;
import org.osgi.service.component.annotations.Component;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;

import de.bxservice.georeferencing.model.MBXSMarker;
import de.bxservice.georeferencing.tools.AbstractGeoreferencingHelper;
import de.bxservice.georeferencing.tools.IGeoreferencingHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Component(
		service = IGeoreferencingHelper.class,
		property= {"helperType:String=MAPBOX"}
)
public class MapboxHelper extends AbstractGeoreferencingHelper {
	
	/**	Logger			*/
	protected CLogger log = CLogger.getCLogger(MapboxHelper.class);

	/**	Mapbox Access Token		*/
	private String API_KEY = MSysConfig.getValue("MAPBOX_API_KEY");
	
	/** HTML Header with all the api references, the javascript script, plus the CSS rules */
	private final String HTML_HEAD = "<!DOCTYPE html>" +
			"<html>" + 
			"<head>" +
			"  <meta charset='utf-8'/>" +
			"  <meta name='viewport' content='initial-scale=1,maximum-scale=1,user-scalable=no' />" +
			"  <script src=\"https://api.tiles.mapbox.com/mapbox-gl-js/v1.7.0/mapbox-gl.js\"></script>" + 
			"  <link href=\"https://api.tiles.mapbox.com/mapbox-gl-js/v1.7.0/mapbox-gl.css\" rel=\"stylesheet\" />" + 
			"  <style>" + 
			"        body { margin:0; padding:0; }" + 
			"        #map { position:absolute; top:0; bottom:0; width:100%; }" + 
			"        .mapboxgl-popup {" + 
			"            max-width: 200px;" + 
			"        }" + 
			"        .mapboxgl-popup-content {" + 
			"            text-align: center;" + 
			"            font-family: 'Open Sans', sans-serif;" + 
			"        }" + 
			"        .mapboxgl-marker {" + 
			"          cursor: pointer;" + 
			"        }" + 
			"    </style>" + 
			"</head> " + 
			"<body>" + 
			"  <div id=\"map\"></div>" +
			"  <script> " + 
			"  mapboxgl.accessToken = '" + API_KEY + "';";
	
	@Override
	public void setLatLong(List<MLocation> locations) {
		if (locations == null)
			return;
		
		int parsedRecords = 0;
		for (MLocation location : locations) {
			setLatLong(location);
			
			//To avoid getting a db block, sleep N second every Y records to release the TRX 
			parsedRecords++;
			if (parsedRecords % MSysConfig.getIntValue("MAPBOX_BATCHSIZE", 50) == 0) {
				try {
				    Thread.sleep(MSysConfig.getIntValue("MAPBOX_WAITING_SECONDS", 1) * 1000);
				}
				catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	@Override
	public void setLatLong(MLocation location) {
		if (location == null || (location.get_Value("Latitude") != null &&
				location.get_Value("Longitude") != null))
			return;
		
		String address = location.getMapsLocation();

		MCountry country = MCountry.get(location.getC_Country_ID());
		String isoCode = country != null ? country.getCountryCode()  : "";

		MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
				.accessToken(API_KEY)
				.country(isoCode)
				.query(address)
				.build();
		
		mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
			@Override
			public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
				
				if (response != null && response.body() != null) {
					List<CarmenFeature> results = response.body().features();

					if (results != null && results.size() > 0) {

						// Save the first results Point.
						Point firstResultPoint = results.get(0).center();
						if (log.isLoggable(Level.INFO)) 
							log.info("Coordinates for " + address + ": long: " + firstResultPoint.longitude() + " lat: " + firstResultPoint.latitude());

						location.set_ValueNoCheck("Latitude", String.valueOf(firstResultPoint.latitude()));
						location.set_ValueNoCheck("Longitude", String.valueOf(firstResultPoint.longitude()));
						location.saveEx(null);
					}					
				}
			}

			@Override
			public void onFailure(Call<GeocodingResponse> arg0, Throwable arg1) {
				log.severe(arg1.getMessage());
			}
		});
	}
	
	@Override
	public String getMapMarkers() {
		
		if (Util.isEmpty(API_KEY)) {
			log.warning("No Mapbox API Key configured");
			return "MISSING API KEY";
		}
		
		/** HTML Closing tag*/
		String HTML_CLOSE = "var map = new mapboxgl.Map({" + 
				"    container: 'map'," + 
				"    style: \"mapbox://styles/mapbox/streets-v11\"," + 
				"    center: [" + initialLongitude + "," + initialLatitude + "]," + 
				"    zoom: " + zoomValue + 
				"});" + 
				" geojson.features.forEach(function(marker) {" + 
				"" + 
				"    new mapboxgl.Marker({color: marker.properties.mcolor})" + 
				"        .setLngLat(marker.geometry.coordinates)" + 
				"        .setPopup(new mapboxgl.Popup({offset: 25}) " + 
				"        .setHTML('<h3>' + marker.properties.title + '</h3><p>' + marker.properties.description + '</p>'))" + 
				"        .addTo(map);" + 
				"});" + 
				"  </script>" + 
				"</body>" + 
				"</html>";
		
		return HTML_HEAD + addMarkers() + HTML_CLOSE;
	}
	
	private String addMarkers() {
		
		if (mapMarkers == null || mapMarkers.isEmpty())
			return "";
		
		StringBuilder featureSegment = new StringBuilder("var geojson = {" +
				"    type: \"FeatureCollection\"," + 
				"    features: [");
		
		String color = null;
		for (MBXSMarker marker : mapMarkers) {
			
			//If no color is defined -> use black
			color = marker.getColor() != null ? marker.getColor() : "#000000";
			
			featureSegment.append(getFeatureCode(getCoordinates(marker), 
					getMarkerText(marker.getTitle()),
					getMarkerText(marker.getDescription()),
							color));
			featureSegment.append(",");
		}

		featureSegment.append("]" + 
				"};");
		
		return featureSegment.toString();
	}
		
	@Override
	public String getCoordinates(MBXSMarker marker) {
		return "[" + marker.getLongitude() + "," + marker.getLatitude() + "]";
	}
	
	private String getFeatureCode(String coordinates, String title, String description, String color) {
		String featureCode = "{ " +
				"type: \"Feature\", " +
				"geometry: { " + 
				"type: \"Point\", "+ 
				"coordinates: " + coordinates +
				"}, " +
				"properties: { " +
				"title: \"" + title + "\", "+ 
				"description: \"" + description + "\", "+
				"mcolor: '" + color + "' "+
				"} "+
				"}";

		return featureCode;
	}
}