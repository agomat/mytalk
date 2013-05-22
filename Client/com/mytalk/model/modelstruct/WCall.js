MyTalk.WCall = DS.Model.extend({
  list: DS.hasMany('MyTalk.Call'),
  totalByteSent: DS.attr('integer'),
  totalByteReceived: DS.attr('integer'),
  totalDuration: DS.attr('integer')
  
}); 
