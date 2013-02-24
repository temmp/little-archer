package net.arogarth.android.littlearcher.activities;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;
import net.arogarth.android.littlearcher.activities.workout.DetailsActivity;
import net.arogarth.android.littlearcher.activities.workout.PropertiesActivity;
import net.arogarth.android.littlearcher.database.RingHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RingCountActivity extends Activity implements Observer {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.passe);

        LayoutInflater li = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        LinearLayout ll = new LinearLayout(getBaseContext());
        ll.setOrientation(LinearLayout.VERTICAL);
        
        if(WorkoutManager.getInstance().getCurrentWorkout().getCountX()) {
        	ll.addView(li.inflate(R.layout.fita_x, null));
        }
        
        ll.addView(li.inflate(R.layout.fita_10_6, null));
        
        if(WorkoutManager.getInstance().getCurrentWorkout().getRings() == 10) {
        	ll.addView(li.inflate(R.layout.fita_5_1, null));
        }

//        ll.addView(li.inflate(R.layout.fita_m, null));
        
        ((ScrollView) findViewById(R.id.fitacontent)).addView(ll);
        
        WorkoutManager.getInstance().addObserver(this);
        
        this.setTotals();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ring_count, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent passe;
    	switch( item.getItemId() ) {
    	case R.id.menu_view_details:
    		passe = new Intent(this, DetailsActivity.class);
        	passe.putExtra("workout_id", WorkoutManager.getInstance().getCurrentWorkout().getId());
        	startActivity(passe);
    		break;
    	case R.id.menu_view_properties:
    		passe = new Intent(this, PropertiesActivity.class);
    		passe.putExtra("workout_id", WorkoutManager.getInstance().getCurrentWorkout().getId());
    		startActivity(passe);
    		break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    public void next(View button) {
    	WorkoutManager.getInstance().save();
    	this.setTotals();
    }
    
    public void decreaseRing(View button) {
    	String ring = button.getTag().toString();
    	WorkoutManager.getInstance().decreaseRing( ring );
    }

    public void increaseRing(View button) {
    	String ring = button.getTag().toString();
    	WorkoutManager.getInstance().increaseRing( ring );
    }
    
	@Override
	public void update(Observable observable, Object data) {
		HashMap<String, Integer> rings = WorkoutManager.getInstance().getRings();
		
		Integer arrows = 0;
		Integer points = 0;
		
		for(String key : rings.keySet()) {
			Integer _arrows = rings.get(key);
			arrows += _arrows;
			
			if(key == "X") {
				points += 10 * _arrows;
			} else if(key == "M") {
				// Do nothings
			} else {
				Integer value = Integer.parseInt(key);
				points += value * _arrows;
			}
		}
		
//		((TextView) findViewById(R.id.ringSum)).setText(points.toString());
//		((TextView) findViewById(R.id.arrowSum)).setText(arrows.toString());
		
		LinearLayout tl = (LinearLayout) findViewById(R.id.ringListM);

//		((TextView) tl.getChildAt( 0 ).findViewById(R.id.ring_count)).setText(rings.get("M").toString());
		
		if(WorkoutManager.getInstance().getCurrentWorkout().getCountX()) {
			tl = (LinearLayout) findViewById(R.id.ringListX);
			((TextView) tl.getChildAt( 0 ).findViewById(R.id.ring_count)).setText(rings.get("X").toString());
		}
		
		tl = (LinearLayout) findViewById(R.id.ringList10_6);
		((TextView) tl.getChildAt(0).findViewById(R.id.ring_count)).setText(rings.get("10").toString());
		((TextView) tl.getChildAt(1).findViewById(R.id.ring_count)).setText(rings.get("9").toString());
		((TextView) tl.getChildAt(2).findViewById(R.id.ring_count)).setText(rings.get("8").toString());
		((TextView) tl.getChildAt(3).findViewById(R.id.ring_count)).setText(rings.get("7").toString());
		((TextView) tl.getChildAt(4).findViewById(R.id.ring_count)).setText(rings.get("6").toString());
		
		if(WorkoutManager.getInstance().getCurrentWorkout().getRings() == 10) {
			tl = (LinearLayout) findViewById(R.id.ringList5_1);
			((TextView) tl.getChildAt(0).findViewById(R.id.ring_count)).setText(rings.get("5").toString());
			((TextView) tl.getChildAt(1).findViewById(R.id.ring_count)).setText(rings.get("4").toString());
			((TextView) tl.getChildAt(2).findViewById(R.id.ring_count)).setText(rings.get("3").toString());
			((TextView) tl.getChildAt(3).findViewById(R.id.ring_count)).setText(rings.get("2").toString());
			((TextView) tl.getChildAt(4).findViewById(R.id.ring_count)).setText(rings.get("1").toString());
		}
	}
	
	private void setTotals() {
		String totalArrows = String.format("%04d", WorkoutManager.getInstance().getTotalArrows());
		((TextView) findViewById(R.id.totalArrows)).setText(totalArrows);
		
		String totalRings = String.format("%04d", WorkoutManager.getInstance().getTotalRings());
		((TextView) findViewById(R.id.totalRings)).setText(totalRings);
	}
}