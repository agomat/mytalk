/**
* Filename: Blacklist.java
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

public class Blacklist implements Serializable{
	
	private String owner;
	private String username;
	
	public Blacklist(){}
	
	public Blacklist(String o, String us){
		username=us;
		owner=o;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String s){
		username=s;
	}
	public String getOwner(){
		return owner;
	}
	public void setOwner(String s){
		owner=s;
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
		Blacklist other = (Blacklist) obj;
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
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
