/**
* Filename: StateUpdateTest.java
* Package: com.mytalk.server.logic.processing.request_processor.state_update_information
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

package com.mytalk.server.logic.processing.request_processor.state_update_information;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.exceptions.IdNotFoundException;
import com.mytalk.server.exceptions.UserNotExistingException;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.empty_information.DeleteAccount;
import com.mytalk.server.logic.processing.request_processor.state_update_information.StateUpdate;
import com.mytalk.server.logic.processing.request_processor.world_information.UpdateAccount;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.UserStatePack;
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
		UpdateAccount updateAccount=new UpdateAccount();
		Authentication auth=new Authentication("user3","user3","123.123.123.3");
		PersonalData personalData=new PersonalData(4,"user3","user3","user3","user3","user3@mytalk.com","emailhash123123123","123.123.123.3");
		WorldPersonalData wpd=new WorldPersonalData(personalData);
		WorldPack pack=new WorldPack(null,wpd);
		String packString=conv.convertJavaToJson(pack);
		ARI ari=new ARI(auth,"Login",packString);
		
		ARI esito=stateUpdate.manage(ari);
		String stringEsito=esito.getInfo();
		UserStatePack userStatePack=(UserStatePack)conv.convertJsonToJava(stringEsito, UserStatePack.class);
		User userEsito=userStatePack.getList();
		User user=new User(4,"user3","user3","user3","emailhash123123123","123.123.123.3",true);
		
		assertEquals(userEsito.getId(),user.getId());
		assertEquals(userEsito.getIp(),user.getIp());
		assertEquals(userEsito.getMd5(),user.getMd5());
		assertEquals(userEsito.getName(),user.getName());
		assertEquals(userEsito.getSurname(),user.getSurname());
		assertEquals(userEsito.getUsername(),user.getUsername());
		assertEquals(userEsito.getOnline(),user.getOnline());
		
		PersonalData updatePersonalData=new PersonalData(4,"user3","user3","user03","user3","user3@mytalk.com","emailhash123123123","123.123.123.3");
		wpd.setPersonalData(updatePersonalData);
		pack.setWorldPersonalData(wpd);
		packString=conv.convertJavaToJson(pack);
		ARI ariUpdate= new ARI(auth,"UpdateAccount",packString);
		updateAccount.manage(ariUpdate);
		esito=stateUpdate.manage(ariUpdate);
		stringEsito=esito.getInfo();
		userStatePack=(UserStatePack)conv.convertJsonToJava(stringEsito, UserStatePack.class);
		userEsito=userStatePack.getList();
		user=new User(4,"user3","user03","user3","emailhash123123123","123.123.123.3",true);
		
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
		userStatePack=(UserStatePack)conv.convertJsonToJava(stringEsito, UserStatePack.class);
		userEsito=userStatePack.getList();
		assertFalse(userEsito.getOnline());

		
		DeleteAccount deleteAccount=new DeleteAccount();
		Authentication authRightTest=new Authentication("user3","user3","123.123.123.3");
		ARI ariDelete=new ARI(authRightTest,"DeleteAccount",null);
		ARI ariResult=deleteAccount.manage(ari);
		UserStatePack userPack=(UserStatePack)conv.convertJsonToJava(ariResult.getInfo(), UserStatePack.class);
		User userTest=userPack.getList();
		ariDelete.setInfo(ariResult.getInfo());
		esito=stateUpdate.manage(ariDelete);
		stringEsito=esito.getInfo();
		userStatePack=(UserStatePack)conv.convertJsonToJava(stringEsito, UserStatePack.class);
		userEsito=userStatePack.getList();
		assertEquals(userTest.getId(),userEsito.getId());
		assertEquals(userTest.getUsername(),userEsito.getUsername());
		assertEquals(userTest.getName(),userEsito.getName());
		assertEquals(userTest.getSurname(),userEsito.getSurname());
		assertEquals(userTest.getMd5(),userEsito.getMd5());
		assertEquals(userTest.getIp(),userEsito.getIp());
		assertEquals(userTest.getOnline(),userEsito.getOnline());
		
		/**
		 *  Le due eccezioni UserNotExistingException e IdNotFoundException non vengono lanciate 
		 *  per correttezza da costruzione
		 */
	}

}
