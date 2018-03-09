package backend.properties;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Props {
	private static Props propsInstance = null;
//	private static ArrayList<String> arrayOfFiles = new ArrayList<String>();
	private HashMap<String, Properties> props = new HashMap<String, Properties>();
	
	private Props() {
		try {
			Properties prop = new Properties(System.getProperties()); 
			prop.load(getClass().getClassLoader().getResourceAsStream("db.properties"));
			this.props.put("db", prop);
			prop.load(getClass().getClassLoader().getResourceAsStream("queries.properties"));
			this.props.put("queries", prop);
			prop.load(getClass().getClassLoader().getResourceAsStream("messages.properties"));
			this.props.put("messages", prop);
//			for(int i = 0; i < arrayOfFiles.size(); i++) {
//				String fileName = arrayOfFiles.get(i);
//				prop.load(getClass().getClassLoader().getResourceAsStream(fileName + ".properties"));
//				props.put(fileName, prop);
//			}
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
	
//	public static void loadFile(String fileName) {
//		arrayOfFiles.add(fileName);
//	};
	

	public String getProp(String key, String prop) {
		return this.props.get(key).getProperty(prop);
	}
	
}
