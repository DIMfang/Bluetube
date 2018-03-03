package backend.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Props {
	
	private static Props propsInstance = null;
	private Properties props = null; 
	
	private Props() {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream("");
			prop.load(is);
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
	

	public String getProp(String prop) {
		return this.props.getProperty(prop);
	}
	
}
