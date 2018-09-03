all:
	javac assn3checker.java
	javac Myset.java
	javac MobilePhone.java
	javac MobilePhoneSet.java
	javac Exchange.java
	javac ExchangeList.java
	javac RoutingMapTree.java
	java assn3checker

clean:
	find . -name "*.class" -type f -delete
	clear
