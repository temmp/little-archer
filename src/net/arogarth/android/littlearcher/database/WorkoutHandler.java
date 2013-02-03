package net.arogarth.android.littlearcher.database;

import java.sql.Timestamp;
import java.util.ArrayList;

import net.arogarth.android.littlearcher.database.models.Workout;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class WorkoutHandler extends DatabaseHandler {

	private static String TABLE_NAME = "workout";

	public static WorkoutHandler getInstance() {
		return new WorkoutHandler();
	}
	
	private WorkoutHandler() {
		super(TABLE_NAME);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String ringCount = "CREATE TABLE " +
				TABLE_NAME +
				" (" +
				"id INTEGER PRIMARY KEY, " +
				"date TEXT, " +
				"name TEXT, " +
				"place TEXT, " +
				"description TEXT," +
				"count_x INTEGER," +
				"rings INTEGER," +
				"arrows INTEGER" +
				")";

		db.execSQL(ringCount);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for (int i = oldVersion; i <= newVersion; i++)
        {
			String sql = "";
            switch(i)
            {
            	case 2:
//		            sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN place TEXT";
//		            db.execSQL(sql);
		            break;
            	case 3:
            		sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN count_x INTEGER;";
    				db.execSQL(sql);
    				sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN rings INTEGER;";
    				db.execSQL(sql);
    				sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN arrows INTEGER;";
    				db.execSQL(sql);
            		break;
            }
        }
	}

	public Workout saveWorkout(Workout workout) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("date", workout.getDate().getTime());
		values.put("name", workout.getName());
		values.put("place", workout.getPlace());
		values.put("description", workout.getDescription());
		values.put("count_x", (workout.getCountX()?1:0));
		values.put("rings", workout.getRings());
		values.put("arrows", workout.getArrows());

		if( workout.getId() == null ) {
			long id = db.insert(TABLE_NAME, null, values);
			workout.setId(id);
		} else {
			db.update(TABLE_NAME, values, "id = ?", new String[] { workout.getId().toString() });
		}
		
		db.close();
		
		return workout;
	}

	public Workout load(Long id) {
		ArrayList<Workout> stack = this.loadList(String.format( "id = %s", id));
		
        Workout result = stack.get(0);
        
		return result;
	}
	
	public ArrayList<Workout> loadList() {
		return this.loadList("");
	}
	
	public ArrayList<Workout> loadList(String where) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(
        		TABLE_NAME,
        		new String[]{"id", "date", "name", "description", "place", "count_x", "rings", "arrows"},
        		where,
        		null, null, null, null);
        
        ArrayList<Workout> stack = new ArrayList<Workout>();
        
        if ( cursor.moveToFirst() ) {

	        do {
	        	Workout row = new Workout();
	        	
	        	row.setId(
	        			cursor.getLong(0));
	        	row.setDate(
	        			new Timestamp(cursor.getLong(1)));
	        	row.setName(
	        			cursor.getString(2));
	        	row.setDescription(
	        			cursor.getString(3));
	        	row.setPlace(
	        			cursor.getString(4));
	        	row.setCountX(
	        			cursor.getInt(5)!=0);
	        	row.setRings(
	        			cursor.getInt(6));
	        	row.setArrows(
	        			cursor.getInt(7));
	        	
	            if(row.getArrows() == 0) {
	            	Integer count = RingHandler.getInstance().getArrows(row.getId());
	            	
	            	row.setRings(10);
	            	row.setArrows(count);
	            	row.setCountX(true);
	            	
	            	WorkoutHandler.getInstance().saveWorkout(row);
	            }
	            
	        	stack.add(row);
	        } while( cursor.moveToNext() );
        }
        
        db.close();
        
        return stack;
    }

	public Workout loadLastWorkout() throws Exception {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.rawQuery(
        		String.format("SELECT MAX(id) AS max_id FROM %s", TABLE_NAME), null);
        
        Workout result = null;
        
        if( cursor.moveToFirst() ) {
        	Integer id = cursor.getInt(0);
        
        	if( id != 0 ) {
	        	ArrayList<Workout> stack = this.loadList(String.format( "id = %s", id));
	        
	        	result = stack.get(0);
        	} else {
        		throw new Exception("Cannot find last workout");
        	}
        }
        
        db.close();
        
        return result;
    }
	
	public void removeWorkout(Long workoutId) {
		RingHandler.getInstance().delete("workout_id = ?", new String[]{ workoutId.toString() });
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, "id = ?", new String[]{ workoutId.toString() });
		db.close();
	}
}