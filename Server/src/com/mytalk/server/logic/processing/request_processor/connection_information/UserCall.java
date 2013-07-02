/**
* Filename: UserCall.java
* Package: com.mytalk.server.logic.processing.request_processor.connection_information
* Author: Nicolo' Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  | 2013-05-13 |    NT	   | [#] Modifica dei nomi di alcune variabili in tutti i metodi al
* 										 fine di renderli più espressivi ed eliminazione import superflui
* 0.1	  |	2013-05-02 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che riceve un pacchetto ARI che, nel campo Information, contiene le informazioni
* necessarie per il corretto funzionamento riguardo la richiesta di una chiamata da parte di un
* utente
*/

package com.mytalk.server.logic.processing.request_processor.connection_information;

import com.mytalk.server.data.model.Blacklist;
import com.mytalk.server.exceptions.IdNotFoundException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ConnectionPack;

public class UserCall extends GenericRequest{
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +UserCall
	 */
	public UserCall(){}
	
	/**
	 * Una volta ricevuto l'ARI, viene catturata la parte Information, necessaria per creare il 
	 * ConnectionPack, i cui dati sono necessari per processare la richiesta. Inoltre vengono 
	 * fatti vari controlli riguardanti la giusta formalità del pacchetto.
	 * Nel caso in cui tutto è corretto, viene fatto un controllo se il chiamante è presente
	 * nella blacklist del chiamato, in caso negativo viene mandato un "SuccessfulUserCall" al
	 * chiamato, altrimenti un "UnsuccessfulUserCall" al chiamante. D'altra parte, in caso di
	 * pacchetto corrotto, viene restituito un ARI contenente il messaggio "CorruptedPack". Se
	 * l'id del chiamato non è stato trovato, viene restituito un pacchetto con campo richiesta
	 * "IdNotFoundUserCall". In tutti i pacchetti di risposta, il campo Information rimane
	 * invariato
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		String infoRequest=ari.getInfo();
		ARI response=null;
		boolean checkAuth=true;
		boolean checkPack=false;
		boolean result=false;
		boolean checkPresence=false;
		ConnectionPack pack=(ConnectionPack)conv.convertJsonToJava(infoRequest, ConnectionPack.class);
		Authentication auth=null;
		try {
			if(ari.getAuth().getUser()==null){
				checkPack=checkAnonymousConnectionPackWellFormed(pack);
				if(!checkPack){	
					return new ARI(null,"CorruptedPack",infoRequest);
				}
			}
			else{
				checkPack=checkConnectionPackWellFormed(pack);
				checkAuth=checkAuthenticationWellFormed(ari.getAuth());
				if(!checkAuth || !checkPack){
					return new ARI(null,"CorruptedPack",infoRequest);
				}
				String callerUsername = da.getUserById(pack.getMyUserId()).getUsername();
				String speakerUsername=da.getUserById(pack.getSpeakerUserId()).getUsername();
				Blacklist blacklist=new Blacklist(speakerUsername,callerUsername);
				checkPresence = da.checkBlacklist(blacklist);
			}
			result=da.checkUserByIp(pack.getSpeakerIp());
			if(result && !checkPresence){
				auth=new Authentication(null, null,pack.getSpeakerIp());
				response=new ARI(auth,"SuccessfulUserCall",infoRequest);
			}
			else{
				response=new ARI(null,"UnsuccessfulUserCall",infoRequest);
			}
		}catch (IdNotFoundException e) {
			response= new ARI(null,"IdNotFoundUserCall",infoRequest);
		} 
		return response;
	}
}
 