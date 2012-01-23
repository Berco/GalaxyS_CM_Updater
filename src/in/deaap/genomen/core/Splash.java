package in.deaap.genomen.core;

import in.deaap.genomen.assist.ShellInterface;

import in.deaap.genomen.filehandler.Flashable;
import in.deaap.genomen.filehandler.SearchRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.widget.Toast;

@SuppressWarnings("unused")
public class Splash extends Activity{

	MediaPlayer ourSound;
		
	@Override
	protected void onCreate(Bundle ZomaarEenNaam) {
		super.onCreate(ZomaarEenNaam);
		// an attempt to use powermanager, not satisfying. app needs to be in /system
		//checkPosition();
		new FindZips().execute();
		setContentView(R.layout.splash);
		ourSound = MediaPlayer.create(Splash.this, R.raw.splashsound);
		
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		boolean music = getPrefs.getBoolean("checkboxMusic", false);
		if (music == true)
		ourSound.start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
	
//	protected void checkPosition(){
//		File f = new File("/system/app/in.deaap.genomen.core-1.apk");
//		if (!f.exists()){
//			Toast.makeText(getApplicationContext(), "'Not a system app yet, start again", Toast.LENGTH_SHORT).show();
//			try {
//				if(ShellInterface.isSuAvailable()){
//				String command = "mount -o rw,remount -t yaffs2 /dev/block/mtdblock2 /system";
//				ShellInterface.runCommand(command);
//				command = "cp /data/app/in.deaap.genomen.core-1.apk /system/app/in.deaap.genomen.core-1.apk";
//				ShellInterface.runCommand(command);
//				command = "rm /data/app/in.deaap.genomen.core-1.apk";
//				ShellInterface.runCommand(command);
//				
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			Toast.makeText(getApplicationContext(), "Yes, we are in /sytem", Toast.LENGTH_SHORT).show();
//		}
//		
//	}
	
	private class FindZips extends AsyncTask<Void, Void, Void> {
		
	private List<Flashable> fls;
	String local_storage_root;
	String data_storage_root;

	@Override
	protected Void doInBackground(Void... arg0) {
				local_storage_root = "/FlashPack/";
				local_storage_root = Environment.getExternalStorageDirectory().toString()+local_storage_root;
				data_storage_root = "/data/in.deaap.genomen.core/";
				data_storage_root = Environment.getDataDirectory().toString()+data_storage_root;
				InputStream is= null;
				OutputStream os = null;
				
				File f = new File(data_storage_root+"totalscript.sh");
				if (!f.exists() || (f.exists())){
		        try {
		        is = getResources().getAssets().open("scripts/totalscript.sh");
		        os = new FileOutputStream(data_storage_root+"totalscript.sh");
		        IOUtils.copy(is, os);
		        is.close();
		        os.flush();
		        os.close();
		        os = null;
		        //Toast.makeText(getApplicationContext(), "script geplaatst", Toast.LENGTH_SHORT).show();
		        } catch (IOException e) {
		        //	Toast.makeText(getApplicationContext(), "script niet geplaatst", Toast.LENGTH_SHORT).show();
		        }
				}
				
		Resources resources = getResources();
		String[] searchfor = resources.getStringArray(R.array.search_for);
		String[] nothing = {"nothing"};
		addStrings(searchfor);
		String[] searchfor2 = getStrings();
		SearchRequest request = new SearchRequest();
		
		fls = request.arrangeForResult(searchfor2, nothing);
		return null;
	}
	public void addStrings(String[] searchfor) {
        
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Set<String> mySet = new HashSet<String>();
				
		for (String ss:searchfor){
			mySet.add(ss);
			
		}
        String teststring = searchfor[1];
        Editor editor = getPrefs.edit();
        editor.putStringSet("een setje", mySet);
        editor.commit();
    }
	public String[] getStrings(){
		Set<String> mySet = new HashSet<String>();
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		mySet = getPrefs.getStringSet("een setje", mySet);
		String[] searchfor = mySet.toArray(new String[0]);
		return searchfor;
	}


	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
								
		Intent openOptionChooser = new Intent ("in.deaap.genomen.core.OPTIONCHOOSER");
		openOptionChooser.putParcelableArrayListExtra("lijst", (ArrayList<? extends Parcelable>) fls);
		startActivity(openOptionChooser);
		}
	}
}
