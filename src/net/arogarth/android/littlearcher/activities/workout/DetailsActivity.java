package net.arogarth.android.littlearcher.activities.workout;

import java.util.ArrayList;
import java.util.zip.Inflater;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.database.RingHandler;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.models.Ring;
import net.arogarth.android.littlearcher.database.models.RingCount;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
        
        setContentView(R.layout.workout_details_p);

        this.buildList(workoutId);
        
//        WorkoutDetailsListAdapter adapter = new WorkoutDetailsListAdapter(workoutId);
//        
//        ((ListView) findViewById(R.id.list_details)).setAdapter(adapter);
//        
//        View row = findViewById(R.id.sum_row);
//        
//        RingCount r = RingHandler.getInstance().getSum(workoutId);
//        
////		((TextView) row.findViewById(R.id.round)).setText(r.getRound().toString());
//		((TextView) row.findViewById(R.id.ringM)).setText(r.getM().toString());
//		((TextView) row.findViewById(R.id.ringX)).setText(r.getX().toString());
//		((TextView) row.findViewById(R.id.ring10)).setText(r.getRing10().toString());
//		((TextView) row.findViewById(R.id.ring9)).setText(r.getRing9().toString());
//		((TextView) row.findViewById(R.id.ring8)).setText(r.getRing8().toString());
//		((TextView) row.findViewById(R.id.ring7)).setText(r.getRing7().toString());
//		((TextView) row.findViewById(R.id.ring6)).setText(r.getRing6().toString());
//		((TextView) row.findViewById(R.id.ring5)).setText(r.getRing5().toString());
//		((TextView) row.findViewById(R.id.ring4)).setText(r.getRing4().toString());
//		((TextView) row.findViewById(R.id.ring3)).setText(r.getRing3().toString());
//		((TextView) row.findViewById(R.id.ring2)).setText(r.getRing2().toString());
//		((TextView) row.findViewById(R.id.ring1)).setText(r.getRing1().toString());
//		((TextView) row.findViewById(R.id.sum)).setText(r.getPoints().toString());
    }
	

	private void buildList(Long workoutId) {
		SQLiteDatabase db = RingHandler.getInstance().getReadableDatabase();
		LayoutInflater li = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		String sql = String.format(
				"SELECT passe FROM %s WHERE workout_id = %s GROUP BY passe ORDER BY passe",
				RingHandler.getInstance().getTableName(),
				workoutId.toString()
				);
		
		Cursor c = db.rawQuery(sql, null);
		
		if( c.moveToFirst() ) {
			Integer lastRound = 0;
			
			do {
				Integer passe = c.getInt(0);
				ArrayList<Ring> rings = RingHandler.getInstance().loadRings(workoutId, passe);
				
				View v = li.inflate(R.layout.workout_list_item_p, null);
				
				Integer points = 0;
				
				if( rings.size() == 3 ) {
					((TextView) v.findViewById(R.id.col1)).setText(rings.get(0).getRing());
					((TextView) v.findViewById(R.id.col2)).setText(rings.get(1).getRing());
					((TextView) v.findViewById(R.id.col3)).setText(rings.get(2).getRing());

					for(Ring r : rings) {
						if(r.getRing().equalsIgnoreCase("X")) {
							points +=10;
						} else if(r.getRing().equalsIgnoreCase("M")) {
							points += 0;
						} else {
							points += Integer.parseInt(r.getRing());
						}
					}
					
					if( c.moveToNext() ) {
						passe = c.getInt(0);
						rings = RingHandler.getInstance().loadRings(workoutId, passe);
						
						((TextView) v.findViewById(R.id.col4)).setText(rings.get(0).getRing());
						((TextView) v.findViewById(R.id.col5)).setText(rings.get(1).getRing());
						((TextView) v.findViewById(R.id.col6)).setText(rings.get(2).getRing());
						
						for(Ring r : rings) {
							if(r.getRing().equalsIgnoreCase("X")) {
								points +=10;
							} else if(r.getRing().equalsIgnoreCase("M")) {
								points += 0;
							} else {
								points += Integer.parseInt(r.getRing());
							}
						}
						
					}
				} else {
					((TextView) v.findViewById(R.id.col1)).setText(rings.get(0).getRing());
					((TextView) v.findViewById(R.id.col2)).setText(rings.get(1).getRing());
					((TextView) v.findViewById(R.id.col3)).setText(rings.get(2).getRing());
					((TextView) v.findViewById(R.id.col4)).setText(rings.get(3).getRing());
					((TextView) v.findViewById(R.id.col5)).setText(rings.get(4).getRing());
					((TextView) v.findViewById(R.id.col6)).setText(rings.get(5).getRing());
					
					for(Ring r : rings) {
						if(r.getRing().equalsIgnoreCase("X")) {
							points +=10;
						} else if(r.getRing().equalsIgnoreCase("M")) {
							points += 0;
						} else {
							points += Integer.parseInt(r.getRing());
						}
					}
				}
				
				((TextView) v.findViewById(R.id.passe_sum)).setText(points.toString());
				
				Integer total = lastRound + points;
				((TextView) v.findViewById(R.id.sub_total)).setText(total.toString());
				
				lastRound = points;
				
				((LinearLayout) findViewById(R.id.list_details)).addView(v);
			} while( c.moveToNext() );
		}
		
		Integer sumX = 0;
		Integer sum10X = 0;
		Integer sumArrows = 0;
		
		sql = String.format("SELECT COUNT(ring) FROM %s WHERE workout_id = %s AND ring = 'X'", RingHandler.getInstance().getTableName(), workoutId.toString());
		c = db.rawQuery(sql, null);
		if( c.moveToFirst() ) {
			sumX += c.getInt(0);
			sum10X += c.getInt(0);
		}
		
		sql = String.format("SELECT COUNT(ring) FROM %s WHERE workout_id = %s AND ring = '10'", RingHandler.getInstance().getTableName(), workoutId.toString());
		c = db.rawQuery(sql, null);
		if( c.moveToFirst() ) {
			sum10X += c.getInt(0);
		}
		
		sql = String.format("SELECT COUNT(id) FROM %s WHERE workout_id = %s", RingHandler.getInstance().getTableName(), workoutId.toString());
		c = db.rawQuery(sql, null);
		if( c.moveToFirst() ) {
			sumArrows += c.getInt(0);
		}
		
		((TextView) findViewById(R.id.total_X)).setText(sumX.toString());
		((TextView) findViewById(R.id.total_10_X)).setText(sum10X.toString());
		((TextView) findViewById(R.id.total_arrows)).setText(sumArrows.toString());
		
		db.close();
	}
}

