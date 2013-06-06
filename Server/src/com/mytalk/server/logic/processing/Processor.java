/**
* Filename: Processor.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolò Toso
* Date: 2013-04-29
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2	  | 2013-05-13 |    NT	   | [#] Modifica dei nomi di alcune variabili in tutti i metodi al
* 										 fine di renderli più espressivi
* 0.1	  |	2013-04-29 |    NT     | [+] Creazione classe, costruttore e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.processing;

import com.google.gson.JsonSyntaxException;
import com.mytalk.server.communication.buffer.Message;
import com.mytalk.server.logic.processing.request_processor.state_update_information.StateUpdate;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Authentication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.*;

public class Processor implements IProcessor{
	//classe che riceve e manda il JSON alla Comunication
	//comunica la richiesta alla GenericRequest
	private Map<String, String> hashmap;
	private Convert convert=new Convert();
	private StateUpdate stateUpdate=new StateUpdate();
	
	public Processor(){
		hashmap = new HashMap<String, String>();
		
		hashmap.put("AcceptCall","com.mytalk.server.logic.processing.request_processor.connection_information.AcceptCall");
		hashmap.put("RefuseCall","com.mytalk.server.logic.processing.request_processor.connection_information.RefuseCall");
		hashmap.put("UserCall","com.mytalk.server.logic.processing.request_processor.connection_information.UserCall");
		
		hashmap.put("CreateAccount","com.mytalk.server.logic.processing.request_processor.world_information.CreateAccount");
		hashmap.put("DeleteAccount","com.mytalk.server.logic.processing.request_processor.empty_information.DeleteAccount");
		hashmap.put("Login","com.mytalk.server.logic.processing.request_processor.world_information.Login");
		hashmap.put("LoginAsAnonymous","com.mytalk.server.logic.processing.request_processor.world_information.LoginAsAnonymous");
		hashmap.put("Logout","com.mytalk.server.logic.processing.request_processor.empty_information.Logout");
		hashmap.put("LogoutToAnonymous","com.mytalk.server.logic.processing.request_processor.empty_information.LogoutToAnonymous");
		
		hashmap.put("BlackListAdd","com.mytalk.server.logic.processing.request_processor.list_information.BlackListAdd");
		hashmap.put("BlackListRemove","com.mytalk.server.logic.processing.request_processor.list_information.BlackListRemove");
		hashmap.put("ListCreate","com.mytalk.server.logic.processing.request_processor.update_list_information.ListCreate");
		hashmap.put("ListDelete","com.mytalk.server.logic.processing.request_processor.update_list_information.ListDelete");
		hashmap.put("ListUserAdd","com.mytalk.server.logic.processing.request_processor.list_information.ListUserAdd");
		hashmap.put("ListUserRemove","com.mytalk.server.logic.processing.request_processor.list_information.ListUserRemove");
		hashmap.put("UpdateListName","com.mytalk.server.logic.processing.request_processor.update_list_information.UpdateListName");
		
		hashmap.put("AddCall","com.mytalk.server.logic.processing.request_processor.give_call_information.AddCall");
		hashmap.put("GetCalls","com.mytalk.server.logic.processing.request_processor.give_call_information.GetCalls");

	}
		
	public List<Message> processRequest(Message message) throws JsonSyntaxException {
		ARI packInfo=convert.convertJsonToJava(message.getJson());			
		if(packInfo.getAuth()!=null){
			packInfo.getAuth().setIp(message.getIp());
		}
		else{
			packInfo.setAuth(new Authentication(null,null,message.getIp()));
		}
		String request=packInfo.getReq();
		ARI esito=null;
		String ipToSend=null;
		List<Message> responseList=new ArrayList<Message>();
		try{
			String r= hashmap.get(request);
			Class<?> cl=Class.forName(r);
			Object obj=cl.newInstance();
			Method m= cl.getDeclaredMethod("manage", ARI.class);
			esito=(ARI)m.invoke(obj, packInfo);	
		}catch(ClassNotFoundException cnfe){
			// Auto-generated catch block
			cnfe.printStackTrace();
		} catch (SecurityException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!packInfo.getReq().equals("Logout") && !packInfo.getReq().equals("LogoutToAnonymous") && !esito.getReq().equals("UnsuccessfulRefuseCall")){
			if(esito.getAuth()!=null){
				if(esito.getAuth().getUser()!=null){
					packInfo.getAuth().setUser(esito.getAuth().getUser());
				}
				ipToSend=esito.getAuth().getIp();
			}
			else{
				ipToSend=message.getIp();
			}
			esito.setAuth(null);
			String json=convert.convertJavaToJson(esito);
			responseList.add(new Message(ipToSend,json));
		}
		
		if(esito.getReq().equals("SuccessfulLogin") || esito.getReq().equals("SuccessfulLogoutToAnonymous") || esito.getReq().equals("SuccessfulLogout") || esito.getReq().equals("SuccessfulDeleteAccount")){
			if(packInfo.getAuth().getUser()!=null){
				ARI ari=stateUpdate.manage(packInfo);
				String jsonAri=convert.convertJavaToJson(ari);
				responseList.add(new Message("broadcast",jsonAri));
			}
		}
		return responseList;
	}
}
