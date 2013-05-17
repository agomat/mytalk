/**
* Filename: AddCall.java
* Package: com.mytalk.server.logic.processing.requestProcessor.stats
* Author: Michael Ferronato
* Date: 2013-05-01 
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-01 |  MF       | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.requestProcessor.stats;



import java.util.List;

import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.exceptions.IdNotFound;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.GiveCallPack;
import com.mytalk.server.logic.shared.modelClient.Call;
import com.mytalk.server.logic.shared.modelClient.WrapperCall;

public class AddCall extends GenericRequest {

	@Override
	public ARI manage(ARI ari) {
		ARI response=null;
		Authentication auth=ari.getAuth();
		String infoRequest=ari.getInfo();
		GiveCallPack pack=(GiveCallPack)conv.convertJsonToJava(infoRequest, GiveCallPack.class);
		boolean checkAuth=this.checkAuthenticationWellFormed(auth);
		boolean checkPack=this.checkGiveCallPackWellFormed(pack);
		if(!checkAuth || !checkPack){
			response=new ARI(null,"CorruptedPack",null);
		}	
		else{
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			WrapperCall wrapperCall=pack.getWrapperCall();//ottengo WrapperCall
			List<Call> listOfCall=wrapperCall.getList();// lista di chiamate
			com.mytalk.server.data.model.Call callServer=null;
			Call callClient=null;
			String caller=null;
			String receiver=null;
			int duration=0;
			String startdate=null;
			int byteR=0;
			int byteS=0;
			try{
				for(int i=0;i<listOfCall.size();i++){
					callClient=listOfCall.get(i); //ottengo una chiamata
					if(callClient.getCaller()){
						caller=auth.getUser();
					}
					else{
						
						caller=(da.getUserById(callClient.getSpeaker())).getUsername();
					}
					receiver=(da.getUserById(callClient.getSpeaker())).getUsername();
					duration=callClient.getDuration();
					startdate=callClient.getStartDate();
					byteR=callClient.getByteReceived();
					byteS=callClient.getByteSent();
					callServer=new com.mytalk.server.data.model.Call(caller,receiver,duration,startdate,byteR,byteS);
					da.addCall(callServer,userAuth);
					response=new ARI(null,"SuccessfulAddCall",null);
				}
			}catch(AuthenticationFail af){
				response=new ARI(null,"AuthenticationFail",null);
			} catch (IdNotFound e) {
				response=new ARI(null,"IdNotFound",null);
			}
		}
		return response;
	}

}
