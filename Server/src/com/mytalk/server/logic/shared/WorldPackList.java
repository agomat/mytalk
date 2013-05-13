/**
* Filename: WorldPackList.java
* Package: com.mytalk.server.logic.shared
* Author: Michael Ferronato
* Date: 2013-05-02
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-05-02 |   MF      | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.shared;

import java.util.List;

import com.mytalk.server.logic.shared.modelClient.User;
import com.mytalk.server.logic.shared.modelClient.UserList;

public class WorldPackList {
	private List<UserList> userList;
	private List<User> list;
	
	public WorldPackList(List<UserList> ul, List<User> l){
		userList=ul;
		list=l;
	}
	
	public List<UserList> getUserList(){
		return userList;
	}
	public void setUserList(List<UserList> l){
		userList=l;
	}
	
	public List<User> getList(){
		return list;
	}
	public void setList(List<User> c){
		list=c;
	}

}
