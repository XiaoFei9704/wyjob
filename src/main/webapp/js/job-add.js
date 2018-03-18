$(document).ready(function(){
	$(".vali-btn").on("click", function(){
		$("#information").removeClass("none");
	});
	$(".ok-btn").on("click", function(){
		$(this).parents(".shade").addClass("none");
	});
	$(".cancel-btn").on("click", function(){
		$(this).parents(".shade").addClass("none");
	});
});