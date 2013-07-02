/**
* Filename: ListCreate.java
* Package: com.mytalk.server.logic.processing.request_processor.update_list_information
* Author: Michael Ferronato
* Date: 2013-05-01 
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-05-22 |    MF     | [#] Modifica dei nomi del metodo manage al fine di renderli
* 									     piu' espressivi e di facile comprensione
* 0.1	  |	2013-05-01 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di creare una nuova lista del client che ne fa richiesta
*/

package com.mytalk.server.logic.processing.request_processor.update_list_information;

import com.mytalk.server.data.model.ListName;
import com.mytalk.server.exceptions.AuthenticationFailException;
import com.mytalk.server.exceptions.ListAlreadyExistsException;
import com.mytalk.server.exceptions.UsernameNotCorrespondingException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.UpdateListPack;


public class ListCreate extends GenericRequest {
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +ListCreate
	 */
	public ListCreate(){}

	/**
	 * Una volta ricevuto l'ARI e fatti i controlli di pacchetto ben formato, usando i dati ricevuti 
	 * dal campo dati ari, viene creato un oggetto di tipo CSDAT1.ListName, utilizzato per creare 
	 * una nuova lista utente utilizzando l'oggetto DataAccess, classe appartenente al componente 
	 * CSDAT2. Viene restituito un "SuccessfulListCreate". Se non va a buon fine, vengono 
	 * sollevate e catturate le seguenti eccezioni: "ListAlreadyExistsException", "AuthenticationFailException",
	 *  "UsernameNotCorrespondingException", ritornando un pacchetto con campo richiesta ListAlreadyExistsException", 
	 *  "AuthenticationFailListCreate" o "UsernameNotCorrespondingListCreate". In caso di pacchetto mal 
	 *  formato, viene restituito un "CorruptedPack"
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari) {
		ARI response=null;
		Authentication auth=ari.getAuth();
		String infoRequest=ari.getInfo();
		UpdateListPack pack=(UpdateListPack)conv.convertJsonToJava(infoRequest,UpdateListPack.class);
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		boolean checkPack=checkPartialUpdateListPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",infoRequest);
		}
		else{
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			String listName=pack.getListName();
			ListName newList=new ListName(listName,pack.getOwner());
			try{
				da.listCreate(newList,userAuth);
				response=new ARI(null,"SuccessfulListCreate",infoRequest);
			}catch(ListAlreadyExistsException lae){
				response=new ARI(null,"ListAlreadyExistsException",infoRequest);
			}catch(AuthenticationFailException af){
				response=new ARI(null,"AuthenticationFailListCreate",infoRequest);
			} catch (UsernameNotCorrespondingException e) {
				response=new ARI(null,"UsernameNotCorrespondingListCreate",infoRequest);
			}
		}
		return response;
	}

}
