/**
* Filename: LoginAsAnonymous.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
* Author: Michael Ferronato
* Date: 2013-05-07
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-07 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.requestProcessor.user;

import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.IpAlreadyLogged;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;

public class LoginAsAnonymous extends GenericRequest {

	@Override
	public ARI manage(ARI ari) {
		ARI response=null;
		if(ari.getAuth()==null || ari.getAuth().getIp()==null){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			OnlineUser o=new OnlineUser(null, ari.getAuth().getIp());
			try {
				da.loginAsAnonymous(o);
				response=new ARI(null,"SuccessfulLoginAsAnonymous",null);//rispondere con l'ip dell'utente
			} catch (IpAlreadyLogged e) {
				response=new ARI(null,"IpAlreadyLogged",null);
			}
		}
		return response;
	}

}
