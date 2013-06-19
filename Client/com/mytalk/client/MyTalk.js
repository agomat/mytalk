/**
* Filename: app.js
* Package: com.mytalk.client
* Dependencies:
* Author: Griggio Massimo
* Date: 2013-04-26
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-04-05 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

MyTalk = Ember.Application.create({
  LOG_TRANSITIONS: true
});

MyTalk.processor = Ember.Namespace.create({});

/*


Ember.run.later(this, function(){

    DS.get("defaultStore").adapter.serializer.keyForHasMany = function(type, name) {
return "list";
    var key = this.keyForAttributeName(type, name);

    if (this.embeddedType(type, name)) {
      return key;
    }

    return this.singularize(key) + "_ids";

}

}, 3500);

*/