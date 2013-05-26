/**
* Filename: Convert.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolò Toso
* Date: 2013-04-29
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2	  |	2013-05-13 | NT        | [#] Modifica dei nomi di alcune variabili in tutti i metodi al
* 										 fine di renderli più espressivi  
* 0.1	  |	2013-04-29 | NT        | [+] Creazione classe, costruttore e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/
package com.mytalk.server.logic.processing;

import com.google.gson.*;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Information;


public class Convert {
	
	private Gson gson=new Gson();
	
	public Convert(){}
	
	//JSON -> Java
	public ARI convertJsonToJava(String stringJson){		
		ARI infoObject =gson.fromJson(stringJson, ARI.class);
		return infoObject;
	}
	
	//JSON -> Java per i pack
	public Information convertJsonToJava(String stringJson,Class info){	
		Information infoEntity=gson.fromJson(stringJson,info);
		return infoEntity;
	}
	
	//Java->JSON
	public String convertJavaToJson(ARI object){
	String stringJson = gson.toJson(object);
	return stringJson;
	}
	
	public String convertJavaToJson(Information infoObject){
		String stringJson = gson.toJson(infoObject);
		return stringJson;
	}
}
