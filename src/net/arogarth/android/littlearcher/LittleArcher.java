package net.arogarth.android.littlearcher;

import java.util.Calendar;

import net.arogarth.android.littlearcher.database.RingHandler;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import android.app.Application;
import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

public class LittleArcher extends Application {

	private static Application instance = null;
			
	@Override
	public void onCreate() {
		super.onCreate();
		
		instance = this;
		
		Log.d("LA", "Create database structure");
		WorkoutHandler.getInstance();
		RingHandler.getInstance();
	}

	public static Context getContext() {
		return instance.getApplicationContext();
	}
}
