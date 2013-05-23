/**
* Filename: UserStatePack.java
* Package: com.mytalk.server.logic.shared
* Author: 
* Date:
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	           |           | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.shared;

import com.mytalk.server.logic.shared.model_client.User;

public class UserStatePack extends Information {
	private User user;
	
	public UserStatePack(User u){
		user=u;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setUser(User u){
		user=u;
	}
}
