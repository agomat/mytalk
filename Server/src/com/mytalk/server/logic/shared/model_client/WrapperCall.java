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
	private Integer totalByteSent=0;
	private Integer totalByteReceived=0;
	private Integer totalDuration=0;
	
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
	
	public Integer getTotalByteSent(){
		return totalByteSent;
	}
	
	public void increaseTotalByteSent(Integer increase){
		totalByteSent=totalByteSent+increase;
	}
	
	public Integer getTotalByteReceived(){
		return totalByteReceived;
	}
	
	public void increaseTotalByteReceived(Integer increase){
		totalByteReceived=totalByteReceived+increase;
	}
	
	public Integer getTotalDuration(){
		return totalDuration;
	}
	
	public void increaseTotalDuration(Integer increase){
		totalDuration=totalDuration+increase;
	}
}
