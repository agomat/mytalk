/**
* Filename: GenericRequest.java
* Package: com.mytalk.server.logic.processing
* Author: Nicolò Toso
* Date: 2013-04-29
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.1	  |	2013-04-29 |    NT     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
*/

package com.mytalk.server.logic.processing.requestProcessor;

import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.data.storage.*;
import com.mytalk.server.logic.processing.Convert;

public abstract class GenericRequest {
	
	protected static IDataAccess da=new DataAccess();
	
	protected static Convert conv=new Convert();
	
	public GenericRequest(){};
	
	public abstract ARI manage(ARI pack);

}
