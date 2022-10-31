package com.codingdojo.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.codingdojo.model.Place;
import com.codingdojo.services.PlaceService;
import com.codingdojo.services.UserService;
import com.codingdojo.model.MapPoints;
@RestController
@RequestMapping("/api")
public class APIController {
	

    @Autowired
	private PlaceService placeService;
	
	@Autowired
	private UserService userService;
	
//	WebClient client = WebClient.create();
//	UriSpec<RequestBodySpec> uriSpec = client.method(HttpMethod.GET);
	
	@GetMapping("/places/placeID")
	protected static String apiGetPlaces(Place place){
		
		String uri = "https://www.google.com/maps/api/geocode/json?address=" + place.getStreet_address()+ " "+place.getCity()+ " "+ place.getState()+ " "+ place.getZip()+"&key=API_KEY";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		System.out.println(result);
		String placeId = "";
		try {
			JSONObject obj = new JSONObject(result);
			placeId = obj.getJSONArray("results").getJSONObject(0).getString("place_id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		place.setPlaceID(placeId);
		return placeId;
	} 
	
	@GetMapping("/places/travel_time")
	protected static HashMap<String, String> apiGetTravelTime(String originId, ArrayList<Place> destinationIds){
		ArrayList<String> placeIds= new ArrayList<String>();
		for(int i =0; i<destinationIds.size(); i++) {
			String destinationId = destinationIds.get(i).getPlaceID();
			placeIds.add("place_id:"+destinationId);
		} 
		String destinationId = String.join("|",placeIds);
			String uri = "https://maps.googleapis.com/maps/api/distancematrix/json?departure_time=now&destinations=" +destinationId+ "&origins=place_id:"+originId+"&key=API_KEY";
			RestTemplate restTemplate = new RestTemplate();
			String result = restTemplate.getForObject(uri, String.class);
			System.out.println(result);
			HashMap<String,String> travelTimes = new HashMap<String,String>();
			try {
				JSONObject obj = new JSONObject(result);
				JSONArray rowsList = obj.getJSONArray("rows");
				System.out.println("rowsList:"+rowsList);
				JSONObject row = rowsList.getJSONObject(0);
				System.out.println("row:"+row);
//				travelTimeObj = obj.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration_in_traffic").getString("text");
				for(int i = 0 ; i < row.length(); i++) {
					JSONArray elementsList = row.getJSONArray("elements");
					for(int j =0; j<elementsList.length();j++) {
						System.out.println("elementsList:"+elementsList);
						JSONObject elementObject = elementsList.getJSONObject(j);
						System.out.println("elementObject:"+elementObject);
						JSONObject durationInTraffic = elementObject.getJSONObject("duration_in_traffic");
						String travelTime = durationInTraffic.getString("text");
						travelTimes.put(destinationIds.get(j).getName(), travelTime);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return travelTimes;
	}
}
