package net.arogarth.android.littlearcher.activities;

import net.arogarth.android.littlearcher.R;
import net.arogarth.android.littlearcher.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);

    }
	
	public void startPasse(View row) {
		Intent passe = new Intent(this, RingCountActivity.class);
		startActivity(passe);
	}
	
	public void overview(View row) {
		Intent passe = new Intent(this, OverviewActivity.class);
		startActivity(passe);
	}
}
