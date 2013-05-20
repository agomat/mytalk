JC = javac -d bin -cp src -encoding utf8
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

CLASSES = src/info/mytalk/core/Server.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	`find . -name "*.class" -exec rm {} \;`