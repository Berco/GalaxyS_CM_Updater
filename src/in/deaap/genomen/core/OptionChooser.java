package in.deaap.genomen.core;

import in.deaap.genomen.assist.ShellInterface;
import in.deaap.genomen.filehandler.FileArrayAdapter;
import in.deaap.genomen.filehandler.Flashable;

import java.io.IOException;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	
	CheckBox mCbFactoryReset;
	CheckBox mCbFormatDataData;
	Button mFlash;
	Button mAdd;
	Button mClear;
	Button mDPI;
	List<Flashable> fls;
	List<Flashable> ExCo;
	protected CharSequence[] options = { "Set DPI to 210", "Remove Bloat", "Wipe Dalvic cache", "Fix Permissions" };
	protected boolean[] selections =  new boolean[ options.length ];
	String DPI = "210";
				
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
		mDPI = (Button) findViewById(R.id.btnDPI);
		
		mFlash.setOnClickListener(this);
		mAdd.setOnClickListener(this);
		mClear.setOnClickListener(this);
		mDPI.setOnClickListener(this);
		
		mCbFactoryReset.setOnClickListener(this);
		mCbFormatDataData.setOnClickListener(this);
		
		Bundle bundle = this.getIntent().getExtras();
		fls = bundle.getParcelableArrayList("lijst");
		}
    
    private void writeAnotherExtendedCommand() {
    	    	
    	FileArrayAdapter adapter = new FileArrayAdapter(OptionChooser.this,R.layout.file_view, fls);
		this.setListAdapter(adapter);
		
		ListView list = getListView();
		list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
				fls.remove(position);
				writeAnotherExtendedCommand();
				return true;
			}
		});
	}
    
    private void writeExtendedCommand() {
		try {
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
			
			command = "echo 'run_program(\"/cache/totalscript.sh\", \"prepare_recovery\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
						
			command = "echo 'format(\"/cache\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
									
			for (Flashable ff: fls){
				String path = ff.getPath().replace("/mnt", "");
				String insert = "echo 'assert(install_zip(\""+path.trim()+"\"));' >> /sdcard/FlashPack/extendedcommand";
				ShellInterface.runCommand(insert);
			}
			
			command = "echo 'ui_print(\"  ---------------------------------------- \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\" \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\"  -- Running GalaxyS_CM_Updater scripts -- \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\" \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\"  ---------------------------------------- \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			
			//  run_program("/sbin/busybox", "umount", "/system");
			ShellInterface.runCommand("echo 'run_program(\"/sbin/busybox\", \"mount\", \"/system\");' >> /sdcard/FlashPack/extendedcommand");
			ShellInterface.runCommand("echo 'run_program(\"/sbin/busybox\", \"chmod\", \"777\", \"/tmp/totalscript.sh\");' >> /sdcard/FlashPack/extendedcommand");
			if (selections[0])
			ShellInterface.runCommand("echo 'run_program(\"/tmp/totalscript.sh\", \"set_dpi\", \""+DPI+"\");' >> /sdcard/FlashPack/extendedcommand");
			if (selections[1])			
			ShellInterface.runCommand("echo 'run_program(\"/tmp/totalscript.sh\", \"clean\");' >> /sdcard/FlashPack/extendedcommand");
			if (selections[2])			
			ShellInterface.runCommand("echo 'run_program(\"/tmp/totalscript.sh\", \"wipe_dalvic\");' >> /sdcard/FlashPack/extendedcommand");
			if (selections[3])			
			ShellInterface.runCommand("echo 'run_program(\"/tmp/totalscript.sh\", \"fix_perms\");' >> /sdcard/FlashPack/extendedcommand");
			
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
			showDialog( R.id.btnFlash );
			break;
		case R.id.btnAdd:
			Intent openFindFiles = new Intent ("in.deaap.genomen.core.FINDFILESACTIVITY");
			startActivityForResult(openFindFiles, R.id.btnAdd);
			break;
		case R.id.btnClear:
			fls.clear();
			writeAnotherExtendedCommand();
			break;
		case R.id.btnDPI:
			Intent openDPI = new Intent ("in.deaap.genomen.core.DPISLIDERACTIVITY");
			startActivityForResult (openDPI, R.id.btnDPI);
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
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case R.id.btnAdd:
			if (resultCode == RESULT_OK){
			Bundle basket = data.getExtras();
			Flashable s = basket.getParcelable("answer");
			fls.add(s);
			writeAnotherExtendedCommand();
			}
			break;
		case R.id.btnDPI:
			if (resultCode == RESULT_OK){
			Bundle basket = data.getExtras();
			DPI = basket.getString("answer");
			options[0] = "Set DPI to "+ DPI;
			Toast.makeText(this, DPI, Toast.LENGTH_SHORT).show();
			}
			break;
		}
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
	
	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch (id){
		default :
			return
			new AlertDialog.Builder(this)
		    .setTitle("This is an alert box.")
		    .setNeutralButton( "Neutral", new DialogButtonClickHandler() )
		    .create();
		case R.id.btnFlash:
			return
			new AlertDialog.Builder(this)
        	.setTitle("Flash all this?")
        	.setMultiChoiceItems( options, selections, new DialogMultiSelectionClickHandler() )
        	.setPositiveButton( "Flash", new DialogButtonClickHandler() )
        	.setNegativeButton( "Cancel", new DialogButtonClickHandler() )
        	//.setNeutralButton( "Test Script", new DialogButtonClickHandler() )
        	.create();
		}
	}
	
	public class DialogMultiSelectionClickHandler implements DialogInterface.OnMultiChoiceClickListener{
		public void onClick(DialogInterface dialog, int clicked, boolean selected) {
			Log.i( "ME", options[clicked] + " selected: " + selected );
		}
	}
	
	public class DialogButtonClickHandler implements DialogInterface.OnClickListener {
		public void onClick( DialogInterface dialog, int clicked ){
			switch(clicked){
				case DialogInterface.BUTTON_POSITIVE:
//					switch (sure){
//						case R.id.btnFlash :
					if(ShellInterface.isSuAvailable()){
					try {
						String doit = "chmod 777 /datadata/in.deaap.genomen.core/totalscript.sh";
						ShellInterface.runCommand(doit);
						doit = "/datadata/in.deaap.genomen.core/totalscript.sh prepare_runtime";
						ShellInterface.runCommand(doit);
						
						writeExtendedCommand();
						ShellInterface.runCommand("mkdir -p /cache/recovery");
						String move = "cp /sdcard/FlashPack/extendedcommand /cache/recovery/extendedcommand";
						ShellInterface.runCommand(move);
						ShellInterface.runCommand("rm /sdcard/FlashPack/extendedcommand");
						ShellInterface.runCommand("reboot recovery");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
//						break;
//					}
					
					break;
				case DialogInterface.BUTTON_NEUTRAL:
					if(ShellInterface.isSuAvailable()){
//						try{
//						String doit = "chmod 777 /datadata/in.deaap.genomen.core/totalscript.sh";
//						ShellInterface.runCommand(doit);
//						String move = "/datadata/in.deaap.genomen.core/totalscript.sh remount";
//						ShellInterface.runCommand(move);
//						move = "/datadata/in.deaap.genomen.core/totalscript.sh prepare_runtime";
//						ShellInterface.runCommand(move);
//						String dpi_setting = "210";
//						move = "/datadata/in.deaap.genomen.core/totalscript.sh set_dpi "+dpi_setting;
//						ShellInterface.runCommand(move);
//						
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
						
						
					}
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
			}
		}
	}

}