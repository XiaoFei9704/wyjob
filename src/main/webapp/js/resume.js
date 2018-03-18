$(document).ready(function(){
	$("#education-btn").on("click", function(){
		$("#add-education").removeClass("none");
	});
	$("#language-btn").on("click", function(){
		$("#add-language").removeClass("none");
	});
	$("#expertise-btn").on("click", function(){
		$("#add-expertise").removeClass("none");
	});
	$("#work-btn").on("click", function(){
		$("#add-work").removeClass("none");
	});
	$("#bias-btn").on("click", function(){
		$("#add-bias").removeClass("none");
	});
	$(".ok-btn").on("click", function(){
		$(this).parents(".shade").addClass("none");
	});
	$(".cancel-btn").on("click", function(){
		$(this).parents(".shade").addClass("none");
	});
	$(document).on("click","#add-education > div > div > dl:nth-child(5) > button.ok-btn",function () {
		var start=$("#add-education").find("> div > div > dl:nth-child(2) > dd > input:nth-child(1)").val();
		var end=$("#add-education").find("> div > div > dl:nth-child(2) > dd > input:nth-child(2)").val();
		var school=$("#add-education").find("> div > div > dl:nth-child(3) > dd > input").val();
		var depart=$("#add-education").find("> div > div > dl:nth-child(4) > dd > input").val();
		if(start!==""&&end!==""&&school!==""&&depart!==""){
			var content="<div class=\"form-line\">\n" +
                "\t\t\t\t\t<span>"+school+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+start+"-"+end+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+depart+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>本科</span>\n" +
                "\t\t\t\t\t<a class=\"delete-btn fr\">删除</a>\n" +
                "\t\t\t\t</div>";
			$("#info-experience").append(content);
		}
    });
	$(document).on("click","#add-work > div > div > button.ok-btn",function () {
		var start=$("#add-work").find("> div > div > dl:nth-child(2) > dd > input:nth-child(1)").val();
		var end=$("#add-work").find("> div > div > dl:nth-child(2) > dd > input:nth-child(2)").val();
		var eName=$("#add-work").find("> div > div > dl:nth-child(3) > dd > input").val();
		var f=$("#add-work").find("> div > div > dl:nth-child(7) > dd > input").val();
		var post=$("#add-work").find("> div > div > dl:nth-child(8) > dd > input").val();
		var type=$("#add-work").find("> div > div > dl:nth-child(10) > dd > span > input").attr("value");
		if(start!==""&&end!==""&&eName!==""&&f!==""&&post!==""&&type!==""){
			var content="<div class=\"form-line\">\n" +
                "\t\t\t\t\t<span>"+eName+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+start+"-"+end+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+f+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+post+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+type+"</span>\n" +
                "\t\t\t\t\t<a class=\"delete-btn fr\">删除</a>\n" +
                "\t\t\t\t</div>";
			$("#main").find("> div > div:nth-child(3)").append(content);
		}
    });
});