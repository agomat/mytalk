/**
* Filename: IPAddressProxy.js
* Package: com.mytalk.client [...]
* Author: Mattia Agostinetto
* Editor: Sublime Text 2
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-01 | MA        | [+] Creazione del proxy IPAddress
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk.IPAddressProxy = Ember.Mixin.create({
    ip: undefined, // binding con PersonalData.IP?
    IP: function() {
        // Lazy creation
        if(!this.get('ip')) {
            $.ajax({
              async: false,
              method: 'GET',
			  url: 'http://' + location.host + '/MyTalk/',
			  context: this
			}).done(function( ip ) {
              ip = $.trim(ip) + ':0';
			  console.log('My IP is: ' + ip);
			  this.set('ip', ip);
			});
        }
        return this.get('ip');
    }.property('ip')
});