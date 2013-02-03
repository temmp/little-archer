package net.arogarth.android.littlearcher.activities.workout;

import java.text.SimpleDateFormat;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.models.Workout;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PropertiesActivity extends Activity {
	private Workout currentWorkout;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Long workoutId = getIntent().getLongExtra("workout_id", 0);
        this.currentWorkout = WorkoutHandler.getInstance().load(workoutId);
        
        setContentView(R.layout.workout_properties);
        
        this.fillContent(this.currentWorkout);
    }
    
    public void onClickSave(View button) {
    	Workout w = this.currentWorkout;
    	
    	w.setName( ((TextView) findViewById(R.id.name)).getText().toString() );
    	w.setPlace( ((TextView) findViewById(R.id.place)).getText().toString() );
    	
//    	String string = ((TextView) findViewById(R.id.date)).getText().toString();
//    	DateFormat df = new DateFormat();
//    	
//		try {
//			Date d = java.text.DateFormat.getDateInstance().parse(string);
//			Timestamp ts = new Timestamp( d.getTime() );
//			w.setDate( ts );
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	w.setDescription( ((TextView) findViewById(R.id.description)).getText().toString() );
    	
    	WorkoutHandler.getInstance().saveWorkout(w);
    	
    	finish();
    }
    
    private void fillContent(Workout workout) {
    	((TextView) findViewById(R.id.name)).setText(workout.getName());
    	((TextView) findViewById(R.id.place)).setText(workout.getPlace());

    	((TextView) findViewById(R.id.date)).setText(SimpleDateFormat.getDateInstance().format(workout.getDate()));
    	((TextView) findViewById(R.id.time)).setText(SimpleDateFormat.getTimeInstance().format(workout.getDate()));
    	
    	((TextView) findViewById(R.id.description)).setText(workout.getDescription());
    }
}
