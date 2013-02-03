package net.arogarth.android.littlearcher.activities.workout;

import java.util.ArrayList;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;
import net.arogarth.android.littlearcher.activities.RingCountActivity;
import net.arogarth.android.littlearcher.database.RingHandler;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.models.Workout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity {
	
	private class ListWorkoutAdapter extends BaseAdapter
		implements OnItemClickListener, OnItemLongClickListener {

		private final LayoutInflater mInflater;
		private ArrayList<Workout> workouts = new ArrayList<Workout>();
		private ListView list;
		
		public ListWorkoutAdapter(ListView list) {
			this.list = list;
			
			list.setOnItemClickListener(this);
	        list.setOnItemLongClickListener(this);
	        
			mInflater = (LayoutInflater) ListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			notifyDataSetChanged();
		}
		
		@Override
		public void notifyDataSetChanged() {
			workouts = WorkoutHandler.getInstance().loadList();
			
			super.notifyDataSetChanged();
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
			
			
			Integer count = 0;
			
			count = RingHandler.getInstance().countArrows(w.getId());
			((TextView) v.findViewById(R.id.arrowSum)).setText(count.toString());
			
			count = RingHandler.getInstance().countRings(w.getId());
			((TextView) v.findViewById(R.id.ringSum)).setText(count.toString());
			
			return v;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
			i.putExtra("workout_id", id);
			startActivity(i);
		}

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
			
			final CharSequence[] items = {"Properties","Resume", "Delete"};
			
	        AlertDialog.Builder builder = new AlertDialog.Builder(ListWorkoutAdapter.this.list.getContext());
	        builder.setTitle("Select");
	        builder.setItems(items, new DialogInterface.OnClickListener(){
	            public void onClick(DialogInterface dialogInterface, int item) {
	            	int i = 0;
	            	
	            	if(item == i++) {
	            		Intent passe = new Intent(ListActivity.this, PropertiesActivity.class);
	            		passe.putExtra("workout_id", id);
	            		startActivity(passe);
	            	} else if(item == i++) {
	            		Workout w = WorkoutHandler.getInstance().loadList(
	            				String.format("id = %s", id)).get(0);
	            		WorkoutManager.getInstance().setCurrentWorkout(w);
	            		
	            		Intent passe = new Intent(ListActivity.this, RingCountActivity.class);
	            		startActivity(passe);
	            	} else if(item == i++) {
        		    	 WorkoutHandler.getInstance().removeWorkout(id);
        		         notifyDataSetChanged();
	            	}
	            	
	                return;
	            }
	        });
	        builder.create().show();
			
			return true;
		}
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.workout_list);

        ListView list = (ListView) findViewById(R.id.list_workouts);
        list.setAdapter(new ListWorkoutAdapter(list));
    }
	
	@Override
	protected void onResume() {
        setContentView(R.layout.workout_list);

        ListView list = (ListView) findViewById(R.id.list_workouts);
        list.setAdapter(new ListWorkoutAdapter(list));	
		
		// TODO Auto-generated method stub
		super.onResume();
	}
}
