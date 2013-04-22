/**
* Filename: ToConfirmAccount.java
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

public class ToConfirmAccount{
	private String username;
	private String password;
	private String email;

	public ToConfirmAccount(){}
	
	public ToConfirmAccount(String us, String pwd, String mail){
		username=us;
		password=pwd;
		email=mail;
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
	
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String e){
		email=e;
	}
}
