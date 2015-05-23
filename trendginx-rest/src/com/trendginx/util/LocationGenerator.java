package com.trendginx.util;

import com.javadocmd.simplelatlng.LatLng;

public class LocationGenerator {
	private String lat;
	private String lon;
	private String data;
	
	public LocationGenerator() {
		data = LatLng.random().toString();
		int endIndex = data.indexOf(',');
		lat = data.substring(1, endIndex);
		lon = data.substring(endIndex+1,data.length()-1);
	}
	
	public String getLat() {
		return lat;
	}

	public String getLon() {
		return lon;
	}

	
}
