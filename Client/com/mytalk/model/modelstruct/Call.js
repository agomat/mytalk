MyTalk.Call = DS.Model.extend({
  speaker: DS.belongsTo('MyTalk.User'),
  caller: DS.attr('boolean'),
  startDate: DS.attr('date'),
  duration: DS.attr('integer'),
  byteSent: DS.attr('integer'),
  byteReceived: DS.attr('integer')
  
});