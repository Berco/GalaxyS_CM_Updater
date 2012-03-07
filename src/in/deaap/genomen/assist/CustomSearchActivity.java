package in.deaap.genomen.assist;

import in.deaap.genomen.R;
import in.deaap.genomen.filehandler.FileArrayAdapter;
import in.deaap.genomen.filehandler.Flashable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class CustomSearchActivity extends ListActivity implements View.OnClickListener{
	
	private Button mAddSearch;
	private Button mDoneSearch;
	private List<Flashable> searchstrings;
@SuppressWarnings("unused")
	private FileArrayAdapter adapter;
	protected CharSequence[] options = { "Delete", "Edit" };
	protected boolean[] selections =  new boolean[ options.length ];
	private String lastClickedName;
	private int lastClickedItem;
	private static final int AddnEdit = 12;
	
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.custom_search);
        	this.setTitle(" Search parameters adjuster ");
        	initialize();
        	setListView();
    }
    
    private void initialize() {
		mAddSearch = (Button) findViewById(R.id.btnAddSearch);
		mDoneSearch = (Button) findViewById(R.id.btnDoneSearch);
		mAddSearch.setOnClickListener(this);
		mDoneSearch.setOnClickListener(this);
		searchstrings = null;
		adapter=null;
		lastClickedName=null;
		lastClickedItem=0;
	}
    
    private void setListView(){
    	String[] searchfor = getStrings();
		searchstrings = new ArrayList<Flashable>();
		for (String ss:searchfor) searchstrings.add(new Flashable(ss,1,""));
		Collections.sort(searchstrings);
    	       	
    	FileArrayAdapter adapter = new FileArrayAdapter(this,R.layout.file_view,searchstrings);
    	this.setListAdapter(adapter);
    	
    	ListView list = getListView();
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
				lastClickedName = searchstrings.get(position).getName();
				lastClickedItem = position;
				showDialog(1);
				return true;
			}
		});
    }
    
    public String[] getStrings(){
		Set<String> mySet = new HashSet<String>();
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		mySet = getPrefs.getStringSet("een setje", mySet);
		String[] searchfor = mySet.toArray(new String[0]);
		return searchfor;
	}
    
    public void addStrings(List<Flashable> searchstrings) {
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Set<String> mySet = new HashSet<String>();
		for (Flashable ss:searchstrings) mySet.add(ss.getName());		
		        
        Editor editor = getPrefs.edit();
        editor.putStringSet("een setje", mySet);
        editor.commit();
    }
    
    // Click Listener
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Flashable o = (Flashable) this.getListAdapter().getItem(position);
		lastClickedName = o.getName();
		lastClickedItem = position;
		showDialog(1);
	}
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btnAddSearch:
			lastClickedName=null;
			startAddnEdit(lastClickedName);
			break;
		case R.id.btnDoneSearch:
			Toast.makeText(getApplicationContext(), "Restart App to initiate new search", Toast.LENGTH_SHORT).show();
			finish();
			break;	
		}
	}
	
	public void startAddnEdit(String lastClickedName){
		Intent openAddnEdit = new Intent ("in.deaap.genomen.assist.ADDSEARCHACTIVITY");
		openAddnEdit.putExtra("string", lastClickedName);
		startActivity(openAddnEdit);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == RESULT_OK){		
				setListView();
			}		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
			return
			new AlertDialog.Builder(this)
        	.setItems( options, new DialogSingleSelectionClickHandler() )
        	.create();
	}
	
	public class DialogSingleSelectionClickHandler implements DialogInterface.OnClickListener{
		public void onClick(DialogInterface dialog, int item) {
            
            if (options[item].toString().contentEquals("Delete")){            	
            	searchstrings.remove(lastClickedItem);
				addStrings(searchstrings);
				setListView();
				}
            if (options[item].toString().contentEquals("Edit")){
            	startAddnEdit(lastClickedName);
            }
        }
	}
	
}