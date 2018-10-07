package cz.bh.lisp

/**
 *
 * @version 2018-10-07
 * @author Patrik Harag
 */
class Main {

    static void main(String... args) {
        println "BH Lisp REPL"

        def repl = new ConsoleRepl()
        repl.start(new InputStreamReader(System.in), new OutputStreamWriter(System.out))
    }

}
