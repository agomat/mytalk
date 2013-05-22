MyTalk.PersonalData = DS.Model.extend(MyTalk.DryHelper, {
  username: DS.attr('string'),
  password: DS.attr('string'),
  name: DS.attr('string'),
  surname: DS.attr('string'),
  email: DS.attr('string'),
  md5: DS.attr('string')

});
