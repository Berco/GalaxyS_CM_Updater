package in.deaap.genomen;

import in.deaap.genomen.R;
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
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class FindFilesActivity extends ListActivity {
	private String startDir = "mnt";
    private File currentDir;
    private FileArrayAdapter adapter;
	private List<Flashable> zipjes;
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
		Log.v("directory", "to fill: " + currentDir);
        fill(currentDir);
    }
                 
    private void fill(File f) {
    	File[]dirs = f.listFiles();
    
    	    	   	
    	this.setTitle(" Zip Explorer: "+f.getName());
		 final List<Flashable>dir = new ArrayList<Flashable>();
		 List<Flashable>fls = new ArrayList<Flashable>();
		 try{
			 for(File ff: dirs)
			 {
				if(ff.isDirectory()){
					Boolean add = false;
					for (Flashable fl:zipjes){
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
		 Collections.sort(fls);
		 
		 int dir_count = dir.size();
		 int files_count = fls.size();
		 dir.addAll(fls);
		 
		 if(!f.getName().equalsIgnoreCase(startDir) )
			 dir.add(0,new Flashable("..",-2,f.getParent()));
		 
		 if(f.getName().equalsIgnoreCase(startDir) && (dir_count == 1)){
			 up = false;
			 Flashable o = dir.get(0);
			 startDir = o.getName();
		 }
				
		 if (dir_count==1 && files_count==0){
			 	int u;
			 	if (up) u = 1; else u = 0;
			 	Flashable o = dir.get(u);
			 	currentDir = new File(o.getPath());
			 	Log.v("directory", "to fill: " + currentDir);
			 	fill(currentDir);
		 }else{
				 adapter = new FileArrayAdapter(FindFilesActivity.this,R.layout.file_view,dir);
				 this.setListAdapter(adapter);
				 ListView list = getListView();
				 list.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent, View view,
							int position, long id) {
							String lastClickedName = dir.get(position).getName();
							startAddnEdit(lastClickedName);
							return true;
						}
					});
		}
    }
    
    public void startAddnEdit(String lastClickedName){
		Intent openAddnEdit = new Intent ("in.deaap.genomen.assist.ADDSEARCHACTIVITY");
		openAddnEdit.putExtra("string", lastClickedName);
		startActivity(openAddnEdit);
	}
 
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
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
    	Intent person = new Intent();
		Bundle backpack = new Bundle();
		backpack.putParcelable("answer", o);
		person.putExtras(backpack);
		setResult(RESULT_OK, person);
		finish();
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
			Intent p = new Intent("in.deaap.genomen.PREFS");
			startActivity(p);
			break;
		}
		return false;
	}
	
}