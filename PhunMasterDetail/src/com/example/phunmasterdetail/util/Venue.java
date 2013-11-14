package com.example.phunmasterdetail.util;

import java.util.List;

public class Venue {

	// Core fields
	private long id;
	private int pcode;
	private float latitude;
	private float longitude;
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;

	// Super Bowl venue fields
	private String tollfreephone;
	private String url;
	private String description;
	private String ticket_link;
	private String image_url;
	private List<ScheduleItem> schedule;

	// computed fields
	private float distance;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTicketLink() {
		return ticket_link;
	}

	public void setTicketLink(String ticketLink) {
		this.ticket_link = ticketLink;
	}

	public List<ScheduleItem> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<ScheduleItem> schedule) {
		this.schedule = schedule;
	}

	public String getTollFreePhone() {
		return tollfreephone;
	}

	public void setTollFreePhone(String tollFreePhone) {
		this.tollfreephone = tollFreePhone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Venue() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Venue && ((Venue) o).getId() == id) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Long.valueOf(id).hashCode();
	}

	public int getPcode() {
		return pcode;
	}

	public void setPcode(int pcode) {
		this.pcode = pcode;
	}

	public String getImageUrl() {
		return image_url;
	}

	public void setImageUrl(String imageUrl) {
		this.image_url = imageUrl;
	}

}