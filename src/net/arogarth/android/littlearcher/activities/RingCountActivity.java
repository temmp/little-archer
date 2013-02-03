package net.arogarth.android.littlearcher.activities;

import java.util.Observable;
import java.util.Observer;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;
import net.arogarth.android.littlearcher.activities.workout.DetailsActivity;
import net.arogarth.android.littlearcher.activities.workout.ListActivity;
import net.arogarth.android.littlearcher.activities.workout.PropertiesActivity;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.models.RingCount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
        
        ll.addView(li.inflate(R.layout.fita_m, null));
        
        if(WorkoutManager.getInstance().getCurrentWorkout().getCountX()) {
        	ll.addView(li.inflate(R.layout.fita_x, null));
        }
        
        ll.addView(li.inflate(R.layout.fita_10_6, null));
        
        if(WorkoutManager.getInstance().getCurrentWorkout().getRings() == 10) {
        	ll.addView(li.inflate(R.layout.fita_5_1, null));
        }
        
        ((ScrollView) findViewById(R.id.fitacontent)).addView(ll);
        
        WorkoutManager.getInstance().addObserver(this);
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
    
    public void save(View button) {
    	WorkoutManager.getInstance().save();
    }
    
    public void cancle(View button) {
    	WorkoutManager.getInstance().reset();
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
		RingCount rc = WorkoutManager.getInstance().getRingCount();
		
		((TextView) findViewById(R.id.ringSum)).setText(rc.getPoints().toString());
		((TextView) findViewById(R.id.arrowSum)).setText(rc.getArrowCount().toString());
		
		LinearLayout tl = (LinearLayout) findViewById(R.id.ringListM);

		
		((TextView) tl.getChildAt( 0 ).findViewById(R.id.ring_count)).setText(rc.getM().toString());
		
		if(WorkoutManager.getInstance().getCurrentWorkout().getCountX()) {
			tl = (LinearLayout) findViewById(R.id.ringListX);
			((TextView) tl.getChildAt( 0 ).findViewById(R.id.ring_count)).setText(rc.getX().toString());
		}
		
		tl = (LinearLayout) findViewById(R.id.ringList10_6);
		((TextView) tl.getChildAt(0).findViewById(R.id.ring_count)).setText(rc.getRing10().toString());
		((TextView) tl.getChildAt(1).findViewById(R.id.ring_count)).setText(rc.getRing9().toString());
		((TextView) tl.getChildAt(2).findViewById(R.id.ring_count)).setText(rc.getRing8().toString());
		((TextView) tl.getChildAt(3).findViewById(R.id.ring_count)).setText(rc.getRing7().toString());
		((TextView) tl.getChildAt(4).findViewById(R.id.ring_count)).setText(rc.getRing6().toString());
		
		if(WorkoutManager.getInstance().getCurrentWorkout().getRings() == 10) {
			tl = (LinearLayout) findViewById(R.id.ringList5_1);
			((TextView) tl.getChildAt(0).findViewById(R.id.ring_count)).setText(rc.getRing5().toString());
			((TextView) tl.getChildAt(1).findViewById(R.id.ring_count)).setText(rc.getRing4().toString());
			((TextView) tl.getChildAt(2).findViewById(R.id.ring_count)).setText(rc.getRing3().toString());
			((TextView) tl.getChildAt(3).findViewById(R.id.ring_count)).setText(rc.getRing2().toString());
			((TextView) tl.getChildAt(4).findViewById(R.id.ring_count)).setText(rc.getRing1().toString());
		}
		
	}
}