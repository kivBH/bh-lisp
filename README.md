# BH Lisp

[![Build Status](https://travis-ci.org/kivBH/bh-lisp.svg?branch=master)](https://travis-ci.org/kivBH/bh-lisp)

**BH Lisp** is a lisp-based interpreted programming language inspired by [Clojure](https://clojure.org/).
It runs on the Java virtual machine and is tightly integrated with Java.

## Features
* Dynamic type checking 
* Automatic conversion of numeric types (follows [Groovy's conversion scheme](http://docs.groovy-lang.org/latest/html/documentation/core-syntax.html#_math_operations))
* Java interoperability
* Poor performance

## Examples
```clojure
; factorial using recursion
(defn fact-1 [a]
    (if (> a 1)
        (* a (fact-1 (- a 1)))
        1))
>> cz.bh.lisp.lib.UserDefinedFunction[a]
(fact-1 0)
>> 1
(fact-1 3)
>> 6
(fact-1 15)
>> 1307674368000
(fact-1 "hello")
>> ERROR: cz.bh.lisp.LispException: Exception while evaluating function 'fact-1', on line: 9:
    cz.bh.lisp.LispException: Exception while evaluating function 'if', on line: 3:
    cz.bh.lisp.LispException: Exception while evaluating function '>', on line: 3:
    java.lang.IllegalArgumentException: Expected parameter type: class java.lang.Number, but was: class java.lang.String
```

```clojure
; factorial using functional programming
(defn fact-2 [n] (reduce * 1 (range 1 (inc n))))
>> cz.bh.lisp.lib.UserDefinedFunction[n]
(fact-2 0)
>> 1
(fact-2 3)
>> 6
(fact-2 15)
>> 1307674368000
(fact-2 "hello")
>> ERROR: cz.bh.lisp.LispException: Exception while evaluating function 'fact-2', on line: 16:
    cz.bh.lisp.LispException: Exception while evaluating function 'reduce', on line: 12:
    cz.bh.lisp.LispException: Exception while evaluating function 'range', on line: 12:
    cz.bh.lisp.LispException: Exception while evaluating function 'inc', on line: 12:
    java.lang.IllegalArgumentException: Expected parameter type: class java.lang.Number, but was: class java.lang.String
```

```clojure
; strings
(defn distinguish-number [n]
    (str n " is a " (if (>= n 0) "positive" "negative") " number"))
>> cz.bh.lisp.lib.UserDefinedFunction[n]
(distinguish-number 10)
>> 10 is a positive number
(distinguish-number -5)
>> -5 is a negative number
```

```clojure
; Java interoperability
(. substring "hello world" 6)
>> world
(* 2 (. parseInt @java.lang.Integer "21"))
>> 42
```

```clojure
; exceptions
(try
    (+ (/ (+ 5 1) 0) 5)
    (catch @java.lang.ArithmeticException e
        (do
            (println "An error occurred...")
            (. getMessage e)))
    (finally
        (println "Executing finally...")))
An error occurred...
Executing finally...
>> Division by zero
```

## Runtime Library
### Math
| Name | Description |
| --- | --- |
| `+` | \[n*\] Returns the sum of nums. (+) returns 0. |
| `-` | \[x ys*\] If no ys are supplied, returns the negation of x, else subtracts the ys from x and returns the result. |
| `*` | \[n*\] Returns the product of nums. (*) returns 1. |
| `/` | \[n d*\] If no denominators are supplied, returns 1/numerator, else returns numerator divided by all of the denominators. |
| `mod` | \[n d\] Returns the modulus of dividing numerator n by denominator d. |
| `=` | \[x y+\] Equality. Returns true if x equals y, false if not. Same as Java x.equals(y) except it also works for nil. |
| `!=` | \[x y+\] Same as (not (= x y...)) |
| `>` | \[n+\] Returns non-nil if nums are in monotonically decreasing order, otherwise false. |
| `>=` | \[n+\] Returns non-nil if nums are in monotonically non-increasing order, otherwise false. |
| `<` | \[n+\] Returns non-nil if nums are in monotonically increasing order, otherwise false. |
| `<=` | \[n+\] Returns non-nil if nums are in monotonically non-decreasing order, otherwise false. |
| `max` | \[n+\] Returns the greatest of the nums. |
| `min` | \[n+\] Returns the least of the nums. |
| `inc` | \[x\] Returns a number one greater than x. |
| `dec` | \[x\] Returns a number one less than x. |
### Collections
| Name | Description |
| --- | --- |
| `set` | \[coll\] Returns a set of the distinct elements of coll. |
| `hash-set` | \[i*\] Returns a new hash set with supplied keys. |
| `list` | \[i*\] Creates a new list containing the items. |
| `first` | \[coll\] Returns the first item in coll. |
| `last` | \[coll\] Returns the last item in coll. |
| `nth` | \[coll i\] Returns the value at the index. Throws an OutOfBoundsException. Works for List and String. |
| `count` | \[coll\] Returns the number of items in the collection. (count nil) returns 0. Also works on strings, and Java Collections and Maps. |
| `contains?` | \[coll k\] Returns true if the coll contains the lookup key k, otherwise returns false. |
| `conj` | \[xs coll\] Returns a new collection with the xs 'added'. (conj nil item) returns (item). The 'addition' may happen at different 'places' depending on the concrete type. |
| `assoc` | \[coll k v\] When applied to a map, returns a new map that contains the mapping of key to val. When applied to a list, returns a new list that contains value v at index k. |
| `str` | \[x*\] With no args, returns the empty string. With one arg x, returns x.toString(). (str nil) returns the empty string. With more than one arg, returns the concatenation of the str values of the args. |
| `range` | \[start end\] Returns a lazy list of nums from start (inclusive) to end (exclusive). |
| `reduce` | \[f init? coll\] Applies a function to an accumulator and each element in a sequence, building a final result. The f should be a function of 2 arguments. |
| `map` | \[f coll+\] Returns a lazy sequence consisting of the result of applying f to the set of first items of each coll, followed by applying f to the set of second items in each coll, until any one of the colls is exhausted. Any remaining items in other colls are ignored. |
### Conditionals
| Name | Description |
| --- | --- |
| `not` | \[b\] Performs logical not. |
| `and` | \[b+\] Evaluates logical and. |
| `or` | \[b+\] Evaluates logical or. |
| `if` | \[test true-branch false-branch\] Evaluates test. |
| `cond` | \[test true-branch\]+ Evaluates tests. Returns the value of first true-branch where test is successful or nil. |
### Bindings
| Name | Description |
| --- | --- |
| `let` | \[\[binding*\] exprs*\] (binding => name value) Evaluates the exprs in a lexical context given by binding and returns the value of the last. If no expressions are supplied, returns nil. |
| `def` | \[binding+\] (binding => name value) Creates a global variable given by binding and returns the value of the last. |
| `defn` | \[name \[params*\] body\] Creates function with given name. Returns function. |
| `fn` | \[\[params*\] body\] Creates anonymous function and returns it. |
### Meta
| Name | Description |
| --- | --- |
| `doc` | Returns documentation as string if available, nil otherwise. |
| `exit` | \[return-code\] Terminates the currently running program. |
| `class` | \[x\] Returns the Class of x. |
| `instance?` | \[c x\] Evaluates x and tests if it is an instance of the class c. Returns true or false |
| `new` | \[class par*\] Creates Java instance. |
| `.` | \[method-name instance par*\] Evaluates Java method. |
| `print` | \[o*\] Prints the object(s) to the standard output stream. |
| `println` | \[o*\] Same as print followed by (newline) |
### Flow
| Name | Description |
| --- | --- |
| `do` | \[expr*\] Evaluates the expressions in order and returns the value of the last. If no expressions are supplied, returns nil. |
### Exceptions
| Name | Description |
| --- | --- |
| `try` | \[body catch-clause* finally-clause?\] catch-clause => ('catch' class name body) finally-clause => ('finally' body) If an exception occurs in body and catch-clauses are provided, the first for which the thrown exception is an instance of the class is considered a matching catch-clause. Exception is stored as name.If there is no matching catch-clause, the exception propagates out of the function. If finally-clause is provided, it will be executed finally. |
| `throw` | \[message\] or \[instance\] Throws LispException with given message or instance of Throwable. |

## Build & Run
Preconditions: Java 8+ (JDK), Gradle

```
gradle build
java -jar build/libs/bh-lisp-<version>.jar
```
