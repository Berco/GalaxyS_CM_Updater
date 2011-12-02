package in.deaap.genomen.core;

import in.deaap.genomen.filehandler.Flashable;
import in.deaap.genomen.filehandler.SearchRequest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;

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
	String nightlyPath;
	String gappsPath;
	String packPath;
	String local_storage_root;

	@Override
	protected Void doInBackground(Void... arg0) {
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
		
		Resources resources = getResources();
		String[] searchfor = resources.getStringArray(R.array.search_for);
		String[] nothing = {"nothing"};
		
		SearchRequest request = new SearchRequest();
		
		fls = request.arrangeForResult(searchfor, nothing);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		for (Flashable ff: fls){
			if (ff.getName().contains("teamhacksung"))
				nightlyPath = ff.getPath().replace("/mnt", "");
			else if (ff.getName().contains("dpi_cleaner"))
				packPath = ff.getPath().replace("/mnt", "");
			else if (ff.getName().contains("gappsv"))
				gappsPath = ff.getPath().replace("/mnt", "");
			}
		Bundle bundle = new Bundle();
		bundle.putString("nightly", nightlyPath);
		bundle.putString("gapps", gappsPath);
		bundle.putString("pack", packPath);
		
		Intent openOptionChooser = new Intent ("in.deaap.genomen.core.OPTIONCHOOSER");
		openOptionChooser.putExtras(bundle);
		startActivity(openOptionChooser);
		}
	}
}
