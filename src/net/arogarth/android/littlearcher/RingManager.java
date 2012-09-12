package net.arogarth.android.littlearcher;

import java.util.Observable;

import net.arogarth.android.littlearcher.database.RingCountHandler;
import net.arogarth.android.littlearcher.database.models.RingCount;

public class RingManager extends Observable {
	private static RingManager instance = null;
	
	public static RingManager getInstance() {
		if( instance == null )
			instance = new RingManager();
		
		return instance;
	}
	
	private RingManager() {
	}
	
	private Integer run = 0;
	
	private Integer[] ringCount = {0,0,0,0,0,0,0,0,0,0};
	
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
		rc.setRound(run);
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
		
		new RingCountHandler().addRingCount( rc );
		
		for(int i=0; i < ringCount.length; i++)
			ringCount[i] = 0;
	
		run++;
		
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
			if( this.ringCount[i] > 10 ) this.ringCount[i] = 10;
		}
		
		this.setChanged();
		
		this.notifyObservers();
	}
	
}
