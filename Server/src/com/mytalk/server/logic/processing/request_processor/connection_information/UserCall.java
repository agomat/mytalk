/**
* Filename: UserCall.java
* Package: com.mytalk.server.logic.processing.request_processor.connection_information
* Author: Nicolò Toso
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2	  | 2013-05-13 |   NT	   | [#] Modifica dei nomi di alcune variabili in tutti i metodi al
* 										 fine di renderli più espressivi ed eliminazione import superflui
* 0.1	  |	2013-05-02 |   NT      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.processing.request_processor.connection_information;

import com.mytalk.server.data.model.Blacklist;
import com.mytalk.server.exceptions.IdNotFound;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.ConnectionPack;

public class UserCall extends GenericRequest{
	public UserCall(){}
	
	public ARI manage(ARI ari){
		String infoRequest=ari.getInfo();
		ARI response=null;
		boolean checkAuth=true;
		boolean checkPack=false;
		boolean result=false;
		boolean checkPresence=false;
		ConnectionPack pack=(ConnectionPack)conv.convertJsonToJava(infoRequest, ConnectionPack.class);
		Authentication auth=null;
		try {
			if(ari.getAuth().getUser()==null){
				checkPack=checkAnonymousConnectionPackWellFormed(pack);
				if(!checkPack){	
					return new ARI(null,"CorruptedPack",infoRequest);
				}
			}
			else{
				checkPack=checkConnectionPackWellFormed(pack);
				checkAuth=checkAuthenticationWellFormed(ari.getAuth());
				if(!checkAuth || !checkPack){
					return new ARI(null,"CorruptedPack",infoRequest);
				}
				String callerUsername = da.getUserById(pack.getMyUserId()).getUsername();
				String speakerUsername=da.getUserById(pack.getSpeakerUserId()).getUsername();
				Blacklist blacklist=new Blacklist(speakerUsername,callerUsername);
				checkPresence = da.checkBlacklist(blacklist);
			}
			result=da.checkUserByIp(pack.getSpeakerIp());
			if(result && !checkPresence){
				auth=new Authentication(null, null,pack.getSpeakerIp());
				response=new ARI(auth,"SuccessfulUserCall",infoRequest);
			}
			else{
				response=new ARI(null,"UnsuccessfulUserCall",infoRequest);
			}
		}catch (IdNotFound e) {
			response= new ARI(null,"IdNotFoundUserCall",infoRequest);
		} 
		return response;
	}
}
 