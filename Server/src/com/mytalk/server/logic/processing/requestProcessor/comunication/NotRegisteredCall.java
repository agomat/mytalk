/**
* Filename: RegisteredCall.java
* Package: com.mytalk.server.logic.processing.requestProcessor.comunication
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

package com.mytalk.server.logic.processing.requestProcessor.comunication;

import com.mytalk.server.logic.shared.ARI;

public class NotRegisteredCall {
	public NotRegisteredCall(){}
	
	public void manage(ARI ari){
		String i=ari.getInfo();
		//conversione dell'info --> Pack
		// conversione ModelClient 2 ModelServer
		
		//com.mytalk.server.data.model.User u=new com.mytalk.server.data.model.User(uc.getUsername(),null,null);
	}
}
