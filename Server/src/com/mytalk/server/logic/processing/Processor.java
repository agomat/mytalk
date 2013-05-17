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
* 0.1	  |	2013-04-29 | NT        | [+] Creazione classe, costruttore e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/
package com.mytalk.server.logic.processing;

import com.mytalk.server.comunication.Message;
import com.mytalk.server.logic.shared.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.*;

public class Processor implements IProcessor{
	//classe che riceve e manda il JSON alla Comunication
	//comunica la richiesta alla GenericRequest
	Map<String, String> hm;
	
	public Processor(){
		hm = new HashMap<String, String>();
		
		hm.put("AcceptCall","com.mytalk.server.logic.processing.requestProcessor.comunication.AcceptCall");
		hm.put("RefuseCall","com.mytalk.server.logic.processing.requestProcessor.comunication.RefuseCall");
		hm.put("UserCall","com.mytalk.server.logic.processing.requestProcessor.comunication.UserCall");
		
		hm.put("CreateAccount","com.mytalk.server.logic.processing.requestProcessor.user.CreateAccount");
		hm.put("DeleteAccount","com.mytalk.server.logic.processing.requestProcessor.user.DeleteAccount");
		hm.put("Login","com.mytalk.server.logic.processing.requestProcessor.user.Login");
		hm.put("LoginAsAnonymous","com.mytalk.server.logic.processing.requestProcessor.user.LoginAsAnonymous");
		hm.put("Logout","com.mytalk.server.logic.processing.requestProcessor.user.Logout");
		
		hm.put("BlackListAdd","com.mytalk.server.logic.processing.requestProcessor.list.BlackListAdd");
		hm.put("BlackListRemove","com.mytalk.server.logic.processing.requestProcessor.list.BlackListRemove");
		hm.put("ListCreate","com.mytalk.server.logic.processing.requestProcessor.list.ListCreate");
		hm.put("ListDelete","com.mytalk.server.logic.processing.requestProcessor.list.ListDelete");
		hm.put("ListUserAdd","com.mytalk.server.logic.processing.requestProcessor.list.ListUserAdd");
		hm.put("ListUserRemove","com.mytalk.server.logic.processing.requestProcessor.list.ListUserRemove");
		
		hm.put("AddCall","com.mytalk.server.logic.processing.requestProcessor.stats.AddCall");
		hm.put("GetCalls","com.mytalk.server.logic.processing.requestProcessor.stats.GetCalls");

	}
		
	//metodo che riceve JSON, ottiene la conversione in ARI e manda la Stringa "richiesta", controllando tipo
	public Message processRequest(Message message){
		
		Convert c=new Convert();
		ARI pack=c.convertJsonToJava(message.getJson());
		String request=pack.getReq();
		ARI esito=null;
		String ipToSend=null;
		Message response=null;
		try{
			String r= hm.get(request);
			Class<?> cl=Class.forName(r);
			Object obj=cl.newInstance();
			Method m= cl.getDeclaredMethod("manage", ARI.class);
			esito=(ARI)m.invoke(obj, pack);	
		}catch(ClassNotFoundException cnfe){
			// TODO Auto-generated catch block
			cnfe.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(esito.getAuth().getIp()!=null){
			ipToSend=esito.getAuth().getIp();
		}
		else{
			ipToSend=message.getIp();
		}
		esito.setAuth(null);
		String json=c.convertJavaToJson(esito);
		response=new Message(ipToSend,json);
		return response;
	}
}
