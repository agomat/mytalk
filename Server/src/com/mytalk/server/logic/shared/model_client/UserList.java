/**
* Filename: UserList.java
* Package: com.mytalk.server.logic.shared.model_client
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

package com.mytalk.server.logic.shared.model_client;

import java.util.List;

public class UserList{
	
	private Integer id;
	private String name;
	private List<Integer> list; 
	
	public UserList(){}
	
	public UserList( Integer i,String n,List<Integer> c){
		list=c;
		name=n;
		id=i;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int i){
		id=i;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String c){
		name=c;
	}
	
	public List<Integer> getList(){
		return list;
	}
	public void setList(List<Integer> c){
		list=c;
	}
}
