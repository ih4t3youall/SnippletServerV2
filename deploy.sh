#!/bin/bash
cd "c:/Users/ih4t3youall/workspace/SnippletServerV2/"
mvn clean install
cd "c:/Users/ih4t3youall/tomcat/apache-tomcat-8.5.35/bin/"
./shutdown.sh
#rm c:/Users/ih4t3youall/tomcat/apache-tomcat-8.5.35/logs/*
rm c:/Users/ih4t3youall/tomcat/apache-tomcat-8.5.35/webapps/SnippletServerV2*
cp c:/Users/ih4t3youall/workspace/SnippletServerV2/target/SnippletServerV2-1.0.0-BUILD-SNAPSHOT.war c:/Users/ih4t3youall/tomcat/apache-tomcat-8.5.35/webapps/SnippletServerV2.war
./startup.sh
sleep 5;
tail -f c:/Users/ih4t3youall/Desktop/nose.log


#apache-tomcat-8.5.35
#c:/Users/ih4t3youall/tomcat/apache-tomcat-8.5.35/
