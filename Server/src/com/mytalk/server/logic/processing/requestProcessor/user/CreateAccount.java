/**
* Filename: CreateAccount.java
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

import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.PersonalData;
import com.mytalk.server.logic.processing.requestProcessor.*;
import com.mytalk.server.exceptions.*;

public class CreateAccount extends GenericRequest{
	
	public CreateAccount(){}
	
	public ARI manage(ARI ari){
		String i=ari.getInfo();
		ARI a=null;
		WorldPack x=(WorldPack)conv.convertJsonToJava(i, WorldPack.class);
		PersonalData p=x.getPersonalData();
		com.mytalk.server.data.model.User u=new com.mytalk.server.data.model.User(p.getUsername(), p.getPassword(), p.getName(), p.getSurname(), p.getEmail());
		try{
			da.createAccount(u);
			a=new ARI(ari.getAuth(), "Successful", null);
		}catch(UsernameAlreadyExisting uae){
			a=new ARI(null, "UsernameAlreadyExisting", null);
		}
		
		return a;
	}

}
