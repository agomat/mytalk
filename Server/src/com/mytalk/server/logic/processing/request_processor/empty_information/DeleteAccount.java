/**
* Filename: DeleteAccount.java
* Package: com.mytalk.server.logic.processing.request_processor.empty_information
* Author: Nicolo' Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  | 2013-05-13 |    NT	   | [#] Modifica dei nomi di alcune variabili in tutti i metodi al
* 										 fine di renderli piu' espressivi
* 0.1	  |	2013-05-02 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di eliminare l'account dell'utente.
*/

package com.mytalk.server.logic.processing.request_processor.empty_information;

import com.mytalk.server.exceptions.AuthenticationFailException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;


public class DeleteAccount extends GenericRequest{

	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +DeleteAccount
	 */
	public DeleteAccount(){}
	
	/**
	 *  Una volta ricevuto l'ARI, viene creato un User con i dati presenti nell'Authentication 
	 *  del campo dati ari, per poi invocare, tramite un oggetto di DataAccess appartenente 
	 *  alla componente CSDAT2, il metodo che si occupera' di eliminare l'utente. Tale operazione 
	 *  puo' avere successo, restituendo "SuccessfulDeleteAccount", o insuccesso con
	 *   "AuthenticationFailDeleteAccount" causato dal fallimento dell'autenticazione
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		ARI response=null;
		com.mytalk.server.data.model.User user=new com.mytalk.server.data.model.User(ari.getAuth().getUser(), ari.getAuth().getPwd(), null, null, null,null);
		try{
			da.deleteAccount(user);
			response=new ARI(null, "SuccessfulDeleteAccount", null);
		}catch(AuthenticationFailException af){
			response=new ARI(null, "AuthenticationFailDeleteAccount", null);
		}
		return response;
	}
}
