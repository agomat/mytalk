/**
* Filename: RefuseCall.java
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

import com.mytalk.server.logic.processing.requestProcessor.*;
import com.mytalk.server.data.model.*;
import com.mytalk.server.logic.shared.*;
import com.mytalk.server.exceptions.*;

public class RefuseCall extends GenericRequest{

	public RefuseCall(){}
	
	public ARI manage(ARI ari){
		String i=ari.getInfo();
		ARI a=null;
		ConnectionPack x=(ConnectionPack)conv.convertJsonToJava(i, ConnectionPack.class);
		boolean result=da.checkUserByIp(x.getMyIp());
		if(result)
			a=new ARI(ari.getAuth(), "SuccessfulRefuseCall", null);
		else
			a=new ARI(ari.getAuth(), "UnsuccessfulRefuseCall", null);
		return a;
	}
}

