/**
* Filename: RefuseCall.java
* Package: com.mytalk.server.logic.processing.request_processor.connection_information
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
* Classe che riceve un pacchetto ARI che, nel campo Information, contiene le informazioni necessarie
* per il corretto funzionamento riguardo il rifiuto della chiamata da parte del ricevente
*/


package com.mytalk.server.logic.processing.request_processor.connection_information;

import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ConnectionPack;

public class RefuseCall extends GenericRequest{
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +RefuseCall
	 */
	public RefuseCall(){}
	
	/**
	 * Una volta ricevuto l'ARI, viene catturata la parte Information, necessaria per creare il 
	 * ConnectionPack, i cui dati sono necessari per processare la richiesta. Viene controllata 
	 * la struttura del pacchetto che, se errata, torna un "CorruptedPack" e il campo information 
	 * appartenente al campo dati ari. Nel caso in cui viene superato il controllo della presenza 
	 * di tutti i campi necessari in tale pacchetto, si puo' controllare se l'indirizzo Ip e' online.
	 *  Se presente, viene restituto un nuovo pacchetto ARI, specificando che la richiesta e' andata 
	 *  a buon fine: "SuccessfulRefuseCall". In tale pacchetto e' presente la stessa Information 
	 *  ricevuta e, riguardo l'Authentication, viene creato un oggetto Authentication contenente 
	 *  l'indirizzo ip del ricevente. In caso opposto, si segnalera' che la richiesta non ha avuto 
	 *  successo: "UnsuccessfulRefuseCall", anche in questo caso il campo Information rimane 
	 *  invariato e non vi e' la parte Authentication
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		String infoRequest=ari.getInfo();
		ARI response=null;
		ConnectionPack pack=(ConnectionPack)conv.convertJsonToJava(infoRequest, ConnectionPack.class);
		boolean checkPack=checkAnonymousConnectionPackWellFormed(pack);
		Authentication auth=null;
		if(!checkPack){
			response=new ARI(null,"CorruptedPack",infoRequest);
		}
		else {
			boolean result=da.checkUserByIp(pack.getSpeakerIp());
			if(result){
				auth=new Authentication(null, null, pack.getSpeakerIp());
				response=new ARI(auth, "SuccessfulRefuseCall", infoRequest);
			}
			else{
				response=new ARI(null, "UnsuccessfulRefuseCall", infoRequest);
			}
		}
		return response;
	}
}

