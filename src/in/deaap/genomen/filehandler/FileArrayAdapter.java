package in.deaap.genomen.filehandler;

import in.deaap.genomen.core.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class FileArrayAdapter extends ArrayAdapter<Flashable>{

	private Context c;
	private int id;
	private List<Flashable>items;
	
	public FileArrayAdapter(Context context, int textViewResourceId, List<Flashable> objects) {
		super(context, textViewResourceId, objects);
		c = context;
		id = textViewResourceId;
		items = objects;
	}
	@Override
	public Flashable getItem(int i)
	 {
		 return items.get(i);
	 }
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(id, null);
			}
			final Flashable o = items.get(position);
			if (o != null) {
				TextView t1 = (TextView) v.findViewById(R.id.TextView01);
				TextView t2 = (TextView) v.findViewById(R.id.TextView02);
				ImageView i = (ImageView) v.findViewById(R.id.imageView1);

				if(t1!=null)
					t1.setText(o.getName());
				if(t2!=null)
					t2.setText(o.getPath());      //(Long.toString(o.getVersion()));

				if(o.getVersion()>=0){
					if(o.getName().contains("teamhacksung"))
						i.setImageResource(R.drawable.cyanogen);
					else if(o.getName().contains("CM7_FuguMod"))
						i.setImageResource(R.drawable.fugumod);
					else if(o.getName().contains("Perka"))
						i.setImageResource(R.drawable.notes);
					else if(o.getName().contains("gappsv"))
						i.setImageResource(R.drawable.goo_inside);
					else i.setImageResource(R.drawable.zip);
				}
				else if (o.getVersion()< 0)
					i.setImageResource(R.drawable.folder);

			}
			return v;
	}

}
