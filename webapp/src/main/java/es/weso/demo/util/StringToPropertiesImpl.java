package es.weso.demo.util;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Property;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.PropertiesImpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StringToPropertiesImpl {
	
	protected static Logger logger = Logger.getLogger(StringToSubjectsImpl.class);

	public static Properties parse(String data) {
		Properties properties;
		JsonParser parser = new JsonParser();
		 JsonObject matters = parser.parse(data).getAsJsonObject();
		 properties = jsonArrayToProperties(matters.get("properties").getAsJsonArray());
		return properties;
	}
	
	 private static Properties jsonArrayToProperties(JsonArray array) {
	        Properties properties = new PropertiesImpl();
	        Iterator<JsonElement> it = array.iterator();
	        while(it.hasNext()) {
	            JsonObject property = it.next().getAsJsonObject();
	            Property prop = new JenaPropertyImpl(
	            		property.get("uri").getAsString(), 
	            		property.get("label").getAsString(), 
	            		 property.get("description").getAsString());
	            properties.addProperty(prop);
	        }
	        return properties;
    }

}
