package com.codingdojo.model;

import org.springframework.beans.factory.annotation.Value;

public class MapPoints {
    @Value("${api.Key}")
    public static String apiKey;
    
	private String origin;
	
	private String destination;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	
}
