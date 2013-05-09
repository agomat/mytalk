$(document).ready(function() {

  $(document).click(function() {
    $("#menu_area").slideUp(400);
  });

  $('#menu_area').click(function(e) {
    e.stopPropagation();
  });

  $('#menu_label').click(function(e) {
    e.stopPropagation();
    if ($('#menu_area').css('display') == 'block')
     $('#menu_area').slideUp(400);
    else
     $('#menu_area').slideDown(400);
  });

  $("#video_tutorial").click(function(){
    $(this).css('cursor', 'default');
    $(this).css('opacity', 1);
    $(this).css('border', 'none');
    $(this).attr('controls', '');
    this.play();  
  });

});

