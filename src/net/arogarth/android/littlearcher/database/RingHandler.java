package net.arogarth.android.littlearcher.database;

import java.lang.reflect.Method;
import java.util.ArrayList;

import net.arogarth.android.littlearcher.database.models.Ring;
import net.arogarth.android.littlearcher.database.models.RingCount;
import net.arogarth.android.littlearcher.database.models.Workout;

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
				"AND passe = %s " +
				"AND ring IN ('X', '10', '9', '8', '7', '6', '5', '4', '3', '2', '1', 'M') ",
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
    	return rings;
    }
    
    public ArrayList<RingCount> getRingCounts(Long workoutId) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	String sql = "";
    	ArrayList<RingCount> rings = new ArrayList<RingCount>();
    	
		sql = String.format(
				"SELECT passe FROM %s WHERE workout_id = %s ORDER BY passe",
				RingHandler.getInstance().getTableName(),
				workoutId.toString()
				);
		
		Cursor passes = db.rawQuery(sql, null);
		
		if( passes.moveToFirst() ) {

			do {
				Integer passe = passes.getInt(0);
				
	    		sql = String.format(
	    			"SELECT " +
	    			"ring AS ring, " +
	    			"COUNT(ring) AS sum " +
					"FROM %s " +
					"WHERE workout_id = %s " +
					"AND passe = %s " +
					"GROUP BY ring " +
					"ORDER BY ring ", TABLE_NAME, workoutId.toString(), passe);
	    	
	    		Cursor c = db.rawQuery(sql, null);
	    		
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
		    				
		    				rc.setRound(passe);
		    				
		    				rings.add(rc);
		    			} catch (Exception e) {
		    				e.printStackTrace();
		    			}
	    		}
		    		
		    	} while( passes.moveToNext() );
		}
		
    	db.close();
    	
    	return rings;
    }
}