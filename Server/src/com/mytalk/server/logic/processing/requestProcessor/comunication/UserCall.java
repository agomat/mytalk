/**
* Filename: UserCall.java
* Package: com.mytalk.server.logic.processing.requestProcessor.comunication
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

package com.mytalk.server.logic.processing.requestProcessor.comunication;

import com.mytalk.server.logic.processing.requestProcessor.*;
import com.mytalk.server.data.model.*;
import com.mytalk.server.logic.shared.*;

public class UserCall extends GenericRequest{
	public UserCall(){}
	
	public ARI manage(ARI ari){
		String i=ari.getInfo();
		ARI a=null;
		ConnectionPack x=(ConnectionPack)conv.convertJsonToJava(i, ConnectionPack.class);
		String new_ConnectionPack=conv.convertJavaToJson(x);
		boolean result=da.checkUserByIp(x.getSpeakerIp());
		if(result){
			Authentication new_auth=new Authentication(null, null, x.getSpeakerIp());
			a=new ARI(new_auth,"SuccessfulUserCall",new_ConnectionPack);
		}
		else{
			Authentication new_auth=new Authentication(null, null, x.getMyIp());
			a=new ARI(new_auth,"UnsuccessfulUserCall",new_ConnectionPack);
		}
		return a;
	}
}
 