/**
* Filename: User.java
* Package: com.mytalk.server.logic.shared.model_client
* Author: Nicolò Toso
* Date: 2013-04-11
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-11 | NT        | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.shared.model_client;

public class User{
	
	private Integer id;
	private String username;
	private String name;
	private String surname;	
	private String md5;
	private String ip;
	private boolean online;

	public User (){}
	
	public User(Integer id_user, String us, String n, String sn, String m, String i, boolean o){
		id=id_user;
		username=us;
		name=n;
		surname=sn;
		online=o;
		ip=i;
		md5=m;
	}
	
	public Integer getId(){
		return id;
	}
	
	public void setId(Integer id_user){
		id=id_user;
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
	
	public String getMd5(){
		return md5;
	}
	
	public void setMd5(String m){
		md5=m;
	}
	
	public String getIp(){
		return ip;
	}
	
	public void setIp(String i){
		ip=i;
	}
}
