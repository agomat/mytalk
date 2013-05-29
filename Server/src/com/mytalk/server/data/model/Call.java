/**
* Filename: Call.java
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

public class Call{
	private int id=0;
	private String caller;
	private String receiver;
	private int duration;
	private String startdate;
	private int byteSent;
	private int byteReceived;

	public Call(){}
	
	public Call(String call, String rece, int time, String date, int bs, int br){
		caller=call;
		receiver=rece;
		duration=time;
		startdate=date;
		byteSent=bs;
		byteReceived=br;
	}
	public int getId(){
		return id;
	}
	public void setId(int n){
		id=n;
	}
	public String getCaller(){
		return caller;
	}
	public void setCaller(String s){
		caller=s;
	}
	public String getReceiver(){
		return receiver;
	}
	public void setReceiver(String s){
		receiver=s;
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
	public String getStartdate(){
		return startdate;
	}
	public void setStartdate(String t){
		startdate=t;
	}
}

	