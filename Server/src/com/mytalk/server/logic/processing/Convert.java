/**
* Filename: Convert.java
* Package: com.mytalk.server.logic.shared
* Author: Nicolo' Toso
* Date: 2013-04-29
*
* Diary:
| Version | Date       | Developer | Changes
* --------+------------+-----------+------------------
* 0.3     | 2013-06-22 |	MF	   | [+] Aggiunta commenti al codice in formato Javadoc
* 0.2	  |	2013-05-13 |    NT     | [#] Modifica dei nomi di alcune variabili in tutti i metodi al
* 										 fine di renderli piu' espressivi  
* 0.1	  |	2013-04-29 |    NT     | [+] Creazione classe, costruttore e metodi   
*
* This software is distributed under GNU/GPL 2.0.
*
* Software licensed to:
* - Zucchetti SRL
* 
* Classe che ha la funzione di incapsulare le operazioni necessarie per la traduzione di un
* oggetto Java in JSON e viceversa. Essa utilizza metodi propri della libreria esterna Gson.
*/
package com.mytalk.server.logic.processing;

import com.google.gson.*;
import com.mytalk.server.logic.shared.ARI;
import com.mytalk.server.logic.shared.Information;


public class Convert {
	
	/**
	 * Identifica l'oggetto Gson necessario per utilizzare i metodi messi a disposizione dalla 
	 * libreria Gson
	 * 
	 * @property -gson
	 * @type {Gson}
	 */
	private Gson gson=new Gson();
	
	/**
	 * Costruttore della classe con corpo vuoto poiche' non vi sono campi dati da inizializzare
	 * 
	 * @method +Convert
	 */
	public Convert(){}
	
	/**
	 * Riceve in input una stringa JSON e utilizzando l'oggetto gson converte tale stringa in 
	 * un oggetto ARI, e restituisce tale oggetto
	 * 
	 * @method +convertJsonToJava
	 * @param {String} stringJson e' l'oggetto che viene convertito utilizzando la libreria esterna
	 * Gson in un oggetto ARI
	 * @return {ARI}
	 */
	public ARI convertJsonToJava(String stringJson){		
		ARI infoObject =gson.fromJson(stringJson, ARI.class);
		return infoObject;
	}
	
	/**
	 * Riceve in input una stringa JSON e un oggetto Class<?> necessario per dire qual e' la 
	 * classe target della conversione; viene utilizzato l'oggetto gson che converte la stringa 
	 * in un oggetto definito dalla classe target, e restituisce tale oggetto
	 * 
	 * @method +convertJsonToJava
	 * @param {String} stringJson e' l'oggetto che viene convertito utilizzando la libreria esterna
	 * Gson in un oggetto Information
	 * @param {Class<?>} info e' l'oggetto di utilita' per definire il tipo di destinazione della
	 * conversione
	 * @return {Information}
	 */
	public Information convertJsonToJava(String stringJson,Class<?> info){	
		Information infoEntity=(Information)gson.fromJson(stringJson,info);
		return infoEntity;
	}
	
	/**
	 * Riceve un oggetto ARI in input e utilizzando l'oggetto gson converte tale oggetto in
	 *  una stringa che viene poi restituita
	 *  
	 * @method +convertJavaToJson 
	 * @param {ARI} object e' l'oggetto che viene convertito in una stringa utilizzando la libreria
	 * esterna Gson
	 * @return {String}
	 */
	public String convertJavaToJson(ARI object){
	String stringJson = gson.toJson(object);
	return stringJson;
	}
	
	/**
	 * Riceve un oggetto Information in input e utilizzando l'oggetto gson converte tale oggetto 
	 * in una stringa che viene poi restituita
	 *  
	 * @method +convertJavaToJson 
	 * @param {Information} object e' l'oggetto che viene convertito in una stringa utilizzando
	 *  la libreria esterna Gson
	 * @return {String}
	 */
	public String convertJavaToJson(Information infoObject){
		String stringJson = gson.toJson(infoObject);
		return stringJson;
	}
}
