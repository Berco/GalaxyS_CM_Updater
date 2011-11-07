package in.deaap.genomen;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

public class Splash extends Activity{

	MediaPlayer ourSound;
	String nightlyPath;
	String gappsPath;
	String packPath;
	String fuguPath;
	String kangPath;
	String local_storage_root;
	
	@Override
	protected void onCreate(Bundle ZomaarEenNaam) {
		// TODO Auto-generated method stub
		super.onCreate(ZomaarEenNaam);
		new FindZips().execute();
		setContentView(R.layout.splash);
		ourSound = MediaPlayer.create(Splash.this, R.raw.splashsound);
		ourSound.start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	private class FindZips extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			local_storage_root = "/FlashPack/";
			local_storage_root = Environment.getExternalStorageDirectory().toString()+local_storage_root;
			InputStream is= null;
	        OutputStream os = null;
	        try {
	        is = getResources().getAssets().open("files/dpi_cleaner_V1g.zip");
	        os = new FileOutputStream(local_storage_root+"dpi_cleaner_V1g.zip");
	        IOUtils.copy(is, os);
	        is.close();
	        os.flush();
	        os.close();
	        os = null;
	        } catch (IOException e) { }
			
	        if(ShellInterface.isSuAvailable()){ 	
				// latest nightly	
					String findNightlyNumber = "find /sdcard/ -name \"cm_galaxysmtd_full-*.zip\" | awk -F\"_\" '{ print  $(NF-1) $NF }' | tr -d '[:alpha:] [:punct:]' | awk 'NR == 1 {m=$1} $1 >= m {m = $1} END { print m }'";
					String nightlyNumber = ShellInterface.getProcessOutput(findNightlyNumber);
					String findNightly = "find /sdcard/ -name \"cm_galaxysmtd_full-" +nightlyNumber.trim()+ ".zip\"";
					nightlyPath = ShellInterface.getProcessOutput(findNightly);
								
				// latest gapps
					String findGappsNumber = "find /sdcard/ -name \"gapps-gb-????????-signed.zip\" | awk -F\"_\" '{ print  $(NF-1) $NF }' | tr -d '[:alpha:] [:punct:]' | awk 'NR == 1 {m=$1} $1 >= m {m = $1} END { print m }'";
					String gappsNumber = ShellInterface.getProcessOutput(findGappsNumber);
					// needs fix for dubble finds. thing to do
					gappsNumber = "20110613";
					String findGapps = "find /sdcard/ -name \"gapps-gb-" +gappsNumber.trim()+ "-signed.zip\"";
					gappsPath = ShellInterface.getProcessOutput(findGapps);
												
				//insert own flashable zip
					String findPack = "find /sdcard/ -name \"dpi_cleaner_V1g.zip\"";
					packPath = ShellInterface.getProcessOutput(findPack);
					
				//search for KANG
					String findKang = "find /sdcard/ -name \"update-cm-7.1.0-GalaxyS-KANG-signed.zip\"";
					kangPath = ShellInterface.getProcessOutput(findKang);
																
				//# find last known fugumod kernel
					String findFuguNumber = "find /sdcard/ -name \"*FuguMod*.zip\" | awk -F\"_\" '{ print  $(NF-1) $NF }' | tr -d '[:alpha:] [:punct:]' | awk 'NR == 1 {m=$1} $1 >= m {m = $1} END { print m }'";
					String fuguNumber = ShellInterface.getProcessOutput(findFuguNumber);
					String findFugu = "find /sdcard/ -name \"*FuguMod*r" +fuguNumber.trim()+ "*.zip\"";
					fuguPath = ShellInterface.getProcessOutput(findFugu);
				} 
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Bundle bundle = new Bundle();
			bundle.putString("fugu", fuguPath);
			bundle.putString("nightly", nightlyPath);
			bundle.putString("gapps", gappsPath);
			bundle.putString("pack", packPath);
			bundle.putString("kang", kangPath);
			
			Intent openOptionChooser = new Intent ("in.deaap.genomen.OPTIONCHOOSER");
			openOptionChooser.putExtras(bundle);
			startActivity(openOptionChooser);		
		}
	
	}
	
}
