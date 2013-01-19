package net.arogarth.android.littlearcher.database.models;

import java.sql.Timestamp;

public class Workout {
	private Long id;
	private Timestamp date;
	private String name;
	private String description;
	private String place;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public final String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public final void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the place
	 */
	public final String getPlace() {
		return place;
	}
	/**
	 * @param place the place to set
	 */
	public final void setPlace(String place) {
		this.place = place;
	}
}
