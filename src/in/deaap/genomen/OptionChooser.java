package in.deaap.genomen;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class OptionChooser extends Activity implements View.OnClickListener{
    /** Called when the activity is first created. */
	
	CheckBox mCbNightly;
	CheckBox mCbGapps;
	CheckBox mCbZattaPack;
	CheckBox mCbFuguMod;
	TextView mText;
	Button mFlash;
	String nightlyPath;
	String gappsPath;
	String packPath;
	String fuguPath;
	String kangPath;
				
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionchooser);
        initialize();
        
        writeAnotherExtendedCommand();
   }

	private void writeExtendedCommand() {
		// TODO Auto-generated method stub
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
			
			if (mCbNightly.isChecked()){
			String insertNightly = "echo 'install_zip(\""+nightlyPath.trim()+"\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(insertNightly);}
			if (mCbGapps.isChecked()){
			String insertGapps = "echo 'install_zip(\""+gappsPath.trim()+"\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(insertGapps);}
			if (mCbZattaPack.isChecked()){
			String insertPack = "echo 'install_zip(\""+packPath.trim()+"\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(insertPack);}
			if (mCbFuguMod.isChecked()){
			String insertFugu = "echo 'install_zip(\""+fuguPath.trim()+"\");' >> /sdcard/FlashPack/extendedcommand";
			ShellInterface.runCommand(insertFugu);}
								
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}

	private void initialize() {
		mCbNightly = (CheckBox) findViewById(R.id.cbNightly);
		mCbGapps = (CheckBox) findViewById(R.id.cbGapps);
		mCbZattaPack = (CheckBox) findViewById(R.id.cbZattaPack);
		mCbFuguMod = (CheckBox) findViewById(R.id.cbFugumod);
		mText = (TextView) findViewById(R.id.tvExtComm);
		mFlash = (Button) findViewById(R.id.btnFlash);
		mFlash.setOnClickListener(this);
		mCbFuguMod.setOnClickListener(this);
		mCbZattaPack.setOnClickListener(this);
		mCbNightly.setOnClickListener(this);
		mCbGapps.setOnClickListener(this);
		
		Bundle bundle = this.getIntent().getExtras();
		nightlyPath = bundle.getString("nightly");
		gappsPath = bundle.getString("gapps");
		packPath = bundle.getString("pack");
		fuguPath = bundle.getString("fugu");
		kangPath = bundle.getString("kang");
		
		if (kangPath.length() > 0){
			sure_dialog(4, "Use KANG instead of Nightly?"); }
				
		}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnFlash:
			sure_dialog(R.id.btnFlash, "Flash");
			break;
		case R.id.cbFugumod:
			writeAnotherExtendedCommand();
			break;
		case R.id.cbGapps:
			writeAnotherExtendedCommand();
			break;
		case R.id.cbNightly:
			writeAnotherExtendedCommand();
			break;
		case R.id.cbZattaPack:
			writeAnotherExtendedCommand();
			break;
		}
	}
	
	private void writeAnotherExtendedCommand() {
		String headerlines = new String(
				'\n' +
				"             -----  FLASHING ROW ----- " + '\n' +
				"             -----   HERE WE GO  ----- " + '\n' +
				"             -----     ZATTA     ----- " + '\n'
				);
			if (mCbNightly.isChecked()){
				headerlines = headerlines + '\n' + '\n' + nightlyPath;}
			if (mCbGapps.isChecked()){
				headerlines = headerlines + '\n' + '\n' + gappsPath;}
			if (mCbZattaPack.isChecked()){
				headerlines = headerlines + '\n' + '\n' + packPath;}
			if (mCbFuguMod.isChecked()){
				headerlines = headerlines + '\n' + '\n' + fuguPath;}
		mText.setText(headerlines);
	}
	
	public void sure_dialog(final int sure, String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
			if (sure != 4){
			builder.setMessage("Are you sure?");	
			}else {
			builder.setMessage("I'll only ask this once!");
			}
		builder.setTitle(text);
		builder.setPositiveButton("Yes", new OnClickListener(){
			@Override
			public void onClick(DialogInterface dialog, int which) {
			switch (sure){
			case R.id.btnFlash :
				try {
					writeExtendedCommand();
					ShellInterface.runCommand("mkdir -p /cache/recovery");
					String move = "cp /sdcard/FlashPack/extendedcommand /cache/recovery/extendedcommand";
					ShellInterface.runCommand(move);
					ShellInterface.runCommand("rm /sdcard/FlashPack/extendedcommand");
					ShellInterface.runCommand("reboot recovery");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				break;
			case 4:
				nightlyPath = kangPath;
				writeAnotherExtendedCommand();
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
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.leftclick, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.leftAboutApp:
			Intent i = new Intent("in.deaap.genomen.ABOUT");
			startActivity(i);
			break;
		case R.id.leftPrefs:
			Intent p = new Intent("in.deaap.genomen.PREFS");
			startActivity(p);
			break;
		case R.id.leftExit:
			finish();
			break;
		}
		return false;
	}

}