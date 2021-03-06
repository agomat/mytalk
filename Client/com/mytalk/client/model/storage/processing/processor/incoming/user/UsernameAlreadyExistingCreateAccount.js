/**
* Filename: UsernameAlreadyExistingCreateAccount.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.user
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
* Author: Agostinetto Mattia
* Date: 2013-05-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-05-01 | MA        | [+] Stesura dell'intera classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Processore che viene eseguito quando utente prova registrarsi al sistema con un username già
* assegnato ad un altro utente
*
*/

MyTalk.processor.UsernameAlreadyExistingCreateAccount = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'UsernameAlreadyExistingCreateAccount',
 /**
  * Il metodo deve notificare l'utente del fatto che l'username non è disponibile
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  process: function (ari) {
    var record = MyTalk.PersonalData.find(0);
    record.set('username','');
    record.get('stateManager').goToState('updated');
    record.get('stateManager').goToState('saved');
    alert("La registrazione non è andata a buon fine, l'username inserito esiste già. Prova a sceglierne un altro");
  },
  /**
  * Il metodo deve ritornare l'attributo _name_
  *
  * @method +getProcessorName
  * @return {String}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  getProcessorName: function () {
    return this.get('name');
  }
});