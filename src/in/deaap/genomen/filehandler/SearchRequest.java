package in.deaap.genomen.filehandler;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import android.util.Log;


public class SearchRequest {
	
	private static final String DIRECTORY = "/mnt/";
	private long version;
	private List<Flashable> AllFiles;
	private List<Flashable> fls;
	
	// main method geeft List<Flashable> gesorteerd terug met laatste versie
	public List<Flashable> arrangeForResult(String[] searchfor, String[] except) {
		
		AllFiles = findFiles(searchfor, except);
				
		fls = new ArrayList<Flashable>();
		String test = "";
		for (Flashable ff:AllFiles) {
			
			String checkString = ff.getName().substring(1, 5);    
		    if (!checkString.contentEquals(test)){ 
		    	if (ff.getName().contains(".zip") && !(ff.getName().toLowerCase().contains("g613"))){
		    	fls.add(new Flashable(ff.getName(),ff.getVersion(), ff.getPath()));
		       	test = ff.getName().substring(1,5);
		       	}
		    }
		}
		return fls;
	}
	
	//main method geeft List<Flashabel> volledig terug == eigenlijk findFiles, voor
	//voor later gebruik als eventueel een echte zipexplorer
	public List<Flashable> arrangeForExplorer(String[] searchfor, String[] except) {
		fls = findFiles(searchfor, except);
		
		//fls = new ArrayList<Flashable>();
		//								//List<Flashable>dirs = new ArrayList<Flashable>();
		//								//Flashable x = new Flashable("", "", "");
		//
		//for (Flashable ff:AllFiles){
		//	fls.add(new Flashable(ff.getName(), ff.getVersion(), ff.getPath()));
		//								//if (!x.getName().equals(ff.getParent())){ 
		//								//	dirs.add(new Flashable(ff.getParent(),"file", ff.getPath()));
		//								//	x= new Flashable(ff.getParent(),"", "");
		//								//}
		//}
		//
		//								//Collections.sort(dirs);
		return fls;
	}

	// helper method, geeft List<Flashable> terug	
	private List<Flashable> findFiles(String[] searchStrings, String[] exceptStrings) {

		FilenameFilter[] filter_include = new FilenameFilter[searchStrings.length];
		int i = 0;
		for (final String take : searchStrings) {
			filter_include[i] = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.contains(take);
				}
			};
		i++;
		}
		
		FilenameFilter[] filter_exclude = new FilenameFilter[exceptStrings.length];
		i = 0;
		for (final String leave : exceptStrings) {
			filter_exclude[i] = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.contains(leave);
				}
			};
		i++;
		}
		
		File[] allMatchingFiles = listFilesAsArray(new File(DIRECTORY), filter_include, filter_exclude, -1);
		
		ArrayList<Flashable> zomaar = new ArrayList<Flashable>();
		for (File ff:allMatchingFiles) {
			
			try {
			    version = Long.parseLong(ff.getName().replaceAll( "[^0-9]", ""));
			} catch(NumberFormatException e) {
				version = 13;
			} 
			
				zomaar.add(new Flashable(ff.getName(),version, ff.getPath()));	
		}
		Collections.sort(zomaar);
		Collections.reverse(zomaar);
		return zomaar;
		
	}
	
	//helper, geeft File[] terug
	public File[] listFilesAsArray(File directory, FilenameFilter[] filter_include, FilenameFilter[] filter_exclude,int recurse) {
			Collection<File> files = listFiles(directory, filter_include, filter_exclude, recurse);
			File[] arr = new File[files.size()];
			return files.toArray(arr);	
	}
		
	// helper, geeft Collection<> terug
	private Collection<File> listFiles(File directory, FilenameFilter[] filter_include, FilenameFilter[] filter_exclude, int recurse) {
		Vector<File> files = new Vector<File>();
		File[] entries = directory.listFiles();
		if (entries != null) {
			for (File entry : entries) {
				for (FilenameFilter filefilter_in : filter_include) {
					if (filter_include == null || filefilter_in.accept(directory, entry.getName())) {
					// origineel	
						int t = 0;
						for (FilenameFilter filefilter_ex : filter_exclude){
							Log.v("SearchRequest", "checked: " + entry.getName());
							if (filter_exclude == null || !filefilter_ex.accept(directory, entry.getName())) {
							t++	;
								Log.v("SearchRequest", "teller=" + Integer.toString(t)+ "/" + Integer.toString(filter_exclude.length));
								if (t == filter_exclude.length){
									files.add(entry);
									Log.v("SearchRequest", "Added: " + entry.getName());
								}
							}
						}
					//einde origineel
					}
				}
				if ((recurse <= -1) || (recurse > 0 && entry.isDirectory())) {
					recurse--;
					files.addAll(listFiles(entry, filter_include, filter_exclude, recurse));
					recurse++;
				}
			}
		}
		return files;
	}

}
