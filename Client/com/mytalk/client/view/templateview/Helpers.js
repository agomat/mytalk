Ember.Handlebars.registerBoundHelper('date',function(date){
  if(date!=null){
   return date.format("HH:mm:ss");
  }
});

Ember.Handlebars.registerBoundHelper('calldate',function(date){
  return new moment(date).format('LLLL');
});

Ember.Handlebars.registerBoundHelper('hourconversion',function(seconds){
  var hstring="";
  var h=parseInt(seconds/3600);
  seconds=seconds-(h*3600);
  var min = parseInt(seconds/60);
  seconds=seconds-(min*60);
  if(h>0){
    if(h<10){
      hstring+="0"+h+"h ";
    }
    else{
      hstring+=h+"h ";
    }
  }
  if(min>0){
    if(min<10){
     hstring+="0"+min+"m ";
    }
    else{
     hstring+=min+"m ";
    }
  }
  if(seconds>0){
    hstring+=seconds+"s ";
  }
  return hstring;
});

Ember.Handlebars.registerBoundHelper('conversion',function(bytes){
  var precision = 2;
  var kilobyte = 1024;
  var megabyte = kilobyte * 1024;
  var gigabyte = megabyte * 1024;
  var terabyte = gigabyte * 1024;
  
  if ((bytes >= 0) && (bytes < kilobyte)) {
    return bytes + ' B';
 
  } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
    return (bytes / kilobyte).toFixed(precision) + ' KB';
 
  } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
    return (bytes / megabyte).toFixed(precision) + ' MB';
 
  } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
    return (bytes / gigabyte).toFixed(precision) + ' GB';
 
  } else if (bytes >= terabyte) {
    return (bytes / terabyte).toFixed(precision) + ' TB';
 
  } else {
    return bytes + ' B';
  }
});
