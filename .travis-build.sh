#!/bin/bash
ROOT=$TRAVIS_BUILD_DIR/..

# Fail the whole script if any command fails
set -e

export SHELLOPTS

. ./.travis-build-without-test.sh

ant -f $TRAVIS_BUILD_DIR/build.xml test
