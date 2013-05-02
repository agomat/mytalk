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
	private String speakerIp;
	private String sdpCall;
	
	
	public ConnectionPack(String cIp,String rIp,String sdpC){
		myIp=cIp;
		speakerIp=rIp;
		sdpCall=sdpC;
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
	
	public String getSdpCall(){
		return sdpCall;
	}
	
	public void setSdpCall(String sdp){
		sdpCall=sdp;
	}
}
