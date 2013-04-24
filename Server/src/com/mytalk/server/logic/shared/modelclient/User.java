/**
* Filename: User.java
* Package: com.mytalk.server.logic.shared.modelclient
* Author: Nicolò Toso
* Date: 2013-04-11
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-11 | NT        | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.shared.modelclient;

public class User{
	private String username;
	private boolean status;

	public User (){}
	
	public User(String us, boolean s){
		username=us;
		status=s;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String u){
		username=u;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public void setStatus(boolean p){
		status=p;
	}
}
