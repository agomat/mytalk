/**
* Filename: AcceptCall.java
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
import com.mytalk.server.logic.shared.*;

public class AcceptCall extends GenericRequest{
	
public AcceptCall(){}
	
	public ARI manage(ARI ari){
		String infoRequest=ari.getInfo();
		ARI response=null;
		Authentication auth=null;
		ConnectionPack pack=(ConnectionPack)conv.convertJsonToJava(infoRequest, ConnectionPack.class);
		boolean check=this.checkConnectionPackWellFormed(pack);
		if(!check){
			response=new ARI(null,"CorruptedConnectionPack",null);
		}
		else {
			boolean result=da.checkUserByIp(pack.getSpeakerIp());
			if(result){
				auth=new Authentication(null, null, pack.getSpeakerIp());
				response=new ARI(auth, "SuccessfulAcceptCall",infoRequest);
			}
			else{
				auth=new Authentication(null,null, pack.getMyIp());
				response=new ARI(auth, "UnsuccessfulAcceptCall", null);
			}
		}
		return response;
	}
}
