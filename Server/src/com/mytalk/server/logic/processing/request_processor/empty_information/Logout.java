/**
* Filename: Logout.java
* Package: com.mytalk.server.logic.processing.request_processor.empty_information
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


package com.mytalk.server.logic.processing.request_processor.empty_information;

import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.LogoutException;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;

public class Logout extends GenericRequest{
	
	public Logout(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		Authentication auth=ari.getAuth();
		if(auth.getIp()==null){
			response= new ARI(null,"CorruptedPack",null);
		}
		else{
			OnlineUser o=new OnlineUser(auth.getUser(), auth.getIp());
			try {
				da.logout(o);
			} catch (LogoutException e) {
			}
			finally{
				response=new ARI(null, "Logout", null);
			}
		}	
		return response;
	}
}
