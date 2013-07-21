package es.weso.demo.rest;

import java.io.IOException;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;

import weso.mediator.config.Configuration;

import com.sun.jersey.api.json.JSONWithPadding;

import es.weso.demo.exceptions.DemoException;
import es.weso.demo.util.QueryExecutor;
import es.weso.demo.util.Results;

@Path("bcn")
public class BCNServices extends Services{	
	
	public BCNServices() throws WesearchException, OntoModelException, 
		IOException, DemoException {
		wesearch = BCNWesearch.getWesearch();
	}

	@Override
	@POST
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@Path("buscar")
	public Response search(@FormParam("query") String query,
			@QueryParam("callback") @DefaultValue("demo") String callback) {
		Results result = QueryExecutor.execute(query,
				Configuration.getProperty("sparql_endpoint_bcn"));
		JSONWithPadding response = new JSONWithPadding(result, callback);
        return Response.ok(response).build();
	}

}
