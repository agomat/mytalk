/**
* Filename: WrapperUser.java
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

public class WrapperUser{
	
	private List<User> list;
	private static Integer id=0;
	
	public WrapperUser(){}
	
	public WrapperUser(List<User> c){
		list=c;
	}
	
	public List<User> getList(){
		return list;
	}
	public void setList(List<User> c){
		list=c;
	}
}
