/**
* Filename: IpcallController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.model.modelstruct.PersonalData
* Author: Griggio Massimo
* Date: 2013-05-18
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-05-18 | MG        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Controller deputato alla gestione di una chiamata da indirizzo IP diretto e del proprio indirizzo IP 
*
*/ 

MyTalk.IpcallController = Ember.ObjectController.extend({
  
  /**
   * Questo metodo è deputato all'ottenimento dell'indirizzo IP dell'utente attuale dell'applicazione. 
   * Il metodo ottiene i dati dal model $PersonalData$ e impostare il $content$ del controller con l'indirizzo IP.
   *
   * @method +myIP                                     
   * @return {Void} 
  */

  myIP: function() {
    var me = MyTalk.PersonalData.find(0);
    this.set('content', me);
  }.property('content'),
  
  ipCall: function(ip) {
    if(MyTalk.PersonalData.find(0).get('ip') == ip) {
      alert("Per chiamare te stesso apri una nuova scheda del browser e utilizza l'ip che ti verrà fornito dal sistema!")
    }
    else {
      var record = MyTalk.User.createRecord({ip: ip, name: "Utente", surname: "anonimo", online: false});
      record.get('stateManager').goToState('saved');
      this.transitionToRoute('calling', record);
    }
  }
});
