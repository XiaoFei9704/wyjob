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
    $(".gender-btn").on("click", function(){
        $(".gender-btn").removeClass("focus-btn");
        $(this).addClass("focus-btn");
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
        var eType=$("#add-work").find("> div > div > dl:nth-child(4) > dd > span > input").attr("value");
        var eScale=$("#add-work").find("> div > div > dl:nth-child(5) > dd > span > input").attr("value");
        var industry=$("#add-work").find("> div > div > dl:nth-child(6) > dd > input").val();
        var founc=$("#add-work > div > div > dl:nth-child(7) > dd > span > input").val();
        var post=$("#add-work").find("> div > div > dl:nth-child(8) > dd > input").val();
        var salary=$("#add-work").find("> div > div > dl:nth-child(9) > dd > input").val();
        var type=$("#add-work > div > div > dl:nth-child(10) > dd > span > input").attr("value");
        var descripction=$("#add-work").find("> div > div > dl.info-textarea > dd > textarea").val();
        if(start!==""&&end!==""&&eName!==""&&founc!==""&&post!==""&&type!==""){
            var content="<div class=\"form-line\">\n" +
                "\t\t\t\t\t<span>"+eName+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+start+"-"+end+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+founc+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+post+"</span>\n" +
                "\t\t\t\t\t<em class=\"vline\"></em>\n" +
                "\t\t\t\t\t<span>"+type+"</span>\n" +
                "\t\t\t\t\t<a class=\"delete-btn fr\">删除</a>\n" +
                "\t\t\t\t</div>";
            $("#main").find("> div > div:nth-child(3)").append(content);
        }
    });
    $(document).on("click","#add-expertise > div > div > dl:nth-child(5) > button.ok-btn",function () {
        var skillName=$("#add-expertise").find("> div > div > dl:nth-child(2) > dd > span > input").val();
        var usingTime=$("#add-expertise").find ("> div > div > dl:nth-child(3) > dd > input").val();
        var masteryDegree=$("#add-expertise").find("> div > div > dl:nth-child(4) > dd > span > input").attr("value");
        if(skillName!==""&&usingTime!==""&&masteryDegree!==""){
            var content="<div class=\"form-line\">"+
                "<span>"+skillName+"</span>"+
                "<em class=\"vline\"></em>"+
                "<span>使用时间：</span>"+
                "<span>"+usingTime+"</span>"+
                "<em class=\"vline\"></em>"+
                "<span>"+masteryDegree+"</span>"+
                "<a class=\"delete-btn fr\">删除</a>"+
                "</div>";
            $("#main").find("> div > div:nth-child(4)").append(content);
        }
    });
    $(document).on("click","#add-language > div > div > dl:nth-child(5) > button.ok-btn",function(){
        var language=$("#add-language > div > div > dl:nth-child(2) > dd > span > input").val();
        var rwAbility=$("#add-language > div > div > dl:nth-child(3) > dd > span > input").val();
        var lsAbility=$("#add-language > div > div > dl:nth-child(4) > dd > span > input").val();
        if (language!==""&&rwAbility!==""&&lsAbility!="") {
            var content = "<div class=\"form-line\">"+
                "<span>"+language+"</span>"+
                "<em class='vline'></em>"+
				"<span>读写能力：</span>"+
				"<span>"+rwAbility+"</span>"+
				"<em class=\"vline\"></em>"+
				"<span>听说能力：</span>"+
				"<span>"+lsAbility+"</span>"+
				"<a class=\"delete-btn fr\">删除</a>"+
				"</div>";
        }
        $("#main").find("> div > div:nth-child(5)").append(content);
    });
    $(document).on("click","#add-bias > div > div > dl:nth-child(5) > button.ok-btn",function(){
        var workingLocation=$("#add-bias").find("> div > div > dl:nth-child(2) > dd > input").val();
        var workingIndutry=$("#add-bias").find("> div > div > dl:nth-child(3) > dd > input").val();
        var workingFunction=$("#add-bias").find("> div > div > dl:nth-child(4) > dd > input").val();
        if (workingFunction!==""&&workingIndutry!==""&&workingLocation!="") {
            var content = "<div class=\"form-line\">"+
                "<span>"+workingLocation+"</span>"+
                "<em class='vline'></em>"+
            "<span>"+workingIndutry+"</span>"+
            "<em class=\"vline\"></em>"+
            "<span>"+workingFunction+"</span>"+
            "<a class=\"delete-btn fr\">删除</a>"+
            "</div>";
        }
        $("#main").find("> div > div:nth-child(6)").append(content);
    });
});