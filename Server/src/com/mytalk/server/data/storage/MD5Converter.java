/**
* Filename: MD5converter.java
* Package: com.mytalk.server.logic
* Author: Nicolo' Mazzucato
* Date: 2013-05-22
*
* Diary:
* Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.2     | 2013-06-18 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.1	  |	2013-05-22 |    NM     | [+] Inserimento classe, oggetti e costruttore     
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe di utilita' che espone un metodo per eseguire l'hash md5 di una stringa
*/


package com.mytalk.server.data.storage;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Converter {

	/**
	 * Ritorna la stringa che corrisponde all'hash md5 della stringa passata
	 * 
	 * @method +getHashMD5
	 * @param {String} s e' l'oggetto che contiene la stringa da convertire in MD5
	 * @return {String}
	 */
	public static String getHashMD5(String s){
		String string=null;
		try {
			byte[] byteMail= s.getBytes("UTF-8");
			MessageDigest md=MessageDigest.getInstance("MD5");
			byte[] digest=md.digest(byteMail);
			BigInteger integer=new BigInteger(1,digest);
			string=integer.toString(16);	
 		} catch (UnsupportedEncodingException e) {
			return s;
		} catch (NoSuchAlgorithmException e) {
			return s;
		}
		return string;
	}
}
