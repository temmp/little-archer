package net.arogarth.android.littlearcher.activities;

import java.sql.Timestamp;
import java.util.Date;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;
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
		Workout w = WorkoutHandler.getInstance().loadLastWorkout();
		WorkoutManager.getInstance().setCurrentWorkout(w);
		
		Intent passe = new Intent(this, RingCountActivity.class);
		startActivity(passe);
	}
	
	public void startWorkout(View row) {
		Workout result = new Workout();
		result.setName(new Date().toLocaleString() );
		result.setDate(new Timestamp(new Date().getTime()));
		result.setName("");
		result = WorkoutHandler.getInstance().saveWorkout(result);
		
		WorkoutManager.getInstance().setCurrentWorkout(result);
		
		Intent passe = new Intent(this, RingCountActivity.class);
		startActivity(passe);
	}
	
	public void overview(View row) {
		Intent passe = new Intent(this, ListWorkoutActivity.class);
		startActivity(passe);
	}
}
