CREATE DATABASE IF NOT EXISTS MyTalk;

USE MyTalk;

CREATE TABLE IF NOT EXISTS Users
(
  username    VARCHAR(20)  NOT NULL,
  id          INTEGER(10) UNIQUE NOT NULL AUTO_INCREMENT,
  password    VARCHAR(32)  NOT NULL,
  name        VARCHAR(50) NOT NULL,
  surname     VARCHAR(50) NOT NULL,
  email       VARCHAR(100)  NOT NULL,
  emailhash   VARCHAR(32) NOT NULL,
  PRIMARY KEY (username)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Blacklists
(
  owner       VARCHAR(20) NOT NULL,
  username    VARCHAR(50) NOT NULL,
  PRIMARY KEY (owner,username),
  FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE,
  FOREIGN KEY (owner) REFERENCES Users(username) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Calls
(
  id            INTEGER(12) NOT NULL  AUTO_INCREMENT,
  caller        VARCHAR(20) ,
  receiver      VARCHAR(20) ,
  duration      INTEGER(10) NOT NULL,
  startdate     VARCHAR(30)   NOT NULL,
  byteSent      INTEGER(10) NOT NULL,
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
  idList      INTEGER(9)  NOT NULL,
  username    VARCHAR(20) NOT NULL,
  PRIMARY KEY (idList,username),
  FOREIGN KEY (idList) REFERENCES ListNames(id) ON DELETE CASCADE,
  FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS OnlineUsers
 (
  ip          VARCHAR(40)  NOT NULL,
  username    VARCHAR(20),
  PRIMARY KEY (ip),
  FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE
) ENGINE=InnoDB;

ALTER TABLE ListNames AUTO_INCREMENT = 2;
