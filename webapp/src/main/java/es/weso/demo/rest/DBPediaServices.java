package es.weso.demo.rest;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;
import org.weso.wesearch.model.impl.URLOntologyLoader;

import com.sun.jersey.api.json.JSONWithPadding;

import weso.mediator.config.Configuration;
import es.weso.demo.util.QueryExecutor;
import es.weso.demo.util.Results;

@Path("dbpedia")
public class DBPediaServices extends Services{
	
	public DBPediaServices() throws WesearchException, OntoModelException, 
		IOException {
		String path = BCNServices.class.getResource(
				Configuration.getProperty("ontologies_dbpedia")).getPath();
		wesearch = factory.createWesearch(new JenaOntoModelWrapper(
				new URLOntologyLoader(FileUtils.readLines(new File(path), 
						"utf-8"))));
	}

	@Override
	@POST
	@Produces("application/json")
	@Path("buscar")
	public Response search(@FormParam("query") String query,
			@QueryParam("callback") @DefaultValue("demo") String callback) {
		Results result = QueryExecutor.execute(query,
				Configuration.getProperty("sparql_endpoint_dbpedia"));
		JSONWithPadding response = new JSONWithPadding(result, callback);
        return Response.ok(response).build();
	}

}
