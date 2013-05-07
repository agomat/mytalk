/**
* Filename: PersonalData.java
* Package: com.mytalk.server.logic.shared.modelclient
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

package com.mytalk.server.logic.shared.modelClient;

public class PersonalData{
		
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String ip;

	public PersonalData(){}
	
	public PersonalData(String us, String pwd, String n, String s,String mail, String i){
		username=us;
		password=pwd;
		email=mail;
		name=n;
		surname=s;
		ip=i;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String u){
		username=u;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setPassword(String p){
		password=p;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String n){
		name=n;
	}
	
	public String getSurname(){
		return surname;
	}
	
	public void setSurname(String s){
		surname=s;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String e){
		email=e;
	}
	
	public String getIp(){
		return ip;
	}
	
	public void setIp(String i){
		ip=i;
	}
}