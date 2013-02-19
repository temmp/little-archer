package net.arogarth.android.littlearcher.database.models;

public class Ring implements Comparable<Ring> {
	private Long id;
	private Long workoutId;
	private Integer passe;
	private String ring;
	
	public Ring() {
		
	}
	
	public Ring(String ring, Integer passe, Long workoutId) {
		this.ring = ring;
		this.passe = passe;
		this.workoutId = workoutId;
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
	/**
	 * @return the workoutId
	 */
	public final Long getWorkoutId() {
		return workoutId;
	}
	/**
	 * @param workoutId the workoutId to set
	 */
	public final void setWorkoutId(Long workoutId) {
		this.workoutId = workoutId;
	}
	/**
	 * @return the passe
	 */
	public final Integer getPasse() {
		return passe;
	}
	/**
	 * @param passe the passe to set
	 */
	public final void setPasse(Integer passe) {
		this.passe = passe;
	}
	/**
	 * @return the ring
	 */
	public final String getRing() {
		return ring;
	}
	/**
	 * @param ring the ring to set
	 */
	public final void setRing(String ring) {
		this.ring = ring;
	}

	@Override
	public int compareTo(Ring ring) {
		if(ring.getRing().equalsIgnoreCase("M") || this.getRing().equalsIgnoreCase("X")) {
			return -100;
		}
		
		if(ring.getRing().equalsIgnoreCase("X") || this.getRing().equalsIgnoreCase("M")) {
			return 100;
		}
		
		Integer thiz = Integer.parseInt(this.getRing());
		Integer argument = Integer.parseInt(ring.getRing());
		
		if(thiz > argument) {
			return -1;
		}
		if(thiz < argument) {
			return 1;
		}
		
		return 0;
	}
}
