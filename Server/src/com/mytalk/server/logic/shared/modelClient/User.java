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
	private String username;
	private String name;
	private String surname;
	private boolean status;
	private String ip;

	public User (){}
	
	public User(String us,String n,String sn, boolean s,String i){
		username=us;
		name=n;
		surname=sn;
		status=s;
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
	
	public boolean getStatus(){
		return status;
	}
	
	public void setStatus(boolean p){
		status=p;
	}
}
