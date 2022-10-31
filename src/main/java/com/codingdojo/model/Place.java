package com.codingdojo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name="places")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message="All fields must be provided.")
    @Size(min=1, max=30)
    private String name; 
    
    @NotEmpty(message="All fields must be provided.")
    @Size(min=1, max=30)
    private String street_address;
    
    @NotEmpty(message="All fields must be provided.")
    @Size(min=1, max=30)
    private String city;
    
    @NotEmpty(message="All fields must be provided.")
    @Size(min=1)
    private String state;
    
    //needs notNull or equivalent annotation
    @Min(value = 1)
    private Long zip;
    
    public float coordinates;
    
    private String place_id;
    

	@Column(updatable =false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
  

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	@JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    
    
    private User user;
    
    public Place() {
        
    }
    
    @PrePersist
    protected void onCreate() {
    	this.updatedAt = new Date();
    	this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
      this.updatedAt = new Date();
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getPlaceID() {
		return place_id;
	}
	
	public void setPlaceID(String place_id) {
		this.place_id = place_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet_address() {
		return street_address;
	}

	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getZip() {
		return zip;
	}

	public void setZip(Long zip) {
		this.zip = zip;
	}

	public float getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(float coordinates) {
		this.coordinates = coordinates;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
