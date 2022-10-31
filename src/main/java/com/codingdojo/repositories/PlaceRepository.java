package com.codingdojo.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.model.Place;



@Repository
public interface PlaceRepository extends CrudRepository<Place , Long> {
	ArrayList<Place> findAll();
	
	Optional<Place> findById(Long id);
	Optional<Place> findByName(String name);
	@SuppressWarnings("unchecked")
	Place save(Place place);
	
	void deleteById(Long id);
}