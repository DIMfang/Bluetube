package backend.register;

import java.util.ArrayList;

import java.io.File;

public class Test {
	
	public static ArrayList<String> getFiles(String format) {
		ArrayList<String> nameOfFiles = new ArrayList<String>(); 
		String path = "C:\\Develop\\apache-tomcat-8.5.20\\conf\\Catalina\\localhost";
		File folder = new File(path);
		// + "\\conf\\Catalina\\localhost"
		File[] listOfFiles = folder.listFiles();
		 
		
		for(int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith("." + format)) {
				nameOfFiles.add(listOfFiles[i].getName()); 
			}
		}
		return nameOfFiles; 
	}

	
	public static void main(String[] args) {
		
		ArrayList<String> arr = new ArrayList<String>();
		//arr = getFiles("properties");
//		Iterator<String> i = arr.iterator();
//		while(i.hasNext()) {
//			System.out.println(i.next());
//		}
//		for(int j = 0; j < arr.size(); j++) {
//			System.out.println(arr.get(j));
//		}
		for(Object s : arr.toArray()) {
			System.out.println(s);
		}
		
		
    }
}


