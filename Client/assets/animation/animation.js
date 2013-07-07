$.fn.dropDownMenu = function() { 
  $(document).unbind('click');
  $('#menu_area').unbind('click');
  $('#menu_label').unbind('click');

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


    function addFiles(files) {
        var file = files[0]; // FileList object
        FS.prepareToReadFile(file.size);
        var reader = new FileReader();
        var chunkId = 0;
        var chunksPerSlice = 20000;
        var sliceSize = chunksPerSlice*FS.CHUNK_SIZE;
        var blob;

        reader.onloadend = function(evt) {
            if (evt.target.readyState == FileReader.DONE) { // DONE == 2
                FS.addChunks(evt.target.result);
                chunkId++;
                if((chunkId+1)*sliceSize < file.size){
                    blob = file.slice(chunkId*sliceSize,(chunkId+1)*sliceSize);
                    reader.readAsArrayBuffer(blob);
                }else if(chunkId*sliceSize < file.size){
                    blob = file.slice(chunkId*sliceSize,file.size);
                    reader.readAsArrayBuffer(blob);
                }else{
                    meta0 = {};
                    meta0.numOfChunks = FS.numOfChunksInFile;
                    meta0.size = files[0].size;
                    meta0.name = files[0].name;
                    meta0.lastModifiedDate = files[0].lastModifiedDate;
                    meta0.type = files[0].type;
                    var i = 0;
                    var ACC = new Array();
                    while( FS.chunks.hasOwnProperty(i) ) {
                      var WARI = {
                        filename: meta0.name, 
                        filetype: meta0.type,
                        numOfChunks: meta0.numOfChunks,
                        chunkId: i,
                        chunk: Base64Binary.encode(FS.chunks[i])
                      };
                      FS.chunks[i] = WARI;
                      ++i;
                    }
                    window.foo = function(i){
                      Ember.run.later(this,function(){
                        console.log(i);
                        window.RTCmanager.send(JSON.stringify( window.FS.chunks[i] ));
                        if (window.FS.chunks[i+1]) window.foo(i+1);
                      },200);
                    };
                    var i = 0;
                    window.RTCmanager.send(JSON.stringify( window.FS.chunks[i] ));
                    if (window.FS.chunks[i+1]) window.foo(i+1);
                }
            }
        };

        blob = file.slice(chunkId*sliceSize,(chunkId+1)*sliceSize);
        reader.readAsArrayBuffer(blob);
        window.reader = reader;
    }


window.FS = new client();