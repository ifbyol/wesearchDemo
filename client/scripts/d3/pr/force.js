var width = 960,
    height = 500;
var r = 15;
var color = d3.scale.category20();

var force = d3.layout.force()
    .charge(-120)
    .linkDistance(30)
    .size([width, height]);

var svg = d3.select("#chart").append("svg")
    .attr("width", width)
    .attr("height", height);

d3.json("datos.json", function(json) {
  force
      .nodes(json.nodes)
      .links(json.links)
      .start();

  var link = svg.selectAll("line.link")
      .data(json.links)
    .enter().append("line")
      .attr("class", "link")
	  .style("stroke-width", function(d) { return Math.sqrt(d.value); });
     // .style("stroke-width", function(d) { return Math.sqrt(d.value); });

  var node = svg.selectAll("circle.node")
      .data(json.nodes)
    .enter().append("circle")
      .attr("class", "node")
      .attr("r", r)
      .style("fill", function(d) { return color(d.group); })
      .call(force.drag);
	  
	 var text = svg.append("g").selectAll("g")
    .data(force.nodes())
	.enter().append("g");

	text.append("text")
		.attr("x", 18)
		.attr("y", ".31em")
	.text(function(d) { return d.name; }); 

  node.append("title")
      .text(function(d) { return d.name; });

  force.on("tick", function() {
    
	link.attr("x1", function(d) { return d.source.x+r; })
        .attr("y1", function(d) { return d.source.y+r; })
        .attr("x2", function(d) { return d.target.x+r; })
        .attr("y2", function(d) { return d.target.y+r; });


 

    node.attr("cx", function(d) { return d.x+r; })
        .attr("cy", function(d) { return d.y+r; });
	
			text.attr("transform", function(d) {return "translate(" + d.x + "," + d.y + ")";});
  });
});
