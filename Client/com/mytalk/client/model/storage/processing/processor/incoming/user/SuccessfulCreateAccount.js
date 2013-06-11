/**
* Filename: SuccessfulCreateAccount.js
* Package: com.mytalk.client.model.storage.processing.processor.incoming.user
* Dependencies: com.mytalk.client.model.storage.processing.processor.incoming.AbstractInProcessorProduct
*               com.mytalk.client.model.storage.processing.ProcessorFactory
* Author: Griggio Massimo
* Date: 2013-05-23
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-05-23 | MG        | [+] Stesura dell'intera classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Processore che viene eseguito quando il server ha salvato correttamente i dati personali dell'utente che
* si è appena registrato al sistema
*
*/

MyTalk.processor.SuccessfulCreateAccount = Ember.Object.extend(MyTalk.AbstractInProcessorProduct, {
  /**
  * Memorizza il nome del processore
  * 
  * @property -name
  * @type {String}
  */
  name: 'SuccessfulCreateAccount',
 /**
  * Il metodo deve creare un nuovo processor _Login_ e deve eseguire l'autenticazione dell'utente che
  * si è appena registrato 
  *
  * @method +process
  * @param {String} Stringa JSON che rappresenta il pacchetto ARI
  * @return {Void}
  * @override CCMOD2.processing.processor.incoming$AbstractInProcessorProduct$
  */
  process: function(ari) {
    var info = JSON.parse(ari.info);
    var personalData = info.worldPersonalData;
    var user = personalData.pd;
    
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct( "Login" );
    processor.process({
      username: user.username,
      password: user.password
    });
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



