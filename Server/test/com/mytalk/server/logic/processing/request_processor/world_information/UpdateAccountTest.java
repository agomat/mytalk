/**
* Filename: UpdateAccountTest.java
* Package: com.mytalk.server.logic.processing.request_processor.world_information
* Author: 
* Date:
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	           |           | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing.request_processor.world_information;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;
import com.mytalk.server.logic.shared.model_client.WorldPersonalData;

public class UpdateAccountTest {

	private EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	@Test
	public void testManage() {
		UpdateAccount updateAccount=new UpdateAccount();
		Authentication auth=new Authentication("user1","user1","123.123.123.1");
		PersonalData personalData=new PersonalData(2,"user1","user1","user1","user1","user1@mytalk.com","us01us01us01us01us01us01us01us01","123.123.123.1");
		WorldPersonalData wpd=new WorldPersonalData(personalData);
		WorldPack pack=new WorldPack(null,wpd);
		String packString=conv.convertJavaToJson(pack);
		ARI ari=new ARI(auth,"UpdateAccount",packString);
		
		ARI response=updateAccount.manage(ari);
		assertEquals("Dati corretti ma fallisce","SuccessfulUpdateAccount",response.getReq());
		
		auth.setUser("user2");
		response=updateAccount.manage(ari);
		assertEquals("Dati di autenticazione errati ma non viene notificato","AuthenticationFailUpdateAccount",response.getReq());
		
		auth.setPwd("user2");
		response=updateAccount.manage(ari);
		assertEquals("Username di autenticazione diverso da quello dei dati","UsernameNotCorrespondingUpdateAccount",response.getReq());
		
		wpd.setPersonalData(null);
		pack.setWorldPersonalData(wpd);
		packString=conv.convertJavaToJson(pack);
		ari.setInfo(packString);
		
		auth.setPwd("user2");
		response=updateAccount.manage(ari);
		assertEquals("Pacchetto mal formato","CorruptedPack",response.getReq());
		
	}

}
