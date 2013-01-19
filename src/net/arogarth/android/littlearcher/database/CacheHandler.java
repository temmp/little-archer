package net.arogarth.android.littlearcher.database;

import java.lang.reflect.Method;
import java.util.ArrayList;

import net.arogarth.android.littlearcher.database.models.Ring;
import net.arogarth.android.littlearcher.database.models.RingCount;
import net.arogarth.android.littlearcher.database.models.Workout;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CacheHandler extends DatabaseHandler {
 
	public static CacheHandler getInstance() {
		return new CacheHandler();
	}
	
	private static String TABLE_NAME = "cache";
	
    private CacheHandler() {
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
                "key TEXT," +
                "value TEXT)";
        
        db.execSQL(ringCount);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    }
    
    /**
     * 
     * @param key
     * @return
     */
    public Long exist(String key) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	Cursor result = db.query(
    			TABLE_NAME,
    			new String[] { "id" },
    			"key = ?",
    			new String[] { key },
    			null, null, null);
    	
    	Long id = null;
    	if(result.moveToFirst()) {
    		id = result.getLong(0);
    	}
    	
    	db.close();
    	return id;
    }
    
    public void delete(String key) {
    	this.delete("key = ?", new String[] { key });
    }
    
    private void delete(String where, String[] args) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.delete(TABLE_NAME, where, args);
    	db.close();
    }
    
    public void setValue(String key, String value) {
    	SQLiteDatabase db = getWritableDatabase();
    	
    	ContentValues values = new ContentValues();
    	values.put("key", key);
    	values.put("value", value);
    	
    	Long id = this.exist(key);
    	if( id == null ) {
    		db.insert(TABLE_NAME, null, values);
    	} else {
    		db.update(TABLE_NAME, values, "id = ?", new String[] { id.toString() });
    	}
    	
    	db.close();
    }
    
    public String getValue(String key) {
    	SQLiteDatabase db = this.getWritableDatabase();
    
    	Cursor cursor = db.rawQuery(
    			String.format( "SELECT value FROM %s WHERE key = '%s'", TABLE_NAME, key), null );
    	
    	String value = null;
    	
    	if( cursor.moveToFirst() ) {
    		value = cursor.getString(0);
    	}
    	
    	db.close();
    	
    	return value;
    }
}