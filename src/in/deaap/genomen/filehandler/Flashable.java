package in.deaap.genomen.filehandler;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Flashable implements Comparable<Flashable>, Parcelable {
	private String name;
	private long version;
	private String path;
	
	public Flashable(String n,long v,String p) {
		name = n;
		version = v;
		path = p; }
	
	public String getName()		{ return name; }
	public long getVersion()	{ return version; }
	public String getPath()		{ return path; }
	
	@Override
	public int compareTo(Flashable o) {
		
			if(this.name != null && Long.toString(this.version) != null){
				if ( this.name.toLowerCase().replaceAll( "[^a-zA-Z]", "").compareTo(o.getName().toLowerCase().replaceAll( "[^a-zA-Z]", "")) == 0   ){
					int num = 0;
					if (this.version < o.version ) num = -1; 
					if (this.version == o.version ) num =  0;
					if (this.version > o.version ) num =  1;
					return num;
									
					//return Long.toString(this.version).compareTo(Long.toString(o.version));
				}else{
					Log.v("flashable", "compared" + this.name.toLowerCase().compareTo(o.getName().toLowerCase()));
					return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
				}
			}
			else 
				throw new IllegalArgumentException();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
}
