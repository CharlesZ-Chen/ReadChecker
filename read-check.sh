#!/bin/bash

WORKING_DIR=$(pwd)
ROOT=$(cd $(dirname "$0")/.. && pwd)
JAVAC=$ROOT/checker-framework/checker/bin-devel/javac

READ_CHECKER=$ROOT/ReadChecker

cd $WORKING_DIR

$JAVAC -processor read.ReadChecker -cp $READ_CHECKER/bin:$READ_CHECKER/build-deps $1
