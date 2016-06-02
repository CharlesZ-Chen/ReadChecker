#!/bin/bash

cd $(dirname "$0")

export ROOT=$(pwd)

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

cd $OPENEJB_CORE

mvn -Dtest=**/ServiceClasspathReadCheckerTest.java test
