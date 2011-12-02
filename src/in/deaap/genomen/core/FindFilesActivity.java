package in.deaap.genomen.core;

import in.deaap.genomen.filehandler.FileArrayAdapter;
import in.deaap.genomen.filehandler.Flashable;
import in.deaap.genomen.filehandler.SearchRequest;

import java.util.List;

import android.app.ListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


public class FindFilesActivity extends ListActivity {
	
	private FileArrayAdapter adapter;
	private List<Flashable> fls;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_main);
		
		Resources resources = getResources();
		String[] searchfor = resources.getStringArray(R.array.search_for);
		String[] zip = {".zip"};
		String[] nothing = {"nothing"};
		
		SearchRequest request = new SearchRequest();
		fls = request.arrangeForExplorer(zip, searchfor);
		fls = request.arrangeForResult(searchfor, nothing);
				
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
	}
	
}