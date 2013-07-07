/**
* Filename: StatsController.js
* Package: com.mytalk.client.controller.controller
* Dependencies: com.mytalk.client.model.storage.processing.processor.outcoming.stats.GetCalls
* Author: Campese Stefano
* Date: 2013-07-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-07-01 | SC        | [+] Scrittura classe
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
*  Controller deputato alla visualizzazione, delle statistiche globali delle chiamate effettuate/ricevute dall'utente autenticato al sistema.
*
*/

MyTalk.StatsController = Ember.ObjectController.extend({
  /**
   * Questo metodo è necessario per collegare il controller al rispettivo model; tale
   * metodo viene invocato ogni qual volta viene creata un'istanza di questo controller.
   *
   * @method -init                                     
   * @return {Void} 
  */
  init:function(){
    this._super();
    this.set('content',MyTalk.WCall.find(0));
  },

  /**
   * Questo metodo deve invocare cancellare tutti i record del model $Call$
   *
   * @method -clearPrevCalls                                 
   * @return {Void} 
  */
  clearPrevCalls:function() {
    var calls = new Array();
    MyTalk.Call.find().forEach(function(call){
      calls.push(call);
    });
    calls.forEach(function(call){
      call.deleteRecord();
      call.get('stateManager').goToState('deleted');
    });
  },

  /**
   * Questo metodo deve invocare il processore $GetCalls$ al fine di poter aggiornare la lista
   * delle chiamate effettuate e ricevute
   *
   * @method +getCalls                                 
   * @return {Void} 
  */
  getCalls:function() {
    this.clearPrevCalls();
    var processorFactory = MyTalk.ProcessorFactory.create({});
    var processor = processorFactory.createProcessorProduct( "GetCalls" );
    processor.process();
  }

});
