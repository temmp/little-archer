package net.arogarth.android.littlearcher.activities;

import java.util.ArrayList;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.R.id;
import net.arogarth.android.littlearcher.R.layout;
import net.arogarth.android.littlearcher.database.RingCountHandler;
import net.arogarth.android.littlearcher.database.models.RingCount;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class OverviewActivity extends Activity {
	
	private class OverviewAdapter extends BaseAdapter {

		private final LayoutInflater mInflater;
		private ArrayList<RingCount> rings = new ArrayList<RingCount>();
		
		public OverviewAdapter() {
			mInflater = (LayoutInflater) OverviewActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			rings = new RingCountHandler().loadResults();
		}
		
		@Override
		public int getCount() {
			return rings.size();
		}

		@Override
		public RingCount getItem(int position) {
			return rings.get(position);
		}

		@Override
		public long getItemId(int position) {
			return rings.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			RingCount rc = getItem(position);
			
			View v = mInflater.inflate(R.layout.overview_row, parent, false);
			((TextView) v.findViewById(R.id.round)).setText(rc.getRound().toString());
			((TextView) v.findViewById(R.id.ring1)).setText(rc.getRing1().toString());
			((TextView) v.findViewById(R.id.ring2)).setText(rc.getRing2().toString());
			((TextView) v.findViewById(R.id.ring3)).setText(rc.getRing3().toString());
			((TextView) v.findViewById(R.id.ring4)).setText(rc.getRing4().toString());
			((TextView) v.findViewById(R.id.ring5)).setText(rc.getRing5().toString());
			((TextView) v.findViewById(R.id.ring6)).setText(rc.getRing6().toString());
			((TextView) v.findViewById(R.id.ring7)).setText(rc.getRing7().toString());
			((TextView) v.findViewById(R.id.ring8)).setText(rc.getRing8().toString());
			((TextView) v.findViewById(R.id.ring9)).setText(rc.getRing9().toString());
			((TextView) v.findViewById(R.id.ring10)).setText(rc.getRing10().toString());
			
			return v;
		}
		
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.overview);

        ((ListView) findViewById(R.id.overview)).setAdapter(new OverviewAdapter());
    }
	
	public void startPasse(View row) {
		Intent passe = new Intent(this, RingCountActivity.class);
		startActivity(passe);
	}
}
