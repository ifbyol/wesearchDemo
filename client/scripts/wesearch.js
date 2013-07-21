/*
 * This function has to ask to wesearch all matters given a 
 * string and the example ('bcn' or 'dbpedia')
 */
function getMatters(stem, example) {
	createLayer();
	if(stem == null) {
		stem = '';
	}
	var url = getBaseUrl(example, 'getmatters') + stem;
	console.log('Stem: ' + stem);
	console.log('Example: ' + example);
	console.log('URL service: ' + url);
	$.ajax({
		type: 'GET',
		url: url,
		cache: true,
		dataType: 'jsonp',
		jsonpCallback: 'demo',
		success: function (data, status, jqXHR) {
			console.log('data: ' + data);
			createClassesDropdown(data);
		},
		error: function (jqXHR, textStatus, errorThrown){
			console.log('error: ' + errorThrown);
		},
		statusCode: {
			404: function() {
				console.log('URL not found');
			}
		},
		complete: function(jqXHR, textStatus) {
			deleteLayer();
		}
	});
}

/*
 * This function has to ask to wesearch all propeties given a matter, a 
 * string and the example ('bcn' or 'dbpedia')
 */
function getProperties(matter, stem, example) {
	createLayer();
	if(stem == null) {
		stem = '';
	}
	var url = getBaseUrl(example, 'getproperties') + stem;
	console.log('Data: ' + matter);
	console.log('URL: ' + url);
	console.log('JSON: ' + matter);
	var data = 'url=' + encodeURIComponent(url) + '&data=' + encodeURIComponent(matter); 
	console.log(data);
	$.ajax({
		type: 'POST',
		url: 'php/peticion.php',
		dataType: 'jsonp',
		data: data,
		jsonpCallback: 'demo',
		success: function (data, status, jqXHR) {
			console.log('response: ' + data);
			createPropertyDropdown(data);
		},
		error: function (jqXHR, textStatus, errorThrown){
			console.log('error: ' + errorThrown);
		},
		statusCode: {
			404: function() {
				console.log('URL not found');
			}
		},
		complete: function(jqXHR, textStatus) {
			deleteLayer();
		}
	});
}

/*
 *This function has to ask to wesearch the value selector from a given property and a matter 
 */
function getValueSelector(matter, property, example) {
	createLayer();
	var params = 'matter=' + matter + '&property=' + property;
	console.log('Parameters: ' + params);
	var url = getBaseUrl(example, 'getselector');
	console.log('URL: ' + url);
	var data = 'url=' + encodeURIComponent(url) + '&matter=' + encodeURIComponent(matter) + 
		'&property=' + encodeURIComponent(property);
	console.log('Data POST:' + data);
	$.ajax({
		type: 'POST',
		url: 'php/servicios.php',
		dataType: 'jsonp',
		data: data,
		cache: true,
		jsonpCallback: 'demo',
		success: function (data, status, jqXHR) {
			console.log('data: ' + JSON.stringify(data));
			lastSelector = data;
			addSelectorInput(data);
		},
		error: function (jqXHR, textStatus, errorThrown){
			console.log('error: ' + textStatus);
		},
		statusCode: {
			404: function() {
				console.log('URL not found');
			}
		},
		complete: function(jqXHR, textStatus) {
			deleteLayer();
		}
	});
}

/*
 * This function has to ask to wesearch create a query from a given matter, property and selector for
 * a example ('bcn' or 'dbpedia')
 */
function createQuery(matter, property, selector, example) {
	createLayer();
	var url = getBaseUrl(example, 'createquery');
	console.log('URL: ' + url);
	console.log('Matter: ' + matter);
	console.log('Property: ' + property);
	console.log('Selector: ' + selector);
	var data = 'url=' + encodeURIComponent(url) + 
		'&matter=' + encodeURIComponent(matter) + 
		'&property=' + encodeURIComponent(property) + 
		'&selector=' + encodeURIComponent(selector);
		console.log('Data: ' + data);
	$.ajax({
		type: 'POST',
		url: 'php/servicios.php',
		dataType: 'jsonp',
		data: data,
		cache: true,
		jsonpCallback: 'demo',
		success: function (data, status, jqXHR) {
			console.log('Data: ' + JSON.stringify(data));
			lastQuery = data;
			showQuery(lastQuery['query']);
		},
		error: function (jqXHR, textStatus, errorThrown){
			console.log('error: ' + errorThrown);
		},
		statusCode: {
			404: function() {
				console.log('URL not found');
			}
		},
		complete: function(jqXHR, textStatus) {
			deleteLayer();
		}
	});
}

/*
 * This function has to ask to wesearch add new sentences to a given query from a given matter, 
 * property and selector
 */
function combineQuery(matter, property, selector, query, example){
	createLayer();
	console.log('Data: ' + data);
	var url = getBaseUrl(example, 'combinequery');
	console.log('Matter: ' + matter);
	console.log('Property: ' + property);
	console.log('Selector: ' + selector);
	console.log('Query: ' + /*JSON.stringify(*/query/*)*/);
	var data = 'url=' + encodeURIComponent(url) + 
		'&matter=' + encodeURIComponent(matter) + 
		'&property=' + encodeURIComponent(property) + 
		'&selector=' + encodeURIComponent(selector) + 
		'&query=' + encodeURIComponent(/*JSON.stringify(*/query/*)*/);
	console.log('URL: ' + url);
	$.ajax({
		type: 'POST',
		url: 'php/servicios.php',
		dataType: 'jsonp',
		data: data,
		cache: true,
		jsonpCallback: 'demo',
		success: function (data, status, jqXHR) {
			console.log('data: ' + JSON.stringify(data));
			lastQuery = data;
			showQuery(lastQuery['query']);
		},
		error: function (jqXHR, textStatus, errorThrown){
			console.log('error: ' + errorThrown);
		},
		statusCode: {
			404: function() {
				console.log('URL not found');
			}
		},
		complete: function(jqXHR, textStatus) {
			deleteLayer();
		}
	});
}

/*
 * This function has to execute the query that has form the user
 */
function search(query, example) {
	createLayer();
	var url = getBaseUrl(example, 'search');
	console.log('Query: ' + query);
	var data = 'url=' + encodeURIComponent(url) + '&query=' + encodeURIComponent(query);
	console.log('Data: ' + data);
	$.ajax({
		type: 'POST',
		url: 'php/servicios.php',
		dataType: 'jsonp',
		data: data,
		cache: true,
		jsonpCallback: 'demo',
		success: function (data, status, jqXHR) {
			console.log('Data: ' + JSON.stringify(data));
			showResults(data['results']);			
		},
		error: function (jqXHR, textStatus, errorThrown){
			console.log('error: ' + errorThrown);
		},
		statusCode: {
			404: function() {
				console.log('URL not found');
			}
		},
		complete: function(jqXHR, textStatus) {
			deleteLayer();
		}
	});
}

/*
 * This function generates base url of a service from a example ('bcn' or 'dbpedia')
 * an the name of the configuration property that contains the name of the service 
 */
function getBaseUrl(example, functionName) {
	var configSelector = 'services-base-uri-' + example;
	var url = config[configSelector] + config[functionName];
	console.log('Config selector: ' + configSelector);
	return url;
}
