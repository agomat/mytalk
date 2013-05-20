MyTalk.WCall = DS.Model.extend({
  list: DS.hasMany('MyTalk.Call'),
  totalByteSent: DS.attr('integer'),
  totalByteReceived: DS.attr('integer'),
  totalDuration: DS.attr('integer'),


  getObject: function() {	// ok, valutarne la reale utilità
    return this.get('list');
  }
  
}); 
