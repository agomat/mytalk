/**
* Filename: LoginAsAnonymous.java
* Package: com.mytalk.server.logic.processing.request_processor.world_information
* Author: Michael Ferronato
* Date: 2013-05-07
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-07 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.world_information;

import com.mytalk.server.data.model.OnlineUser;
import com.mytalk.server.exceptions.IpAlreadyLogged;
import com.mytalk.server.logic.processing.request_processor.GenericRequest;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;
import com.mytalk.server.logic.shared.model_client.WorldPersonalData;

public class LoginAsAnonymous extends GenericRequest {

	@Override
	public ARI manage(ARI ari) {
		ARI response=null;
		if(ari.getAuth()==null || ari.getAuth().getIp()==null){
			response=new ARI(null,"CorruptedPack",null);
		}else{
			OnlineUser o=new OnlineUser(null, ari.getAuth().getIp());
			String infoResponse=null;
			try {
				da.loginAsAnonymous(o);
				PersonalData pd=new PersonalData(null,null,null,null,null,null,null,ari.getAuth().getIp());
				WorldPersonalData wpd=new WorldPersonalData(pd);
				WorldPack wlp=new WorldPack(null,wpd);
				infoResponse=conv.convertJavaToJson(wlp);
				response=new ARI(null,"SuccessfulLoginAsAnonymous",infoResponse);
			} catch (IpAlreadyLogged e) {
				response=new ARI(null,"IpAlreadyLoggedLoginAsAnonymous",infoResponse);
			}
		}
		return response;
	}

}
