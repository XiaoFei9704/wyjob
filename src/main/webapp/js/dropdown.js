$(document).ready(function(){
  $(".dropdown-menu").hide(); 
  $(".ipt").on("click", function(){
    $(this).parent().siblings(".dropdown-menu").show(); 
  });
  $(".dropdown-menu").find("li").each(function () {
    $(this).on("click", function () {
      isBox = false;
      var text = $(this).text();
      $(this).parent().parent().siblings(".dropdown-select").children(".ipt").val(text);
      $(".dropdown-menu").hide();
    })
  })
  $('.ipt').blur(function(){ 
    $(".dropdown-menu").delay(200).hide(100); 
  });

  $(".dropdown-menu2").hide(); 
  $(".ipt2").focus(function () { // input绑定焦点事件，触发时打开焦点开关
    $(this).parent().siblings(".dropdown-menu2").show(); 
  });

  $(".dropdown-menu2").find("li").each(function () { // 传值给input，同时关闭焦点开关
    $(this).on("click", function () {
      isBox = false;
      var text = $(this).text();
      $(this).parent().parent().siblings(".dropdown-select2").children(".ipt2").val(text);
      $(".dropdown-menu2").hide();
    })
  })
  $('.ipt2').blur(function(){ 
    $(".dropdown-menu2").delay(100).hide(100); 
  });
})