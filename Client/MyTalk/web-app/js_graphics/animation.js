$.fn.dropDownMenu = function() { 
  $(document).unbind('click');
  $('#menu_area').unbind('click');
  $('#menu_label').unbind('click');

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

  $('#menu_area a').click(function(e) {
    setTimeout(function () {
      $(document).click();
    }, 300);
    });
}

$.fn.videoTutorial = function() { 
  $('#video_tutorial').unbind('click');
  $("#video_tutorial").click(function(){
    $(this).css('cursor', 'default');
    $(this).css('opacity', 1);
    $(this).css('border', 'none');
    $(this).attr('controls', '');
    this.play();  
  });
}

