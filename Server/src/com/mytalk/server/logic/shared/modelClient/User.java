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

package com.mytalk.server.logic.shared.modelClient;

public class User{
	//private Integer id;
	private String username;
	private boolean online;
	private String name;
	private String surname;	
	private String ip;

	public User (){}
	
	public User(String us, boolean o,String n,String sn,String i){
		username=us;
		name=n;
		surname=sn;
		online=o;
		ip=i;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String u){
		username=u;
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
	
	public void setSurname(String sn){
		surname=sn;
	}
	
	public boolean getOnline(){
		return online;
	}
	
	public void setStatus(boolean o){
		online=o;
	}
}
