package net.arogarth.android.littlearcher.database;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import net.arogarth.android.littlearcher.database.models.RingCount;
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
				"description TEXT" +
				")";

		db.execSQL(ringCount);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		onCreate(db);
	}

	public Workout saveWorkout(Workout workout) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("date", workout.getDate().getTime());
		values.put("name", workout.getName());
		values.put("description", workout.getDescription());

		long id = db.insert(TABLE_NAME, null, values);

		workout.setId(id);
		
		db.close();
		
		return workout;
	}

	public ArrayList<Workout> loadList() {
		return this.loadList("");
	}
	
	public ArrayList<Workout> loadList(String where) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(
        		TABLE_NAME,
        		new String[]{"id", "date", "name", "description"},
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
	        	
	        	stack.add(row);
	        } while( cursor.moveToNext() );
        }
        
        db.close();
        
        return stack;
    }

	public Workout loadLastWorkout() {
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
        		result = new Workout();
        		result.setName(new Date().toLocaleString() );
        		result.setDate(new Timestamp(new Date().getTime()));
        		result.setName("");
        		
        		result = this.saveWorkout(result);
        	}
        	
        }
        
        db.close();
        
        return result;
    }
	
	public void removeWorkout(Long workoutId) {
		RingCountHandler.getInstance().delete("workout_id = ?", new String[]{ workoutId.toString() });
		
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, "id = ?", new String[]{ workoutId.toString() });
		db.close();
	}
}