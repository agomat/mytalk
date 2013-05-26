/**
* Filename: LogoutAsAnonymous.java
* Package: com.mytalk.server.logic.processing.request_processor.empty_information
* Author: Michael Ferronato
* Date: 2013-05-17
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2	  |	2013-05-22 |   MF      | [+] Modifica dei nomi del metodo manage al fine di renderli
* 									     più espressivi e di facile comprensione
* 0.1	  |	2013-05-17 |   MF      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.empty_information;

import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.LogoutException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;

public class LogoutToAnonymous extends GenericRequest {
	LogoutToAnonymous(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		if(!checkAuth){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			OnlineUser onlineUser=new OnlineUser(auth.getUser(), auth.getIp());
			try {
				da.logoutToAnonymous(onlineUser);
			} catch (LogoutException e) {
			}	
			finally{
				response=new ARI(null, "Logout", null);
			}
		}
		return response;
	}
}
