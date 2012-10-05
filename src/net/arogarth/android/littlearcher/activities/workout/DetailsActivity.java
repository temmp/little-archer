package net.arogarth.android.littlearcher.activities.workout;

import java.util.ArrayList;
import java.util.zip.Inflater;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.database.RingHandler;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.models.RingCount;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	private class WorkoutDetailsListAdapter extends BaseAdapter {

		private ArrayList<RingCount> results = new ArrayList<RingCount>();
		
		public WorkoutDetailsListAdapter(Long workoutId) {
			results = RingHandler.getInstance().getRingCounts(workoutId);
		}
		
		@Override
		public int getCount() {
			return this.results.size();
		}

		@Override
		public RingCount getItem(int position) {
			return this.results.get(position);
		}

		@Override
		public long getItemId(int position) {
			return this.results.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater mInflater = (LayoutInflater) DetailsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			RingCount r = this.getItem(position);
			View row = mInflater.inflate(R.layout.overview_row, parent, false);
			
			((TextView) row.findViewById(R.id.round)).setText(r.getRound().toString());
			((TextView) row.findViewById(R.id.ringM)).setText(r.getM().toString());
			((TextView) row.findViewById(R.id.ringX)).setText(r.getX().toString());
			((TextView) row.findViewById(R.id.ring10)).setText(r.getRing10().toString());
			((TextView) row.findViewById(R.id.ring9)).setText(r.getRing9().toString());
			((TextView) row.findViewById(R.id.ring8)).setText(r.getRing8().toString());
			((TextView) row.findViewById(R.id.ring7)).setText(r.getRing7().toString());
			((TextView) row.findViewById(R.id.ring6)).setText(r.getRing6().toString());
			((TextView) row.findViewById(R.id.ring5)).setText(r.getRing5().toString());
			((TextView) row.findViewById(R.id.ring4)).setText(r.getRing4().toString());
			((TextView) row.findViewById(R.id.ring3)).setText(r.getRing3().toString());
			((TextView) row.findViewById(R.id.ring2)).setText(r.getRing2().toString());
			((TextView) row.findViewById(R.id.ring1)).setText(r.getRing1().toString());
			((TextView) row.findViewById(R.id.sum)).setText(r.getPoints().toString());
			
			return row;
		}
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Long workoutId = getIntent().getLongExtra("workout_id", 0);
        
        setContentView(R.layout.workout_details);

        WorkoutDetailsListAdapter adapter = new WorkoutDetailsListAdapter(workoutId);
        
        ((ListView) findViewById(R.id.list_details)).setAdapter(adapter);
        
        View row = findViewById(R.id.sum_row);
        
        RingCount r = RingHandler.getInstance().getSum(workoutId);
        
//		((TextView) row.findViewById(R.id.round)).setText(r.getRound().toString());
		((TextView) row.findViewById(R.id.ringM)).setText(r.getM().toString());
		((TextView) row.findViewById(R.id.ringX)).setText(r.getX().toString());
		((TextView) row.findViewById(R.id.ring10)).setText(r.getRing10().toString());
		((TextView) row.findViewById(R.id.ring9)).setText(r.getRing9().toString());
		((TextView) row.findViewById(R.id.ring8)).setText(r.getRing8().toString());
		((TextView) row.findViewById(R.id.ring7)).setText(r.getRing7().toString());
		((TextView) row.findViewById(R.id.ring6)).setText(r.getRing6().toString());
		((TextView) row.findViewById(R.id.ring5)).setText(r.getRing5().toString());
		((TextView) row.findViewById(R.id.ring4)).setText(r.getRing4().toString());
		((TextView) row.findViewById(R.id.ring3)).setText(r.getRing3().toString());
		((TextView) row.findViewById(R.id.ring2)).setText(r.getRing2().toString());
		((TextView) row.findViewById(R.id.ring1)).setText(r.getRing1().toString());
		((TextView) row.findViewById(R.id.sum)).setText(r.getPoints().toString());
    }
}
