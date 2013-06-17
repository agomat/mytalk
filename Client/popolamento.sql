drop database if exists MyTalk;
create database if not exists MyTalk;
use MyTalk;

CREATE TABLE IF NOT EXISTS Users
(
  username varchar(20)  NOT NULL,
  id integer(10) UNIQUE NOT NULL AUTO_INCREMENT,
  password varchar(32)  NOT NULL,
  name varchar(50) NOT NULL,
  surname varchar(50) NOT NULL,
  email varchar(100)  NOT NULL,
  emailhash varchar(32) NOT NULL,
  PRIMARY KEY (username)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Blacklists
(
        owner   VARCHAR(20) NOT NULL,
        username  VARCHAR(50) NOT NULL,
        PRIMARY KEY (owner,username),
        FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE,
        FOREIGN KEY (owner) REFERENCES Users(username) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Calls
(
        id      INTEGER(12) NOT NULL  AUTO_INCREMENT,
        caller    VARCHAR(20) ,
        receiver  VARCHAR(20) ,
        duration  INTEGER(10) NOT NULL,
        startdate VARCHAR(20)   NOT NULL,
        byteSent  INTEGER(10) NOT NULL,
        byteReceived  INTEGER(10) NOT NULL,
        PRIMARY KEY (id),
        FOREIGN KEY (caller) REFERENCES Users(username) ON DELETE SET NULL,
        FOREIGN KEY (receiver) REFERENCES Users(username) ON DELETE SET NULL
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ListNames
(
  id      INTEGER(9)  NOT NULL  AUTO_INCREMENT,
    name    VARCHAR(20) NOT NULL,
    owner   VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (owner) REFERENCES Users(username) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS UserLists
(
        idList    INTEGER(9)  NOT NULL,
        username  VARCHAR(20) NOT NULL,
        PRIMARY KEY (idList,username),
        FOREIGN KEY (idList) REFERENCES ListNames(id) ON DELETE CASCADE,
        FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS OnlineUsers
 (
  ip      varchar(40)  NOT NULL,
  username   varchar(20),
  PRIMARY KEY (ip),
  FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
) ENGINE=InnoDB;

ALTER TABLE ListNames AUTO_INCREMENT = 2;

DELIMITER //
DROP PROCEDURE IF EXISTS popolamento;
CREATE PROCEDURE popolamento (p INT, s INT)
BEGIN
        DECLARE i INT DEFAULT s;
        ciclo: WHILE i < p DO
          INSERT INTO Users VALUES 
              (CONCAT("user",i),i,md5(CONCAT("user",i,"user",i)),CONCAT("user",i),CONCAT("user",i),"agomat@gmail.com",md5("agomat@gmail.com"));
          SET i = i + 1;
        END WHILE ciclo;
END; //
DELIMITER ;

source .popolamento/popolamentoTestUsers.sql; 
source .popolamento/popolamentoTestBlacklists.sql;
source .popolamento/popolamentoTestCalls.sql;
source .popolamento/popolamentoTestListNames.sql;
source .popolamento/popolamentoTestOnlineUsers.sql;
source .popolamento/popolamentoTestUserLists.sql;

