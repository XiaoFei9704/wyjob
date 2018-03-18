$(document).ready(function(){
  $(".scale").click(function(){
    $(".scale").removeClass("selected");
    $(this).addClass("selected");
  });
  $(".industry").click(function(){
    $(".industry").removeClass("selected");
    $(this).addClass("selected");
  });
  $(".enterprise-type").click(function(){
    $(".enterprise-type").removeClass("selected");
    $(this).addClass("selected");
  });
});
// $(document).ready(function(){
//   $("div#industry").click(function(){
//     // $("div#industry").removeClass("selected");
//     $(this).addClass("selected");
//   });
// });