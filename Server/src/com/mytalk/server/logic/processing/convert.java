/**
* Filename: convert.java
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

public class convert {
	
	Gson gson=new Gson();
	
	public convert(){}
	
	//JSON -> Java
	public ARI JsonToJava(String stringJson){
		ARI user =gson.fromJson(stringJson, ARI.class);
		return user;
	}
	
	//Java->JSON
	public String JavaToJson(ARI create){
	String user = gson.toJson(create);
	return user;
	}
}
