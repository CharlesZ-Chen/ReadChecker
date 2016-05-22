#!/bin/bash

# this script depends on the checker branch of Charles's fork of dljc:
# https://github.com/CharlesZ-Chen/do-like-javac.git

# TODO: this is not a good way to solve the problem, but currently since
# dljc override the javac classpath in its cmd, thus I have to add a little code
# of joining the sys CLASSPATH to the --classpath in dljc's javac cmd.

ROOT=$(cd $(dirname "$0") && pwd)
DLJC=$ROOT/../do-like-javac

export CLASSPATH=$ROOT/bin:$ROOT/build-deps

#parsing build command of the target program
build_cmd=$1
shift
while [ $# -gt 0 ]
do
    build_cmd="$build_cmd $1"
    shift
done

python $DLJC/dljc -t checker --checker read.ReadChecker -- $build_cmd
