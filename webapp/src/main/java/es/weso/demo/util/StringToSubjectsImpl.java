package es.weso.demo.util;

import java.util.Iterator;

import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.SubjectsImpl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StringToSubjectsImpl {

	public static Matters parse(String data) {
		Matters subjects;
		JsonParser parser = new JsonParser();
		 JsonObject matters = parser.parse(data).getAsJsonObject();
		 subjects = jsonArrayToMatters(matters.get("matters").getAsJsonArray());
		return subjects;
	}
	
	 private static Matters jsonArrayToMatters(JsonArray array) {
	        Matters matters = new SubjectsImpl();
	        Iterator<JsonElement> it = array.iterator();
	        while(it.hasNext()) {
	            JsonObject matter = it.next().getAsJsonObject();
	            Matter mat = new MatterImpl(matter.get("label").getAsString() ,
	                    matter.get("uri").getAsString(),
	                    matter.get("description").getAsString());
	            matters.addMatter(mat);
	        }
	        return matters;
    }

}
