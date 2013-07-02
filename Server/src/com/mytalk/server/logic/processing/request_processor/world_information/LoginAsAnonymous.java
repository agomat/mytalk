/**
* Filename: LoginAsAnonymous.java
* Package: com.mytalk.server.logic.processing.request_processor.world_information
* Author: Michael Ferronato
* Date: 2013-05-07
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-05-22 |    MF     | [#] Modifica dei nomi del metodo manage al fine di renderli
* 									     più espressivi e di facile comprensione
* 0.1	  |	2013-05-07 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di effettuare il login ad un utente anonimo
*/


package com.mytalk.server.logic.processing.request_processor.world_information;

import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.IpAlreadyLogged;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;
import com.mytalk.server.logic.shared.model_client.WorldPersonalData;

public class LoginAsAnonymous extends GenericRequest {
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +LoginAsAnonymous
	 */
	public LoginAsAnonymous(){}
	
	/**
	 * Una volta ricevuto il pacchetto ARI, si controlla se è ben formato. Se la risposta è negativa,
	 *  viene tornato un "CorruptedPack". D'altra parte, viene creato un onlineUser necessario per 
	 *  memorizzare l'indirizzo ip dell'utente anonimo. Tale oggetto verrà passato come parametro 
	 *  al metodo invocato sull'oggetto DataAccess, appartenente alla componente CSDAT2. Infine, 
	 *  il campo Information del nuovo pacchetto ARI conterrà un WorldPack, sotto forma di stringa 
	 *  JSON, contenente l'indirizzo Ip e il campo richiesta "SuccessfulLoginAsAnonymous". 
	 *  Altrimenti verrà lanciata e catturata la seguente eccezione: "IpAlreadyLogged" ritornando 
	 *  un pacchetto con campo richiesta "IpAlreadyLoggedLoginAsAnonymous".
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari) {
		ARI response=null;
		if(ari.getAuth()==null || ari.getAuth().getIp()==null){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			OnlineUser onlineUser=new OnlineUser(null, ari.getAuth().getIp());
			String infoResponse=null;
			try {
				da.loginAsAnonymous(onlineUser);
				PersonalData pd=new PersonalData(null,null,null,null,null,null,null,ari.getAuth().getIp());
				WorldPersonalData worldPersonalData=new WorldPersonalData(pd);
				WorldPack worldPack=new WorldPack(null,worldPersonalData);
				infoResponse=conv.convertJavaToJson(worldPack);
				response=new ARI(null,"SuccessfulLoginAsAnonymous",infoResponse);
			} catch (IpAlreadyLogged e) {
				response=new ARI(null,"IpAlreadyLoggedLoginAsAnonymous",infoResponse);
			}
		}
		return response;
	}

}
