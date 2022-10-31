package com.codingdojo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.model.LoginUser;
import com.codingdojo.model.Place;
import com.codingdojo.model.User;
import com.codingdojo.model.MapPoints;
import com.codingdojo.services.PlaceService;
import com.codingdojo.services.UserService;


@Controller
public class MainController{

	// Add once service is implemented:
	@Autowired
	private UserService userService;

	@Autowired
	private PlaceService placeService;

	@GetMapping("/")
	    public String index(Model model) {
	    
		// Bind empty User and LoginUser objects to the JSP
	    // to capture the form input
	    model.addAttribute("newUser", new User());
	    model.addAttribute("newLogin", new LoginUser());
	    return "login.jsp";
	    	
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, BindingResult result, Model model,
			HttpSession session) {

		User createdUser = userService.registerUser(newUser, result);
		if (result.hasErrors()) {
			model.addAttribute("newLogin", new LoginUser());
			return "login.jsp";
		}

		// No errors!
		// Store their ID from the DB in session,
		// in other words, log them in.
		session.setAttribute("firstName", createdUser.getUserName());
		session.setAttribute("userId", createdUser.getId() );
		session.setAttribute("email", createdUser.getEmail());
		return "redirect:/dash";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("newUser") User newUser, @Valid @ModelAttribute("newLogin") LoginUser newLogin,
			BindingResult result, Model model, HttpSession session) {

		User user = userService.login(newLogin, result);
		if (result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "login.jsp";
		}

		// No errors!
		// Store their ID from the DB in session,
		// in other words, log them in.
		session.setAttribute("firstName", user.getUserName());
		session.setAttribute("userId", user.getId());
		session.setAttribute("email", user.getEmail());
		return "redirect:/dash";
	}

	@GetMapping("/dash")
	public String dash(Model model, User user, HttpSession session) {
		if (userService.validateSession(session)) {
			ArrayList<Place> allPlaces = this.placeService.allPlaces();
			String userEmail = (String) session.getAttribute("email");
			User currentUser = this.userService.findUser(userEmail);
			model.addAttribute("user", currentUser);
			model.addAttribute("places", allPlaces);

			if(session.getAttribute("origin") == null) {
				Place startUpVal = this.placeService.placeName("Jarvis'");
				session.setAttribute("origin", startUpVal.getPlaceID());
			}
			String origin = session.getAttribute("origin").toString();
			HashMap<String, String> travelTimes = APIController.apiGetTravelTime(origin, allPlaces);
			session.setAttribute("travelTimes", travelTimes);
			
			return "dash.jsp";
		}
		return "redirect/";
	}
	
	@PostMapping("/set/routemap")
	public String setRouteMap(@ModelAttribute("locationInput") MapPoints locationInput,
			Model model, HttpSession session, BindingResult result) {
		model.addAttribute("locationInput", new MapPoints());
		String start = locationInput.getOrigin();
		System.out.println(start);
		String end = locationInput.getDestination();
		System.out.println(end);
		Place origin = this.placeService.placeName(start);
		Place destination = this.placeService.placeName(end);
		System.out.println(destination);
		System.out.println(origin);
		session.setAttribute("origin", origin.getPlaceID());
		session.setAttribute("destination", destination.getPlaceID());
		return "redirect:/dash";
	}

	@GetMapping("/add/location")
	public String addLocation(HttpSession session, Model model) {
		if (userService.validateSession(session)) {
			model.addAttribute("place", new Place());
			return "addLocation.jsp";
		}
		return "redirect:/";
	}

	@PostMapping("/process/new/location")
	public String processLocation(@Valid @ModelAttribute("place") Place place, BindingResult result,
			HttpSession session) {
		if (result.hasErrors()) {
			System.out.println(result.getFieldErrors());
			return "addLocation.jsp";
		} else {
			place.setPlaceID(APIController.apiGetPlaces(place));
			placeService.createPlace(place);
			System.out.println(place.getPlaceID());
			return "redirect:/dash";
		}
	}
}

//Place startingOrigin = this.placeService.placeName("Home");
//session.setAttribute("origin",startingOrigin);