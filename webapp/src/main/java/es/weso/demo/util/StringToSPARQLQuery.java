package es.weso.demo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.weso.wesearch.domain.impl.SPARQLQuery;
import org.weso.wesearch.domain.impl.filters.Filters;
import org.weso.wesearch.domain.impl.filters.Operator;
import org.weso.wesearch.domain.impl.filters.SPARQLFilter;
import org.weso.wesearch.domain.impl.filters.SPARQLFilters;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class StringToSPARQLQuery {
	
	 public static SPARQLQuery parse(String data) throws IOException {
	        JsonParser parser = new JsonParser();
	        JsonObject selectorObj = parser.parse(data).getAsJsonObject();
	        String query = selectorObj.get("query").getAsString();
	        int nextVar = selectorObj.get("nextVar").getAsInt();
	        List<String> clauses = extractClauses(selectorObj);
	        Map<String, Filters> filters = extractFilters(
	        		selectorObj.get("filters").getAsJsonObject());
	        SPARQLQuery result = new SPARQLQuery();
	        result.setClauses(clauses);
	        result.setNextVar(nextVar);
	        result.setQuery(query);
	        result.setFilters(filters);
	        return result;
	    }

	    @SuppressWarnings("rawtypes")
		private static Map<String, Filters> extractFilters(JsonObject filters) {
	        Map<String, Filters> result = new HashMap<String, Filters>();
	        Iterator it = filters.entrySet().iterator();
	        while(it.hasNext()) {
	            Map.Entry entry = (Map.Entry)it.next();
	            String key = (String)entry.getKey();
	            JsonElement element = (JsonElement)entry.getValue();
	            if(element.isJsonNull()) {
	                result.put(key, null);
	            } else {
	                SPARQLFilters filtersObj = createFilters(element);
	                result.put(key, filtersObj);
	            }
	        }
	        return result;
	    }

	    private static SPARQLFilters createFilters(JsonElement element) {
	        String clause = element.getAsJsonObject().get("filter")
	                .getAsJsonObject().get("clause").getAsString();
	        SPARQLFilters result = new SPARQLFilters(new SPARQLFilter(clause));
	        if(!element.getAsJsonObject().get("filters").isJsonNull()) {
	            String op = element.getAsJsonObject().get("op").getAsString();
	            result.setOp(Operator.valueOf(op));
	            result.setFilters(createFilters(element.getAsJsonObject()
	            		.get("filters")));
	        }
	        return result;
	    }


	    private static List<String> extractClauses(JsonObject selectorObj) {
	        List<String> clauses = new LinkedList<String>();
	        JsonArray clausesArray = selectorObj.get("clauses")
	        		.getAsJsonArray();
	        Iterator<JsonElement> it = clausesArray.iterator();
	        while(it.hasNext()) {
	            clauses.add(it.next().getAsString());
	        }
	        return clauses;
	    }

}
