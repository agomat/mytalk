/**
* Filename: ARI.java
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
package com.mytalk.server.logic.shared;

public class ARI{
	
	private Authentication auth;
	private String req;
	private String info;
	
	public ARI(){}
	
	public ARI(Authentication a, String r, String i){
		auth=a;
		req=r;
		info=i;
	}
	
	public void setAuth(Authentication a){
		auth=a;
	}
	public Authentication getAuth(){
		return auth;
	}
	public void setReq(String r){
		req=r;
	}
	public String getReq(){
		return req;
	}
	public void setInfo(String i){
		info=i;
	}
	public String getInfo(){
		return info;
	}
}
