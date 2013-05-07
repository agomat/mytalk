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
		Call other = (Call) obj;
		if (byteReceived != other.byteReceived) {
			return false;
		}
		if (byteSent != other.byteSent) {
			return false;
		}
		if (caller == null) {
			if (other.caller != null) {
				return false;
			}
		} else if (!caller.equals(other.caller)) {
			return false;
		}
		if (duration != other.duration) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (receiver == null) {
			if (other.receiver != null) {
				return false;
			}
		} else if (!receiver.equals(other.receiver)) {
			return false;
		}
		if (startdate == null) {
			if (other.startdate != null) {
				return false;
			}
		} else if (!startdate.equals(other.startdate)) {
			return false;
		}
		return true;
	}
}
