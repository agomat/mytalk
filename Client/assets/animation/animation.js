$.fn.dropDownMenu = function(dettach) { 
  $(document).unbind('click');
  $('#menu_area').unbind('click');
  $('#menu_label').unbind('click');

  if( dettach ) return;

  $(document).click(function() {
    $("#menu_area").slideUp(200);
  });

  $('#menu_area').click(function(e) {
    e.stopPropagation();
  });

  $('#menu_label').click(function(e) {
    e.stopPropagation();
    if ($('#menu_area').css('display') == 'block')
      $('#menu_area').slideUp(200);
    else
      $('#menu_area').slideDown(200);
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

$.fn.bindEnterKeyPressed = function() { 
  $("#chat textarea").keyup(function(event){
    if(event.keyCode == 13){
      $("#submitMessage").click();
    }
  });
}

$.fn.adjustSizes = function() { 
  var maxWho = maxDate = 0,
    who = $('.who_call'),
    date = $('.data_call');

  who.each(function(i,v){
    var width = $(v).width();
    if (width > maxWho) {
      maxWho = width;
    }
  });
  who.width(maxWho);

  date.each(function(i,v){
    var width = $(v).width();
    if (width > maxDate) {
      maxDate = width;
    }
  });
  date.width(maxDate);
}

$.fn.infobox = function() {
  $('.infobox').tipsy({gravity: $.fn.tipsy.autoNS});
}

if(!console.debug) {
  console.debug = console.log;
}