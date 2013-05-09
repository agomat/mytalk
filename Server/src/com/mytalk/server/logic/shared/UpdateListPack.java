/**
* Filename: UpdateListPack.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolò Toso
* Date: 2013-04-23
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-23 | NT        | [+] Creazione classe e costruttore
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/
package com.mytalk.server.logic.shared;
import java.util.List;

import com.mytalk.server.logic.shared.modelClient.*;

public class UpdateListPack extends Information{
	
	private String listName;
	private String owner;
	private String newListName;
	
	public UpdateListPack(String n,String o,String nn){
		listName=n;
		owner=o;
		newListName=nn;
	}
	
	public String getListName(){
		return listName;
	}
	
	public void setListName(String n){
		listName=n;
	}
	
	public String getOwner(){
		return owner;
	}
	
	public void setOwner(String o){
		owner=o;
	}
	
	public String getNewListName(){
		return newListName;
	}
	
	public void setNewListName(String n){
		newListName=n;
	}
}
