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
import com.mytalk.server.exceptions.UserNotExisting;
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
		Authentication auth=ari.getAuth();
		boolean checkAuth=checkAuthenticationWellFormed(auth);
		if(!checkAuth){
			response=new ARI(null,"CorruptedPack",null);
		}
		else{
			com.mytalk.server.data.model.User userAuth=new com.mytalk.server.data.model.User(auth.getUser(),auth.getPwd(),null,null,null,null);
			List<com.mytalk.server.data.model.Call> listOfCall=null;
			com.mytalk.server.data.model.Call callServer=null;
			List<Call> listCallClient=new ArrayList<Call>();
			boolean caller=false;
			Integer speaker=null;
			GiveCallPack giveCallPack=null;
			String infoResponse=null;
			WrapperCall wrapperCall=new WrapperCall(listCallClient);
			try{
				listOfCall=da.getCalls(userAuth);//ottengo una lista di chiamateServer
				for(int i=0;i<listOfCall.size();i++){
					callServer=listOfCall.get(i); // ottengo una chiamata formato modelServer
					if(callServer.getCaller().equals(userAuth.getUsername())){ //verifico chi è il chiamante
						caller=true;
						speaker=da.getIdFromUsername(callServer.getReceiver());
						
						listCallClient.add(new Call(speaker, caller, callServer.getStartdate(),callServer.getDuration() , callServer.getByteSent(),callServer.getByteReceived()));
						//incremento contatori statistiche globali
						wrapperCall.increaseTotalByteSent(callServer.getByteSent());
						wrapperCall.increaseTotalByteReceived(callServer.getByteReceived());
						wrapperCall.increaseTotalDuration(callServer.getDuration());
					}
					else{
						caller=false;
						speaker=da.getIdFromUsername(callServer.getCaller());
						listCallClient.add(new Call(speaker, caller, callServer.getStartdate(), callServer.getDuration(), callServer.getByteReceived(),callServer.getByteSent()));
						//incremento contatori statistiche globali invertendo byteReceived con byteSent
						wrapperCall.increaseTotalByteSent(callServer.getByteReceived());
						wrapperCall.increaseTotalByteReceived(callServer.getByteSent());
						wrapperCall.increaseTotalDuration(callServer.getDuration());
					}			
				}
				giveCallPack=new GiveCallPack(wrapperCall);
				infoResponse=conv.convertJavaToJson(giveCallPack);
				response=new ARI(null,"GiveCalls",infoResponse);
			}catch(AuthenticationFail af){
				response=new ARI(null,"AuthenticationFail",null);
			} catch (UserNotExisting e) {
				response=new ARI(null,"UsernameNotExisting",null);
			}	
		}
		return response;
	}

}
