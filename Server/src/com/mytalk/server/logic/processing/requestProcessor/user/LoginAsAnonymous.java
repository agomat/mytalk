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
import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.IpAlreadyLogged;
import com.mytalk.server.exceptions.IpNotLogged;
import com.mytalk.server.exceptions.UserAlreadyLogged;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.PersonalData;

public class LoginAsAnonymous extends GenericRequest {

	@Override
	public ARI manage(ARI ari) {
		String i=ari.getInfo();
		ARI response=null;
		WorldPack x=(WorldPack)conv.convertJsonToJava(i, WorldPack.class);
		PersonalData p=x.getPersonalData();
		OnlineUser o=new OnlineUser(p.getUsername(), p.getIp());
		com.mytalk.server.data.model.User u=new com.mytalk.server.data.model.User(p.getUsername(), p.getPassword(), p.getName(), p.getSurname(), p.getEmail());
		da.loginAsAnonymous(o);
		
		return response;
	}

}
