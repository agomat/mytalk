/**
* Filename: AddCall.java
* Package: com.mytalk.server.logic.processing.request_processor.give_call_information
* Author: Michael Ferronato
* Date: 2013-05-01 
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-05-22 |    MF     | [+] Modifica dei nomi del metodo manage al fine di renderli
* 									     più espressivi e di facile comprensione
* 0.1	  |	2013-05-01 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di aggiungere una chiamata effettuata nella lista delle chiamate
*/

package com.mytalk.server.logic.processing.request_processor.give_call_information;

import java.util.List;

import com.mytalk.server.exceptions.AuthenticationFailException;
import com.mytalk.server.exceptions.IdNotFoundException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.GiveCallPack;
import com.mytalk.server.logic.shared.model_client.Call;
import com.mytalk.server.logic.shared.model_client.WrapperCall;

public class AddCall extends GenericRequest {
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +AddCall
	 */
	public AddCall(){}
	/**
	 * Ricevuto il pacchetto ARI, si controlla se è ben formato. Se la risposta è negativa, 
	 * viene restituito un pacchetto con richiesta "CorruptedPack". Viceversa, si crea un 
	 * oggetto di tipo CSDAT1.model.User, contenente le informazioni dell'Authentication del 
	 * campo dati ari. Inoltre si crea un oggetto di tipo CSDAT1.model.Call contenente i 
	 * dati ottenuti riguardanti la chiamata. Tale oggetto lo si aggiunge chiamando il metodo 
	 * opportuno del DataAccess, appartenente alla componente CSDAT2. Si ritorna un nuovo pacchetto 
	 * ARI, specificando che l'aggiunta è andata a buon fine: "SuccessfulAddCall". Altrimenti 
	 * vengono catturate le seguenti eccezioni: "AuthenticationFailException" e "IdNotFoundException", ritornando un 
	 * pacchetto con valore richiesta "AuthenticationFailAddCall" (in caso di 
	 * autenticazione fallita) o "IdNotFoundAddCall" (id non trovato). In tutti i pacchetti 
	 * di risposta, il campo Information rimane invariato
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
		GiveCallPack pack=(GiveCallPack)conv.convertJsonToJava(infoRequest, GiveCallPack.class);
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		boolean checkPack=checkGiveCallPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}	
		else{
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			WrapperCall wrapperCall=pack.getWrapperCall();
			List<Call> listOfCall=wrapperCall.getList();
			com.mytalk.server.data.model.Call callServer=null;
			Call callClient=null;
			String caller=null;
			String receiver=null;
			int duration=0;
			String startdate=null;
			int byteR=0;
			int byteS=0;
			try{
				callClient=listOfCall.get(0); 
				caller=auth.getUser();
				receiver=(da.getUserById(callClient.getSpeaker())).getUsername();
				duration=callClient.getDuration();
				startdate=callClient.getStartDate();
				byteR=callClient.getByteReceived();
				byteS=callClient.getByteSent();
				callServer=new com.mytalk.server.data.model.Call(caller,receiver,duration,startdate,byteR,byteS);
				da.addCall(callServer,userAuth);
				response=new ARI(null,"SuccessfulAddCall",infoRequest);
			}catch(AuthenticationFailException af){
				response=new ARI(null,"AuthenticationFailAddCall",infoRequest);
			} catch (IdNotFoundException e) {
				response=new ARI(null,"IdNotFoundAddCall",infoRequest);
			}
		}
		return response;
	}

}
