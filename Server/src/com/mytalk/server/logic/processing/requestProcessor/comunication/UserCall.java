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
		String infoRequest=ari.getInfo();
		ARI a=null;
		ConnectionPack pack=(ConnectionPack)conv.convertJsonToJava(infoRequest, ConnectionPack.class);
		Authentication auth=null;
		boolean check=this.checkConnectionPackWellFormed(pack);
		if(!check){
			auth=new Authentication(null,null,pack.getMyIp());// potrebbe cambiare
			a=new ARI(auth,"CorruptedConnectionPack",null);
		}
		else {
			boolean result=da.checkUserByIp(pack.getSpeakerIp());
			if(result){
				auth=new Authentication(null, null,pack.getSpeakerIp());
				a=new ARI(auth,"SuccessfulUserCall",infoRequest);
			}
			else{
				auth=new Authentication(null, null, pack.getMyIp());
				a=new ARI(auth,"UnsuccessfulUserCall",null);
			}
		}
		return a;
	}
}
 