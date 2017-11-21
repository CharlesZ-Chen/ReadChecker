#!/bin/bash

# this script depends on the checker branch of Charles's fork of dljc:
# https://github.com/CharlesZ-Chen/do-like-javac.git
# (this is not a good way to solve the problem, but currently since
# dljc override the javac classpath in its cmd, thus I have to add a little code
# of joining the sys CLASSPATH to the --classpath in dljc's javac cmd.)

CUR_DIR=$(pwd)
ROOT=$(cd $(dirname "$0")/.. && pwd)
READ_CHECKER=$ROOT/ReadChecker
DLJC=$ROOT/do-like-javac

export CLASSPATH=$READ_CHECKER/bin:$READ_CHECKER/lib

if [ ! -d $DLJC ] ; then
    cd $ROOT
    git clone https://github.com/CharlesZ-Chen/do-like-javac.git --branch checker
fi

#parsing build command of the target program
build_cmd=$1
shift
while [ $# -gt 0 ]
do
    build_cmd="$build_cmd $1"
    shift
done

cd $CUR_DIR

# debug_onlyCompile="--onlyCompileBytecodeBase true"
debug_cmd="python $DLJC/dljc -t testminimizer --debuggedTool check --expectReturnCode 1  --checker org.checkerframework.checker.nullness.NullnessChecker --expectOutputRegex 'error: cannot find symbol' -- $build_cmd "

infer_cmd="python $DLJC/dljc -t checker --checker read.ReadChecker -- $build_cmd "

running_cmd=$infer_cmd

echo "============ Important variables ============="
echo "JSR308: $JSR308"
echo "CLASSPATH: $CLASSPATH"
echo "build cmd: $build_cmd"
echo "running cmd: $running_cmd"
echo "============================================="

eval "$running_cmd"

echo "---- Reminder: do not forget to clean up the project! ----"


