package in.deaap.genomen.core;

import in.deaap.genomen.filehandler.FileArrayAdapter;
import in.deaap.genomen.filehandler.Flashable;
import in.deaap.genomen.filehandler.SearchRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class FindFilesActivity extends ListActivity {
	private String startDir = "mnt";
    private File currentDir;
    private FileArrayAdapter adapter;
	private List<Flashable> zipjes;
	private int level;
	private boolean up = true;
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        String startDir = "mnt";
        currentDir = new File("/"+ startDir + "/");
        
        String[] nothing = {"nothing"};
		String[] zip = {"*.zip"};
		SearchRequest request = new SearchRequest();
		
		zipjes = request.arrangeForExplorer(zip, nothing);
        fill(currentDir);
    }
                 
    private void fill(File f) {
    	File[]dirs = f.listFiles();
    	level = f.toString().replaceAll( "[^/]", "").length();
    	    	   	
    	this.setTitle(" Zip Explorer: "+f.getName());
		 List<Flashable>dir = new ArrayList<Flashable>();
		 List<Flashable>fls = new ArrayList<Flashable>();
		 try{
			 for(File ff: dirs)
			 {
				if(ff.isDirectory()){
					Boolean add = false;
					for (Flashable fl:zipjes){
					//	String checkString = StartOfPath(fl.getPath());
					//	if (ff.getAbsolutePath().contains(checkString)) add = true;
						if (fl.getPath().contains(ff.getPath())) add = true;
					}
					if (add) dir.add(new Flashable(ff.getName(),-1,ff.getAbsolutePath()));
				}else {
					Boolean add = false;
					for (Flashable fl:zipjes){
						if (fl.getName().equals(ff.getName())) add = true;
					}
					if (add) fls.add(new Flashable(ff.getName(),1,ff.getAbsolutePath()));
				}
			 }
		 }catch(Exception e) { }
		 
		 Collections.sort(dir);
		 int dir_count = dir.size();
		 Collections.sort(fls);
		 int files_count = fls.size();
		 dir.addAll(fls);
		 
		 if(!f.getName().equalsIgnoreCase(startDir))
			 dir.add(0,new Flashable("..",-2,f.getParent()));
		 
		 if (dir_count==1 && files_count==0){
			 	int u;
			 	if (up) u = 1; else u = 0;
			 	Flashable o = dir.get(u);
			 	currentDir = new File(o.getPath());
				fill(currentDir);
		}else{
				 adapter = new FileArrayAdapter(FindFilesActivity.this,R.layout.file_view,dir);
				 this.setListAdapter(adapter);}
    }
 
    
    // Click Listener
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Flashable o = adapter.getItem(position);
		if (o.getName().equals("..")) up = false; else up = true;
		if(o.getVersion() <= 0 ){	
				currentDir = new File(o.getPath());
				fill(currentDir);
		}
		else
		{
			onFileClick(o);
		}
	}
    private void onFileClick(Flashable o)
    {
    	//String test = StartOfPath(o.getPath());
    	//Toast.makeText(this, "result: "+ test, Toast.LENGTH_SHORT).show();
    	
    	Intent person = new Intent();
		Bundle backpack = new Bundle();
		backpack.putParcelable("answer", o);
		person.putExtras(backpack);
		setResult(RESULT_OK, person);
		finish();
    }
    
	@SuppressWarnings("unused")
	private String StartOfPath(String checkString){
    	int p = 0;
    	int l = 0;
    	String paadsje = null;
    	for (l=0;l<checkString.length();l++){
    		if (p<(level+1)){	
    			Character g = checkString.charAt(l);
    			paadsje = paadsje + g;
    			if (g.toString().contentEquals("/")) p++; 
    		}	
    	}
    	paadsje = paadsje.substring(0, (paadsje.length()-1));
    	return paadsje;
    }
    
    @Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.leftclick_optionchooser, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		
		case R.id.leftPrefs:
			Intent p = new Intent("in.deaap.genomen.core.PREFS");
			startActivity(p);
			break;
		case R.id.leftExit:
			finish();
			break;
		}
		return false;
	}
	
}