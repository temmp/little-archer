package net.arogarth.android.littlearcher.activities.settings;

import net.arogarth.android.littlearcher.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MainSettingsActivity extends PreferenceActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.main);
    }
}