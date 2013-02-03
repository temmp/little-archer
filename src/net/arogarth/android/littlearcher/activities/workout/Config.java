package net.arogarth.android.littlearcher.activities.workout;

import java.sql.Timestamp;
import java.util.Date;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;
import net.arogarth.android.littlearcher.activities.RingCountActivity;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.models.Workout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;

public class Config extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.workout_config);
    }
	
	public void clickBack(View object) {
		finish();
	}
	
	public void clickNext(View object) {
		Integer arrows = ((Spinner) findViewById(R.id.spinnerArrows)).getSelectedItemPosition();
		Integer rings = ((Spinner) findViewById(R.id.spinnerRings)).getSelectedItemPosition();
		Boolean countX = ((CheckBox) findViewById(R.id.checkBoxCountX)).isChecked();
		
		Workout result = new Workout();
		result.setName(new Date().toLocaleString() );
		result.setDate(new Timestamp(new Date().getTime()));
		result.setName("");
		result.setRings( rings == 0 ? 5 : 10 );
		result.setArrows( arrows == 0 ? 3 : 6 );
		result.setCountX( countX );
		result = WorkoutHandler.getInstance().saveWorkout(result);
		WorkoutManager.getInstance().setCurrentWorkout(result);
		
		Intent passe = new Intent(this, RingCountActivity.class);
		startActivity(passe);
		finish();
	}
}
