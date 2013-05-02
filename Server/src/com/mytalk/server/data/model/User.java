/**
* Filename: User.java
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

public class User{
	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;

	public User (){}
	
	public User(String us, String pwd, String mail, String n, String s){
		username=us;
		password=pwd;
		email=mail;
		name=n;
		surname=s;
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
}
