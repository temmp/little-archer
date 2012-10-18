package net.arogarth.android.littlearcher.database;

import java.util.ArrayList;

import net.arogarth.android.littlearcher.database.models.RingCount;
import net.arogarth.android.littlearcher.database.models.Workout;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RingCountHandler extends DatabaseHandler {
 
	public static RingCountHandler getInstance() {
		return new RingCountHandler();
	}
	
	private static String TABLE_NAME = "ring_count";
	
    private RingCountHandler() {
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
                "workout_id INTEGER, round INTEGER," +
                "ring1 INTEGER, ring2 INTEGER, ring3 INTEGER," +
                "ring4 INTEGER, ring5 INTEGER, ring6 INTEGER," +
                "ring7 INTEGER, ring8 INTEGER, ring9 INTEGER," +
                "ring10 INTEGER, X INTEGER, M INTEGER)";
        
        db.execSQL(ringCount);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    }
    
    public void addRingCount(RingCount rings) {
    	SQLiteDatabase db = getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put("workout_id", rings.getWorkoutId());
    	values.put("round", rings.getRound());
    	values.put("ring1", rings.getRing1());
    	values.put("ring2", rings.getRing2());
    	values.put("ring3", rings.getRing3());
    	values.put("ring4", rings.getRing4());
    	values.put("ring5", rings.getRing5());
    	values.put("ring6", rings.getRing6());
    	values.put("ring7", rings.getRing7());
    	values.put("ring8", rings.getRing8());
    	values.put("ring9", rings.getRing9());
    	values.put("ring10", rings.getRing10());
    	values.put("X", rings.getX());
    	values.put("M", rings.getM());
    	
    	db.insert(TABLE_NAME, null, values);
    	db.close();
    }
    
    public ArrayList<RingCount> loadResults() {
    	return this.loadResults("");
    }
    
    public ArrayList<RingCount> loadResults(String where) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(
        		TABLE_NAME,
        		new String[]{"id", "workout_id", "round",
        				"ring1", "ring2", "ring3", "ring4", "ring5",
        				"ring6", "ring7", "ring8", "ring9", "ring10", "X", "M"},
        		where,
        		null, null, null, null);
        
        ArrayList<RingCount> stack = new ArrayList<RingCount>();
        
        if ( cursor.moveToFirst() ) {

	        do {
	        	RingCount row = new RingCount();
	        	row.setId(cursor.getLong(0));
	        	row.setRound(cursor.getInt(2));
	        	row.setRing1(cursor.getInt(3));
	        	row.setRing2(cursor.getInt(4));
	        	row.setRing3(cursor.getInt(5));
	        	row.setRing4(cursor.getInt(6));
	        	row.setRing5(cursor.getInt(7));
	        	row.setRing6(cursor.getInt(8));
	        	row.setRing7(cursor.getInt(9));
	        	row.setRing8(cursor.getInt(10));
	        	row.setRing9(cursor.getInt(11));
	        	row.setRing10(cursor.getInt(12));
	        	row.setX(cursor.getInt(13));
	        	row.setM(cursor.getInt(14));
	        	
	        	stack.add(row);
	        } while( cursor.moveToNext() );
        
        }
        
        db.close();
        
        return stack;
    }
    
    public Integer getNextRound(Workout workout) {
    	return this.getNextRound(workout.getId());
    }
    
    public Integer getNextRound(Long workoutId) {
    	Integer round = 0;
    	
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	Cursor cursor = db.rawQuery(
    			String.format( "SELECT MAX(round) FROM %s WHERE workout_id = %s", TABLE_NAME, workoutId.toString()), null );
    	
    	if( cursor.moveToFirst() ) {
    		round = cursor.getInt(0);
    		round++;
    	}
    	
    	db.close();
    	
    	return round;
    }
    
    public RingCount getSum(Long workoutId) {
		SQLiteDatabase db = this.getReadableDatabase();
		
        String sql = String.format("SELECT " +
        		"SUM(ring1) AS sum_ring1, " +
        		"SUM(ring2) AS sum_ring2, " +
        		"SUM(ring3) AS sum_ring3, " +
        		"SUM(ring4) AS sum_ring4, " +
        		"SUM(ring5) AS sum_ring5, " +
        		"SUM(ring6) AS sum_ring6, " +
        		"SUM(ring7) AS sum_ring7, " +
        		"SUM(ring8) AS sum_ring8, " +
        		"SUM(ring9) AS sum_ring9, " +
        		"SUM(ring10) AS sum_ring10, " +
        		"SUM(X) AS sum_X, " +
        		"SUM(M) AS sum_M " +
        		"FROM %s " +
        		"WHERE workout_id = %s", TABLE_NAME, workoutId);
        
        Cursor cursor = db.rawQuery(sql, null);
        
        RingCount row = new RingCount();
        
        if ( cursor.moveToFirst() ) {
//        	row.setId(cursor.getLong(0));
//        	row.setRound(cursor.getInt(2));
        	row.setRing1(cursor.getInt(0));
        	row.setRing2(cursor.getInt(1));
        	row.setRing3(cursor.getInt(2));
        	row.setRing4(cursor.getInt(3));
        	row.setRing5(cursor.getInt(4));
        	row.setRing6(cursor.getInt(5));
        	row.setRing7(cursor.getInt(6));
        	row.setRing8(cursor.getInt(7));
        	row.setRing9(cursor.getInt(8));
        	row.setRing10(cursor.getInt(9));
        	row.setX(cursor.getInt(10));
        	row.setM(cursor.getInt(11));
        }
        
        db.close();
        
        return row;
	}
    
    public void delete(String where, String[] args) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_NAME, where, args);
    	db.close();
    }
}