package in.deaap.genomen.core;

import in.deaap.genomen.assist.ShellInterface;
import in.deaap.genomen.filehandler.FileArrayAdapter;
import in.deaap.genomen.filehandler.Flashable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

public class OptionChooser extends ListActivity implements View.OnClickListener{
    /** Called when the activity is first created. */
	
	CheckBox mCbFactoryReset;
	CheckBox mCbFormatDataData;
	Button mFlash;
	Button mAdd;
	Button mClear;
	List<Flashable> fls;
	List<Flashable> ExCo;
				
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionchooser);
        initialize();
        writeAnotherExtendedCommand();   	
   }

    private void initialize() {
		mCbFactoryReset = (CheckBox) findViewById(R.id.cbFactoryReset);
		mCbFormatDataData = (CheckBox) findViewById(R.id.cbFormatDataData);
		mFlash = (Button) findViewById(R.id.btnFlash);
		mAdd = (Button) findViewById(R.id.btnAdd);
		mClear = (Button) findViewById(R.id.btnClear);
		
		mFlash.setOnClickListener(this);
		mAdd.setOnClickListener(this);
		mClear.setOnClickListener(this);
		mCbFactoryReset.setOnClickListener(this);
		mCbFormatDataData.setOnClickListener(this);
		
		Bundle bundle = this.getIntent().getExtras();
		fls = bundle.getParcelableArrayList("lijst");
		}
    
    private void writeAnotherExtendedCommand() {
    	ExCo = new ArrayList<Flashable>();
    	for (Flashable ff : fls)
    		ExCo.add(new Flashable(ff.getName(), ff.getVersion(), ff.getPath()));
    	
    	FileArrayAdapter adapter = new FileArrayAdapter(OptionChooser.this,R.layout.file_view, ExCo);
		this.setListAdapter(adapter);
		
		ListView list = getListView();
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Flashable tekst = ExCo.get(position);
				Toast.makeText(OptionChooser.this, tekst.getName() + " long clicked",Toast.LENGTH_SHORT).show();
				// Return true to consume the click event. In this case the
				// onListItemClick listener is not called anymore.
				fls.remove(position);
				writeAnotherExtendedCommand();
				return true;
			}
		});
	}
    
    private void writeExtendedCommand() {
		try {
		// header
			String command = new String("echo 'ui_print(\" \");' > /sdcard/FlashPack/extendedcommand");
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\"  -----  FLASHING ROW ----- \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\"  -----   HERE WE GO  ----- \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\"  -----     ZATTA     ----- \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\" \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			
			command = "echo 'format(\"/cache\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			
			for (Flashable ff: ExCo){
				String path = ff.getPath().replace("/mnt", "");
				String insert = "echo 'assert(install_zip(\""+path.trim()+"\"));' >> /sdcard/FlashPack/extendedcommand";
				ShellInterface.runCommand(insert);
			}
			command = "echo 'format(\"/cache\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			
			
			
			} catch (IOException e) {
			e.printStackTrace();
			}
		}

    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		// Get the item that was clicked
		Flashable o = (Flashable) this.getListAdapter().getItem(position);
		Toast.makeText(this, "You selected: " + o.getName(), Toast.LENGTH_SHORT).show();

	}
    
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnFlash:
			if(ShellInterface.isSuAvailable())
			sure_dialog(R.id.btnFlash, "Flash");
			break;
		case R.id.btnAdd:
			Intent openFindFiles = new Intent ("in.deaap.genomen.core.FINDFILESACTIVITY");
			startActivityForResult(openFindFiles, 0);
			break;
		case R.id.btnClear:
			fls.clear();
			writeAnotherExtendedCommand();
			break;
		case R.id.cbFactoryReset:
			writeAnotherExtendedCommand();
			break;
		case R.id.cbFormatDataData:
			writeAnotherExtendedCommand();
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			Bundle basket = data.getExtras();
			Flashable s = basket.getParcelable("answer");
			fls.add(s);
			writeAnotherExtendedCommand();
		}
	}
			
	public void sure_dialog(final int sure, String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure?");	
		builder.setTitle(text);
		builder.setPositiveButton("Yes", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
			switch (sure){
			case R.id.btnFlash :
				try {
					writeAnotherExtendedCommand();
					writeExtendedCommand();
					ShellInterface.runCommand("mkdir -p /cache/recovery");
					String move = "cp /sdcard/FlashPack/extendedcommand /cache/recovery/extendedcommand";
					ShellInterface.runCommand(move);
					ShellInterface.runCommand("rm /sdcard/FlashPack/extendedcommand");
					ShellInterface.runCommand("reboot recovery");
				} catch (Exception e) {
					e.printStackTrace();
				} 
				break;
			}
			};});
		
		builder.setNegativeButton("No", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}});
		AlertDialog alert = builder.create();
		alert.show();
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
		case R.id.leftAboutApp:
			Intent i = new Intent("in.deaap.genomen.core.ABOUT");
			startActivity(i);
			break;
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