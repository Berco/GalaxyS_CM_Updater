package in.deaap.genomen.core;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class DpiSliderActivity extends Activity{
	private int dpi = 210;
	private String DPI = String.valueOf(dpi);
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dpi_slider);
		
		final SeekBar dpiSB = (SeekBar) findViewById(R.id.sbDPIslider);
		final TextView dpiTV = (TextView) findViewById(R.id.tvDPIvalue);
		Button abutton = (Button) findViewById(R.id.btnDPIapply);
		Button rbutton = (Button) findViewById(R.id.btnDPIcancel);
						
		dpiTV.setText(String.valueOf(dpi));
		dpiSB.setProgress(prog_value(dpi));
		
		abutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) { 
				Intent person = new Intent();
				Bundle backpack = new Bundle();
				backpack.putString("answer", DPI);
				person.putExtras(backpack);
				setResult(RESULT_OK, person);
				finish();
			}
		});
		
		rbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				finish();
			}
		});
		
		dpiSB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
			DPI = String.valueOf(dpi = dpi_value(6-progress));
			dpiTV.setText(DPI);
			}
		});}
	
	private int prog_value(int dpi2) {
		int val = 6-((240-dpi2)/10);
		return val;
	}

	private int dpi_value(int a) {
		int val = 240-a*10;
		if (val==180)
			return 182;
		return val;
	}
}
