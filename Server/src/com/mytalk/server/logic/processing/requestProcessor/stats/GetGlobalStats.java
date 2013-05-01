/**
* Filename: GetGlobalStats.java
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

import com.mytalk.server.logic.processing.requestProcessor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.GiveCallPack;
import com.mytalk.server.logic.shared.modelClient.Call;
import com.mytalk.server.logic.shared.modelClient.WrapperCall;

public class GetGlobalStats extends GenericRequest {

	public GetGlobalStats(){}
	
	public ARI manage(ARI ari) {
		ARI response=null;
		GetCalls getCalls=new GetCalls();
		ARI ariResponse=getCalls.manage(ari);//passo l'ari anche se il campo richiesta è diverso
		String infoRequest=null;
		GiveCallPack packRequest=null;
		GiveCallPack packResponse=null;
		List<Call> listOfCallClient=null;
		WrapperCall wrapperCall=null;
		List<Call> listCallResponse=new ArrayList<Call>();
		Call statsCall=null;
		String infoResponse=null;
		int bs=0,br=0,d=0;
		if(ariResponse.getReq().equals("GiveCalls")){
			infoRequest=ariResponse.getInfo(); //ottengo l'info
			packRequest=(GiveCallPack)conv.convertJsonToJava(infoRequest, GiveCallPack.class);
			wrapperCall=packRequest.getWrapperCall();//contiene una list<Call>
			listOfCallClient=wrapperCall.getList(); // lista di call
			for(int i=0;i<listOfCallClient.size();i++){
				bs=bs+listOfCallClient.get(i).getByteSent();
				br=br+listOfCallClient.get(i).getByteReceived();			
				d=d+listOfCallClient.get(i).getDuration();
			}
			statsCall=new Call(null,bs,br,d,null,false);//chiamata che rappresenta le statistiche
			listCallResponse.add(statsCall); //creo la lista con una chiamata
			packResponse=new GiveCallPack(listCallResponse); // creo il GiveCallPack
			infoResponse=conv.convertJavaToJson(packResponse);
			response=new ARI(null,"GiveGlobalStats",infoResponse);
		}
		return response;
	}

}
