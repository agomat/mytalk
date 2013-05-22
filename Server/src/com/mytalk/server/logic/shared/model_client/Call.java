/**
* Filename: Call.java
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

public class Call{
	
	private Integer speaker; 
	private boolean caller;
	private String startDate;
	private Integer duration;
	private Integer byteSent;
	private Integer byteReceived;
	
	public Call(){}
	
	public Call(Integer s, boolean c, String sd, Integer d, Integer bs, Integer br){
		speaker=s;
		byteSent=bs;
		byteReceived=br;
		duration=d;
		startDate=sd;
		caller=c;
	}
	
	public Integer getSpeaker(){
		return speaker;
	}
	public void setSpeaker(Integer n){
		speaker=n;
	}
	public boolean getCaller(){
		return caller;
	}
	public void setCaller(boolean s){
		caller=s;
	}
	public Integer getDuration(){
		return duration;
	}
	public void setDuration(Integer t){
		duration=t;
	}
	public Integer getByteSent(){
		return byteSent;
	}
	public void setByteSent(Integer n){
		byteSent=n;
	}
	public Integer getByteReceived(){
		return byteReceived;
	}
	public void setByteReceived(Integer n){
		byteReceived=n;
	}
	public String getStartDate(){
		return startDate;
	}
	public void setStartDate(String t){
		startDate=t;
	}
	
}
