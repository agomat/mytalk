/**
* Filename: ProcessorFactory.js
* Package: com.mytalk.client.model.storage.processing
* Dependencies:  com.mytalk.client.model.storage.processing.processor.incoming.communication.CorruptedPack   
*                com.mytalk.client.model.storage.processing.processor.incoming.communication.UnsuccessfulAcceptCall
*                com.mytalk.client.model.storage.processing.processor.incoming.communication.UnsuccessfulUserCall
*                com.mytalk.client.model.storage.processing.processor.incoming.communication.SuccessfulUserCall
*                com.mytalk.client.model.storage.processing.processor.incoming.communication.IdNotFoundUserCall
*                com.mytalk.client.model.storage.processing.processor.incoming.communication.SuccessfulRefuseCall
*                com.mytalk.client.model.storage.processing.processor.incoming.communication.SuccessfulAcceptCall
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UserNotExistingListUserAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.ListAlreadyExists
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UserNotExistingListUserRemove
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UserAlreadyBlacklisted
*                com.mytalk.client.model.storage.processing.processor.incoming.list.SuccessfulBlackListAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UsernameNotCorrespondingListCreate
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UserAlreadyListed
*                com.mytalk.client.model.storage.processing.processor.incoming.list.SuccessfulBlackListRemove
*                com.mytalk.client.model.storage.processing.processor.incoming.list.SuccessfulListCreate
*                com.mytalk.client.model.storage.processing.processor.incoming.list.ListNotExistingListDelete
*                com.mytalk.client.model.storage.processing.processor.incoming.list.IdNotFoundListUserAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.AuthenticationFailBlacklistRemove
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UsernameNotCorrespondingListUserAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.SuccessfulListUserAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.AuthenticationFailListUserAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.IdNotFoundBlacklistAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.SuccessfulListUserRemove
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UserNotExistingBlacklistAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.ListAlreadyExistsRenameList
*                com.mytalk.client.model.storage.processing.processor.incoming.list.AuthenticationFailListCreate
*                com.mytalk.client.model.storage.processing.processor.incoming.list.AuthenticationFailRenameList
*                com.mytalk.client.model.storage.processing.processor.incoming.list.ListNotExistingListUserAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.SuccessfulListDelete
*                com.mytalk.client.model.storage.processing.processor.incoming.list.AuthenticationFailBlacklistAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.ListNotExistingListUserRemove
*                com.mytalk.client.model.storage.processing.processor.incoming.list.ListNotExistingRenameList
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UsernameNotCorrespondingListDelete
*                com.mytalk.client.model.storage.processing.processor.incoming.list.IdNotFoundBlacklistRemove
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UserNotListed
*                com.mytalk.client.model.storage.processing.processor.incoming.list.AuthenticationFailListDelete
*                com.mytalk.client.model.storage.processing.processor.incoming.list.AuthenticationFailListUserRemove
*                com.mytalk.client.model.storage.processing.processor.incoming.list.SuccessfulRenameList
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UsernameNotCorrespondingListUserRemove
*                com.mytalk.client.model.storage.processing.processor.incoming.list.IdNotFoundListUserRemove
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UserNotCorrespondingBlacklistAdd
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UserNotBlacklisted
*                com.mytalk.client.model.storage.processing.processor.incoming.list.UsernameNotCorrespondingRenameList
*                com.mytalk.client.model.storage.processing.processor.incoming.stats.AuthenticationFailGetCalls
*                com.mytalk.client.model.storage.processing.processor.incoming.stats.SuccessfulAddCall
*                com.mytalk.client.model.storage.processing.processor.incoming.stats.AuthenticationFailAddCall
*                com.mytalk.client.model.storage.processing.processor.incoming.stats.GiveCalls
*                com.mytalk.client.model.storage.processing.processor.incoming.stats.UsernameNotExistingGetCalls
*                com.mytalk.client.model.storage.processing.processor.incoming.stats.IdNotFoundAddCall
*                com.mytalk.client.model.storage.processing.processor.incoming.user.IpAlreadyLoggedLogin
*                com.mytalk.client.model.storage.processing.processor.incoming.user.SuccessfulCreateAccount
*                com.mytalk.client.model.storage.processing.processor.incoming.user.StateUpdate
*                com.mytalk.client.model.storage.processing.processor.incoming.user.UserNotLoggedLogin
*                com.mytalk.client.model.storage.processing.processor.incoming.user.SuccessfulLogin
*                com.mytalk.client.model.storage.processing.processor.incoming.user.AuthenticationFailLogin
*                com.mytalk.client.model.storage.processing.processor.incoming.user.SuccessfulLoginAsAnonymous
*                com.mytalk.client.model.storage.processing.processor.incoming.user.SuccessfulDeleteAccount
*                com.mytalk.client.model.storage.processing.processor.incoming.user.AuthenticationFailDeleteAccount
*                com.mytalk.client.model.storage.processing.processor.incoming.user.IpAlreadyLoggedLoginAsAnonymous
*                com.mytalk.client.model.storage.processing.processor.incoming.user.UsernameCorrespondingExistingLogin
*                com.mytalk.client.model.storage.processing.processor.incoming.user.UserAlreadyLoggedLogin
*                com.mytalk.client.model.storage.processing.processor.incoming.user.IpNotLoggedLogin
*                com.mytalk.client.model.storage.processing.processor.incoming.user.UsernameAlreadyExistingCreateAccount
*                com.mytalk.client.model.storage.processing.processor.outcoming.communication.AcceptCall
*                com.mytalk.client.model.storage.processing.processor.outcoming.communication.UserCall
*                com.mytalk.client.model.storage.processing.processor.outcoming.communication.RefuseCall
*                com.mytalk.client.model.storage.processing.processor.outcoming.list.ListUserAdd
*                com.mytalk.client.model.storage.processing.processor.outcoming.list.UpdateListName
*                com.mytalk.client.model.storage.processing.processor.outcoming.list.BlackListRemove
*                com.mytalk.client.model.storage.processing.processor.outcoming.list.ListCreate
*                com.mytalk.client.model.storage.processing.processor.outcoming.list.ListUserRemove
*                com.mytalk.client.model.storage.processing.processor.outcoming.list.BlackListAdd
*                com.mytalk.client.model.storage.processing.processor.outcoming.list.ListDelete
*                com.mytalk.client.model.storage.processing.processor.outcoming.stats.AddCall
*                com.mytalk.client.model.storage.processing.processor.outcoming.stats.GetCalls
*                com.mytalk.client.model.storage.processing.processor.outcoming.user.CreateAccount
*                com.mytalk.client.model.storage.processing.processor.outcoming.user.Login
*                com.mytalk.client.model.storage.processing.processor.outcoming.user.DeleteAccount
*                com.mytalk.client.model.storage.processing.processor.outcoming.user.LogoutToAnonymous
* Author: Agostinetto Mattia
* Date: 2013-05-01
*
* Diary:
*
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1     | 2013-05-01 | MA        | [+] Scrittura classe e factory method
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*
* Questa classe espone un metodo che restituisce un processore in grado di gestire una
* richiesta
*/

MyTalk.ProcessorFactory = Ember.Object.extend({
  /**
  * Crea una nuova connessione WebSocket se l'attributo
  * _socket_ è nullo e lo salva in _socket_ rendendo l'attributo _socket_ 
  * disponibile a tutte le classi che vogliano usare questo Mixin. 
  * \\
  * Il metodo, oltre a creare la connessione, deve fornire la callback _onmessage_ all'oggetto
  * WebSocket appena creato. La callback riceve come parametro la stringa del messaggio ricevuto
  * dal server. Dal messaggio va estratta la parte $richiesta$ (_req_) di _ARI_ per passarla
  * ad un oggetto _ProcessorFactory_ creato immediatamente prima. L'oggetto ritornerà il processore
  * atto a gestire la richiesta. Andrà invocato su di esso il metodo _process_ passandogli il
  * messaggio ricevuto dal server
  *
  * @method +createProcessorProduct
  * @param {String} il nome della richiesta per cui si vuole il processore
  * @return {Object} un processore oppure null se non esiste alcun processore avente nome _requestName_
  */
  createProcessorProduct: function(requestName) { 
    var processor = eval("MyTalk.processor." + requestName);
    if( processor ) {
      return processor.create({});
    } else {
      console.debug('Processor ' + requestName + ' does not exists');
      return null;
    }
  }

});
