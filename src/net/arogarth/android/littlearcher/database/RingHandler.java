package net.arogarth.android.littlearcher.database;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;

import net.arogarth.android.littlearcher.database.models.Ring;
import net.arogarth.android.littlearcher.database.models.RingCount;
import net.arogarth.android.littlearcher.database.models.Workout;

import android.text.format.DateFormat;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public RingCount getSum(Long workoutId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = null;
    	String sql = "";
    	RingCount ring = new RingCount();
    	
		sql = String.format(
			"SELECT " +
			"ring AS ring, " +
			"COUNT(ring) AS sum " +
			"FROM %s " + 
			"WHERE workout_id = %s " +
			"GROUP BY ring " +
			"ORDER BY ring ", TABLE_NAME, workoutId.toString());
	
		c = db.rawQuery(sql, null);
		
		if( c.moveToFirst() ) {
			try {
    			RingCount rc = new RingCount();
    			
    			String ringNumber = c.getString(0);
    			Integer count = c.getInt(1);
    			
    			Class<?> clazz = rc.getClass();
    			
    			String methodName = "";
    			if( ringNumber.equalsIgnoreCase("M") || ringNumber.equalsIgnoreCase("X") )
    				methodName = ringNumber;
    			else
    				methodName = "Ring" + ringNumber;
    			
    			Method method = clazz.getMethod("set" + methodName, Integer.class);
				method.invoke(ring, count);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    		
    	db.close();
    	
    	return ring;
	}
    
    public void delete(String where, String[] args) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_NAME, where, args);
    	db.close();
    }
    
    public ArrayList<RingCount> getRingCounts(Long workoutId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor c = null;
    	String sql = "";
    	Integer maxPasse = 0;
    	ArrayList<RingCount> rings = new ArrayList<RingCount>();
    	
    	sql = String.format("SELECT MAX(passe) FROM %s WHERE workout_id = %s", TABLE_NAME, workoutId.toString());
    	
    	c = db.rawQuery(sql, null);
    	
    	if( c.moveToFirst() )
    		maxPasse = c.getInt(0);
    	
    	for(Integer i=1; i <= maxPasse; i++) {
    		sql = String.format(
    			"SELECT " +
    			"ring AS ring, " +
    			"COUNT(ring) AS sum " +
				"FROM %s " +
				"WHERE workout_id = %s " +
				"AND passe = %s " +
				"GROUP BY ring " +
				"ORDER BY ring ", TABLE_NAME, workoutId.toString(), i.toString());
    	
    		c = db.rawQuery(sql, null);
    		
    		if( c.moveToFirst() ) {
	    			try {
		    			RingCount rc = new RingCount();
		    			
		    			String ringNumber = c.getString(0);
		    			Integer count = c.getInt(1);
		    			
		    			Class<?> clazz = rc.getClass();
		    			
		    			String methodName = "";
		    			if( ringNumber.equalsIgnoreCase("M") || ringNumber.equalsIgnoreCase("X") )
		    				methodName = ringNumber;
		    			else
		    				methodName = "Ring" + ringNumber;
		    			
		    			Method method = clazz.getMethod("set" + methodName, Integer.class);
	    				method.invoke(rc, count);
	    				
	    				rc.setRound(i);
	    				
	    				rings.add(rc);
	    			} catch (Exception e) {
	    				e.printStackTrace();
	    			}
    		}
    		
    	}
    	db.close();
    	
    	return rings;
    }
}