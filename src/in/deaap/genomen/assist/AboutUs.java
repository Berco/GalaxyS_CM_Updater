package in.deaap.genomen.assist;

import in.deaap.genomen.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUs extends Activity{
	
	TextView tekst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		tekst = (TextView) findViewById(R.id.tvAbout);
		
		String about = new String(
				"  This app is to ease the flashing" + '\n' +
				"  of ICS made by TeamHacksung" + '\n' + '\n' +
				"  It is not perfect, sometimes" + '\n' +
				"  your found files are not correct," + '\n' +
				"  their path or the file itself" + '\n'
				);
		tekst.setText(about);
	}
}
