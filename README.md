# ReadChecker ![](https://travis-ci.org/CharlesZ-Chen/ReadChecker.svg?branch=master)
A type system that enforces [CERT-FIO08-J rule](https://www.securecoding.cert.org/confluence/display/java/FIO08-J.+Distinguish+between+characters+or+bytes+read+from+a+stream+and+-1) based on [Checker Framework](http://types.cs.washington.edu/checker-framework/)

## Dependences

This project is developed based on [Checker Framework](http://types.cs.washington.edu/checker-framework/), to use this checker, below dependences are required:

- checker framework
- jsr308-langtools
- annotation-tools

## Build

First, to have a better file structure, you may want to creat a root directory called `jsr308`.

In `jsr308`, clone this project. In the clone, run `./setup.sh`. This script will download and build all neccessary dependences, followed by building Read Checker and running test suites of Read Checker.

It is suggested to further configure `JSR308` environment variable for your convenience:

- In your bash profile file, export `JSR308` as the absolute path of your `jsr308` directory:

  ```bash
  export JSR308=<the absolute path of your jsr308 dir in your machine>
  ```

This `JSR308` environment variable is required for using my version of [do-like-javac](https://github.com/CharlesZ-Chen/do-like-javac) to run Read Checker on a project with its build command, and it is also allow running Read Checker with a more concise command.


## Running Read Checker on a single Java file




## Notes on useful materials
- [CERT rule FIO08-J](https://www.securecoding.cert.org/confluence/display/java/FIO08-J.+Distinguish+between+characters+or+bytes+read+from+a+stream+and+-1)
- [William Pugh, Defective Java Code: Turning WTF Code into a Learning Experience, JavaOne Conference, 2008.](http://www.oracle.com/technetwork/server-storage/ts-6589-159312.pdf)
- [WTF CodeSOD 20061102](http://thedailywtf.com/articles/Please_Supply_a_Test_Case)
