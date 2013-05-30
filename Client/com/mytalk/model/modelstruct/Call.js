MyTalk.Call = DS.Model.extend({
  speaker: DS.belongsTo('MyTalk.User'),
  caller: DS.attr('boolean'),
  startDate: DS.attr('date'),
  duration: DS.attr('number'),
  byteSent: DS.attr('number'),
  byteReceived: DS.attr('number')
  
});