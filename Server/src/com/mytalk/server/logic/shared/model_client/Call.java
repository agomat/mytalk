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
	private int duration;
	private int byteSent;
	private int byteReceived;
	
	public Call(){}
	
	public Call(Integer s, boolean c, String sd, int d, int bs, int br){
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
	public int getDuration(){
		return duration;
	}
	public void setDuration(int t){
		duration=t;
	}
	public int getByteSent(){
		return byteSent;
	}
	public void setByteSent(int n){
		byteSent=n;
	}
	public int getByteReceived(){
		return byteReceived;
	}
	public void setByteReceived(int n){
		byteReceived=n;
	}
	public String getStartDate(){
		return startDate;
	}
	public void setStartDate(String t){
		startDate=t;
	}
	
}
