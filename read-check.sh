#!/bin/bash

WORKING_DIR=$(pwd)
ROOT=$(cd $(dirname "$0")/.. && pwd)
JAVAC=$ROOT/checker-framework/checker/bin-devel/javac

READ_CHECKER=$ROOT/ReadChecker

cd $WORKING_DIR

java_files=$1
shift
while [ $# -gt 0 ]
do
    java_files="$java_files $1"
    shift
done

$JAVAC -processor read.ReadChecker -cp $READ_CHECKER/bin:$READ_CHECKER/lib $java_files
