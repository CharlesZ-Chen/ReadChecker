# Instruction of demo the read bug in tomee SimpleJSonParser.java:

## prerequisites

This demo depends on two tools:

- git
- maven

To running this demo, you need to have above two tools on your machine first.

## Demo

in this directory, run:

```
./tomee-SimpleJSonParser-bug.sh
```

This script will download the current trunk version of `apache tomee`, insert our test file to `tomee/container/openejb-core/src/test/java/org/apache/openejb/config/` (This is a test directory contains some test files for tomee/**/openejb-core), and running our testcase by maven.

## Result

Running `./tomee-SimpleJSonParser-bug.sh` will cause the shell terminal stuck in an infinite loop when junit running our test case. This is caused by the `cast before check` bug in line 50 of file `tomee/container/openejb-core/src/main/java/org/apache/openejb/util/SimpleJSonParser.java`.

Our ReadChecker also detect other two similar bugs in SimpleParser.java (line 67, line 88).
