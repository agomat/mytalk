/**
* Filename: CreateAccount.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
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


package com.mytalk.server.logic.processing.requestProcessor.user;

import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.PersonalData;
import com.mytalk.server.logic.processing.requestProcessor.*;
import com.mytalk.server.exceptions.*;

public class CreateAccount extends GenericRequest{
	
	public CreateAccount(){}
	
	public ARI manage(ARI ari){
		String infoRequest=ari.getInfo();
		ARI response=null;
		WorldPack pack=(WorldPack)conv.convertJsonToJava(infoRequest, WorldPack.class);
		boolean checkPack=this.checkWorldPackWellFormed(pack);
		if(!checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			PersonalData p=pack.getWorldPersonalData().getPersonalData();
			String md5=this.getHashMD5(p.getEmail());
			com.mytalk.server.data.model.User u=new com.mytalk.server.data.model.User(p.getUsername(), p.getPassword(),p.getEmail(), p.getName(), p.getSurname(),md5);
			try{
				da.createAccount(u);
				response=new ARI(ari.getAuth(), "SuccessfulCreateAccount", null);
			}catch(UsernameAlreadyExisting uae){
				response=new ARI(null, "UsernameAlreadyExisting", null);
			}
		}
		return response;
	}

}
