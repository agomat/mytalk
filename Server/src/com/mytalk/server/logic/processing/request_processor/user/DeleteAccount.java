/**
* Filename: DeleteAccount.java
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

import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;

public class DeleteAccount extends GenericRequest{

	public DeleteAccount(){}
	
	public ARI manage(ARI ari){
		String infoRequest=ari.getInfo();
		ARI response=null;
		WorldPack pack=(WorldPack)conv.convertJsonToJava(infoRequest, WorldPack.class);
		boolean checkPack=checkWorldPackWellFormed(pack);
		if(!checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			PersonalData p=pack.getWorldPersonalData().getPersonalData();
			com.mytalk.server.data.model.User u=new com.mytalk.server.data.model.User(p.getUsername(), p.getPassword(), p.getName(), p.getSurname(), p.getEmail(),p.getMd5());
			try{
				da.deleteAccount(u);
				response=new ARI(null, "SuccessfulDeleteAccount", null);
			}catch(AuthenticationFail af){
				response=new ARI(null, "AuthenticationFail", null);
			}
		}
		return response;
	}
}
