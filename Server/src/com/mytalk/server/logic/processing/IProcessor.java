/**
* Filename: IProcessor.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolò Toso
* Date: 2013-04-29
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2	  | 2013-05-06 | NT        | [+] Aggiunta metodo public List<Message> processRequest(Message message)
* 0.1	  |	2013-04-29 | NT        | [+] Creazione interfaccia 
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/


package com.mytalk.server.logic.processing;

import java.util.List;

import com.mytalk.server.communication.buffer.Message;

public interface IProcessor {
	
	public List<Message> processRequest(Message message);
	
}
