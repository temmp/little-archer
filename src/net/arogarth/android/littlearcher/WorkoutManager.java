package net.arogarth.android.littlearcher;

import java.util.Observable;

import net.arogarth.android.littlearcher.database.RingCountHandler;
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
	
	private Integer[] ringCount = {0,0,0,0,0,0,0,0,0,0};
	
	private Workout currentWorkout;
	
	private Integer getNextRun() {
		return RingCountHandler.getInstance().getNextRound(this.currentWorkout);
	}
	
	public void setCurrentWorkout(Workout workout) {
		this.currentWorkout = workout;
	}
	
	public Workout getCurrentWorkout() {
		return this.currentWorkout;
	}
	
	public void increaseRing(Integer ringNumber) {
		this.ringCount[ ringNumber - 1 ]++;
		
		this.validate();
	}
	
	public void decreaseRing(Integer ringNumber) {
		this.ringCount[ ringNumber - 1 ]--;

		this.validate();
	}
	
	public Integer getSum() {
		Integer sum = 0;
		
		for(int i = 0; i < this.ringCount.length; i++ )
			sum += this.ringCount[i] * ( i + 1 );
		
		return sum;
	}
	
	public Integer getRingCount(Integer index) {
		return this.ringCount[index];
	}
	
	public Integer[] getRingCounts() {
		return this.ringCount;
	}
	
	public void save() {
		
		RingCount rc = new RingCount();
	
		rc.setWorkout( this.getCurrentWorkout() );
		rc.setRound( this.getNextRun() );
		rc.setRing1(ringCount[0]);
		rc.setRing2(ringCount[1]);
		rc.setRing3(ringCount[2]);
		rc.setRing4(ringCount[3]);
		rc.setRing5(ringCount[4]);
		rc.setRing6(ringCount[5]);
		rc.setRing7(ringCount[6]);
		rc.setRing8(ringCount[7]);
		rc.setRing9(ringCount[8]);
		rc.setRing10(ringCount[9]);
		
		RingCountHandler.getInstance().addRingCount( rc );
		
		for(int i=0; i < ringCount.length; i++)
			ringCount[i] = 0;
	
		this.reset();
	}
	
	public void reset() {
		for(int i=0; i < ringCount.length; i++)
			ringCount[i] = 0;
		
		this.validate();
	}
	
	private void validate() {
		for(int i = 0; i < this.ringCount.length; i++) {
			if( this.ringCount[i] < 0 ) this.ringCount[i] = 0;
		}
		
		this.setChanged();
		
		this.notifyObservers();
	}
	
}
