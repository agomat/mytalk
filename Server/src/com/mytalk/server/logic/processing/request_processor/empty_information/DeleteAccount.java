/**
* Filename: DeleteAccount.java
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

import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;


public class DeleteAccount extends GenericRequest{

	public DeleteAccount(){}
	
	public ARI manage(ARI ari){
		ARI response=null;
		com.mytalk.server.data.model.User u=new com.mytalk.server.data.model.User(ari.getAuth().getUser(), ari.getAuth().getPwd(), null, null, null,null);
		try{
			da.deleteAccount(u);
			response=new ARI(null, "SuccessfulDeleteAccount", null);
		}catch(AuthenticationFail af){
			response=new ARI(null, "AuthenticationFailDeleteAccount", null);
		}
		return response;
	}
}
