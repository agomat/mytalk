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
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.PersonalData;

public class LogoutAsAnonymous extends GenericRequest {
	LogoutAsAnonymous(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		if(!checkAuth){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			OnlineUser o=new OnlineUser(auth.getUser(), auth.getIp());
			try {
				da.logoutToAnonymous(o);
				response=new ARI(null, "Logout", null);
			} catch (LogoutException e) {
				response= new ARI(null, "LogoutException",null);
			}		
		}
		return response;
	}
}
