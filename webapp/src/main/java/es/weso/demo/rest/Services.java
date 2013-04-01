package es.weso.demo.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.weso.utils.WesearchException;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.domain.Matters;
import org.weso.wesearch.domain.Properties;
import org.weso.wesearch.domain.Query;
import org.weso.wesearch.domain.ValueSelector;
import org.weso.wesearch.domain.impl.JenaPropertyImpl;
import org.weso.wesearch.domain.impl.MatterImpl;
import org.weso.wesearch.domain.impl.SPARQLQuery;
import org.weso.wesearch.factories.WesearchFactory;
import org.weso.wesearch.factories.impl.JenaWesearchFactory;

import com.google.gson.Gson;
import com.sun.jersey.api.json.JSONWithPadding;

import es.weso.demo.util.StringToSPARQLQuery;
import es.weso.demo.util.StringToValueSelector;

public abstract class Services {
	
	protected WesearchFactory factory = new JenaWesearchFactory();
	protected Wesearch wesearch;
	protected Gson gson = new Gson();
	
	@GET
	@Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
	@Path("obtenerClases")
	public Response getMatters(
			@QueryParam("string") @DefaultValue("") String stem, 
			@QueryParam("callback") @DefaultValue("demo") String callback) {
		JSONWithPadding response = null;
		Matters result = null;		
		try {
			result = wesearch.getMatters(stem);
			response = new JSONWithPadding(result, callback);
			return Response.ok(response).build();
		} catch (WesearchException e) {
			return Response.serverError().build();
		}
	}
	
	@POST
    @Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("obtenerPropiedades")
	public Response getProperties(MatterImpl matter, 
			@QueryParam("string") @DefaultValue("") String stem,
			@QueryParam("callback") @DefaultValue("demo") String callback) {
		JSONWithPadding response = null;
		Properties result = null;
		try {
			result = wesearch.getProperties(matter, stem);
			response = new JSONWithPadding(result, callback);
			return Response.ok(response).build();
		} catch (WesearchException e) {
			return Response.serverError().build();
		}
	}
	
	@POST
    @Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
    @Path("obtenerSelector")
    public Response getValueSelector(@FormParam("matter")String matter,
              @FormParam("property") String property,
              @QueryParam("callback") @DefaultValue("demo") String callback) {
        MatterImpl matterObj = gson.fromJson(matter, MatterImpl.class);
        JenaPropertyImpl propertyObj = gson.fromJson(property, 
        		JenaPropertyImpl.class);
        ValueSelector result = null;
        JSONWithPadding response = null;
        try {
            result = wesearch.getValueSelector(matterObj, propertyObj);
            response = new JSONWithPadding(result, callback);
            return Response.ok(response).build();
        } catch (WesearchException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
    @Path("crearConsulta")
    public Response createQuery(@FormParam("matter")String matter,
            @FormParam("property") String property,
            @FormParam("selector") String selector,
            @QueryParam("callback") @DefaultValue("demo") String callback) {
        MatterImpl matterObj = gson.fromJson(matter, MatterImpl.class);
        JenaPropertyImpl propertyObj = gson.fromJson(property, 
        		JenaPropertyImpl.class);
        ValueSelector selectorObj = StringToValueSelector.parse(selector);
        JSONWithPadding response = null;
        try {
            Query result = wesearch.createQuery(matterObj, propertyObj,
                    selectorObj);
            response = new JSONWithPadding(result, callback);
            //return Response.ok(result.obtainQuery()).build();
            return Response.ok(response).build();
        } catch (WesearchException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
    @Path("combinarConsulta")
    public Response combineQuery(@FormParam("query")String query,
             @FormParam("matter") String matter,
             @FormParam("property") String property,
             @FormParam("selector") String selector,
             @QueryParam("callback") @DefaultValue("demo") String callback){
        SPARQLQuery queryObj = null;
        JSONWithPadding response = null;
        try {
            queryObj = StringToSPARQLQuery.parse(query);
            MatterImpl matterObj = gson.fromJson(matter, MatterImpl.class);
            JenaPropertyImpl propertyObj = gson.fromJson(property, 
            		JenaPropertyImpl.class);
            ValueSelector selectorObj = StringToValueSelector.parse(selector);
            Query result = wesearch.combineQuery(queryObj, matterObj, 
            		propertyObj, selectorObj);
            response = new JSONWithPadding(result, callback);
            return Response.ok(response).build();
        } catch (WesearchException e) {
            return Response.serverError().build();
        } catch (IOException e) {
            return Response.serverError().build();
        }
    }

    @POST
    @Produces({ "application/x-javascript", MediaType.APPLICATION_JSON })
    @Path("buscar")
    public abstract Response search(@FormParam("query") String query,
    		@QueryParam("callback") @DefaultValue("demo") String callback);

}
