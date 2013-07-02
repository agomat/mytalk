/**
* Filename: LoginTest.java
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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mytalk.server.EnvironmentSetter;
import com.mytalk.server.logic.shared.model_client.UserList;
import com.mytalk.server.logic.processing.Convert;
import com.mytalk.server.logic.processing.request_processor.world_information.Login;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;
import com.mytalk.server.logic.shared.WorldPack;
import com.mytalk.server.logic.shared.model_client.PersonalData;
import com.mytalk.server.logic.shared.model_client.User;

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
		
		ARI ari=new ARI(authRightTest,"Login",null);
		ARI ariResult=login.manage(ari);
		assertEquals("Login fallito ma i dati sono corretti","SuccessfulLogin",ariResult.getReq());
		
		String worldPackString=ariResult.getInfo();
		WorldPack packTest=(WorldPack)conv.convertJsonToJava(worldPackString, WorldPack.class);
		
		List<User> list=packTest.getWorldList().getList();
		for(int i=0;i<list.size();i++){	
			if(list.get(i).getUsername().equals("user1")){
				fail("E' presente anche lui stesso");
			}
			if(list.get(i).getUsername().equals("user0") || list.get(i).getUsername().equals("user2") || list.get(i).getUsername().equals("user3")){
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
		assertEquals("24c9e15e52afc47c225b757e7bee1f9d",pd.getPassword());
		assertEquals("user1@mytalk.com",pd.getEmail());
		assertEquals("emailhash123123123",pd.getMd5());
		
		Authentication authWrongTest=new Authentication("user1","user0","111.111.111.1");
		
		ari=new ARI(authWrongTest,"Login",null);
		ariResult=login.manage(ari);
		assertEquals("Login effettuato anche se viene fallita l'autenticazione","AuthenticationFailLogin",ariResult.getReq());
		
		//UserNotCorrespondingLogin non viene mai sollevata per costruzione
		
		authWrongTest.setIp("111.11.111.1");
		authWrongTest.setPwd("user1");
		ari.setAuth(authWrongTest);
		ariResult=login.manage(ari);
		assertEquals("Tentativo di login con ip non loggato","IpNotLoggedLogin",ariResult.getReq());
		
		
		ari.setAuth(authRightTest);
		ariResult=login.manage(ari);
		assertEquals("Tentativo di login con user gia' loggato","UserAlreadyLoggedLogin",ariResult.getReq());
		
		authRightTest=new Authentication("user5","user5","123.123.123.0");
		ari.setAuth(authRightTest);
		ariResult=login.manage(ari);
		assertEquals("Tentativo di login con ip gia' assegnato ad un altro user","IpAlreadyLoggedLogin",ariResult.getReq());
		
		//UserNotLoggedException mai lanciata poiche' i candidati sono oggetti ricavati dal db e quindi consistenti
	}

}
