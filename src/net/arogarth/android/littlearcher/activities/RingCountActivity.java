package net.arogarth.android.littlearcher.activities;

import java.util.Observable;
import java.util.Observer;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.RingManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
        
        RingManager.getInstance().addObserver(this);
    }

    public void save(View button) {
    	RingManager.getInstance().save();
    }
    
    public void cancle(View button) {
    	RingManager.getInstance().reset();
    }
    
    public void reduceRing(View button) {
    	Integer ring = Integer.parseInt( button.getTag().toString() );
    	
    	RingManager.getInstance().decreaseRing( ring );
    }

    public void increaseRing(View button) {
    	Integer ring = Integer.parseInt( button.getTag().toString() );
    	
    	RingManager.getInstance().increaseRing( ring );
    }
    
	@Override
	public void update(Observable observable, Object data) {
		
		((TextView) findViewById(R.id.ringSum)).setText("= " + RingManager.getInstance().getSum().toString());
		
		TableLayout tl = (TableLayout) findViewById(R.id.ringList);
		
		Integer[] rings = RingManager.getInstance().getRingCounts();
		
		for( int i = 0; i < rings.length; i++) {
			int index = (rings.length - 1) - i;
			
			View row = tl.getChildAt( index );
			
			((TextView) row.findViewById(R.id.ringCount)).setText(rings[i].toString());
		}
	}
}