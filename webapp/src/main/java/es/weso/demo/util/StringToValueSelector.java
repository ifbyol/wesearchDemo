package es.weso.demo.util;

import java.util.Iterator;

import org.weso.wesearch.domain.Matter;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Value;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.SubjectsImpl;
import org.weso.wesearch.domain.impl.ValueSelectorImpl;
import org.weso.wesearch.domain.impl.values.DateValue;
import org.weso.wesearch.domain.impl.values.NumericValue;
import org.weso.wesearch.domain.impl.values.ObjectValue;
import org.weso.wesearch.domain.impl.values.StringValue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StringToValueSelector {

	public static ValueSelector parse(String data) {
        JsonParser parser = new JsonParser();
        JsonObject selectorObj = parser.parse(data).getAsJsonObject();
        String type = selectorObj.get("type").getAsString();
        Value<?> value = obtainValueSelector(type, selectorObj.get("value"));
        ValueSelector result = new ValueSelectorImpl(type);
        result.setValue(value);
        return result;
    }

    private static Value<?> obtainValueSelector(String type, JsonElement value) {
        if(type.equals("textfield") || type.equals("undefined")) {
            return new StringValue(value.getAsString());
        } else if(type.equals("numeric")) {
            return new NumericValue(value.getAsNumber().doubleValue());
        } else if(type.equals("date")) {
            return new DateValue(StringToDateParser.parseCompleteDateExtend(
            		value.getAsString()));
        } else if(type.equals("object")) {
            return new ObjectValue(JsonArrayToMatters(value.getAsJsonObject()
            		.get("value").getAsJsonArray()));
        }
        throw new RuntimeException("Invalid type of value selector");
    }

    private static Matters JsonArrayToMatters(JsonArray array) {
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
