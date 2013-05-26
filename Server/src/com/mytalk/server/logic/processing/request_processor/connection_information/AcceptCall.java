/**
* Filename: AcceptCall.java
* Package: com.mytalk.server.logic.processing.request_processor.connection_information
* Author: Nicolò Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2	  | 2013-05-13 |   NT	   | [#] Modifica dei nomi di alcune variabili nel metodo manage al
* 										 fine di renderli più espressivi ed eliminazione import superflui
* 0.1	  |	2013-05-02 |   NT      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.connection_information;

import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ConnectionPack;

public class AcceptCall extends GenericRequest{
	
public AcceptCall(){}
	
public ARI manage(ARI ari){
	String infoRequest=ari.getInfo();
	ARI response=null;
	Authentication auth=null;
	ConnectionPack pack=(ConnectionPack)conv.convertJsonToJava(infoRequest, ConnectionPack.class);
	boolean checkPack=checkAnonymousConnectionPackWellFormed(pack);
	if(!checkPack){
		response=new ARI(null,"CorruptedPack",infoRequest);
	}
	else {
		boolean result=da.checkUserByIp(pack.getSpeakerIp());
		if(result){
			auth=new Authentication(null, null, pack.getSpeakerIp());
			response=new ARI(auth, "SuccessfulAcceptCall",infoRequest);
		}
		else{
			response=new ARI(null, "UnsuccessfulAcceptCall", infoRequest);
		}
	}
	return response;
}
}
