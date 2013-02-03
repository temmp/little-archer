package net.arogarth.android.littlearcher.activities;

import java.sql.Timestamp;
import java.util.Date;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;
import net.arogarth.android.littlearcher.activities.workout.Config;
import net.arogarth.android.littlearcher.activities.workout.ListActivity;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.models.Workout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
    }
	
	public void resumeWorkout(View row) {
		try {
			Workout w = WorkoutHandler.getInstance().loadLastWorkout();
			WorkoutManager.getInstance().setCurrentWorkout(w);
			Intent passe = new Intent(this, RingCountActivity.class);
			startActivity(passe);
		} catch (Exception e) {
			
		}
	}
	
	public void startWorkout(View row) {
		Intent passe = new Intent(this, Config.class);
		startActivity(passe);
	}
	
	public void overview(View row) {
		Intent passe = new Intent(this, ListActivity.class);
		startActivity(passe);
	}
	
	public void arrowcounter(View row) {
		Intent passe = new Intent(this, ArrowcounterActivity.class);
		startActivity(passe);
	}
}
