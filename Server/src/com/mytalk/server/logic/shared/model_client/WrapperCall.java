/**
* Filename: WrapperCall.java
* Package: com.mytalk.server.logic.shared.model_client
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

package com.mytalk.server.logic.shared.model_client;

import java.util.List;

public class WrapperCall{
	
	private List<Call> list;
	private int totalByteSent=0;
	private int totalByteReceived=0;
	private int totalDuration=0;
	
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
	
	public int getTotalByteSent(){
		return totalByteSent;
	}
	
	public void increaseTotalByteSent(int increase){
		totalByteSent=totalByteSent+increase;
	}
	
	public int getTotalByteReceived(){
		return totalByteReceived;
	}
	
	public void increaseTotalByteReceived(int increase){
		totalByteReceived=totalByteReceived+increase;
	}
	
	public int getTotalDuration(){
		return totalDuration;
	}
	
	public void increaseTotalDuration(int increase){
		totalDuration=totalDuration+increase;
	}
}
