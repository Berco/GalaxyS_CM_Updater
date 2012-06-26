package in.deaap.genomen;

import in.deaap.genomen.assist.ShellInterface;
import in.deaap.genomen.R;
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
	String brand;
	String data_location;
	String sdcard_location;
	String external_location;
				
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionchooser);
        this.setTitle(R.string.app_name);
        initialize();
        //selections[0] = true;
        //selections[1] = true;
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
		getDeviceId();
		}
    
    private void getDeviceId() {
		// TODO Auto-generated method stub
    	// Acer A500
    	// samsung GT-I9000
    	brand = android.os.Build.MANUFACTURER.toLowerCase().trim();
    	if (brand.equals("acer")){
    		Toast.makeText(this, "Brand: Acer", Toast.LENGTH_SHORT).show();
    		data_location = "/data/data/";
    		sdcard_location = "/data/media/";
    		external_location = "/sdcard/";
    	}else if(brand.equals("samsung")){
    		Toast.makeText(this, "Brand: Samsung", Toast.LENGTH_SHORT).show();
    		data_location = "/datadata/";
    		sdcard_location = "/sdcard/";
    		external_location = "/emmc/";
    	}else{
    		Toast.makeText(this, "Whatever this device is, I don't know." + brand, Toast.LENGTH_SHORT).show();
    		finish();
    	}
    	 
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
			String command  = new String("mkdir -p /mnt/sdcard/FlashPack");
			ShellInterface.runCommand(command);
			
			ShellInterface.runCommand("mkdir -p /cache/recovery");
			ShellInterface.runCommand("chmod 777 /cache/recovery");
			command = "echo 'ui_print(\" \");' > /cache/recovery/extendedcommand";
			ShellInterface.runCommand(command);
			
			command = "echo 'ui_print(\" \");' > /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\"  -----  FLASHING ROW ----- \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\"  -----   HERE WE GO  ----- \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\"  -----     ZATTA     ----- \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
			command = "echo 'ui_print(\" \");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);

			ShellInterface.runCommand("echo 'run_program(\"/sbin/busybox\", \"mount\", \"/system\");' >> /sdcard/FlashPack/extendedcommand");
			ShellInterface.runCommand("echo 'run_program(\"/sbin/busybox\", \"chmod\", \"777\", \"/cache/totalscript.sh\");' >> /sdcard/FlashPack/extendedcommand");
			command = "echo 'run_program(\"/cache/totalscript.sh\", \"prepare_recovery\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
						
			command = "echo 'format(\"/cache\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(command);
									
			for (Flashable ff: fls){
				String path = ff.getPath().replace("/mnt", "");
				// nu: /sdcard/etc of /emmc/etc voor samsung
				// nu: /sdcard/etc of /external_sd voor acer
				//data_location = "/data/data/";
	    		//sdcard_location = "/data/media/";
	    		//external_location = "/sdcard/";
				if (brand.equals("acer")){
					path = path.replace("/sdcard/", sdcard_location);
					path = path.replace("/external_sd/", external_location);
				}
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
		Flashable o = (Flashable) this.getListAdapter().getItem(position);
		Toast.makeText(this, "You pressed: " + o.getName(), Toast.LENGTH_SHORT).show();

	}
    
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnFlash:
			showDialog( R.id.btnFlash );
			break;
		case R.id.btnAdd:
			Intent openFindFiles = new Intent ("in.deaap.genomen.FINDFILESACTIVITY");
			startActivityForResult(openFindFiles, R.id.btnAdd);
			break;
		case R.id.btnClear:
			fls.clear();
			writeAnotherExtendedCommand();
			break;
		case R.id.btnDPI:
			Intent openDPI = new Intent ("in.deaap.genomen.DPISLIDERACTIVITY");
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
		
		case R.id.leftPrefs:
			Intent p = new Intent("in.deaap.genomen.PREFS");
			startActivity(p);
			break;
		case R.id.AddItem:
			Intent openFindFiles = new Intent ("in.deaap.genomen.FINDFILESACTIVITY");
			startActivityForResult(openFindFiles, R.id.btnAdd);
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
					if(ShellInterface.isSuAvailable()){
					try {
						String doit = "chmod 777 "+ data_location + "in.deaap.genomen/totalscript.sh";
						ShellInterface.runCommand(doit);
						doit = data_location + "in.deaap.genomen/totalscript.sh prepare_runtime"+brand;
						ShellInterface.runCommand(doit);
						writeExtendedCommand();
						ShellInterface.runCommand("mkdir -p /cache/recovery");
						ShellInterface.runCommand("chmod 777 /cache/recovery");
						String move = "cp /sdcard/FlashPack/extendedcommand /cache/recovery/extendedcommand";
						ShellInterface.runCommand(move);
						ShellInterface.runCommand("rm /sdcard/FlashPack/extendedcommand");
						ShellInterface.runCommand("reboot recovery");
						} catch (Exception e) {
							e.printStackTrace();
					}
					}
					break;
				case DialogInterface.BUTTON_NEGATIVE:
					break;
			}
		}
	}

}