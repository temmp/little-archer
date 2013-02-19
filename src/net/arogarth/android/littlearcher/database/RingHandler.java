package net.arogarth.android.littlearcher.database;

import java.util.ArrayList;
import java.util.Collections;

import net.arogarth.android.littlearcher.database.models.Ring;
import net.arogarth.android.littlearcher.database.models.Workout;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RingHandler extends DatabaseHandler {
 
	public static RingHandler getInstance() {
		return new RingHandler();
	}
	
	private static String TABLE_NAME = "rings";
	
    private RingHandler() {
        super(TABLE_NAME);
    }
 
    public String getTableName() {
    	return TABLE_NAME;
    }
    
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String ringCount = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY," +
                "workout_id INTEGER," +
                "passe INTEGER," +
                "ring TEXT)";
        
        db.execSQL(ringCount);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    }
    
    public void addRing(Ring ring) {
    	SQLiteDatabase db = getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put("workout_id", ring.getWorkoutId());
    	values.put("passe", ring.getPasse());
    	values.put("ring", ring.getRing());

    	db.insert(TABLE_NAME, null, values);
    	
    	db.close();
    }
    
    public Integer getNextRound(Workout workout) {
    	return this.getNextRound(workout.getId());
    }
    
    public Integer getNextRound(Long workoutId) {
    	Integer round = 0;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.rawQuery(
    			String.format( "SELECT MAX(passe) FROM %s WHERE workout_id = %s", TABLE_NAME, workoutId.toString()), null );
    	
    	if( cursor.moveToFirst() ) {
    		round = cursor.getInt(0);
    		round++;
    	}
    	
    	db.close();
    	
    	return round;
    }
    
    public Integer countArrows(Long workoutId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.rawQuery(
				String.format("SELECT count(id) FROM %s WHERE workout_id = %s", TABLE_NAME, workoutId.toString()), null);
    	
    	Integer count = 0;
    	if( c.moveToFirst() )
    		count = c.getInt(0);
    	
    	db.close();
    	
    	return count;
    }
    
    public Integer countRings(Long workoutId) {
    	Integer sumRings = 0;
    	
    	ArrayList<Ring> rings = this.loadRings(workoutId);
    	for(Ring r : rings) {
    		if(r.getRing().equalsIgnoreCase("M")) {
    			sumRings += 0;
    		} else if(r.getRing().equalsIgnoreCase("X")) {
    			sumRings += 10;
    		} else {
    			try {
    				sumRings += Integer.valueOf(r.getRing());
    			} catch (Exception e) { }
    		}
    	}
    	
    	return sumRings;
    }
    
    public void delete(String where, String[] args) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_NAME, where, args);
    	db.close();
    }
    
    public ArrayList<Ring> loadRings(Long workoutId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	ArrayList<Ring> rings = new ArrayList<Ring>();
		
		String sql = String.format(
			"SELECT " +
			"id, " +
			"workout_id, " +
			"passe, " +
			"ring " +
			"FROM %s " +
			"WHERE workout_id = %s " +
			"AND ring IN ('X', '10', '9', '8', '7', '6', '5', '4', '3', '2', '1', 'M') ",
			TABLE_NAME, workoutId.toString());
	
		Cursor c = db.rawQuery(sql, null);
		
    	if( c.moveToFirst() ) {
    		do {
    			Ring r = new Ring();
    			r.setId(c.getLong(0));
    			r.setWorkoutId(c.getLong(1));
    			r.setPasse(c.getInt(2));
    			r.setRing(c.getString(3));
    			rings.add(r);
    		} while( c.moveToNext() );
    	}
    	db.close();
    	
    	return rings;
    }
    public ArrayList<Ring> loadRings(Long workoutId, Integer passe) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	ArrayList<Ring> rings = new ArrayList<Ring>();
    	
    	String sql = String.format(
    			"SELECT " +
				"id, " +
				"workout_id, " +
				"passe, " +
				"ring " +
				"FROM %s " +
				"WHERE workout_id = %s " +
				"AND passe = %s",
				TABLE_NAME, workoutId.toString(), passe.toString());
    	
    	Cursor c = db.rawQuery(sql, null);
    	
    	if( c.moveToFirst() ) {
    		do {
    			Ring r = new Ring();
    			r.setId(c.getLong(0));
    			r.setWorkoutId(c.getLong(1));
    			r.setPasse(c.getInt(2));
    			r.setRing(c.getString(3));
    			rings.add(r);
    		} while( c.moveToNext() );
    	}
    	
    	db.close();
    	
    	Collections.sort(rings);
    	
    	return rings;
    }
    
    public Integer getArrows(Long workoutId) {
    	Integer count = 6;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = db.query(
    			TABLE_NAME,
    			new String[] {"COUNT(id)"},
    			"workout_id =?",
    			new String[] {workoutId.toString()},
    			"passe",
    			null,
    			null);
    	
    	if(c.moveToFirst()) {
    		count = c.getInt(0);
    		
    		if(count != 3 && count != 6) {
    			count = 6;
    		}
    	}
    	
    	return count;
    }
}