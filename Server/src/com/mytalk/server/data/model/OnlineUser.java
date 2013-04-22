/**
* Filename: OnlineUser.java
* Package: com.mytalk.server.data.model
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

package com.mytalk.server.data.model;

public class OnlineUser{
	private String username;
	private String ip;

	public OnlineUser(){}
	
	public OnlineUser(String us, String ipadd){
		username=us;
		ip=ipadd;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String s){
		username=s;
	}
	public String getIp(){
		return ip;
	}
	public void setIp(String s){
		ip=s;
	}
}
