package net.arogarth.android.littlearcher.activities;

import java.util.Observable;
import java.util.Observer;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;
import net.arogarth.android.littlearcher.database.models.RingCount;

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
		
		LinearLayout tl = (LinearLayout) findViewById(R.id.ringList);

		int i=0;
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getM().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getX().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing10().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing9().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing8().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing7().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing6().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing5().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing4().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing3().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing2().toString());
		((TextView) tl.getChildAt( i++ ).findViewById(R.id.ring_count)).setText(rc.getRing1().toString());
		
	}
}