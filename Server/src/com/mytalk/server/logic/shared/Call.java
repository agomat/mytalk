/**
* Filename: Call.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolò Toso
* Date: 2013-04-18
*
* Diary:
*
* Version |
Date
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-12 | NT        | [+] Creazione classe e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.shared;

import java.sql.Time;

public class Call{
	
	private String speaker;
	private int byteSent;
	private int byteReceived;
	private Time duration;
	private boolean caller;
	
	public Call(){}
	
	public Call(String s, int bs, int br, Time d, boolean c){
		speaker=s;
		byteSent=bs;
		byteReceived=br;
		duration=d;
		caller=c;
	}
	
	public String getSpeaker(){
		return speaker;
	}
	public void setSpeaker(String n){
		speaker=n;
	}
	public boolean getCaller(){
		return caller;
	}
	public void setCaller(boolean s){
		caller=s;
	}
	public Time getDuration(){
		return duration;
	}
	public void setDuration(Time t){
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
	
}
