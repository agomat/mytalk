/**
* Filename: CreateAccount.java
* Package: com.mytalk.server.logic.processing.request_processor.user
* Author: Nicolò Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-02 |   NT      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.user;

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
			PersonalData p=pack.getWorldPersonalData().getPersonalData();
			String md5=getHashMD5(p.getEmail());
			String pwdHash=getHashMD5(p.getPassword());
			com.mytalk.server.data.model.User u=new com.mytalk.server.data.model.User(p.getUsername(), pwdHash,p.getEmail(), p.getName(), p.getSurname(),md5);
			try{
				da.createAccount(u);
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
