package net.arogarth.android.littlearcher.database.models;

public class Cache {
	private Long id;
	private String key;
	private String value;
	
	public Cache() {
		
	}
	
	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public final void setId(Long id) {
		this.id = id;
	}
	
	public final String getKey() {
		return this.key;
	}
	
	public final void setKey(String key) {
		this.key = key;
	}
	
	public final String getValue() {
		return this.value;
	}
	
	public final void setValue(String value) {
		this.value = value;
	}
	
	
}
