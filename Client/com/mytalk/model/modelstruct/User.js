MyTalk.User = DS.Model.extend(MyTalk.DryHelper, {
  username: DS.attr('string'),
  lists: DS.hasMany('MyTalk.List'),
  name: DS.attr('string'),
  surname: DS.attr('string'),
  md5: DS.attr('string'),
  ip: DS.attr('string'),
  online: DS.attr('boolean'),
  unmatched: DS.attr('boolean'), // TODO: per mattia: tenere questa proprietà offline

});


