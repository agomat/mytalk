/**
* Filename: UserList.java
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

import java.io.Serializable;

public class UserList implements Serializable{
	
	private int idList;
	private String username;
	
	public UserList(){}

	public UserList(int ident, String us){
		idList=ident;
		username=us;
	}

	public int getIdList(){
		return idList;
	}
	public void setIdList(int n){
		idList=n;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String s){
		username=s;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UserList other = (UserList) obj;
		if (idList != other.idList) {
			return false;
		}
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		return true;
	}
}
