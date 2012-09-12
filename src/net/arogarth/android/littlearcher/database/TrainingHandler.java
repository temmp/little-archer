package net.arogarth.android.littlearcher.database;

import java.sql.Timestamp;
import java.util.ArrayList;

import net.arogarth.android.littlearcher.database.models.Training;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TrainingHandler extends DatabaseHandler {

	private static String TABLE_NAME = "training";

	public TrainingHandler() {
		super(TABLE_NAME);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String ringCount = "CREATE TABLE "
				+ TABLE_NAME
				+ " ("
				+ "id INTEGER PRIMARY KEY, date TEXT, name TEXT, description TEXT)";

		db.execSQL(ringCount);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onCreate(db);
	}

	public void addTraining(Training training) {
		SQLiteDatabase db = getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("date", training.getDate().getTime());
		values.put("name", training.getName());
		values.put("description", training.getDescription());

		db.insert(TABLE_NAME, null, values);

		db.close();
	}

	public ArrayList<Training> loadList() {
		return this.loadList("");
	}
	
	public ArrayList<Training> loadList(String where) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(
        		TABLE_NAME,
        		new String[]{"id", "date", "name", "description"},
        		where,
        		null, null, null, null);
        
        ArrayList<Training> stack = new ArrayList<Training>();
        
        if (cursor != null) {
            cursor.moveToFirst();

	        do {
	        	Training row = new Training();
	        	
	        	row.setId(
	        			cursor.getInt(0));
	        	row.setDate(
	        			new Timestamp(cursor.getLong(1)));
	        	row.setName(
	        			cursor.getString(2));
	        	row.setDescription(
	        			cursor.getString(3));
	        	
	        	stack.add(row);
	        } while( cursor.moveToNext() );
        }
        
        return stack;
    }

}