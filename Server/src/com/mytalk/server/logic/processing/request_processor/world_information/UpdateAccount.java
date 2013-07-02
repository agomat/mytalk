/**
* Filename: UpdateAccount.java
* Package: com.mytalk.server.logic.processing.request_processor.world_information
* Author: Michael Ferronato
* Date: 2013-07-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-07-02 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-07-02 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che si occupa di fare l'aggiornamento dei dati dell'account dell'utente che fa la richiesta
*/


package com.mytalk.server.logic.processing.request_processor.world_information;

import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;
import com.mytalk.server.data.model.User;
import com.mytalk.server.data.storage.MD5Converter;
import com.mytalk.server.exceptions.AuthenticationFailException;
import com.mytalk.server.exceptions.UsernameNotCorrespondingException;

public class UpdateAccount extends GenericRequest {

	/**
	 * Una volta ricevuto il pacchetto ARI, si controlla se è ben formato. Se la risposta è negativa,
	 *  viene restituito un "CorruptedPack". Viceversa, si crea un oggetto di tipo 
	 *  CSDAT1.model.User, contenente le informazioni presenti nel campo dati ari. Inoltre vengono 
	 *  codificati l'indirizzo email e la password, in modo tale da salvarle codificate e non 
	 *  inviarle in chiaro. Infine, chiamando il metodo opportuno del DataAccess (appartenente alla 
	 *  componente CSDAT2), si prosegue con l'aggiornamento dell'account.
	 *  Si ritorna un nuovo pacchetto ARI, specificando che l'aggiunta è andata a buon fine: 
	 *  "SuccessfulUpdateAccount". Altrimenti vengono lanciate e catturate le seguente eccezioni:
	 *  "AuthenticationFailException" e "UsernameNotCorrespondingException", ritornando un pacchetto con campo richiesta
	 *  "AuthenticationFailUpdateAccount" o "UsernameNotCorrespondingUpdateAccount".
	 *    
	 * @method +manage
	 * @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 * poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 * @return {ARI}
	 */
	public ARI manage(ARI ari) {
		String infoRequest= ari.getInfo();
		ARI response;
		WorldPack pack=(WorldPack)conv.convertJsonToJava(infoRequest, WorldPack.class);
		boolean checkPack=checkWorldPackWellFormed(pack);
		if(!checkPack){
			response=new ARI(null,"CorruptedPack",infoRequest);
		}
		else {
			PersonalData pd=pack.getWorldPersonalData().getPersonalData();
			String pwdHash=MD5Converter.getHashMD5(pd.getPassword());
			String mailHash=MD5Converter.getHashMD5(pd.getEmail());
			User authenticate= new User(ari.getAuth().getUser(),ari.getAuth().getPwd(),null,null,null,null);
			User userClient=new User(pd.getUsername(),pwdHash,pd.getEmail(),pd.getName(),pd.getSurname(),mailHash);
			try {
				da.updateAccount(userClient,authenticate);
				response=new ARI(null, "SuccessfulUpdateAccount", infoRequest);
			} catch (AuthenticationFailException e) {
				response=new ARI(null, "AuthenticationFailUpdateAccount", infoRequest);
			} catch (UsernameNotCorrespondingException e) {
				response=new ARI(null, "UsernameNotCorrespondingUpdateAccount", infoRequest);
			}
		}
		return response;
	}

}
