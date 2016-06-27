#!/bin/bash

cd $(dirname "$0")

export ROOT=$(pwd)

CONTAINER=$ROOT/tomee/container
OPENEJB_CORE=$ROOT/tomee/container/openejb-core
TEST_FILE_DIR=$ROOT/tomee/container/openejb-core/src/test/java/org/apache/openejb/config/
TEST_FILE=$ROOT/ServiceClasspathReadCheckerTest.java

if [ ! -d $ROOT/tomee ]; then
    git clone https://github.com/apache/tomee.git
fi

cd $TEST_FILE_DIR

if [ ! -f ServiceClasspathReadCheckerTest.java ]; then
    ln -s $TEST_FILE ./
fi

## Build tomee/container first to get required dependences for testing
cd $CONTAINER
## quick build cmd: https://github.com/apache/tomee
mvn -Pquick -Dsurefire.useFile=false -DdisableXmlReport=true -DuniqueVersion=false -ff -Dassemble -DskipTests -DfailIfNoTests=false clean install

cd $OPENEJB_CORE

gtimeout 300 mvn -Dtest=**/ServiceClasspathReadCheckerTest.java test

echo "---------------------------------------------------------------------------------------------"
echo " R E P O R T"
echo "---------------------------------------------------------------------------------------------"
echo "Above test will stuck in infinite loop forever, thus I terminate it after it runs 5 minutes.
The reason of this infinite loop is caused by the defect on line 53 in SimpleJSonParser.java:
https://github.com/apache/tomee/blob/master/container/openejb-core/src/main/java/org/apache/openejb/util/SimpleJSonParser.java#L53
In this file, Line 35 correctly reads a character from the input stream and line 36 comments that the integer comparison must precede the cast.
However, lines 53 compares the character with -1, which will always fail.
The same defects also appear on line 70 and line 89 in the same file."

