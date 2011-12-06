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

	
	
 
	// All for making them parcelable
	public Flashable(Parcel in) {
		readFromParcel(in);
	}
  
	@Override
	public int describeContents() {
		return 0;
	}
 
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeLong(version);
		dest.writeString(path);
	}

	private void readFromParcel(Parcel in) {
 		name = in.readString();
		version = in.readLong();
		path = in.readString();
	}
     
    @SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR =
    	new Parcelable.Creator() {
            public Flashable createFromParcel(Parcel in) {
                return new Flashable(in);
            }
 
            public Flashable[] newArray(int size) {
                return new Flashable[size];
            }
        };	

}
