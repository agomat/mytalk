/**
 * Filename: DataAccessTest.java
 * Package: com.mytalk.server.data.storage
 * Author: Nicolò Mazzucato
 * Date: 2013-05-22
 *
 * Diary:
 * Version | Date       | Developer | Changes
 * --------+------------+-----------+------------------
 * 0.1	   | 2013-05-22 |    NM     | [+] Inserimento classe e metodi   
 *
 * This software is distributed under GNU/GPL 2.0.
 *
 * Software licensed to:
 * - Zucchetti SRL
 */

package com.mytalk.server.data;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.mytalk.server.data.storage.DataAccessTest;
import com.mytalk.server.data.storage.dao.GenericDAOTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   DataAccessTest.class,
   GenericDAOTest.class
})
public class DataTestSuite {

}
