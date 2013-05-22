/**
* Filename: StateUpdateTest.java
* Package: com.mytalk.server.logic.processing.request_processor.user
* Author: Michael Ferronato
* Date: 2013-05-20
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-20 |    MF     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.processing.request_processor.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.user.StateUpdate;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;
import com.mytalk.server.logic.shared.model_client.User;
import com.mytalk.server.logic.shared.model_client.WorldPersonalData;

public class StateUpdateTest {

	private EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		StateUpdate stateUpdate=new StateUpdate();
		Authentication auth=new Authentication("user3","user3","123.123.123.3");
		PersonalData personalData=new PersonalData(4,"user3","user3","user3","user3","user3@mytalk.com","emailhash123123123","123.123.123.3");
		WorldPersonalData wpd=new WorldPersonalData(personalData);
		WorldPack pack=new WorldPack(null,wpd);
		String packString=conv.convertJavaToJson(pack);
		ARI ari=new ARI(auth,"Login",packString);
		ARI esito=stateUpdate.manage(ari);
		String stringEsito=esito.getInfo();
		WorldPack worldPack=(WorldPack)conv.convertJsonToJava(stringEsito, WorldPack.class);
		User userEsito=worldPack.getWorldList().getList().get(0);
		User user=new User(4,"user3","user3","user3","emailhash123123123","123.123.123.3",true);
		
		assertEquals(userEsito.getId(),user.getId());
		assertEquals(userEsito.getIp(),user.getIp());
		assertEquals(userEsito.getMd5(),user.getMd5());
		assertEquals(userEsito.getName(),user.getName());
		assertEquals(userEsito.getSurname(),user.getSurname());
		assertEquals(userEsito.getUsername(),user.getUsername());
		assertEquals(userEsito.getOnline(),user.getOnline());
		
		
		ari.setReq("Logout");
		esito=stateUpdate.manage(ari);
		stringEsito=esito.getInfo();
		worldPack=(WorldPack)conv.convertJsonToJava(stringEsito, WorldPack.class);
		userEsito=worldPack.getWorldList().getList().get(0);
		assertFalse(userEsito.getOnline());
		// Le due eccezioni non vengono lanciate per correttezza da costruzione
	}

}
