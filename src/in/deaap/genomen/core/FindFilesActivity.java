package in.deaap.genomen.core;

import in.deaap.genomen.filehandler.FileArrayAdapter;
import in.deaap.genomen.filehandler.Flashable;
import in.deaap.genomen.filehandler.SearchRequest;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class FindFilesActivity extends ListActivity {
	
	private FileArrayAdapter adapter;
	private List<Flashable> fls;
	private String[] searchfor;
	protected CharSequence[] options = { "Pre-defined", "All others", "All" };
	//private Boolean searchStandard;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_main);
		
		Resources resources = getResources();
		searchfor = resources.getStringArray(R.array.search_for);
		String[] zip = {".zip"};
				
		SearchRequest request = new SearchRequest();
		fls = request.arrangeForExplorer(zip, searchfor);
						
		adapter = new FileArrayAdapter(FindFilesActivity.this,R.layout.file_view, fls);
		this.setListAdapter(adapter);
	}
	
	
	
	// Click Listener
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Flashable o = adapter.getItem(position);
		Toast.makeText(this, "File Clicked: "+o.getName(), Toast.LENGTH_SHORT).show();
		
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
		blowUp.inflate(R.menu.leftclick_findfilesactivity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.changeFilter:
			showDialog( R.id.changeFilter );
			
			break;
		case R.id.leftPrefs:
			Intent p = new Intent("in.deaap.genomen.core.PREFS");
			startActivity(p);
			break;
		case R.id.cancelFindfiles:
			finish();
			break;
		}
		return false;
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch (id){
		default :
			return
			new AlertDialog.Builder(this)
		    .setTitle("This is an alert box.")
		    .setNeutralButton( "Neutral", new DialogButtonClickHandler() )
		    .create();
		case R.id.changeFilter:
			return
			new AlertDialog.Builder(this)
        	.setTitle("selectlist")
        	.setItems( options, new DialogSingleSelectionClickHandler() )
        	.setNeutralButton( "Annuleer", new DialogButtonClickHandler() )
        	.create();

		}
	}
	
	public class DialogSingleSelectionClickHandler implements DialogInterface.OnClickListener{
		public void onClick(DialogInterface dialog, int item) {
            Toast.makeText(getApplicationContext(), options[item], Toast.LENGTH_SHORT).show();
            
            SearchRequest request = new SearchRequest();
			String[] nothing = {"nothing"};
			String[] zip = {".zip"};
			
			switch(item){
			case 0:
				fls = request.arrangeForExplorer(searchfor, nothing);
				break;
			case 1:
				fls = request.arrangeForExplorer(zip, searchfor);
				break;
			case 2:
				fls = request.arrangeForExplorer(zip, nothing);
				break;
			}
								
			adapter = new FileArrayAdapter(FindFilesActivity.this,R.layout.file_view, fls);
			FindFilesActivity.this.setListAdapter(adapter);
        }
	}
	public class DialogButtonClickHandler implements DialogInterface.OnClickListener {
		public void onClick( DialogInterface dialog, int clicked ){
			switch(clicked){
				case DialogInterface.BUTTON_POSITIVE:
					Toast.makeText(getApplicationContext(), "Postitive' button clicked", Toast.LENGTH_SHORT).show();
					break;
				case DialogInterface.BUTTON_NEUTRAL:
					Toast.makeText(getApplicationContext(), "'Neutral' button clicked", Toast.LENGTH_SHORT).show();
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					Toast.makeText(getApplicationContext(), "'Negative' button clicked", Toast.LENGTH_SHORT).show();
					break;
			}
		}
	}
}