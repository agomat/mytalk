/**
* Filename: Convert.java
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

import com.google.gson.*;
import com.mytalk.server.logic.shared.*;

public class Convert {
	
	Gson gson=new Gson();
	
	public Convert(){}
	
	//JSON -> Java
	public ARI convertJsonToJava(String stringJson){
		ARI object =gson.fromJson(stringJson, ARI.class);
		return object;
	}
	
	//JSON -> Java per i pack
	public Information convertJsonToJava(String stringJson,Class info){	
		Information infoEntity=gson.fromJson(stringJson,info);
		return infoEntity;
	}
	
	//Java->JSON
	public String convertJavaToJson(ARI create){
	String string = gson.toJson(create);
	return string;
	}
	
	public String convertJavaToJson(Information info){
		String string = gson.toJson(info);
		return string;
	}
}
