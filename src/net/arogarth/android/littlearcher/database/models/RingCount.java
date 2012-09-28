package net.arogarth.android.littlearcher.database.models;

public class RingCount {
	private Long id;
	private Long workoutId;
	private Integer round = 0;
	private Integer ring1 = 0;
	private Integer ring2 = 0;
	private Integer ring3 = 0;
	private Integer ring4 = 0;
	private Integer ring5 = 0;
	private Integer ring6 = 0;
	private Integer ring7 = 0;
	private Integer ring8 = 0;
	private Integer ring9 = 0;
	private Integer ring10 = 0;
	private Integer X = 0;
	private Integer M = 0;
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
	
	public void setWorkoutId(Long workoutId) {
		this.workoutId = workoutId;
	}
	
	public Long getWorkoutId() {
		return this.workoutId;
	}
	
	public void setWorkout(Workout workout) {
		this.setWorkoutId( workout.getId() );
	}
	
	/**
	 * @return the round
	 */
	public Integer getRound() {
		return round;
	}
	/**
	 * @param round the round to set
	 */
	public void setRound(Integer round) {
		this.round = round;
	}
	/**
	 * @return the ring1
	 */
	public Integer getRing1() {
		return ring1;
	}
	/**
	 * @param ring1 the ring1 to set
	 */
	public void setRing1(Integer ring1) {
		this.ring1 = ring1;
	}
	/**
	 * @return the ring2
	 */
	public Integer getRing2() {
		return ring2;
	}
	/**
	 * @param ring2 the ring2 to set
	 */
	public void setRing2(Integer ring2) {
		this.ring2 = ring2;
	}
	/**
	 * @return the ring3
	 */
	public Integer getRing3() {
		return ring3;
	}
	/**
	 * @param ring3 the ring3 to set
	 */
	public void setRing3(Integer ring3) {
		this.ring3 = ring3;
	}
	/**
	 * @return the ring4
	 */
	public Integer getRing4() {
		return ring4;
	}
	/**
	 * @param ring4 the ring4 to set
	 */
	public void setRing4(Integer ring4) {
		this.ring4 = ring4;
	}
	/**
	 * @return the ring5
	 */
	public Integer getRing5() {
		return ring5;
	}
	/**
	 * @param ring5 the ring5 to set
	 */
	public void setRing5(Integer ring5) {
		this.ring5 = ring5;
	}
	/**
	 * @return the ring6
	 */
	public Integer getRing6() {
		return ring6;
	}
	/**
	 * @param ring6 the ring6 to set
	 */
	public void setRing6(Integer ring6) {
		this.ring6 = ring6;
	}
	/**
	 * @return the ring7
	 */
	public Integer getRing7() {
		return ring7;
	}
	/**
	 * @param ring7 the ring7 to set
	 */
	public void setRing7(Integer ring7) {
		this.ring7 = ring7;
	}
	/**
	 * @return the ring8
	 */
	public Integer getRing8() {
		return ring8;
	}
	/**
	 * @param ring8 the ring8 to set
	 */
	public void setRing8(Integer ring8) {
		this.ring8 = ring8;
	}
	/**
	 * @return the ring9
	 */
	public Integer getRing9() {
		return ring9;
	}
	/**
	 * @param ring9 the ring9 to set
	 */
	public void setRing9(Integer ring9) {
		this.ring9 = ring9;
	}
	/**
	 * @return the ring10
	 */
	public Integer getRing10() {
		return ring10;
	}
	/**
	 * @param ring10 the ring10 to set
	 */
	public void setRing10(Integer ring10) {
		this.ring10 = ring10;
	}
	/**
	 * @return the x
	 */
	public Integer getX() {
		return X;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(Integer x) {
		X = x;
	}
	/**
	 * @return the m
	 */
	public Integer getM() {
		return M;
	}
	/**
	 * @param m the m to set
	 */
	public void setM(Integer m) {
		M = m;
	}
	
	public Integer getPoints() {
		Integer points = 0;
		
		points += this.getX() * 10;
		points += this.getRing10() * 10;
		points += this.getRing9() * 9;
		points += this.getRing8() * 8;
		points += this.getRing7() * 7;
		points += this.getRing6() * 6;
		points += this.getRing5() * 5;
		points += this.getRing4() * 4;
		points += this.getRing3() * 3;
		points += this.getRing2() * 2;
		points += this.getRing1() * 1;
		
		return points;
	}
	
	public Integer getArrowCount() {
		return this.getX()
				+ this.getM()
				+ this.getRing10()
				+ this.getRing9()
				+ this.getRing8()
				+ this.getRing7()
				+ this.getRing6()
				+ this.getRing5()
				+ this.getRing4()
				+ this.getRing3()
				+ this.getRing2()
				+ this.getRing1();
	}
}
