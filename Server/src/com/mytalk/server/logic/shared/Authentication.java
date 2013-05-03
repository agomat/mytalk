/**
* Filename: Authentication.java
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

public class Authentication{
	private String username;
	private String password;
	private String ip;
	
	public Authentication(String user, String pwd,String i)
	{
		username=user;
		password=pwd;
		ip=i;
	}
	public void setUser(String u){
		username=u;
	}
	public String getUser(){
		return username;
	}
	public void setPwd(String p){
		password=p;
	}
	public String getPwd(){
		return password;
	}
	
	public void setIp(String i){
		ip=i;
	}
	public String getIp(){
		return ip;
	}
}
