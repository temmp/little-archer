package net.arogarth.android.littlearcher.database.models;

/**
 * Represent a ring on the target
 * 
 * each ring is belongs to a workout and to a one pass
 * 
 * the ring Property can have one of the following value:
 * - X => extra (count as 10 but with mor weight)
 * - 1..10 => ring point
 * - M => missing (0 points)
 *
 * Compareable rule:
 * X > 10 > ... 1 > M
 * 
 * @author arogarth
 *
 */
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

	/**
	 * compare this ring to another ring
	 * 
	 * if this ring is M or the the other ring is an X return -1
	 * if this ring is M or the the other ring is an X return +1
	 * if this ring is greater than the other return -1 otherwise return +1
	 * 
	 * as default the rings are equal and return 0
	 * 
	 * @return int
	 */
	@Override
	public int compareTo(Ring ring) {
		if(ring.getRing().equalsIgnoreCase("M") || this.getRing().equalsIgnoreCase("X")) {
			return -1;
		}
		
		if(ring.getRing().equalsIgnoreCase("X") || this.getRing().equalsIgnoreCase("M")) {
			return 1;
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
