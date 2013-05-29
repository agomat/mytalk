/**
* Filename: CreateAccount.java
* Package: com.mytalk.server.logic.processing.request_processor.world_information
* Author: Nicolò Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2	  | 2013-05-13 |   NT	   | [#] Modifica dei nomi di alcune variabili in tutti i metodi al
* 										 fine di renderli più espressivi
* 0.1	  |	2013-05-02 |   NT      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.world_information;

import com.mytalk.server.data.storage.MD5Converter;
import com.mytalk.server.exceptions.UsernameAlreadyExisting;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;

public class CreateAccount extends GenericRequest{
	
	public CreateAccount(){}
	
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
				ari.getAuth().setUser(pack.getWorldPersonalData().getPersonalData().getUsername());
				ari.getAuth().setPwd(pack.getWorldPersonalData().getPersonalData().getPassword());
				response=new ARI(ari.getAuth(), "SuccessfulCreateAccount", infoRequest);
			}catch(UsernameAlreadyExisting uae){
				response=new ARI(null, "UsernameAlreadyExistingCreateAccount", infoRequest);
			}
		}
		return response;
	}

}
