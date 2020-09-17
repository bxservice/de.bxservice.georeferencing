package de.bxservice.georeferencing.tools;

import java.util.List;

import org.compiere.util.Util;

import de.bxservice.georeferencing.model.MBXSMarker;

public class DefaultMarkersGeojson {
	private String defaultMarkerColor;
	public DefaultMarkersGeojson() {}
	
	public DefaultMarkersGeojson(String defaultMarkerColor) {
		this.defaultMarkerColor = defaultMarkerColor;
	}
	
	/**
	 * Returns a valid HTML String 
	 * @param text String to be converted
	 * @return String with HTML values
	 */
	public static String getMarkerText(String text) {
		if (!Util.isEmpty(text)) {
			if (text.contains("\""))
				text = text.replace("\"","&quot;");
			if (text.contains("<"))
				text = text.replace("<","&lt;");
			if (text.contains(">"))
				text = text.replace("<","&gt;");
			
			return text.replace("\n", "<br>");
		}
		return "";
	}
	
	public String buildMarkersAsGeojson(List<MBXSMarker> mapMarkers) {
		return buildMarkersAsGeojson(mapMarkers, null);
	}
	public String buildMarkersAsGeojson(List<MBXSMarker> mapMarkers, String jsVarName) {
		StringBuilder featureSegment = new StringBuilder();
		
		if (jsVarName == null)
			jsVarName = "geojson";
		
		featureSegment.append("var ").append(jsVarName).append(" = {type: \"FeatureCollection\",features: [");
		
		int countMarkerItem = 0;
		for (MBXSMarker marker : mapMarkers) {
			countMarkerItem++;
			
			buiildFeatureGeojson(featureSegment, marker);
			
			// latest item don't need
			if (countMarkerItem != mapMarkers.size())
				featureSegment.append(",");
		}

		featureSegment.append("]};");
		
		return featureSegment.toString();
	}
	
	protected void buiildFeatureGeojson(StringBuilder builderGeojson, MBXSMarker marker) {
		builderGeojson.append("{ type: \"Feature\", geometry: { type: \"Point\", coordinates: [").
			append(marker.getLongitude()).append(", ").append(marker.getLatitude()).
			append("]},properties: { ");
		buildFeatureProperties (builderGeojson, marker);
		builderGeojson.append("}} ");
	}
	
	/**
	 * override this one in case you want marker has more information or difference properties name
	 * @param builderGeojson
	 * @param marker
	 */
	protected void buildFeatureProperties(StringBuilder builderGeojson, MBXSMarker marker) {
		builderGeojson.append("title: \"").append(getMarkerText(marker.getTitle())).append("\", "); 
		builderGeojson.append("description: \"").append(getMarkerText(marker.getDescription())).append("\", ");
		String markerColor = this.defaultMarkerColor;
		if (marker.getColor() != null)
			markerColor = marker.getColor();
		if (markerColor != null)
			builderGeojson.append("mcolor: '").append(getMarkerText(markerColor)).append("' ");
	}
}
