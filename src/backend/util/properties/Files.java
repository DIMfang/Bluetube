package backend.util.properties;

import java.io.File;
import java.util.ArrayList;

public class Files {
	
	public static ArrayList<String> getFiles(String path, String format) {
		ArrayList<String> nameOfFiles = new ArrayList<String>(); 
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		 
		for(File file : listOfFiles) {
			if(file.isFile() && file.getName().endsWith("." + format)) {
				nameOfFiles.add(file.getName()); 
			}
		}
		return nameOfFiles; 
	}

}
