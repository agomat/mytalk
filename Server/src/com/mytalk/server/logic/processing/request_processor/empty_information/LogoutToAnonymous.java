/**
* Filename: LogoutAsAnonymous.java
* Package: com.mytalk.server.logic.processing.request_processor.empty_information
* Author: Michael Ferronato
* Date: 2013-05-17
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-05-22 |    MF     | [+] Modifica dei nomi del metodo manage al fine di renderli
* 									     più espressivi e di facile comprensione
* 0.1	  |	2013-05-17 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di passare da utente autenticato ad utente anonimo, un client
*/


package com.mytalk.server.logic.processing.request_processor.empty_information;

import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.LogoutException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;

public class LogoutToAnonymous extends GenericRequest {
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +LogoutToAnonymous
	 */
	public LogoutToAnonymous(){}
	
	/**
	 * Una volta ricevuto l'ARI, viene controllato se il pacchetto è ben formato. Se la risposta 
	 * è negativa, viene ritornato un pacchetto con richiesta "CorruptedPack". In seguito viene
	 *  creato un oggetto di tipo OnlineUser con i dati presenti nell'Authentication del campo dati 
	 *  ari. Tale oggetto viene utilizzato dal metodo di DataAccess, appartenente alla componente 
	 *  CSDAT2, per consentire il cambiamento da autenticato ad anonimo. Nel caso in cui tutto 
	 *  prosegue secondo i piani, viene restituito un "SuccessfulLogoutToAnonymous", altrimenti 
	 *  viene catturata l'eccezione "LogoutException" e ritornato un pacchetto con valore richiesta 
	 *   "UnsuccessfulLogoutToAnonymous"
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		if(!checkAuth){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			OnlineUser onlineUser=new OnlineUser(auth.getUser(), auth.getIp());
			try {
				da.logoutToAnonymous(onlineUser);
				response=new ARI(null, "SuccessfulLogoutToAnonymous", null);
			} catch (LogoutException e) {
				response=new ARI(null, "UnsuccessfulLogoutToAnonymous", null);
			}	
		}
		return response;
	}
}
