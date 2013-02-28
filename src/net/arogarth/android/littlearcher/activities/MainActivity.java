package net.arogarth.android.littlearcher.activities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;
import net.arogarth.android.littlearcher.activities.settings.MainSettingsActivity;
import net.arogarth.android.littlearcher.activities.workout.Config;
import net.arogarth.android.littlearcher.activities.workout.DetailsActivity;
import net.arogarth.android.littlearcher.activities.workout.ListActivity;
import net.arogarth.android.littlearcher.activities.workout.PropertiesActivity;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.helper.DatabaseBackupHelper;
import net.arogarth.android.littlearcher.database.models.Workout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent passe;
    	switch( item.getItemId() ) {
    	case R.id.menu_main_settings:
    		passe = new Intent(this, MainSettingsActivity.class);
//        	passe.putExtra("workout_id", WorkoutManager.getInstance().getCurrentWorkout().getId());
        	startActivity(passe);
    		break;
    	case R.id.menu_main_backups:
    		passe = new Intent(this, BackupManagerActivity.class);
//        	passe.putExtra("workout_id", WorkoutManager.getInstance().getCurrentWorkout().getId());
    		startActivity(passe);
    		break;
    	}
    	
    	return super.onOptionsItemSelected(item);
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
