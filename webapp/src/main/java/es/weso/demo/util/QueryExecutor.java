package es.weso.demo.util;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class QueryExecutor {
	
	public static Results execute(String queryString, String url) {
		StringBuilder query = new StringBuilder();
		query.append("PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> \n");
		query.append(queryString);
        Results results = new Results();
        QueryExecution qe = SPARQLExecutor.executeQuery(query.toString(), url);
        ResultSet rs = qe.execSelect();
        while(rs.hasNext()) {
            QuerySolution qs = rs.next();
            String uri = qs.get("res").toString();
            results.add(uri);
        }
        qe.close();
        return results;
    }

}
