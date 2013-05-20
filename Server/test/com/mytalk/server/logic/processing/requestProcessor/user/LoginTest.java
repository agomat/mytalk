/**
* Filename: LoginTest.java
* Package: com.mytalk.server.logic.processing.requestProcessor.user
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


package com.mytalk.server.logic.processing.requestProcessor.user;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.shared.modelClient.UserList;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.modelClient.PersonalData;
import com.mytalk.server.logic.shared.modelClient.WorldPersonalData;
import com.mytalk.server.logic.shared.modelClient.User;

public class LoginTest {

	private EnvironmentSetter envSetter=new EnvironmentSetter();
	private Convert conv=new Convert();
	
	@Before
	public void setTestEnvironment(){
		envSetter.cleanDB();
		envSetter.initDB();
	}
	
	@Test
	public void testManage() {
		Login login=new Login();
		Authentication authRightTest=new Authentication("user1","user1","123.123.123.1");
		PersonalData personalData=new PersonalData("user1","user1","user1","user1","user1@mytalk.com","emailhash123123123","123.123.123.1");
		WorldPersonalData wpd=new WorldPersonalData(personalData);
		WorldPack pack=new WorldPack(null,wpd);
		String packString=conv.convertJavaToJson(pack);
		
		ARI ari=new ARI(authRightTest,"Login",packString);
		ARI ariResult=login.manage(ari);
		assertEquals("Login fallito ma i dati sono corretti","SuccessfulLogin",ariResult.getReq());
		
		String worldPackString=ariResult.getInfo();
		WorldPack packTest=(WorldPack)conv.convertJsonToJava(worldPackString, WorldPack.class);
		
		List<User> list=packTest.getWorldList().getList();
		for(int i=0;i<list.size();i++){		
			if(list.get(i).getUsername().equals("user0") || list.get(i).getUsername().equals("user1") || list.get(i).getUsername().equals("user2") || list.get(i).getUsername().equals("user3")){
				assertTrue(list.get(i).getUsername(),list.get(i).getOnline());	
			}
			else{
				assertFalse(list.get(i).getUsername(),list.get(i).getOnline());
			}
		}
		
		List<UserList> listUserList=packTest.getWorldList().getUserList();
		List<Integer> listTest=new ArrayList<Integer>();
		listTest.add(new Integer(1));
		listTest.add(new Integer(3));
		listTest.add(new Integer(4));
		listTest.add(new Integer(5));
		for(int j=0;j<listUserList.size();j++){
			for(int k=0;k<listUserList.get(j).getList().size();k++){
				if(listUserList.get(j).getName().equals("Blacklist")){
					assertEquals(listUserList.get(j).getList().get(k),new Integer(8));
				}
				if(listUserList.get(j).getName().equals("friends")){
					assertEquals(listUserList.get(j).getList().get(k),listTest.get(k));
				}
			}
		}
		
		PersonalData pd=packTest.getWorldPersonalData().getPersonalData();
		assertEquals("123.123.123.1",pd.getIp());
		assertEquals("user1",pd.getUsername());
		assertEquals("user1",pd.getSurname());
		assertEquals("user1",pd.getName());
		assertEquals("user1",pd.getPassword());
		assertEquals("user1@mytalk.com",pd.getEmail());
		assertEquals("emailhash123123123",pd.getMd5());
		

		
		Authentication authWrongTest=new Authentication("user1","user0","111.111.111.1");
		
		ari=new ARI(authWrongTest,"Login",packString);
		ariResult=login.manage(ari);
		assertEquals("Login effettuato anche se viene fallita l'autenticazione","AuthenticationFail",ariResult.getReq());
		
		PersonalData personalData1=new PersonalData("user11","user11","user11","user11","user11@mytalk.com","us11us11us11us11us11us11us11us11","111.111.111.11");
		wpd=new WorldPersonalData(personalData1);
		pack=new WorldPack(null,wpd);
		packString=conv.convertJavaToJson(pack);
		ari=new ARI(authRightTest,"Login",packString);
		ariResult=login.manage(ari);
		assertEquals("Login effettuato con dati autenticazione diversi da quelli in personal data","UsernameNotExisting",ariResult.getReq());
		
		authWrongTest.setIp("111.11.111.1");
		authWrongTest.setPwd("user1");
		ari.setAuth(authWrongTest);
		wpd.setPersonalData(personalData);
		packString=conv.convertJavaToJson(pack);
		ari.setInfo(packString);
		ariResult=login.manage(ari);
		assertEquals("Tentativo di login con ip non loggato","IpNotLogged",ariResult.getReq());
		
		
		ari.setAuth(authRightTest);
		ariResult=login.manage(ari);
		assertEquals("Tentativo di login con user già loggato","UserAlreadyLogged",ariResult.getReq());
		
		PersonalData personalData2=new PersonalData("user5","user5","user5","user5","user5@mytalk.com","us05us05us08us08us08us08us08us08","123.123.123.0");
		wpd.setPersonalData(personalData2);
		packString=conv.convertJavaToJson(pack);
		ari.setInfo(packString);
		authRightTest=new Authentication("user5","user5","123.123.123.0");
		ari.setAuth(authRightTest);
		ariResult=login.manage(ari);
		assertEquals("Tentativo di login con ip già assegnato ad un altro user","IpAlreadyLogged",ariResult.getReq());
		//UserNotLogged mai lanciata poiché i candidati sono oggetti ricavati dal db e quindi consistenti
	}

}
