package es.weso.demo.util;

import java.util.LinkedList;
import java.util.List;

public class Results {

	private List<String> results;

    public Results() {
        results = new LinkedList<String>();
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public void add(String uri) {
        if(uri != null) {
            results.add(uri);
        }
    }
}
