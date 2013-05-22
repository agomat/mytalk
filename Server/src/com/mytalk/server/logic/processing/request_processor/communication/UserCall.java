/**
* Filename: UserCall.java
* Package: com.mytalk.server.logic.processing.request_processor.communication
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

package com.mytalk.server.logic.processing.request_processor.communication;

import com.mytalk.server.data.model.Blacklist;
import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.IdNotFound;
import com.mytalk.server.exceptions.UsernameNotCorresponding;
import com.mytalk.server.logic.processing.request_processor.*;
import com.mytalk.server.logic.shared.*;

public class UserCall extends GenericRequest{
	public UserCall(){}
	
	public ARI manage(ARI ari){
		String infoRequest=ari.getInfo();
		ARI response=null;
		boolean checkAuth=false;
		boolean checkPack=false;
		boolean result=false;
		ConnectionPack pack=(ConnectionPack)conv.convertJsonToJava(infoRequest, ConnectionPack.class);
		Authentication auth=null;
		
		if(ari.getAuth()==null){
			checkPack=checkAnonymousConnectionPackWellFormed(pack);
			if(!checkPack){
				response=new ARI(null,"CorruptedPack",null);
			}
			else{
				result=da.checkUserByIp(pack.getSpeakerIp());
				if(result){
					auth=new Authentication(null, null,pack.getSpeakerIp());
					response=new ARI(auth,"SuccessfulUserCall",infoRequest);
				}
				else{
					auth=new Authentication(null, null, pack.getMyIp());
					response=new ARI(auth,"UnsuccessfulUserCall",null);
				}
			}
		}
		else{
			checkAuth=checkAuthenticationWellFormed(ari.getAuth());
			checkPack=checkConnectionPackWellFormed(pack);
			if(!checkPack || !checkAuth){
				response=new ARI(null,"CorruptedPack",null);
			}
			else {
				result=da.checkUserByIp(pack.getSpeakerIp());
				com.mytalk.server.data.model.User user=new com.mytalk.server.data.model.User(ari.getAuth().getUser(),ari.getAuth().getPwd(),null,null,null,null);
				try {
					String callerUsername=da.getUserById(pack.getMyUserId()).getUsername();
					String speakerUsername=da.getUserById(pack.getSpeakerUserId()).getUsername();
					Blacklist b=new Blacklist(callerUsername,speakerUsername);
					boolean checkPresence=false;
				
					checkPresence = da.checkBlacklist(user,b);
					if(result && !checkPresence){
						auth=new Authentication(null, null,pack.getSpeakerIp());
						response=new ARI(auth,"SuccessfulUserCall",infoRequest);
					}
					else{
						auth=new Authentication(null, null, pack.getMyIp());
						response=new ARI(auth,"UnsuccessfulUserCall",null);
					}
				} catch (AuthenticationFail e) {
					auth=new Authentication(null, null, pack.getMyIp());
					response= new ARI(auth,"AuthenticationFail",infoRequest);
				} catch (UsernameNotCorresponding e) {
					auth=new Authentication(null, null, ari.getAuth().getIp());
					response= new ARI(auth,"UsernameNotCorresponding",infoRequest);
				} catch (IdNotFound e) {
					auth=new Authentication(null, null, ari.getAuth().getIp());
					response= new ARI(auth,"IdNotFound",infoRequest);
				}		
			}
		}
		return response;
	}
}
 