package com.droidoxy.easymoneyrewards.model;

public class UserHistory {
	private String title, thumbnailUrl,year,rating,genre,url,id;

	/**
	 private int year;
	 private double rating;
	 private ArrayList<String> genre;

	 */
	public UserHistory() {
	}

//	public Movie(String name, String thumbnailUrl, int year, double rating, ArrayList<String> genre) {

	public UserHistory(String name, String thumbnailUrl, String year, String rating, String genre, String url, String id) {

		this.title = name;
		this.thumbnailUrl = thumbnailUrl;
		this.year = year;
		this.rating = rating;
		this.genre = genre;
		this.url = url;
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

}