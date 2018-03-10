package backend.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Props {
	
	private static Props propsInstance = null;
	private ArrayList<String> arrayOfFiles = new ArrayList<String>();
	private HashMap<String, Properties> props = new HashMap<String, Properties>();
	
	private Props() {
		try {
			arrayOfFiles = Files.getFiles("properties");
			Properties prop = new Properties(System.getProperties()); 
			for(int i = 0; i < arrayOfFiles.size(); i++) {
				String[] fileName = arrayOfFiles.get(i).split("\\.");
				prop.load(getClass().getClassLoader().getResourceAsStream(arrayOfFiles.get(i)));
				props.put(fileName[0], prop);
			}
			
		}catch(IOException e){
			System.out.println(e.toString());
		}
	}
	
	public static Props getInstance() {
		if(propsInstance == null) {
			synchronized (Props.class) {
				if(propsInstance == null) {
					propsInstance = new Props();
				}
			}
		}
		return propsInstance;
	}
		
	// Getters
	public String getProperty(String key, String prop) {
		return this.props.get(key).getProperty(prop);
	}
	public String getDB(String prop) {
		return this.props.get("db").getProperty(prop);
	}
	public String getQuery(String prop) {
		return this.props.get("queries").getProperty(prop);
	}
	public String getMessage(String prop) {
		return this.props.get("messages").getProperty(prop);
	}
	
}
