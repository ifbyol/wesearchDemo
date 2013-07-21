package es.weso.demo.rest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.weso.utils.OntoModelException;
import org.weso.utils.WesearchException;
import org.weso.wesearch.Wesearch;
import org.weso.wesearch.factories.impl.JenaWesearchFactory;
import org.weso.wesearch.model.impl.FileOntologyLoader;
import org.weso.wesearch.model.impl.JenaOntoModelWrapper;

import weso.mediator.config.Configuration;

public class DBPediaWesearch {

	private static Wesearch wesearch = null;
	
	public static Wesearch getWesearch() throws WesearchException, OntoModelException, IOException {
		if(wesearch == null) {
			String path = BCNServices.class.getResource(
					Configuration.getProperty("ontologies_dbpedia")).getPath();
			List<String> files = FileUtils.readLines(new File(path), 
					"utf-8");
			String[] uris = new String[files.size()];
			for(int i = 0; i < files.size(); i++) {
				 uris[i] = BCNServices.class.getResource(files.get(i)).getPath();
			}
			wesearch = new JenaWesearchFactory().createWesearch(new JenaOntoModelWrapper(
					new FileOntologyLoader(uris)));
		}
		return wesearch;
	}

}
