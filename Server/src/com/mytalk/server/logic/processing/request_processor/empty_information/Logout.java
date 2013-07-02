/**
* Filename: Logout.java
* Package: com.mytalk.server.logic.processing.request_processor.empty_information
* Author: Nicolo' Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  | 2013-05-13 |    NT	   | [#] Modifica dei nomi di alcune variabili in tutti i metodi al
* 										 fine di renderli più espressivi
* 0.1	  |	2013-05-02 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di effettuare la disconnessione di un client dal sistema
*/


package com.mytalk.server.logic.processing.request_processor.empty_information;

import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.LogoutException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;

public class Logout extends GenericRequest{
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +Logout
	 */
	public Logout(){}
	
	/**
	 * Una volta ricevuto l'ARI, viene controllato se è presente l'indirizzo IP in tale pacchetto.
	 *  Se non presente, viene ritornato un pacchetto con richiesta "CorruptedPack". 
	 *  In seguito viene creato un oggetto di tipo OnlineUser con i dati presenti 
	 *  nell'Authentication del campo dati ari. Tale oggetto viene utilizzato dal metodo di 
	 *  DataAccess, appartenente alla componente CSDAT2, per rendere l'utente offline. Se non si 
	 *  rilevano errori viene restituito un "SuccessfulLogout", altrimenti viene catturata l'eccezione 
	 *  "LogoutException" e ritornato un pacchetto con valore richiesta  "UnsuccessfulLogout"
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		if(auth.getIp()==null){
			response= new ARI(null,"CorruptedPack",null);
		}
		else{
			OnlineUser onlineUser=new OnlineUser(auth.getUser(), auth.getIp());
			try {
				auth.setUser(da.getUsernameByIp(auth.getIp()));
				da.logout(onlineUser);
				response=new ARI(auth, "SuccessfulLogout", null);
			} catch (LogoutException e) {
				response=new ARI(null, "UnsuccessfulLogout", null);
			} 
		}	
		return response;
	}
}
