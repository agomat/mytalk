MyTalk.Call = DS.Model.extend({
  speaker: DS.belongsTo('MyTalk.User'), //
  caller: DS.attr('boolean'),           //
  startDate: DS.attr('date'),           //

  duration: DS.attr('number'),
  byteSent: DS.attr('number'),
  byteReceived: DS.attr('number'),

  myIP: DS.attr('string'),
  myUserId: DS.attr('string'),
  myRTCinfo: DS.attr('string'),
  speakerIp: DS.attr('string'),
  speakerUserId: DS.attr('string'),
  speakerRTCinfo: DS.attr('string')

});