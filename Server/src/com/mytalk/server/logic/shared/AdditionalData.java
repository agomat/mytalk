/**
* Filename: AdditonalData.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolò Toso
* Date: 2013-04-18
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-12 | NT        | [+] Creazione classe e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.shared;

public class AdditionalData{
		
	private String password;
	private String email;
	private String code;
	private String sdp;
	private String user;
	private String stats;
	
	public AdditionalData(){}
	
	public AdditionalData(String p, String e, String c, String s, String u,String stat){
		password=p;
		email=e;
		code=c;
		sdp=s;
		user=u;
		stats=stat;
	}
	
	public String getPassword(){
		return password;
	}
	public void setPassword(String n){
		password=n;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String n){
		email=n;
	}
	public String getCode(){
		return code;
	}
	public void setCode(String n){
		code=n;
	}
	public String getSdp(){
		return sdp;
	}
	public void setSdp(String n){
		sdp=n;
	}
	public String getUser(){
		return user;
	}
	public void setUser(String n){
		user=n;
	}
	public String getStats(){
		return stats;
	}
	public void setStats(String s){
		stats=s;
	}
}