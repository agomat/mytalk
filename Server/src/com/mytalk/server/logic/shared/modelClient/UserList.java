/**
* Filename: UserList.java
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
* 0.1	  |	2013-04-18 | NT        | [+] Creazione classe e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.shared.modelClient;

import java.util.List;

public class UserList{
	
	private String name;
	private List<String> list;
	
	public UserList(){}
	
	public UserList(List<String> c, String n){
		list=c;
		name=n;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String c){
		name=c;
	}
	
	public List<String> getList(){
		return list;
	}
	public void setList(List<String> c){
		list=c;
	}
}
