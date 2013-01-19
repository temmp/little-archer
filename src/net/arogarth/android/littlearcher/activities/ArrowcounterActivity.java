package net.arogarth.android.littlearcher.activities;


import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.database.CacheHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ArrowcounterActivity extends Activity {

	private Integer counter = 0;

	private static final String counterCacheKey = "arrowcounter.counter";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.arrowcounter);
        
        Object o = CacheHandler.getInstance().getValue(counterCacheKey);

        try {
        	this.counter = Integer.parseInt(o.toString());
        } catch (Exception e) {
        	this.counter = 0;
        }
        
        if(this.counter == null) this.counter = 0;
        	
        this.displayCounter();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.arrowcounter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch( item.getItemId() ) {
    	case R.id.reset:
    		this.reset(null);
    		break;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    public void reset(View object) {
    	this.counter = 0;
    	CacheHandler.getInstance().delete(counterCacheKey);
    	this.displayCounter();
    }
    
    public void increment(View object) {
    	this.counter++;
    	if(this.counter > 999) this.counter = 0;
    	
    	CacheHandler.getInstance().setValue(counterCacheKey, this.counter.toString());
    	this.displayCounter();
	}
    
    private void displayCounter() {
    	String text = String.format("%03d", this.counter);
    	((TextView) findViewById(R.id.arrowCount)).setText(text);
    }
}