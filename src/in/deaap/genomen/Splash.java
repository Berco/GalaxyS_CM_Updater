package in.deaap.genomen;

import in.deaap.genomen.assist.ShellInterface;
import in.deaap.genomen.R;

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
import android.app.ProgressDialog;
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
	

	
	private class FindZips extends AsyncTask<Void, Void, Void> {
		
	private List<Flashable> fls;
	String local_storage_root;
	String data_storage_root;
	ProgressDialog dialog;

	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		dialog = new ProgressDialog(Splash.this);
		dialog.setTitle("One moment..");
		dialog.setMessage("searching sdcards for zipfiles.");
		dialog.show();
	}
	@Override
	protected Void doInBackground(Void... arg0) {
				local_storage_root = "/FlashPack/";
				local_storage_root = Environment.getExternalStorageDirectory().toString()+local_storage_root;
				data_storage_root = "/data/in.deaap.genomen/";
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
				
		
		addStrings();
		String[] searchfor = getStrings();
		String[] nothing = {"nothing"};
		SearchRequest request = new SearchRequest();
		
		fls = request.arrangeForResult(searchfor, nothing);
		return null;
	}
	public void addStrings() {
		Resources resources = getResources();
		String[] searchfor = resources.getStringArray(R.array.search_for);
				
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Set<String> mySet = new HashSet<String>();
        mySet = getPrefs.getStringSet("een setje", mySet);
        if (mySet.isEmpty()){
			for (String ss:searchfor) mySet.add(ss);
	        Editor editor = getPrefs.edit();
	        editor.putStringSet("een setje", mySet);
	        editor.commit();
        }
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
		dialog.dismiss();
								
		Intent openOptionChooser = new Intent ("in.deaap.genomen.OPTIONCHOOSER");
		openOptionChooser.putParcelableArrayListExtra("lijst", (ArrayList<? extends Parcelable>) fls);
		startActivity(openOptionChooser);
		}
	}
}
