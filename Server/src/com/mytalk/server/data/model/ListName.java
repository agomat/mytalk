/**
* Filename: ListName.java
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

public class ListName{
	private int id=0;
	private String name;
	private String owner;

	public ListName(){}
	
	public ListName(String n, String o){
		name=n;
		owner=o;
	}
	public int getId(){
		return id;
	}
	public void setId(int n){
		id=n;
	}
	public String getName(){
		return name;
	}
	public void setName(String s){
		name=s;
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
		ListName other = (ListName) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (owner == null) {
			if (other.owner != null) {
				return false;
			}
		} else if (!owner.equals(other.owner)) {
			return false;
		}
		return true;
	}
}
