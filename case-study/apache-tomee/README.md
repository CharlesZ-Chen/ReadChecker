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

Running `./tomee-SimpleJSonParser-bug.sh` will cause the shell terminal stuck in an infinite loop when `junit` running our test case. This is caused by the `cast before check` bug in line 50 of file `tomee/container/openejb-core/src/main/java/org/apache/openejb/util/SimpleJSonParser.java`.

Our ReadChecker also detect other two similar bugs in SimpleParser.java (line 67, line 88).

## Running Read Checker on `apache tomee`

First download the trunk version of [apache tomee](https://github.com/apache/tomee). (or if you already downloaded it by above demo script, do a `mvn clean` first in `tomee` directory.)

If you only want to see the `SimpleJSonParser` bug find by Read Checker, for the reason of saving time, you could running Read Checker only on `openejb-core` component of `apache tomee`:
- In `tomee/container/openejb-core` run:
  ```bash
  $JSR308/ReadChecker/run-dljc.sh mvn -Pquick -Dsurefire.useFile=false -DdisableXmlReport=true -DuniqueVersion=false -ff -Dassemble -DskipTests -DfailIfNoTests=false install
  ```  
  The running result will start to print out after around 5 minutes.

If you want to check the whole `apache tomee` project by Read Checker:

- In the `apache tomee` clone, run `run-dljc.sh` with tomee's quick build command (for saving time):

  ```bash
  $JSR308/ReadChecker/run-dljc.sh mvn -Pquick -Dsurefire.useFile=false -DdisableXmlReport=true -DuniqueVersion=false -ff -Dassemble -DskipTests -DfailIfNoTests=false install
  ```

  The running result will start to print on the screen after around 15~20 minutes (tomee is a really big project...).

