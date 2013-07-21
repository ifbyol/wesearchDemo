var square = new Sonic({

	width : 100,
	height : 100,

	stepsPerFrame : 4,
	trailLength : 1,
	pointDistance : .01,
	fps : 25,

	fillColor : '#FF7B24',

	setup : function() {
		this._.lineWidth = 10;
	},

	step : function(point, i, f) {

		var progress = point.progress, degAngle = 360 * progress, angle = Math.PI / 180 * degAngle, angleB = Math.PI / 180 * (degAngle - 180), size = i * 5;

		this._.fillRect(Math.cos(angle) * 25 + (50 - size / 2), Math.sin(angle) * 15 + (50 - size / 2), size, size);

		this._.fillStyle = '#0C8300';

		this._.fillRect(Math.cos(angleB) * 15 + (50 - size / 2), Math.sin(angleB) * 25 + (50 - size / 2), size, size);

		if (point.progress == 1) {

			this._.globalAlpha = f < .5 ? 1 - f : f;

			this._.fillStyle = '#000000';

			this._.beginPath();
			this._.arc(50, 50, 5, 0, 360, 0);
			this._.closePath();
			this._.fill();

		}

	},

	path : [['line', 40, 10, 60, 90]]

});
/**
 *create layer 
 */
var play=false;
function createLayer() {
	var created = document.getElementById('loading');
	if (!created) {

		var doc = document.body;
		var div_loading = document.createElement('div');
		div_loading.id = 'loading';
		doc.appendChild(div_loading);

		var section_load = document.getElementById('loading');
		section_load.innerHTML += "<div id=\"image_load\"></div> <blink id=\"load_blink\">LOADING...</blink>";
		if(play==false){
		square.play();
		play=true;
		}
		var section_image = document.getElementById("image_load");
		section_image.appendChild(square.canvas);
	}
}
/**
 *delete layer 
 */
function deleteLayer() {
	var div_loading = document.getElementById('loading');
	if (div_loading) {
		var padre = div_loading.parentNode;
		padre.removeChild(div_loading);

	}
}
