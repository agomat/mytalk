/**
* Filename: WrapperCall.java
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

package com.mytalk.server.logic.shared.modelclient;

import java.util.List;

public class WrapperCall{
	
	private List<Call> list;
	
	public WrapperCall(){}
	
	public WrapperCall(List<Call> c){
		list=c;
	}
	
	public List<Call> getList(){
		return list;
	}
	public void setList(List<Call> c){
		list=c;
	}
}
