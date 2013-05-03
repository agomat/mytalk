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

import com.mytalk.server.logic.shared.*;
import com.mytalk.server.logic.processing.requestProcessor.*;
import com.mytalk.server.logic.processing.requestProcessor.list.*;
import com.mytalk.server.logic.processing.requestProcessor.stats.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.*;

public class Processor {
	//classe che riceve e manda il JSON alla Comunication
	//comunica la richiesta alla GenericRequest
	Map<String, String> hm;
	
	public Processor(){
		hm = new HashMap<String, String>();
		
		// hm.put("richiesta","com.mytalk.server.logic.processing.requestProcessor.pack.richiesta")
		
		hm.put("UserCall","com.mytalk.server.logic.processing.requestProcessor.comunication.UserCall");
		hm.put("RefuseCall","com.mytalk.server.logic.processing.requestProcessor.comunication.RefuseCall");
		
		/*hm.put("CreateAccount",);
		hm.put("DeleteAccount",);
		hm.put("Login",);
		hm.put("Logout",);
		
		hm.put("ListCreate",);
		hm.put("ListDelete",);
		hm.put("ListUserAdd",);
		hm.put("ListUserRemove",);
		hm.put("BlacklistAdd",);
		hm.put("BlackListRemove",);
		
		hm.put("GetCalls",);
		hm.put("AddCall",);*/
	}
		
	//metodo che riceve JSON, ottiene la conversione in ARI e manda la Stringa "richiesta", controllando tipo
	public void processRequest(String json){
		Convert c=new Convert();
		ARI pack=c.convertJsonToJava(json);
		String request=pack.getReq();
		ARI response=null;
		try{
			String r= hm.get(request);
			Class<?> cl=Class.forName(r);
			Object obj=cl.newInstance();
			Method m= cl.getDeclaredMethod("manage", ARI.class);
			response=(ARI)m.invoke(obj, pack);	
		}catch(ClassNotFoundException cnfe){
			
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
		
		up(response);
	}
	
	//metodo che riceve l'ARI, lo converte in JSON e manda la comunication
	public void up(ARI response){
		
		/*
		 * 1) controllare se è una chiamata o no
			- in caso di chiamata: UserCall
				- successful, mettere indirizzo IP destinatario e SDP
				- unsuccessful, mettere indirizzo IP chiamante
			- altrimenti mettere indirizzo IP chiamante
			
			2) RefuseCall da C2->C1: manda indirizzo IP del chiamante al server, così quest'ultimo sa chi avvertire
			   AcceptCall da C2->C1: manda al server 
			   							- il suo indirizzo IP (C2) così C1 sa l'IP a cui dovrà chiamare
			   							- l'indirizzo IP di C1 così il server sa a chi mandare la conferma
			   							- suo SDP 
			   							
			3) N.B.: durante UserCall e AcceptCall, C1 e C2 devono trasmettersi l'SDP in modo tale che la chiamata vada a buon fine
			
			4) convertire da ARI-JSON
			 
		*/
	}
}
