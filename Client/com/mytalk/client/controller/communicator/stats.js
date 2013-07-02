// Display statistics/*
/*
var bytePrev = 0;
var timestampPrev = 0;

var statCollector = setInterval(function() {
  var display = function(str) {
    $('bitrate').innerHTML = str;
  }

      PC.getStats(function(stats) {
        var statsString = '';
        var results = stats.result();
        var bitrateText = 'No bitrate stats';
        for (var i = 0; i < results.length; ++i) {
          var res = results[i];
          statsString += '<h3>Report ';
          statsString += i;
          statsString += '</h3>';
            statsString += dumpStats(res);
            // The bandwidth info for video is in a type ssrc stats record
            // with googFrameHeightReceived defined.
            // Should check for mediatype = video, but this is not
            // implemented yet.
            if (res.type == 'ssrc' && res.stat('googFrameHeightReceived')) {
              var bytesNow = res.stat('bytesReceived');
              if (timestampPrev > 0) {
                 var bitRate = Math.round((bytesNow - bytesPrev) * 8 /
                    (res.timestamp - timestampPrev));
                 bitrateText = bitRate + ' kbits/sec';
              }
              timestampPrev = res.timestamp;
              bytesPrev = bytesNow;
            }
          
        }
        $('stats').innerHTML = statsString;
        display(bitrateText);
      });

}, 1000);

// Dumping a stats variable as a string.
// might be named toString?
function dumpStats(obj) {
  var statsString = 'Timestamp:';
  statsString += obj.timestamp;
  if (obj.id) {
     statsString += "<br>id ";
     statsString += obj.id;
  }
  if (obj.type) {
     statsString += " type ";
     statsString += obj.type;
  }
  if (obj.names) {
    names = obj.names();
    for (var i = 0; i < names.length; ++i) {
       statsString += '<br>';
       statsString += names[i];
       statsString += ':';
       statsString += obj.stat(names[i]);
    }
  } else {
    if (obj.stat('audioOutputLevel')) {
      statsString += "audioOutputLevel: ";
      statsString += obj.stat('audioOutputLevel');
      statsString += "<br>";
    }
  }
  return statsString;
}*/