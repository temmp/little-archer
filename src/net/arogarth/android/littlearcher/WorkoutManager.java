package net.arogarth.android.littlearcher;

import java.lang.reflect.Method;
import java.util.Observable;

import net.arogarth.android.littlearcher.database.RingHandler;
import net.arogarth.android.littlearcher.database.models.Ring;
import net.arogarth.android.littlearcher.database.models.RingCount;
import net.arogarth.android.littlearcher.database.models.Workout;

public class WorkoutManager extends Observable {
	private static WorkoutManager instance = null;
	
	public static WorkoutManager getInstance() {
		if( instance == null )
			instance = new WorkoutManager();
		
		return instance;
	}
	
	private WorkoutManager() {
	}
	
	private RingCount rings = new RingCount();
	
	private Workout currentWorkout;
	
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
		Class<?> clazz = rings.getClass();
		
		String methodName = "";
		if( ringNumber.equalsIgnoreCase("M") || ringNumber.equalsIgnoreCase("X") )
			methodName = ringNumber;
		else
			methodName = "Ring" + ringNumber;
		
		Method method;
		try {
			method = clazz.getMethod("get" + methodName);
			Integer count = (Integer) method.invoke(rings);
			
			count++;
			
			method = clazz.getMethod("set" + methodName, Integer.class);
			method.invoke(rings, count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.validate();
	}
	
	public void decreaseRing(String ringNumber) {
		Class<?> clazz = rings.getClass();
		
		String methodName = "";
		if( ringNumber.equalsIgnoreCase("M") || ringNumber.equalsIgnoreCase("X") )
			methodName = ringNumber;
		else
			methodName = "Ring" + ringNumber;
		
		Method method;
		try {
			method = clazz.getMethod("get" + methodName);
			Integer count = (Integer) method.invoke(rings);
			
			if( count > 0 )
				count--;
			
			method = clazz.getMethod("set" + methodName, Integer.class);
			method.invoke(rings, count);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.validate();
	}
	
	public Integer getRingCount(String index) {
		Class<?> clazz = rings.getClass();
		
		String methodName = "";
		if( index.equalsIgnoreCase("M") || index.equalsIgnoreCase("X") )
			methodName = index;
		else
			methodName = "Ring" + index;
		
		Integer count = 0;
		Method method;
		try {
			method = clazz.getMethod("get" + methodName);
			count = (Integer) method.invoke(rings);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return count;
	}
	
	public RingCount getRingCount() {
		return this.rings;
	}
	
	public void save() {
	
		Integer passe = this.getNextRun();
		Long workoutId = this.getCurrentWorkout().getId();
		
		for(int i=0; i < this.rings.getX(); i++) {
			Ring ring = new Ring("X", passe, workoutId);
			RingHandler.getInstance().addRing(ring);
		}
		
		for(Integer j=10; j>0; j--) {
			try {
				Class<?> clazz = rings.getClass();
				
				String methodName = "getRing" + j;
				
				Method method = clazz.getMethod(methodName);
				Integer count = (Integer) method.invoke(rings);
				
				for(int i=0; i < count; i++) {
					Ring ring = new Ring(j.toString(), passe, workoutId);
					RingHandler.getInstance().addRing(ring);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i=0; i < this.rings.getM(); i++) {
			Ring ring = new Ring("M", passe, workoutId);
			RingHandler.getInstance().addRing(ring);
		}
		
		this.reset();
	}
	
	public void reset() {
		rings = new RingCount();
		
		this.validate();
	}
	
	private void validate() {
//		for(int i = 0; i < this.ringCount.length; i++) {
//			if( this.ringCount[i] < 0 ) this.ringCount[i] = 0;
//		}
		
		this.setChanged();
		
		this.notifyObservers();
	}
	
}
