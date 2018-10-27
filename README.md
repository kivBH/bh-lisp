# BH Lisp

[![Build Status](https://travis-ci.org/kivBH/bh-lisp.svg?branch=master)](https://travis-ci.org/kivBH/bh-lisp)

**BH Lisp** is a lisp-based interpreted programming language inspired by [Clojure](https://clojure.org/).
It runs on the Java virtual machine and is tightly integrated with Java.

## Features
* Dynamic type checking 
* Automatic conversion of numeric types (follows [Groovy's conversion scheme](http://docs.groovy-lang.org/latest/html/documentation/core-syntax.html#_math_operations))

## Build & Run
Preconditions: Java 8+ (JDK), Gradle

```
gradle build
java -jar build/libs/bh-lisp-<version>.jar
```
