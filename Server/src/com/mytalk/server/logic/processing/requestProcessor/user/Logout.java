/**
* Filename: Logout.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
* Author: 
* Date:
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	           |           | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.requestProcessor.user;

import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.PersonalData;
import com.mytalk.server.logic.processing.requestProcessor.*;

public class Logout extends GenericRequest{
	
	public Logout(){}
	
	public ARI manage(ARI ari){
		String i=ari.getInfo();
		ARI response=null;
		WorldPack x=(WorldPack)conv.convertJsonToJava(i, WorldPack.class);
		PersonalData p=x.getPersonalData();
		OnlineUser o=new OnlineUser(p.getUsername(), p.getIp());
		com.mytalk.server.data.model.User u=new com.mytalk.server.data.model.User(p.getUsername(), p.getPassword(), p.getName(), p.getSurname(), p.getEmail());
		da.logout(o);
		response=new ARI(null, "SuccessfulLogout", null);	
		return response;
	}
}
