#!/bin/bash

export JVM_OPTS=-XstartOnFirstThread
export MAIN_CLASS=net.eclipseforum.id3tagman.ID3TagManGUI
export LIB_PATH=lib
export CLASSPATH=.

for i in $LIB_PATH/*.jar
do 
echo $i
export CLASSPATH=$CLASSPATH:$i 
done

echo CLASSPATH=$CLASSPATH
echo JAVA_HOME=$JAVA_HOME

java -cp $CLASSPATH $JVM_OPTS $MAIN_CLASS
