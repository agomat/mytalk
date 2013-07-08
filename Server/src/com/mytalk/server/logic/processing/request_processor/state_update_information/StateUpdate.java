/**
* Filename: StateUpdate.java
* Package: com.mytalk.server.logic.processing.request_processor.state_update_information
* Author: Michael Ferronato
* Date: 2013-05-20
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-05-22 |    MF     | [#] Modifica dei nomi del metodo manage al fine di renderli
* 									     piu' espressivi e di facile comprensione
* 0.1	  |2013-05-20  |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di creare un pacchetto contenente le informazioni relative al cambio
* stato di un utente. Essa, a differenza delle altre richieste, non viene fatta dal client ma e' 
* conseguenza delle operazioni di cambio stato, infatti viene attivata dal server quando necessaria,
* al fine di notificare a tutti gli user autenticati ed online il cambio stato
*/


package com.mytalk.server.logic.processing.request_processor.state_update_information;

import com.mytalk.server.exceptions.IdNotFoundException;
import com.mytalk.server.exceptions.UserNotExistingException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.UserStatePack;
import com.mytalk.server.logic.shared.model_client.User;

public class StateUpdate extends GenericRequest{
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +StateUpdate
	 */
	public StateUpdate(){}
	/**
	 * Una volta ricevuto il pacchetto ARI in input si va a verificare come prima cosa il valore 
	 * del campo richiesta del pacchetto per determinare se il nuovo stato e' online(Login e UpdateAccount)
	 *  od offline(Logout, LogoutToAnonymous e DeleteAccount). Successivamente si procede con 
	 * l'elaborazione dei dati, al fine di creare un pacchetto di informazione di tipo UserStatePack,
	 *  contenente al suo interno un oggetto User che identifica tutte le informazioni relative 
	 *  all'user che ha fatto il cambio stato, in particolare il suo nuovo stato, identificato da un 
	 *  booleano. Infine viene fatta la conversione del UserStatePack in una stringa JSON, la quale 
	 *  viene inserita come campo information del pacchetto ARI da restituire con campo richiesta 
	 *  StateUpdate, e viene restituito tale pacchetto. Nel caso di cambio stato dovuto alla 
	 *  cancellazione di un account, e' necessario un procedimento diverso per recuperare le 
	 *  informazioni relative all'utente che ne fa richiesta, poiche' i suoi dati vengono cancellati 
	 *  dal server, percio' si utilizzano i dati contenuti nella parte information del ARI che viene 
	 *  ricevuto in input
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		ARI response=null;
		Integer id;
		UserStatePack packOut;
		UserStatePack packIn;
		String infoResponse;
		User userClient=null;
		boolean status=false;
		if(ari.getReq().equals("Login") || ari.getReq().equals("UpdateAccount")){
			status=true;
		}
		if(ari.getReq().equals("DeleteAccount")){
			packIn=(UserStatePack)conv.convertJsonToJava(ari.getInfo(), UserStatePack.class);
			userClient=new User(packIn.getList().getId(),packIn.getList().getUsername(),packIn.getList().getName(),packIn.getList().getSurname(),packIn.getList().getMd5(),ari.getAuth().getIp(),status);
			packOut= new UserStatePack(userClient);
			infoResponse=conv.convertJavaToJson(packOut);
			return new ARI(null,"StateUpdate",infoResponse);
		}
		try {
			id = da.getIdFromUsername(ari.getAuth().getUser());
			com.mytalk.server.data.model.User user=da.getUserById(id);
			userClient=new User(id,user.getUsername(),user.getName(),user.getSurname(),user.getEmailHash(),ari.getAuth().getIp(),status);
			packOut= new UserStatePack(userClient);
			infoResponse=conv.convertJavaToJson(packOut);
			response=new ARI(null,"StateUpdate",infoResponse);
		} catch (UserNotExistingException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IdNotFoundException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}	
		return response;
	}
}
