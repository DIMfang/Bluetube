package backend.util.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Props {
	
	private static Props propsInstance = null;
	private ArrayList<String> arrayOfFiles = new ArrayList<String>();
	private static HashMap<String, Properties> props = new HashMap<String, Properties>();
	
	private Props() {
		try {
			String path = System.getProperty("catalina.home") + "\\conf\\Catalina\\localhost";
			arrayOfFiles = Files.getFiles(path, "properties");
			Properties prop = new Properties(System.getProperties()); 
			for(int i = 0; i < arrayOfFiles.size(); i++) {
				String[] fileName = arrayOfFiles.get(i).split("\\.");
				prop.load(getClass().getClassLoader().getResourceAsStream(arrayOfFiles.get(i)));
				props.put(fileName[0], prop);
			}
			
		} catch(IOException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void getInstance() {
		if(propsInstance == null) {
			synchronized (Props.class) {
				if(propsInstance == null) {
					propsInstance = new Props();
				}
			}
		}
	}
		
	// Getters
	public static String getProperty(String key, String prop) {
		return props.get(key).getProperty(prop);
	}
	public static String getDB(String prop) {
		return props.get("db").getProperty(prop);
	}
	public static String getQuery(String prop) {
		return props.get("queries").getProperty(prop);
	}
	public static String getMessage(String prop) {
		return props.get("messages").getProperty(prop);
	}
	
}
