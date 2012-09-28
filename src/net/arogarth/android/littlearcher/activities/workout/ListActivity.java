package net.arogarth.android.littlearcher.activities.workout;

import java.util.ArrayList;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.R.id;
import net.arogarth.android.littlearcher.R.layout;
import net.arogarth.android.littlearcher.database.RingCountHandler;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.models.RingCount;
import net.arogarth.android.littlearcher.database.models.Workout;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListActivity extends Activity {
	
	private class ListWorkoutAdapter extends BaseAdapter implements OnItemClickListener {

		private final LayoutInflater mInflater;
		private ArrayList<Workout> workouts = new ArrayList<Workout>();
		
		public ListWorkoutAdapter() {
			mInflater = (LayoutInflater) ListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			workouts = WorkoutHandler.getInstance().loadList();
			
		}
		
		@Override
		public int getCount() {
			return workouts.size();
		}

		@Override
		public Workout getItem(int position) {
			return workouts.get(position);
		}

		@Override
		public long getItemId(int position) {
			return workouts.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Workout w = getItem(position);
			
			View v = mInflater.inflate(R.layout.workout_list_item, parent, false);
			((TextView) v.findViewById(R.id.workout_name)).setText(w.getName());
			((TextView) v.findViewById(R.id.workout_date)).setText(w.getDate().toLocaleString());
			
			Integer count = RingCountHandler.getInstance().loadResults(
					String.format("workout_id = %s", w.getId().toString())).size();

			((TextView) v.findViewById(R.id.workout_count)).setText(count.toString());
			
			return v;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
			i.putExtra("workout_id", id);
			startActivity(i);
		}

	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.workout_list);

        ListWorkoutAdapter a = new ListWorkoutAdapter();
        ListView list = (ListView) findViewById(R.id.list_workouts);
        
        list.setAdapter(a);
        list.setOnItemClickListener(a);
    }
}
