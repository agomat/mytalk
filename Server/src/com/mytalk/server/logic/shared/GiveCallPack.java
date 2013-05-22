/**
* Filename: GiveCallPack.java
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

import com.mytalk.server.logic.shared.model_client.WrapperCall;

public class GiveCallPack extends Information{
	
	private WrapperCall wc;
	
	public GiveCallPack(WrapperCall c){
		wc=c;
	}
	
	public WrapperCall getWrapperCall(){
		return wc;
	}
	
	public void setWrapperCall(WrapperCall c){
		wc=c;
	}
}

