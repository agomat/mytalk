/**
* Filename: LogoutAsAnonymous.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
* Author: Michael Ferronato
* Date: 2013/05/17
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013/05/17 |   MF      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.requestProcessor.user;

import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.LogoutException;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.PersonalData;

public class LogoutAsAnonymous extends GenericRequest {
	LogoutAsAnonymous(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		String infoRequest=ari.getInfo();
		WorldPack pack=(WorldPack)conv.convertJsonToJava(infoRequest, WorldPack.class);
		boolean checkPack=checkWorldPackWellFormed(pack);
		if(!checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			PersonalData p=pack.getWorldPersonalData().getPersonalData();
			OnlineUser o=new OnlineUser(p.getUsername(), ari.getAuth().getIp());
			try {
				da.logoutToAnonymous(o);
				response=new ARI(null, "SuccessfulLogoutAsAnonymous", null);
			} catch (LogoutException e) {
				response= new ARI(null, "LogoutException",null);
			}
				
		}
		return response;
	}
}
