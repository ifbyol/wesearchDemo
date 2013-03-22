package es.weso.demo.rest;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;
import org.weso.wesearch.model.impl.URLOntologyLoader;

import es.weso.demo.util.QueryExecutor;
import es.weso.demo.util.Results;

import weso.mediator.config.Configuration;

@Path("bcn")
public class BCNServices extends Services{	
	
	public BCNServices() throws WesearchException, OntoModelException, 
		IOException {
		String path = BCNServices.class.getResource(
				Configuration.getProperty("ontologies_bcn")).getPath();
		wesearch = factory.createWesearch(new JenaOntoModelWrapper(
					new URLOntologyLoader(FileUtils.readLines(new File(path), 
							"utf-8"))));
	}

	@Override
	@POST
	@Produces("application/json")
	@Path("buscar")
	public Response search(@FormParam("query") String query) {
		Results result = QueryExecutor.execute(query,
				Configuration.getProperty("sparql_endpoint_bcn"));
        return Response.ok(result).build();
	}

}
