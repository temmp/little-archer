package net.arogarth.android.littlearcher.activities.workout;

import java.util.ArrayList;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.database.RingHandler;
import net.arogarth.android.littlearcher.database.models.Ring;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailsActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Long workoutId = getIntent().getLongExtra("workout_id", 0);
        
        setContentView(R.layout.workout_details);

        this.buildList(workoutId);
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
			Integer total = 0;
			
			do {
				Integer passe = c.getInt(0);
				ArrayList<Ring> rings = new ArrayList<Ring>();
				
				View v = li.inflate(R.layout.workout_details_list_item, null);
				
				Integer points = 0;
				Integer row = 0;
				
				rings.addAll(
						RingHandler.getInstance().loadRings(workoutId, passe));
				
				if( rings.size() == 3 ) {
					if( c.moveToNext() ) {
						passe = c.getInt(0);
						rings.addAll(
								RingHandler.getInstance().loadRings(workoutId, passe));
					}
				}

				try {
					((TextView) v.findViewById(R.id.col1)).setText(rings.get(0).getRing());
					((TextView) v.findViewById(R.id.col2)).setText(rings.get(1).getRing());
					((TextView) v.findViewById(R.id.col3)).setText(rings.get(2).getRing());
					((TextView) v.findViewById(R.id.col4)).setText(rings.get(3).getRing());
					((TextView) v.findViewById(R.id.col5)).setText(rings.get(4).getRing());
					((TextView) v.findViewById(R.id.col6)).setText(rings.get(5).getRing());
				} catch(Exception e) { }
				   
				row = 0;
				for(int i=0; i < 6 && i < rings.size(); i++) {
					Ring r = rings.get(i);
				
					if(r.getRing().equalsIgnoreCase("X")) {
						row +=10;
					} else if(r.getRing().equalsIgnoreCase("M")) {
						row += 0;
					} else {
						row += Integer.parseInt(r.getRing());
					}
					
					if(i == 2 ) {
						((TextView) v.findViewById(R.id.total_row_1)).setText(row.toString());
						points += row;
						row = 0;
					} else if( i == 5 ) {
						((TextView) v.findViewById(R.id.total_row_2)).setText(row.toString());
						points += row;
						row = 0;
					}
				}
				
				((TextView) v.findViewById(R.id.passe_sum)).setText(points.toString());
				
				total += points;
				((TextView) v.findViewById(R.id.sub_total)).setText(total.toString());
				
				
				((LinearLayout) findViewById(R.id.list_details)).addView(v);
			} while( c.moveToNext() );
		}
		
		Integer sumM = 0;
		Integer sumX = 0;
		Integer sum10X = 0;
		Integer sumArrows = 0;
		
		sql = String.format("SELECT COUNT(ring) FROM %s WHERE workout_id = %s AND ring = 'M'", RingHandler.getInstance().getTableName(), workoutId.toString());
		c = db.rawQuery(sql, null);
		if( c.moveToFirst() ) {
			sumM += c.getInt(0);
		}
		
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
		
		((TextView) findViewById(R.id.total_M)).setText(sumM.toString());
		((TextView) findViewById(R.id.total_X)).setText(sumX.toString());
		((TextView) findViewById(R.id.total_10_X)).setText(sum10X.toString());
		((TextView) findViewById(R.id.total_arrows)).setText(sumArrows.toString());
		
		db.close();
	}
}

