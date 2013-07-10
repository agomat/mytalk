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
  var sizes = ['b', 'KB', 'MB', 'GB', 'TB'];
  if (bytes == 0) return '0';
  var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
  return (bytes / Math.pow(1024, i)).toFixed(2) + ' ' + sizes[i];
});

Handlebars.registerHelper('setIndex', function(value){
    this.index = Number(value + 1); //I needed human readable index, not zero based
});