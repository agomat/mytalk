USE MyTalk;

INSERT INTO Users VALUES ("pinco","pallino","pinco","pallino","pinco.pallino@tomare.omo");
INSERT INTO Users VALUES ("alby","gay","alby","gay","alby.gay@tomare.omo");
INSERT INTO Users VALUES ("mazzu","coreano","mazzu","coreano","mazzu.coreano@tomare.omo");
INSERT INTO Users VALUES ("army","africano","army","africano","army.africano@tomare.omo");
INSERT INTO Users VALUES ("mike","maddafacca","mike","maddafacca","mike.maddafacca@tomare.omo");
INSERT INTO Users VALUES ("campa","pollo","campa","pollo","campa.pollo@tomare.omo");
INSERT INTO Users VALUES ("macs","cuea","macs","cuea","macs.cuea@tomare.omo");
INSERT INTO Users VALUES ("mattia","script","mattia","script","mattia.script@tomare.omo");

INSERT INTO OnlineUsers VALUES ("111.111.111.1","alby");
INSERT INTO OnlineUsers VALUES ("111.111.111.2","army");
INSERT INTO OnlineUsers VALUES ("111.111.111.3","mazzu");
INSERT INTO OnlineUsers (ip)VALUES ("111.111.111.4");


INSERT INTO Calls (caller,receiver,duration,startdate,byteSent,byteReceived)VALUES ("alby","army","010203","2013-03-15 13:13:13","1000","999");
INSERT INTO Calls (caller,receiver,duration,startdate,byteSent,byteReceived)VALUES ("army","alby","000030","2013-03-16 14:14:14","999","715");
INSERT INTO Calls (caller,receiver,duration,startdate,byteSent,byteReceived)VALUES ("mike","mazzu","041842","2013-03-17 15:15:15","1000","999");

INSERT INTO Blacklists VALUES ("army","alby");
INSERT INTO Blacklists VALUES ("mazzu","mike");

INSERT INTO ListNames(name,owner) VALUES ("Friend","alby");
INSERT INTO ListNames(name,owner) VALUES ("Friend","mazzu");

INSERT INTO UserLists VALUES (1,"mike");
INSERT INTO UserLists VALUES (2,"mike");



