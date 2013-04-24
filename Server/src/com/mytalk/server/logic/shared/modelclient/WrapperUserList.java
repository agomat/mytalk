/**
* Filename: WrapperUserList.java
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

package com.mytalk.server.logic.shared.modelclient;

import java.util.List;

public class WrapperUserList{
	
	private List<UserList> list;
	
	public WrapperUserList(){}
	
	public WrapperUserList(List<UserList> c){
		list=c;
	}
	
	public List<UserList> getList(){
		return list;
	}
	public void setList(List<UserList> c){
		list=c;
	}
}
