/**
* Filename: CreateAccount.java
* Package: com.mytalk.server.logic.processing.request_processor.world_information
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
* Classe che si occupa di creare un nuovo account del client che ne fa richiesta
*/


package com.mytalk.server.logic.processing.request_processor.world_information;

import com.mytalk.server.data.storage.MD5Converter;
import com.mytalk.server.exceptions.UsernameAlreadyExisting;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;

public class CreateAccount extends GenericRequest{
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +CreateAccount
	 */
	public CreateAccount(){}
	
	/**
	 * Una volta ricevuto il pacchetto ARI, si controlla se è ben formato. Se la risposta è negativa,
	 *  viene restituito un "CorruptedPack". Viceversa, si crea un oggetto di tipo 
	 *  CSDAT1.model.User, contenente le informazioni presenti nel campo dati ari. Inoltre vengono 
	 *  codificati l'indirizzo email e la password, in modo tale da salvarle codificate e non 
	 *  inviarle in chiaro. Infine, chiamando il metodo opportuno del DataAccess (appartenente alla 
	 *  componente CSDAT2), si prosegue con l'aggiunta dell'account, che non implica il login 
	 *  automatico da parte dell'utente. Si ritorna un nuovo pacchetto ARI, specificando che 
	 *  l'aggiunta è andata a buon fine: "SuccessfulCreateAccount". Altrimenti viene catturata la seguente 
	 *  eccezione "UsernameAlreadyExisting" e ritornato un pacchetto con valore richiesta 
	 *  "UsernameAlreadyExistingCreateAccount"
	 *  
	 *  @method +manage
	 *  @param {ARI} ari e' l'oggetto che contiene le informazioni necessarie alla classe per
	 *  poter processare la specifica richiesta e restituire il risultato dell'elaborazione
	 *  @return {ARI}
	 */
	public ARI manage(ARI ari){
		String infoRequest=ari.getInfo();
		ARI response=null;
		WorldPack pack=(WorldPack)conv.convertJsonToJava(infoRequest, WorldPack.class);
		boolean checkPack=checkWorldPackWellFormed(pack);
		if(!checkPack){
			response=new ARI(null,"CorruptedPack",infoRequest);
		}else{
			PersonalData personalData=pack.getWorldPersonalData().getPersonalData();
			String md5=MD5Converter.getHashMD5(personalData.getEmail());
			String pwdHash=MD5Converter.getHashMD5(personalData.getPassword());
			com.mytalk.server.data.model.User user=new com.mytalk.server.data.model.User(personalData.getUsername(), pwdHash,personalData.getEmail(), personalData.getName(), personalData.getSurname(),md5);
			try{
				da.createAccount(user);
				response=new ARI(null, "SuccessfulCreateAccount", infoRequest);
			}catch(UsernameAlreadyExisting uae){
				response=new ARI(null, "UsernameAlreadyExistingCreateAccount", infoRequest);
			}
		}
		return response;
	}

}
