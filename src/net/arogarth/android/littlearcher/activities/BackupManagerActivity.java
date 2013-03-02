package net.arogarth.android.littlearcher.activities;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;
import java.util.Date;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.WorkoutManager;
import net.arogarth.android.littlearcher.activities.workout.ListActivity;
import net.arogarth.android.littlearcher.activities.workout.PropertiesActivity;
import net.arogarth.android.littlearcher.database.WorkoutHandler;
import net.arogarth.android.littlearcher.database.helper.DatabaseBackupHelper;
import net.arogarth.android.littlearcher.database.models.Workout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.method.DateTimeKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class BackupManagerActivity extends Activity {

	private class ListBackupAdapter extends BaseAdapter implements
			OnItemLongClickListener {

		private Context context = null;
		private ArrayList<File> files = new ArrayList<File>();;
		private ListView list;
		
		public ListBackupAdapter(ListView list) {
			list.setOnItemLongClickListener(this);
			
			this.list = list;
			this.context = list.getContext();
			
			this.notifyDataSetChanged();
		}
		
		@Override
		public void notifyDataSetChanged() {
			this.files = new DatabaseBackupHelper(this.context).listBackups();
			
			super.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return this.files.size();
		}

		@Override
		public File getItem(int arg0) {
			return this.files.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LayoutInflater mInflater = (LayoutInflater) BackupManagerActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			File f = this.getItem(arg0);
			View v = mInflater.inflate(R.layout.backup_list_item, arg2, false);

			try {
				String date = f.getName().toLowerCase().replace(".zip", "");
				Date d = new SimpleDateFormat("yyyyMMddkkmmss").parse(date);
				date = java.text.DateFormat.getDateTimeInstance().format(d);
				
				((TextView) v.findViewById(R.id.date)).setText(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			((TextView) v.findViewById(R.id.backup_filename)).setText(f.getName());

			return v;
		}

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				final int arg2, long arg3) {


			String title = getResources().getString(R.string.select);
			String[] items = getResources().getStringArray(R.array.backup_list_properties);

			AlertDialog.Builder builder = new AlertDialog.Builder(
					ListBackupAdapter.this.context);
			builder.setTitle(title);

			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialogInterface, int item) {
					int i = 0;

					if (item == i++) {
						try {
							File f = ListBackupAdapter.this.getItem(arg2);
							
							DatabaseBackupHelper dbh = new DatabaseBackupHelper(ListBackupAdapter.this.context);
							dbh.restoreBackup(f);
							
							Toast.makeText(getApplicationContext(),
									"Database restore successfull!",
									Toast.LENGTH_SHORT).show();
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(),
									"Database restore failed...!!",
									Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}
					} else if (item == i++) {
						File f = ListBackupAdapter.this.getItem(arg2);
						if(f.delete()) {
							Toast.makeText(getApplicationContext(),
									"File deleted!",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(),
									"Cannot delete file...!!",
									Toast.LENGTH_LONG).show();
						}

						notifyDataSetChanged();
					}

					return;
				}
			});
			builder.create().show();

			return true;
		}
	}

	private ListBackupAdapter adapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.backup_list_backups);

		ListView lv = (ListView) findViewById(R.id.backups); 
		this.adapter = new ListBackupAdapter(lv); 
		lv.setAdapter(this.adapter);
	}
	
	public void backup(View view) {
		try {
		    DatabaseBackupHelper dbh = new DatabaseBackupHelper(getApplicationContext());
		    dbh.createBackup();
		    
		    this.adapter.notifyDataSetChanged();
		    
		    Toast.makeText(getApplicationContext(), "Database backup successfull!", Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Database backup failed...!!", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
}
