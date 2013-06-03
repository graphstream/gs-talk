// ================
// = slides stuff =
// ================
(function( $, document, window, undefined ) {
	window.slides = window.slides || {};
	console.log("this -> "+window.slides+ (window.slides === 'undefined'));
	window.slides.SCALE = {
		w: 800,
		h: 500
	};

	$(function() {

		rescale(window.slides.SCALE);

		$.jmpress("template", "mytemplate", {
			children: function(idx) {
				return {
					x: idx * window.slides.SCALE.w,
					y: 0
				};
			}
		});

		//outlook();

		$('#simple').jmpress({
			animation: {
				transitionDuration: '0s'
			}
		});


		step_events();
		create_menu();
		prettyPrint();
		jsfiddle_links();

	}());


	function jsfiddle_links() {
		var list = $('pre[data-src]');
		for (var i = 0; i < list.length; i++) {
			$(list[i]).append("<p class='jsfiddle'><a href='" + $(list[i]).attr('data-src') + "'><img  src='img/jsfiddle.png'></a></p>");
		}
	}

	function step_events() {
		$('.step').on('enterStep', function(event) {
			if ($(this).hasClass("active")) {
				if ($(this).hasClass("bad")) {
					$('body').addClass("bad");
				} else if ($(this).hasClass("outlook")) {
					$('body').addClass("outlook");
				}
			}

			$('body').removeClass("count");
			return true;
		}).on('leaveStep', function(event) {
			if ($(this).hasClass("bad")) {
				$('body').removeClass("bad");
			} else if ($(this).hasClass("outlook")) {
				$('body').removeClass("outlook");
			}
			return true;
		});
	}

	function create_menu() {

		$('.step').each(function(i, e) {
			console.log(e);
			$("#menu > ul:last-child").append("<li>&nbsp;<a href='#/" + $(e).attr('id') + "'>" + $(e).attr('id').replace("_"," ") + "</a>&nbsp;</li>");
		});

		// events
		$("#menu_handle").mouseover(function(ev) {
			$('#menu').addClass("active");
			$('#menu_handle').hide(300);
			return true;
		});
		$("#menu").mouseleave(function(ev) {
			$('#menu').removeClass("active");
			$('#menu_handle').show(300);
			return true;
		});
	}

	function outlook() {
		$.getJSON('data/outline.json', function(data) {
			var items = [];
			$.each(data.content, function(key, value) {
				var subitems = [];
				console.log(value.title);
				$.each(value.content, function(key, value) {
					subitems.push(value.title);
				});
				items.push('<li>' + value.title + '<ul>' + subitems.join(" - ") + '</ul></li>');
			});
			$("<h2>" + data.title + "</h2>").appendTo('.step.outlook');
			$("<ol/>", {
				html: items.join("")
			}).appendTo('.step.outlook').addClass("x2");

		}).error(function() { console.log("Problem with json format of the outline.json file"); });
	}

	function rescale() {

		// determine scale
		var W = window.innerWidth;
		var H = window.innerHeight;
		var screen_ratio = W / H;
		var slide_ratio = window.slides.SCALE.w / window.slides.SCALE.h;
		if (screen_ratio > slide_ratio) {
			window.slides.SCALE.ratio = (H / window.slides.SCALE.h);
			window.slides.SCALE.w = parseInt(window.slides.SCALE.w * window.slides.SCALE.ratio, 10);
			window.slides.SCALE.h = (H);
		} else {
			//scale to W
			window.slides.SCALE.ratio = (W / window.slides.SCALE.w);
			window.slides.SCALE.w = W;
			window.slides.SCALE.h = parseInt(window.slides.SCALE.h * window.slides.SCALE.ratio, 10);
		}

		// rescale all the slides
		$('.step').width(window.slides.SCALE.w);
		$('.step').height(window.slides.SCALE.h);

		// update font size
		window.slides.SCALE.font_size = parseInt($("body").css('font-size'), 10);
		window.slides.SCALE.font_size = Math.floor(window.slides.SCALE.font_size * window.slides.SCALE.ratio);
		$("body").css('font-size', window.slides.SCALE.font_size + "px");

		// update size of embeded objects
		$('object > embed').each(function() {
			$(this).width($(this).width() * window.slides.SCALE.ratio);
			$(this).height($(this).height() * window.slides.SCALE.ratio);
		});
		$('object').each(function() {
			$(this).css("height", Math.floor(parseInt($(this).css("height"), 10) * window.slides.SCALE.ratio) + "px");
			$(this).css("width", Math.floor(parseInt($(this).css("width"), 10) * window.slides.SCALE.ratio) + "px");
		});

	}


}(jQuery, document, window));