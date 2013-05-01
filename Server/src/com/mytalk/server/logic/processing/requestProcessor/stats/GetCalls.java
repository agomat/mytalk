/**
* Filename: GetCalls.java
* Package: com.mytalk.server.logic.processing.requestProcessor.list
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

import java.util.ArrayList;
import java.util.List;

import com.mytalk.server.exceptions.AuthenticationFail;
import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.GiveCallPack;
import com.mytalk.server.logic.shared.modelClient.Call;
import com.mytalk.server.logic.shared.modelClient.WrapperCall;

public class GetCalls extends GenericRequest {
	
	public GetCalls(){}

	public ARI manage(ARI ari) {
		ARI response=null;
		//creo oggetto necessario per l'autenticazione
		Authentication auth=ari.getAuth();
		com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null);
		List<com.mytalk.server.data.model.Call> listOfCall=null;
		com.mytalk.server.data.model.Call callServer=null;
		List<Call> listCallClient=new ArrayList<Call>();
		boolean caller=false;
		String speaker=null;
		GiveCallPack giveCallPack=null;
		String infoResponse=null;
		try{
			listOfCall=da.getCalls(auth.getUser(),userAuth);//ottengo una lista di chiamate
			for(int i=0;i<listOfCall.size();i++){
				callServer=listOfCall.get(i); // ottengo una chiamata formato modelServer
				if(callServer.getCaller().equals(userAuth.getUsername())){ //verifico chi è il chiamante
					caller=true;
					speaker=callServer.getReceiver();
					listCallClient.add(new Call(speaker,callServer.getByteSent(),callServer.getByteReceived(),callServer.getDuration(),callServer.getStartdate(),caller));
				}
				else{
					caller=false;
					speaker=callServer.getCaller();
					listCallClient.add(new Call(speaker,callServer.getByteReceived(),callServer.getByteSent(),callServer.getDuration(),callServer.getStartdate(),caller));
				}			
			}

			giveCallPack=new GiveCallPack(listCallClient);
			infoResponse=conv.convertJavaToJson(giveCallPack);
			response=new ARI(null,"GiveCalls",infoResponse);
		}catch(AuthenticationFail af){
			response=new ARI(null,"AuthenticationFail",null);
		}	
		return response;
	}

}
