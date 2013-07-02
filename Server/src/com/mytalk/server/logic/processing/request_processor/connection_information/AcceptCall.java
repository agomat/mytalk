/**
* Filename: AcceptCall.java
* Package: com.mytalk.server.logic.processing.request_processor.connection_information
* Author: Nicolo' Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  | 2013-05-13 |    NT	   | [#] Modifica dei nomi di alcune variabili nel metodo manage al
* 										 fine di renderli più espressivi ed eliminazione import superflui
* 0.1	  |	2013-05-02 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che riceve un pacchetto ARI che, nel campo Information, contiene le informazioni
* necessarie per il corretto funzionamento riguardo l'accettazione della chiamata da parte del
* ricevente
*/


package com.mytalk.server.logic.processing.request_processor.connection_information;

import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ConnectionPack;

public class AcceptCall extends GenericRequest{
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +AcceptCall
	 */
	public AcceptCall(){}
	
	/**
	 * Una volta ricevuto l'ARI, viene catturata la parte Information, necessaria per creare 
	 * il ConnectionPack, i cui dati sono necessari per processare la richiesta. Nel caso in cui 
	 * viene superato il controllo della presenza di tutti i campi necessari in tale pacchetto, 
	 * si può controllare se l'indirizzo Ip è online. Se presente, si può rispondere con un nuovo 
	 * pacchetto ARI, specificando che la richiesta è andata a buon fine: "SuccessfulAcceptCall".
	 *  In tale pacchetto è presente la stessa Information ricevuta e, riguardo l'Authentication, 
	 *  viene creato un oggetto Authentication contenente l'indirizzo ip del ricevente. 
	 *  In caso opposto, si segnalerà che la richiesta non ha avuto successo: 
	 *  "UnsuccessfulAcceptCall", anche in questo caso il campo Information rimane invariato e non 
	 *  vi è la parte Authentication
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		String infoRequest=ari.getInfo();
		ARI response=null;
		Authentication auth=null;
		ConnectionPack pack=(ConnectionPack)conv.convertJsonToJava(infoRequest, ConnectionPack.class);
		boolean checkPack=checkAnonymousConnectionPackWellFormed(pack);
		if(!checkPack){
			response=new ARI(null,"CorruptedPack",infoRequest);
		}
		else {
			boolean result=da.checkUserByIp(pack.getSpeakerIp());
			if(result){
				auth=new Authentication(null, null, pack.getSpeakerIp());
				response=new ARI(auth, "SuccessfulAcceptCall",infoRequest);
			}
			else{
				response=new ARI(null, "UnsuccessfulAcceptCall", infoRequest);
			}
		}
		return response;
	}
}
