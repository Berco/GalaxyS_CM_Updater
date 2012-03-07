package in.deaap.genomen.assist;

import java.util.HashSet;
import java.util.Set;
import in.deaap.genomen.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddSearchActivity extends Activity implements View.OnClickListener{
	
	Button mOkayAdd;
	Button mCancelAdd;
	EditText mAddString;
	String mEditString;
	Set<String> myStringSet;
	Boolean add=true;
	int item = 0;
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        	super.onCreate(savedInstanceState);
        	setContentView(R.layout.add_search);
        	this.setTitle(" Search parameters adjuster ");
        	initialize();	
    }
    
    @Override
	protected void onPause() {
		super.onPause();
		finish();
	}
    
    private void initialize() {
    	mAddString = (EditText) findViewById(R.id.etAddSearch);
    	mOkayAdd = (Button) findViewById(R.id.btnOkayAdd);
    	mCancelAdd = (Button) findViewById(R.id.btnCancelAdd);
		mOkayAdd.setOnClickListener(this);
		mCancelAdd.setOnClickListener(this);	
		
		myStringSet = getStringSet();
		
		Bundle bundle = this.getIntent().getExtras();
		mEditString = bundle.getString("string");
		mAddString.setText(mEditString);
		
		if (myStringSet.contains(mEditString)) add = false;
		
	}
        
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.btnOkayAdd:
			String newSearch = mAddString.getText().toString();
			if (!newSearch.isEmpty()){
				if (!add) myStringSet.remove(mEditString);
				myStringSet.add(newSearch);
				addStrings(myStringSet);
				Intent openCustomSearch = new Intent ("in.deaap.genomen.assist.CUSTOMSEARCHACTIVITY");
				
				startActivity(openCustomSearch);
				//Intent person = new Intent();
				//setResult(RESULT_OK, person);
				finish();
			}
			finish();
			break;
		case R.id.btnCancelAdd:
			finish();
			break;	
		}
	}
	
	public Set<String> getStringSet(){
		Set<String> myStringSet = new HashSet<String>();
		SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		myStringSet = getPrefs.getStringSet("een setje", myStringSet);
		return myStringSet;
	}
    
    public void addStrings(Set<String> myStringSet) {
        SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Editor editor = getPrefs.edit();
        editor.putStringSet("een setje", myStringSet);
        editor.commit();
    }
}