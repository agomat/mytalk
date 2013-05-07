  
/*$("#login").click(function(){
    $("#target").html('<form><input type="text" id="user"><input type="password" id="pass"></form>');
});*/
$(document).ready(function() {

$(document).click(function() {
    $("#menu_area").slideUp(400);
});

$('#menu_area').click(function( e ) {
    e.stopPropagation();
});

$('#menu_label').click(function( e ) {
    e.stopPropagation();
  
  $('#menu_area').slideDown(400, function() {
    // Animation complete.
  });
});
});
