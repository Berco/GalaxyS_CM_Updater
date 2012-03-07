package in.deaap.genomen.assist;

import in.deaap.genomen.R;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class Prefs extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
//		Preference myPref = (Preference) findPreference("changeSearch");
//		myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
//		             public boolean onPreferenceClick(Preference preference) {
//		                 //open browser or intent here
//		            	 Toast.makeText(getApplicationContext(), "gedrukt", Toast.LENGTH_SHORT).show();
//		            	 Intent i = new Intent("in.deaap.genomen.assist.ABOUTUS");
//		     			 startActivity(i);
//		            	 return false;
//		            	 
//		             }
//		         });

	}

}
