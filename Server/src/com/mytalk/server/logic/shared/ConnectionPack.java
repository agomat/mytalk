/**
* Filename: ConnectionPack.java
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

public class ConnectionPack extends Information{
	
	private String myIp;
	private Integer myUserId;
	private String speakerIp;
	private Integer speakerUserId;
	private String RTCinfo;
	
	
	public ConnectionPack(String cIp,Integer cn,String rIp,Integer rn,String rtc){
		myIp=cIp;
		speakerIp=rIp;
		RTCinfo=rtc;
		myUserId=cn;
		speakerUserId=rn;
	}
	
	public String getMyIp(){
		return myIp;
	}
	
	public void setMyIp(String ip){
		myIp=ip;
	}
	
	public String getSpeakerIp(){
		return speakerIp;
	}
	
	public void setSpeakerIp(String ip){
		speakerIp=ip;
	}
	
	public String getRTCinfo(){
		return RTCinfo;
	}
	
	public void setRTCinfo(String rtc){
		RTCinfo=rtc;
	}
	
	public Integer getMyUserId(){
		return myUserId;
	}
	
	public void setMyUserId(Integer mi){
		myUserId=mi;
	}
	
	public Integer getSpeakerUserId(){
		return speakerUserId;
	}
	
	public void setSpeakerUserId(Integer si){
		speakerUserId=si;
	}
}
