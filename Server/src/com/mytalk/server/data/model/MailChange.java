/**
* Filename: MailChange.java
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

public class MailChange{
	private String username;
	private String newmail;
	private String code;

	public MailChange(){}
	
	public MailChange(String us, String nm, String c){
		username=us;
		newmail=nm;
		code=c;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String s){
		username=s;
	}
	public String getNewmail(){
		return newmail;
	}
	public void setNewmail(String s){
		newmail=s;
	}
	public String getCode(){
		return code;
	}
	public void setCode(String s){
		code=s;
	}
}
