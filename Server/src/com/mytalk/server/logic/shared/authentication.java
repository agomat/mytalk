/**
* Filename: authentication.java
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

class authentication{
	public String username;
	public String password;
	
	public authentication(String a, String b)
	{
		username=a;
		password=b;
	}
	public void setUser(String r){
		username=r;
	}
	public String getUser(){
		return username;
	}
	public void setPwd(String r){
		password=r;
	}
	public String getPwd(){
		return password;
	}
}
