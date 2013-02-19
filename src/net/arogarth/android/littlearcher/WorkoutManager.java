package net.arogarth.android.littlearcher;

import java.util.HashMap;
import java.util.Observable;

import net.arogarth.android.littlearcher.database.RingHandler;
import net.arogarth.android.littlearcher.database.models.Ring;
import net.arogarth.android.littlearcher.database.models.Workout;

public class WorkoutManager extends Observable {
	private static WorkoutManager instance = null;
	
	public static WorkoutManager getInstance() {
		if( instance == null )
			instance = new WorkoutManager();
		
		return instance;
	}
	
	private WorkoutManager() {
		this.initRings();
	}
	
	private HashMap<String, Integer> rings = new HashMap<String, Integer>();
	
	private Workout currentWorkout;
	
	private void initRings() {
		rings.clear();
		rings.put("1", 0);
		rings.put("2", 0);
		rings.put("3", 0);
		rings.put("4", 0);
		rings.put("5", 0);
		rings.put("6", 0);
		rings.put("7", 0);
		rings.put("8", 0);
		rings.put("9", 0);
		rings.put("10", 0);
		rings.put("M", 0);
		rings.put("X", 0);
	}
	
	private Integer getNextRun() {
		return RingHandler.getInstance().getNextRound(this.currentWorkout);
	}
	
	public void setCurrentWorkout(Workout workout) {
		this.currentWorkout = workout;
	}
	
	public Workout getCurrentWorkout() {
		return this.currentWorkout;
	}
	
	public void increaseRing(String ringNumber) {
		if( this.getArrowCount() >= this.getCurrentWorkout().getArrows() ) {
			return;
		}
		
		Integer value = this.rings.get(ringNumber);
		value++;
		this.rings.put(ringNumber, value);
		
		this.validate();
	}
	
	public void decreaseRing(String ringNumber) {
		Integer value = this.rings.get(ringNumber);
		if(value <= 0) {
			return;
		}
		
		value--;
		this.rings.put(ringNumber, value);
		
		this.validate();
	}
	
	public Integer getArrowCount() {
		Integer i = 0;
		for(String key : this.rings.keySet()) {
			i += this.rings.get(key);
		}
		
		return i;
	}
	
	public void save() {
		int max = this.getCurrentWorkout().getArrows();
		
		if(this.getArrowCount() < max) {
			Integer i = this.rings.get("M");
			i += max - this.getArrowCount();
			this.rings.put("M", i);
		}
		
		Integer passe = this.getNextRun();
		Long workoutId = this.getCurrentWorkout().getId();
		
		for(String key : this.rings.keySet()) {
			for(int i = 0; i < this.rings.get(key); i++) {
				Ring ring = new Ring(key, passe, workoutId);
				RingHandler.getInstance().addRing(ring);
			}
		}
		
		this.reset();
	}
	
	public void reset() {
		this.initRings();
		
		this.validate();
	}
	
	public HashMap<String, Integer> getRings() {
		return this.rings;
	}
	
	private void validate() {
//		for(int i = 0; i < this.ringCount.length; i++) {
//			if( this.ringCount[i] < 0 ) this.ringCount[i] = 0;
//		}
		
		this.setChanged();
		
		this.notifyObservers();
	}
	
}
