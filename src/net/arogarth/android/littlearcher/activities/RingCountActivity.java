package net.arogarth.android.littlearcher.activities;

import java.util.Observable;
import java.util.Observer;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class RingCountActivity extends Activity implements Observer {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.passe);

        LayoutInflater li = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.fita10, null);
        ((ScrollView) findViewById(R.id.fitacontent)).addView(v);
        
        WorkoutManager.getInstance().addObserver(this);
    }

    public void save(View button) {
    	WorkoutManager.getInstance().save();
    }
    
    public void cancle(View button) {
    	WorkoutManager.getInstance().reset();
    }
    
    public void decreaseRing(View button) {
    	Integer ring = Integer.parseInt( button.getTag().toString() );
    	WorkoutManager.getInstance().decreaseRing( ring );
    }

    public void increaseRing(View button) {
    	Integer ring = Integer.parseInt( button.getTag().toString() );
    	WorkoutManager.getInstance().increaseRing( ring );
    }
    
	@Override
	public void update(Observable observable, Object data) {
		
		((TextView) findViewById(R.id.ringSum)).setText("= " + WorkoutManager.getInstance().getSum().toString());
		
		LinearLayout tl = (LinearLayout) findViewById(R.id.ringList);
		
		Integer[] rings = WorkoutManager.getInstance().getRingCounts();
		
		for( int i = 0; i < rings.length; i++) {
			int index = (rings.length - 1) - i;
			
			View row = tl.getChildAt( index );
			
			((TextView) row.findViewById(R.id.ring_count)).setText(rings[i].toString());
		}
	}
}