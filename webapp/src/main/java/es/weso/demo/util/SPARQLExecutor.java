package es.weso.demo.util;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.Syntax;

public class SPARQLExecutor {
	
	public static QueryExecution executeQuery(String queryStr, 
			String urlEndpoint) {
        Query query = QueryFactory.create(queryStr, Syntax.syntaxARQ);
        return QueryExecutionFactory.sparqlService(urlEndpoint, query);
    }

}
